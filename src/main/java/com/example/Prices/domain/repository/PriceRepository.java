package com.example.Prices.domain.repository;

import com.example.Prices.domain.entity.Price;

import java.time.LocalDateTime;
import java.util.List;

public interface PriceRepository {

    List<Price> findByBrandProductAndDate(Long brandId, Long productId, LocalDateTime appDate);
}
