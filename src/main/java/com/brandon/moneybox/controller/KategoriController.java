package com.brandon.moneybox.controller;

import com.brandon.moneybox.entity.Kategori;
import com.brandon.moneybox.model.KategoriResponse;
import com.brandon.moneybox.model.Response;
import com.brandon.moneybox.model.TransaksiResponse;
import com.brandon.moneybox.service.KategoriService;
import com.brandon.moneybox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/kategori")
public class KategoriController {
    @Autowired
    private UserService userService;

    @Autowired
    private KategoriService kategoriService;

    @GetMapping("/{id}")
    public Response<KategoriResponse> getKategoriById(@PathVariable String id, @RequestHeader("X-API-TOKEN") String token ) {
        String username = userService.validateToken(token);
        if (username != null) {
            return kategoriService.getKategoriById(id);
        } else {
            return Response.<KategoriResponse>builder().error("Unauthorized").build();
        }
    }

    @PostMapping
    public Response<String> createKategori(@RequestHeader("X-API-TOKEN") String token, @RequestBody Kategori kategori) {
        String username = userService.validateToken(token);
        if (username != null) {
            return kategoriService.createKategori(kategori);
        } else {
            return Response.<String>builder().error("Unauthorized").build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> deleteKategori(@PathVariable String id, @RequestHeader("X-API-TOKEN") String token) {
        String username = userService.validateToken(token);
        if (username != null) {
            // Lanjutkan dengan menghapus kategori
            return ResponseEntity.ok(kategoriService.deleteKategori(id));
        } else {
            return ResponseEntity.ok(Response.<String>builder().error("Unauthorized").build());
        }
    }

    @GetMapping
    public Response<List<KategoriResponse>> getAllKategori(@RequestHeader("X-API-TOKEN") String token ) {
        String username = userService.validateToken(token);
        if (username != null) {
            return kategoriService.getAllKategori();
        } else {
            return Response.<List<KategoriResponse>>builder().error("Unauthorized").build();
        }
    }

}