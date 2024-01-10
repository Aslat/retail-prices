package com.example.Prices.infrastructure.rest;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.application.GetPriceUseCase;
import com.example.Prices.infrastructure.rest.exceptionhandler.PriceNotFoundException;
import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Objects;

@Controller
public class PriceController {
    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceMapper priceMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceMapper = priceMapper;
    }

    @GetMapping(value = "/price")
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam(value = "brandId") Long brandId,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "appDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appDate
    ) {
        Price price = getPriceUseCase.getPrice(
                brandId, productId, appDate);


        if (Objects.isNull(price)) {
            throw new PriceNotFoundException("No price found for the input data");
        }

        PriceResponse priceResponse = priceMapper.toPriceResponse(price);

        return ResponseEntity.ok(priceResponse);
    }
}
