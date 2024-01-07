package com.example.Prices.domain.service;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    public PriceServiceImpl(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getPrice(Long brandId, Long productId, LocalDateTime appDate) {
        if(Objects.isNull(brandId) ||Objects.isNull(productId) | Objects.isNull(appDate)){
            return null;
        }

        List<Price> prices = priceRepository.findByBrandProductAndDate(brandId, productId, appDate);
        Price price = null;
        if (!prices.isEmpty()) {
            price = prices.stream().max(Comparator.comparing(Price::getPriority)).get();
        }
        return price;
    }
}
