package com.brandon.moneybox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransaksiResponse {

    private String username;

    private String kategori;

    private String keterangan;

    private Long nominal;

    private String tanggal;

    private Long id;
}
