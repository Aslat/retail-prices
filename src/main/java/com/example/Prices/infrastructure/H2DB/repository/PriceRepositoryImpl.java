package com.example.Prices.infrastructure.H2DB.repository;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.domain.repository.PriceRepository;
import com.example.Prices.infrastructure.H2DB.PriceEntity;
import com.example.Prices.infrastructure.H2DB.mapper.PriceEntityMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryImpl implements PriceRepository {

    final SpringDataPriceRepository springDataPriceRepository;

    final PriceEntityMapper priceEntityMapper;

    public PriceRepositoryImpl(SpringDataPriceRepository springDataPriceRepository, PriceEntityMapper priceEntityMapper) {
        this.springDataPriceRepository = springDataPriceRepository;
        this.priceEntityMapper = priceEntityMapper;
    }

    @Override
    public List<Price> findByBrandProductAndDate(Long brandId, Long productId, LocalDateTime appDate) {
        List<PriceEntity> priceEntityList = springDataPriceRepository.findByBrandProductAndDate(brandId, productId, appDate);

        return priceEntityMapper.toPriceList(priceEntityList);
    }
}
