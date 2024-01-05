package com.example.Prices.domain.service;

import com.example.Prices.domain.entity.Price;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PriceServiceImpl implements PriceService {
    @Override
    public Price getPrice(Long brandId, Long productId, LocalDateTime appDate) {
        return null;
    }
}
