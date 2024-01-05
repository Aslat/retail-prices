package com.example.Prices.domain.service;

import com.example.Prices.domain.entity.Price;

import java.time.LocalDateTime;

public interface PriceService {

    public Price getPrice(Long brandId, Long productId, LocalDateTime appDate);
}
