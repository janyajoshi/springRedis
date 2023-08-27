package com.lazybeast.rediscache.service;


import com.lazybeast.rediscache.dto.ProductDto;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ProductService {

    private List<ProductDto> products = new ArrayList<>();

    @PostConstruct
    private void initializeProducts(){
        for (int i=0; i<3; i++){
            products.add(new ProductDto((long) i+1, new StringBuilder().append("Product ").append(i+1).toString(), (float) ((i+1)*100)));
        }
    }

    @Cacheable(value = "allProducts")
    public List<ProductDto> getAll(){
        log.info("Getting All Products");
        return products;
    }

//    if required to have more than 1 key variable, add the parameter and join them by a seperator
//    eg-> @Cacheable(cacheNames = "product", key = "#param1 + '-' + #param2.toString()")
    @Cacheable(value = "product", key = "#id")
    public ProductDto getById(Long id){
        log.info(new StringBuilder().append("Getting By id: ").append(id).toString());
        Optional<ProductDto> productDtoOptional = products.stream().filter(p -> p.getId() == id).findFirst();
        return productDtoOptional.get();
    }

    @CacheEvict(cacheNames = {"allProducts", "product"}, allEntries = true)
    public void addProduct(ProductDto productDto){
        products.add(productDto);
    }


//    if required to remove more than 1 key, create a helper function which takes key as input and uses same in CacheEvict
//    call this helper function with one key at a time,
    @CacheEvict(cacheNames = {"product"},  key = "#id")
    public void modifyProductPrice(long id, Float price){
        log.info(new StringBuilder().append("Modifying Product id: ").append(id).append(" new price: ").append(price).toString());
        products.stream().filter(p -> p.getId() == id).forEach(p -> p.setPrice(price));
    }
}
