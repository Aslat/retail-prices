package com.example.Prices.application.mapper;


import com.example.Prices.application.response.PriceResponse;
import com.example.Prices.domain.entity.Price;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class PriceMapper {

    //This could have been done properly with MapStruct, but I got some problems and
    // prefer to just continue
    public PriceResponse toPriceResponse(Price price){
        if(Objects.isNull(price)){
            return null;
        }

        return PriceResponse.builder()
                .priceList(price.getPriceList())
                .brandId(price.getBrandId())
                .endDate(price.getEndDate())
                .price(price.getPrice())
                .productId(price.getProductId())
                .startDate(price.getStartDate())
                .build();
    }
}
