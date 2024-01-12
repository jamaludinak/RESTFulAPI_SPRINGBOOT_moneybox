package com.brandon.moneybox.controller;

import com.brandon.moneybox.entity.Transaksi;
import com.brandon.moneybox.model.*;
import com.brandon.moneybox.service.TransaksiService;
import com.brandon.moneybox.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transaksi")
public class TransaksiController {

    @Autowired
    private UserService userService;

    @Autowired
    private TransaksiService transaksiService;

    @PostMapping
    public Response<String> createTransaksi(@RequestHeader("X-API-TOKEN") String token, @RequestBody Transaksi transaksi) {
        String username = userService.validateToken(token);
        if (username != null) {
            transaksi.setUsername(username);
            return transaksiService.createTransaksi(transaksi);
        } else {
            return Response.<String>builder().error("Unauthorized").build();
        }
    }

    @PatchMapping("/{id}")
    public Response<String> updateTransaksi(@PathVariable Long id, @RequestHeader("X-API-TOKEN") String token, @RequestBody UpdateTransaksiRequest updateTransaksiRequest){
        String username = userService.validateToken(token);
        if (username != null) {
            return transaksiService.updateTransaksi(token, updateTransaksiRequest, id);
        } else {
            return Response.<String>builder().error("Unauthorized").build();
        }
    }

    @GetMapping("/{id}")
    public Response<TransaksiResponse> getTransaksiById(@PathVariable Long id, @RequestHeader("X-API-TOKEN") String token ) {
        String username = userService.validateToken(token);
        if (username != null) {
            return transaksiService.getTransaksiById(id);
        } else {
            return Response.<TransaksiResponse>builder().error("Unauthorized").build();
        }
    }

    @GetMapping
    public Response<List<TransaksiResponse>> getAllTransaksi( @RequestHeader("X-API-TOKEN") String token ) {
        String username = userService.validateToken(token);
        if (username != null) {
            return transaksiService.getAllTransaksi();
        } else {
            return Response.<List<TransaksiResponse>>builder().error("Unauthorized").build();
        }
    }

    @DeleteMapping("/{id}")
    public Response<String> deleteTransaksi(@PathVariable Long id, @RequestHeader("X-API-TOKEN") String token){
        String username = userService.validateToken(token);
        if (username != null) {
            return transaksiService.deleteTransaksi(id);
        } else {
            return Response.<String>builder().error("Unauthorized").build();
        }
    }

}
