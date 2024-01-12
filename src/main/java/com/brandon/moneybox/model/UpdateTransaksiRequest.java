package com.brandon.moneybox.model;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTransaksiRequest {

    @Size(max = 100)
    private String keterangan;

    private Long nominal;

    private String tanggal;

}
