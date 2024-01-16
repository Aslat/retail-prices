package com.example.Prices.application;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import com.example.Prices.domain.exceptions.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

public class PriceService implements GetPriceUseCase {

    private final PriceRepository priceRepository;

    public PriceService(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Override
    public Price getPrice(Long brandId, Long productId, LocalDateTime appDate) {
        List<Price> prices = priceRepository.findByBrandProductAndDate(brandId, productId, appDate);

        return prices.stream().max(Comparator.comparing(Price::getPriority))
                .orElseThrow(() -> new PriceNotFoundException("No price found for the input data"));
    }
}
