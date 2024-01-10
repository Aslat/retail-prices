package com.example.Prices.infrastructure.rest;

import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.request.PriceRequest;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.usecase.GetPriceUseCase;
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
        PriceRequest priceRequest = PriceRequest.builder()
                .productId(1L)
                .brandId(1L)
                .appDate(LocalDateTime.now())
                .build();
        Price price = Price.builder().build();
        PriceResponse priceResponse = PriceResponse.builder().build();

        given(getPriceUseCase.getPrice(priceRequest.getBrandId(), priceRequest.getProductId(), priceRequest.getAppDate()))
                .willReturn(price);
        given(priceMapper.toPriceResponse(price)).willReturn(priceResponse);

        //WHEN
        ResponseEntity<PriceResponse> response = priceController.getPrice(priceRequest);

        //THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());

        then(getPriceUseCase).should().getPrice(priceRequest.getBrandId(), priceRequest.getProductId(), priceRequest.getAppDate());
        then(priceMapper).should().toPriceResponse(price);
    }

}