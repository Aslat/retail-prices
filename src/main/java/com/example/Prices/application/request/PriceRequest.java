package com.example.Prices.application.request;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PriceRequest {

    private Long brandId;
    private Long productId;
    private LocalDateTime appDate;

}