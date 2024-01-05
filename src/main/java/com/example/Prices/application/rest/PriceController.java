package com.example.Prices.application.rest;

import com.example.Prices.application.mapper.PriceMapper;
import com.example.Prices.application.request.PriceRequest;
import com.example.Prices.application.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.service.PriceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PriceController {
    private final PriceService priceService;
    private final PriceMapper priceMapper;

    public PriceController(PriceService priceService, PriceMapper priceMapper) {
        this.priceService = priceService;
        this.priceMapper = priceMapper;
    }

    @RequestMapping(value = "/price" ,method = RequestMethod.POST)
    public ResponseEntity<PriceResponse> getPrice(@RequestBody PriceRequest priceRequest) {
        Price price = priceService.getPrice(
                priceRequest.getBrandId(), priceRequest.getProductId(), priceRequest.getAppDate());

        PriceResponse priceResponse = priceMapper.toPriceResponse(price);
        if (priceResponse != null) {
            return ResponseEntity.ok(priceResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
