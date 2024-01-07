package com.example.Prices.infrastructure.H2DB.mapper;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.infrastructure.H2DB.PriceEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Component
public class PriceEntityMapper {
    //This could have been done properly with MapStruct, but I got some problems and
    // prefer to just continue
    public List<Price> toPriceList(List<PriceEntity> priceEntityList){
        if(Objects.isNull(priceEntityList)){
            return null;
        }
        if(priceEntityList.isEmpty()){
            return Collections.emptyList();
        }

        return priceEntityList.stream().map(this::toPrice).toList();
    }

    private Price toPrice(PriceEntity priceEntity){
        return Price.builder()
                .priceList(priceEntity.getPriceList())
                .brandId(priceEntity.getBrand().getId())
                .endDate(priceEntity.getEndDate())
                .price(priceEntity.getPrice())
                .productId(priceEntity.getProductId())
                .startDate(priceEntity.getStartDate())
                .build();
    }
}
