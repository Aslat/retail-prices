package com.example.Prices.infrastructure.spring;

import com.example.Prices.application.PriceService;
import com.example.Prices.domain.repository.PriceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfiguration {

    private final PriceRepository priceRepository;

    public AppConfiguration(PriceRepository priceRepository) {
        this.priceRepository = priceRepository;
    }

    @Bean
    PriceService priceService(){
        return new PriceService(priceRepository);
    }
}
