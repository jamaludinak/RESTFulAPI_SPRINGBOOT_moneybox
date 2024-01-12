package com.brandon.moneybox.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "transaksi")
public class Transaksi {
    @Id
    private Long id;
    private String keterangan;
    private Long nominal;
    private String tanggal;
    private String username;
    private String kategori;

}
