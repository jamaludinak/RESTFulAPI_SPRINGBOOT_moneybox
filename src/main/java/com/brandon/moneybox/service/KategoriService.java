package com.brandon.moneybox.service;

import com.brandon.moneybox.entity.Kategori;
import com.brandon.moneybox.model.*;
import com.brandon.moneybox.repository.KategoriRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class KategoriService {

    @Autowired
    private KategoriRepository kategoriRepository;

    public Response<String> createKategori(Kategori kategori){
        try {
            if (kategori.getNama() == null || kategori.getNama().isEmpty()){
                return Response.<String>builder().error("Data not be blank").build();
            }
            Optional<Kategori> existingKategori = kategoriRepository.findById(kategori.getNama());
            if (existingKategori.isPresent()) {
                return Response.<String>builder().error("kategori already exists").build();
            }

            kategoriRepository.save(kategori);
            return Response.<String>builder().data("Berhasil Membuat Kategori").build();
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<KategoriResponse> getKategoriById(String nama) {
        try {
            Optional<Kategori> optionalKategori = kategoriRepository.findById(nama);

            if (optionalKategori.isPresent()) {
                Kategori kategori = optionalKategori.get();
                KategoriResponse kategoriInfo = KategoriResponse.builder()
                        .nama(kategori.getNama())
                        .jenis(kategori.getJenis())
                        .build();
                return Response.<KategoriResponse>builder().data(kategoriInfo).build();
            } else {
                return Response.<KategoriResponse>builder().error("Kategori not found").build();
            }
        } catch (Exception e) {
            return Response.<KategoriResponse>builder().error(e.getMessage()).build();
        }
    }

    public Response<String> deleteKategori(String nama) {
        try {
            if (kategoriRepository.existsById(nama)) {
                kategoriRepository.deleteById(nama);
                return Response.<String>builder().data("OK").build();
            } else {
                return Response.<String>builder().error("Kategori not found").build();
            }
        } catch (Exception e) {
            return Response.<String>builder().error("Failed to delete category: " + e.getMessage()).build();
        }
    }

    public Response<List<KategoriResponse>> getAllKategori() {
        try {
            List<Kategori> allKategori = kategoriRepository.findAll();

            List<KategoriResponse> kategoriList = allKategori.stream()
                    .map(kategori -> KategoriResponse.builder()
                            .nama(kategori.getNama())
                            .jenis(kategori.getJenis())
                            .build())
                    .collect(Collectors.toList());
            return Response.<List<KategoriResponse>>builder().data(kategoriList).build();
        } catch (Exception e) {
            return Response.<List<KategoriResponse>>builder().error(e.getMessage()).build();
        }
    }

}
