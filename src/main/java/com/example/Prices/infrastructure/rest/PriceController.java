package com.example.Prices.infrastructure.rest;

import com.example.Prices.application.GetPriceUseCase;
import com.example.Prices.domain.entity.Price;
import com.example.Prices.infrastructure.rest.exceptionhandler.PriceNotFoundException;
import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import org.openapitools.api.PriceApi;
import org.openapitools.model.PriceResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.time.OffsetDateTime;
import java.util.Objects;

@RestController
public class PriceController implements PriceApi {
    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceMapper priceMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceMapper = priceMapper;
    }

    @Override
    public ResponseEntity<PriceResponse> getPrice(Long brandId, Long productId, OffsetDateTime appDate) {
        Price price = getPriceUseCase.getPrice(brandId, productId, appDate.toLocalDateTime());

        if (Objects.isNull(price)) {
            throw new PriceNotFoundException("No price found for the input data");
        }

        PriceResponse priceResponse = priceMapper.toPriceResponse(price);

        return ResponseEntity.ok(priceResponse);
    }
}
