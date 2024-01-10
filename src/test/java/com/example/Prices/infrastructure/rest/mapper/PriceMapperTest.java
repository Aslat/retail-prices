package com.example.Prices.infrastructure.rest.mapper;

import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

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
        Price price = Price.builder()
                .productId(1L)
                .priority(3)
                .priceList(2L)
                .price(12.3)
                .brandId(21L)
                .startDate(LocalDateTime.now().minusDays(1))
                .endDate(LocalDateTime.now().plusDays(1))
                .build();

        //WHEN
        PriceResponse result = priceMapper.toPriceResponse(price);

        //THEN
        Assertions.assertEquals(price.getProductId(), result.getProductId());
        Assertions.assertEquals(price.getPrice(), result.getPrice());
        Assertions.assertEquals(price.getPriceList(), result.getPriceList());
        Assertions.assertEquals(price.getBrandId(), result.getBrandId());
        Assertions.assertEquals(price.getStartDate(), result.getStartDate());
        Assertions.assertEquals(price.getEndDate(), result.getEndDate());
    }
}