package com.brandon.moneybox.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class KategoriResponse {
    private String nama;
    private Boolean jenis;
}
