package com.example.Prices.infrastructure.H2DB.repository;

import com.example.Prices.infrastructure.H2DB.PriceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SpringDataPriceRepository extends CrudRepository<PriceEntity, Long> {
    @Query("""
        SELECT p
        FROM PriceEntity p
        JOIN p.brand b
        WHERE b.id = ?1 AND p.productId = ?2 AND (?3 BETWEEN p.startDate AND p.endDate)
        ORDER BY p.priority ASC
        """)
    List<PriceEntity> findByBrandProductAndDate(Long brandId, Long productId, LocalDateTime appDate);
}
