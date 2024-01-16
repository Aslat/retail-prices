package com.example.Prices.application;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import com.example.Prices.domain.exceptions.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
class PriceServiceTest {

    @InjectMocks
    PriceService priceService;

    @Mock
    PriceRepository priceRepository;

    public static Stream<Arguments> provideNullParams() {
        return Stream.of(
                Arguments.of(null, 1L, LocalDateTime.now()),
                Arguments.of(1L, null, LocalDateTime.now()),
                Arguments.of(1L, 1L, null)
        );
    }

    @ParameterizedTest
    @MethodSource("provideNullParams")
    public void getPriceWhenReceivingNull(Long brandId, Long productId, LocalDateTime appDate){

        //WHEN
        assertThrows(PriceNotFoundException.class, () -> priceService.getPrice(brandId, productId, appDate));

        //THEN
        then(priceRepository).should().findByBrandProductAndDate(brandId, productId, appDate);
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
        assertThrows(PriceNotFoundException.class, () -> priceService.getPrice(brandId, productId, appDate));

        //THEN
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