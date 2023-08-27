package com.lazybeast.rediscache.controller;

import com.lazybeast.rediscache.dto.ProductDto;
import com.lazybeast.rediscache.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BasicController {

    @Autowired
    private ProductService productService;

    @GetMapping("/getAll")
    public List<ProductDto> allProducts(){
        return productService.getAll();
    }

    @GetMapping("/getById/{id}")
    public ProductDto getProductById(@PathVariable long id){
        return productService.getById(id);
    }

    @GetMapping("/modifyProductPrice/{id}/{price}")
    public ResponseEntity<?> modifyPrice(@PathVariable("id") long id, @PathVariable("price") Float price){
        productService.modifyProductPrice(id, price);
        return ResponseEntity.ok("Modified");
    }

    @PostMapping("/addProduct")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto){
        productService.addProduct(productDto);
        return ResponseEntity.ok("Product Added");
    }
}
