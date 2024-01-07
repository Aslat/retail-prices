package com.example.Prices.domain.service;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
class PriceServiceImplTest {

    @InjectMocks
    PriceServiceImpl priceService;

    @Mock
    PriceRepository priceRepository;

    @Test
    public void getPriceWhenReceivingNull(){
        //WHEN
        Price result = priceService.getPrice(null, 1L, LocalDateTime.now());

        //THEN
        assertNull(result);

        //WHEN
        result = priceService.getPrice(1L, null, LocalDateTime.now());

        //THEN
        assertNull(result);

        //WHEN
        result = priceService.getPrice(1L, 1L, null);

        //THEN
        assertNull(result);
        then(priceRepository).shouldHaveNoInteractions();
    }

    @Test
    public void getPriceFromEmptyResult(){
        //GIVEN
        List<Price> priceList = Collections.emptyList();

        final Long brandId = 1L;
        final Long productId = 1L;
        final LocalDateTime appDate = LocalDateTime.now();

        given(priceRepository.findByBrandProductAndDate(brandId, productId, appDate))
                .willReturn(priceList);

        //WHEN
        Price result = priceService.getPrice(brandId, productId, appDate);

        //THEN
        assertNull(result);

        then(priceRepository).should().findByBrandProductAndDate(brandId, productId, appDate);
    }

    @Test
    public void getPriceFromAmbiguousResult(){
        //GIVEN
        List<Price> priceList = List.of(
            Price.builder().price(11.1).priority(1).build(),
            Price.builder().price(22.2).priority(2).build(),
            Price.builder().price(33.3).priority(2).build()
        );

        final Long brandId = 1L;
        final Long productId = 1L;
        final LocalDateTime appDate = LocalDateTime.now();

        given(priceRepository.findByBrandProductAndDate(brandId, productId, appDate))
                .willReturn(priceList);

        //WHEN
        Price result = priceService.getPrice(brandId, productId, appDate);

        //THEN
        assertEquals(22.2, result.getPrice());

        then(priceRepository).should().findByBrandProductAndDate(brandId, productId, appDate);
    }
}