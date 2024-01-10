package com.example.Prices;

import com.example.Prices.infrastructure.rest.PriceController;
import com.example.Prices.infrastructure.rest.response.PriceResponse;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PricesApplicationTests {

	@Autowired
	private PriceController priceController;

	private static Stream<Arguments> provideTestPrices() {
		return Stream.of(
				Arguments.of(LocalDateTime.of(2020,6,14,10, 0), 35.5, 1L),
				Arguments.of(LocalDateTime.of(2020,6,14,16, 0), 25.45, 2L),
				Arguments.of(LocalDateTime.of(2020,6,14,21, 0), 35.5, 1L),
				Arguments.of(LocalDateTime.of(2020,6,15,10, 0), 30.5, 3L),
				Arguments.of(LocalDateTime.of(2020,6,16,21, 0), 38.95, 4L)
		);
	}

	@ParameterizedTest
	@MethodSource("provideTestPrices")
	void getPriceIntegrationTest(LocalDateTime appDate, Double price, Long priceList) {
		//GIVEN
		Long brandId = 1L;
		Long productId = 35455L;

		//WHEN
		ResponseEntity<PriceResponse> priceResponseEntity = priceController.getPrice(brandId, productId, appDate);

		//THEN
		assertEquals(HttpStatus.OK, priceResponseEntity.getStatusCode());
		assertNotNull(priceResponseEntity.getBody());

		PriceResponse priceResponse = priceResponseEntity.getBody();
		assertEquals(priceResponse.getBrandId(), brandId);
		assertEquals(priceResponse.getProductId(), productId);
		assertTrue(priceResponse.getStartDate().isBefore(appDate));
		assertTrue(priceResponse.getEndDate().isAfter(appDate));
		assertEquals(priceResponse.getPrice(), price);
		assertEquals(priceResponse.getPriceList(), priceList);
	}
}
