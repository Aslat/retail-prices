package com.example.Prices.infrastructure.H2DB;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryImpl implements PriceRepository {
    @Override
    public List<Price> findByBrandProductAndDate(Long brandId, Long productId, LocalDateTime appDate) {
        return null;
    }
}
