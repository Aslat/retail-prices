package com.example.Prices.infrastructure.rest;

import com.example.Prices.application.GetPriceUseCase;
import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.exceptions.PriceNotFoundException;
import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.openapitools.model.PriceResponse;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.OffsetDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        final OffsetDateTime appDate = OffsetDateTime.now();

        Price price = Price.builder().build();
        PriceResponse priceResponse = new PriceResponse();

        given(getPriceUseCase.getPrice(brandId, productId, appDate.toLocalDateTime()))
                .willReturn(price);
        given(priceMapper.toPriceResponse(price)).willReturn(priceResponse);

        //WHEN
        ResponseEntity<PriceResponse> response = priceController.getPrice(brandId, productId , appDate);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());

        then(getPriceUseCase).should().getPrice(brandId, productId, appDate.toLocalDateTime());
        then(priceMapper).should().toPriceResponse(price);
    }

    @Test
    public void getPriceReturnNotFound(){
        //GIVEN
        final Long brandId = 1L;
        final Long productId = 1L;
        final OffsetDateTime appDate = OffsetDateTime.now();

        given(getPriceUseCase.getPrice(brandId, productId, appDate.toLocalDateTime())).willThrow(PriceNotFoundException.class);

        //WHEN
        assertThrows(PriceNotFoundException.class, () -> priceController.getPrice(brandId, productId , appDate));

        //THEN
        then(getPriceUseCase).should().getPrice(brandId, productId, appDate.toLocalDateTime());
        then(priceMapper).shouldHaveNoInteractions();
    }
}