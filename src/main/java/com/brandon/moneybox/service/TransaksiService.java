package com.brandon.moneybox.service;

import com.brandon.moneybox.entity.Transaksi;
import com.brandon.moneybox.model.*;
import com.brandon.moneybox.repository.TransaksiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransaksiService {

    @Autowired
    private TransaksiRepository transaksiRepository;

    public Response<String> createTransaksi(Transaksi transaksi) {
        try {
            if (transaksi.getUsername() == null){
                return Response.<String>builder().error("Data not be blank").build();
            }
            Optional<Transaksi> existingTransaksi = transaksiRepository.findById(transaksi.getId());
            if (existingTransaksi.isPresent()) {
                return Response.<String>builder().error("Transaksi already exists").build();
            }
            transaksiRepository.save(transaksi);
            return Response.<String>builder().data("Berhasil Membuat Transaksi").build();
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<String> updateTransaksi(String token, UpdateTransaksiRequest transaksiRequest, Long id) {
        try {
                Optional<Transaksi> optionalTransaksi = transaksiRepository.findById(id);
                if (optionalTransaksi.isPresent()) {
                    Transaksi transaksi = optionalTransaksi.get();

                    if (transaksiRequest.getKeterangan() != null) {
                        transaksi.setKeterangan(transaksiRequest.getKeterangan());
                    }
                    if (transaksiRequest.getNominal() != null) {
                        transaksi.setNominal(transaksiRequest.getNominal());
                    }
                    if (transaksiRequest.getTanggal() != null) {
                        transaksi.setTanggal(transaksiRequest.getTanggal());
                    }
                    transaksiRepository.save(transaksi);
                    return Response.<String>builder().data("Transaksi updated successfully").build();
                } else {
                    return Response.<String>builder().error("Transaksi not found").build();
                }
        } catch (Exception e) {
            return Response.<String>builder().error(e.getMessage()).build();
        }
    }

    public Response<TransaksiResponse> getTransaksiById(Long nama) {
        try {
            Optional<Transaksi> optionalTransaksi = transaksiRepository.findById(nama);

            if (optionalTransaksi.isPresent()) {
                Transaksi transaksi = optionalTransaksi.get();
                TransaksiResponse transaksiInfo = TransaksiResponse.builder()
                        .id(transaksi.getId())
                        .keterangan(transaksi.getKeterangan())
                        .nominal(transaksi.getNominal())
                        .tanggal(transaksi.getTanggal())
                        .kategori(transaksi.getKategori())
                        .username(transaksi.getUsername()).build();

                return Response.<TransaksiResponse>builder().data(transaksiInfo).build();
            } else {
                return Response.<TransaksiResponse>builder().error("Transaksi not found").build();
            }
        } catch (Exception e) {
            return Response.<TransaksiResponse>builder().error(e.getMessage()).build();
        }
    }

    public Response<List<TransaksiResponse>> getAllTransaksi() {
        try {
            List<Transaksi> allTransaksi = transaksiRepository.findAll();

            List<TransaksiResponse> transaksiList = allTransaksi.stream()
                    .map(transaksi -> TransaksiResponse.builder()
                            .id(transaksi.getId())
                            .keterangan(transaksi.getKeterangan())
                            .nominal(transaksi.getNominal())
                            .tanggal(transaksi.getTanggal())
                            .kategori(transaksi.getKategori())
                            .username(transaksi.getUsername())
                            .build())
                    .collect(Collectors.toList());

            return Response.<List<TransaksiResponse>>builder().data(transaksiList).build();
        } catch (Exception e) {
            return Response.<List<TransaksiResponse>>builder().error(e.getMessage()).build();
        }
    }

    public Response<String> deleteTransaksi(Long nama) {
        try {
            if (transaksiRepository.existsById(nama)) {
                transaksiRepository.deleteById(nama);
                return Response.<String>builder().data("OK").build();
            } else {
                return Response.<String>builder().error("Transaksi not found").build();
            }
        } catch (Exception e) {
            return Response.<String>builder().error("Failed to delete Transaksi: " + e.getMessage()).build();
        }
    }


}
