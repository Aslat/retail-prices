package com.example.Prices.infrastructure.rest;

import com.example.Prices.infrastructure.rest.mapper.PriceMapper;
import com.example.Prices.infrastructure.rest.request.PriceRequest;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.usecase.GetPriceUseCase;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PriceController {
    private final GetPriceUseCase getPriceUseCase;
    private final PriceMapper priceMapper;

    public PriceController(GetPriceUseCase getPriceUseCase, PriceMapper priceMapper) {
        this.getPriceUseCase = getPriceUseCase;
        this.priceMapper = priceMapper;
    }

    @RequestMapping(value = "/price" ,method = RequestMethod.POST)
    public ResponseEntity<PriceResponse> getPrice(@RequestBody PriceRequest priceRequest) {
        Price price = getPriceUseCase.getPrice(
                priceRequest.getBrandId(), priceRequest.getProductId(), priceRequest.getAppDate());

        PriceResponse priceResponse = priceMapper.toPriceResponse(price);
        if (priceResponse != null) {
            return ResponseEntity.ok(priceResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
