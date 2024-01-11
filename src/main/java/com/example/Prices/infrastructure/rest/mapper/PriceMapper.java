package com.example.Prices.infrastructure.rest.mapper;


import com.example.Prices.domain.entity.Price;
import org.openapitools.model.PriceResponse;
import org.springframework.stereotype.Component;

import java.time.ZoneOffset;
import java.util.Objects;

@Component
public class PriceMapper {

    //This could have been done properly with MapStruct, but I got some problems and
    // prefer to just continue
    public PriceResponse toPriceResponse(Price price){
        if(Objects.isNull(price)){
            return null;
        }

        PriceResponse priceResponse = new PriceResponse();
        priceResponse.priceList(price.getPriceList());
        priceResponse.brandId(price.getBrandId());
        priceResponse.endDate(price.getEndDate().atOffset(ZoneOffset.UTC));
        priceResponse.price(price.getPrice());
        priceResponse.productId(price.getProductId());
        priceResponse.startDate(price.getStartDate().atOffset(ZoneOffset.UTC));

        return priceResponse;
    }
}
