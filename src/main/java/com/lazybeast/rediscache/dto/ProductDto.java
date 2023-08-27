package com.lazybeast.rediscache.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class ProductDto implements Serializable {
    private long id;
    private String name;
    private Float price;
}
