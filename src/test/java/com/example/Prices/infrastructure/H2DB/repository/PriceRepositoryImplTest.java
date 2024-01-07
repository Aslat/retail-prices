package com.example.Prices.infrastructure.H2DB.repository;

import com.example.Prices.domain.entity.Price;
import com.example.Prices.infrastructure.H2DB.PriceEntity;
import com.example.Prices.infrastructure.H2DB.mapper.PriceEntityMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@SpringBootTest
class PriceRepositoryImplTest {

    @InjectMocks
    PriceRepositoryImpl priceRepository;

    @Mock
    SpringDataPriceRepository springDataPriceRepository;

    @Mock
    PriceEntityMapper priceEntityMapper;

    @Test
    public void findByBrandProductAndDate(){
        //GIVEN
        final Long brandId = 1L;
        final Long productId = 1L;
        final LocalDateTime appDate = LocalDateTime.now();

        List<PriceEntity> priceEntityList = List.of(new PriceEntity());
        List<Price> priceList = List.of(Price.builder().build());

        given(springDataPriceRepository.findByBrandProductAndDate(brandId, productId, appDate))
                .willReturn(priceEntityList);
        given(priceEntityMapper.toPriceList(priceEntityList)).willReturn(priceList);

        //WHEN
        priceRepository.findByBrandProductAndDate(brandId, productId, appDate);

        //THEN
        then(springDataPriceRepository).should().findByBrandProductAndDate(brandId, productId, appDate);
        then(priceEntityMapper).should().toPriceList(priceEntityList);

    }
}