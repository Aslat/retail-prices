package com.example.Prices.application.mapper;

import com.example.Prices.application.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PriceMapperTest {

    PriceMapper priceMapper;

    @BeforeEach
    void setUp(){
        priceMapper = new PriceMapper();
    }

    @Test
    public void toPriceResponseWhenNull(){
        //GIVEN

        //WHEN
        PriceResponse priceResponse = priceMapper.toPriceResponse(null);

        //THEN
        Assertions.assertNull(priceResponse);
    }

    @Test
    public void toPriceResponseWhenSimpleCase(){
        //GIVEN
        Price price = Price.builder().build();
        price.setProductId(1L);


        //WHEN
        PriceResponse result = priceMapper.toPriceResponse(price);

        //THEN
        Assertions.assertEquals(1L, result.getProductId());
    }
}