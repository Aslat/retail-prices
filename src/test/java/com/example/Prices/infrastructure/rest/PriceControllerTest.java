package com.example.Prices.infrastructure.rest;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.usecase.GetPriceUseCase;
import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
class PriceControllerTest {

    @InjectMocks
    PriceController priceController;

    @Mock
    GetPriceUseCase getPriceUseCase;
    @Mock
    PriceMapper priceMapper;

    @Test
    public void getPrice(){
        //GIVEN
        final Long brandId = 1L;
        final Long productId = 1L;
        final LocalDateTime appDate = LocalDateTime.now();

        Price price = Price.builder().build();
        PriceResponse priceResponse = PriceResponse.builder().build();

        given(getPriceUseCase.getPrice(brandId, productId, appDate))
                .willReturn(price);
        given(priceMapper.toPriceResponse(price)).willReturn(priceResponse);

        //WHEN
        ResponseEntity<PriceResponse> response = priceController.getPrice(brandId, productId , appDate);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());

        then(getPriceUseCase).should().getPrice(brandId, productId, appDate);
        then(priceMapper).should().toPriceResponse(price);
    }

}