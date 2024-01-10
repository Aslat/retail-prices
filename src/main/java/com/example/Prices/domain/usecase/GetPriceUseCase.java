package com.example.Prices.domain.usecase;

import com.example.Prices.domain.entity.Price;

import java.time.LocalDateTime;

public interface GetPriceUseCase {

    public Price getPrice(Long brandId, Long productId, LocalDateTime appDate);
}
