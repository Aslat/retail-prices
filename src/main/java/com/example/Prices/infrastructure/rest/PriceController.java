package com.example.Prices.infrastructure.rest;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.usecase.GetPriceUseCase;
import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class PriceController {
    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceMapper priceMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceMapper = priceMapper;
    }

    //@RequestMapping(value = "/price" ,method = RequestMethod.POST)
    //public ResponseEntity<PriceResponse> getPrice(@RequestBody PriceRequest priceRequest) {
    @GetMapping(value = "/price")
    public ResponseEntity<PriceResponse> getPrice(
            @RequestParam(value = "brandId") Long brandId,
            @RequestParam(value = "productId") Long productId,
            @RequestParam(value = "appDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime appDate
    ) {
        Price price = getPriceUseCase.getPrice(
                brandId, productId, appDate);

        PriceResponse priceResponse = priceMapper.toPriceResponse(price);
        if (priceResponse != null) {
            return ResponseEntity.ok(priceResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
