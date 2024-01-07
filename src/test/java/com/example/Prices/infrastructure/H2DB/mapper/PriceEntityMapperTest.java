package com.example.Prices.infrastructure.H2DB.mapper;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.infrastructure.H2DB.BrandEntity;
import com.example.Prices.infrastructure.H2DB.PriceEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

@SpringBootTest
class PriceEntityMapperTest {

    PriceEntityMapper priceEntityMapper;

    @BeforeEach
    void setUp(){
        priceEntityMapper = new PriceEntityMapper();
    }

    @Test
    public void toPriceListWhenNullOrEmpty(){
        //WHEN
        List<Price> priceList = priceEntityMapper.toPriceList(null);

        //THEN
        Assertions.assertNull(priceList);

        //WHEN
        priceList = priceEntityMapper.toPriceList(Collections.emptyList());

        //THEN
        Assertions.assertEquals(Collections.emptyList(), priceList);
    }

    @Test
    public void toPriceListWhenSimpleCase(){
        //GIVEN
        BrandEntity brand = new BrandEntity();
        brand.setId(20L);

        PriceEntity priceEntity = new PriceEntity();
        priceEntity.setBrand(brand);
        priceEntity.setProductId(10L);
        List<PriceEntity> priceEntityList = List.of(priceEntity);


        //WHEN
        List<Price> result = priceEntityMapper.toPriceList(priceEntityList);

        //THEN
        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(priceEntity.getProductId(), result.get(0).getProductId());
        Assertions.assertEquals(brand.getId(), result.get(0).getBrandId());
    }
}