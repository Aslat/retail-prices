package com.example.Prices;

import com.example.Prices.infrastructure.rest.PriceController;
import com.example.Prices.infrastructure.rest.exceptionhandler.PriceNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.openapitools.model.PriceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
		ResponseEntity<PriceResponse> priceResponseEntity = priceController.getPrice(brandId, productId, appDate.atOffset(ZoneOffset.UTC));

		//THEN
		assertEquals(HttpStatus.OK, priceResponseEntity.getStatusCode());
		assertNotNull(priceResponseEntity.getBody());

		PriceResponse priceResponse = priceResponseEntity.getBody();
		assertEquals(brandId, priceResponse.getBrandId());
		assertEquals(productId, priceResponse.getProductId());
		assertTrue(priceResponse.getStartDate().isBefore(appDate.atOffset(ZoneOffset.UTC)));
		assertTrue(priceResponse.getEndDate().isAfter(appDate.atOffset(ZoneOffset.UTC)));
		assertEquals(price, priceResponse.getPrice());
		assertEquals(priceList, priceResponse.getPriceList());
	}

	@Test
	public void getPriceNotFoundIT(){
		//GIVEN
		Long brandId = 1L;
		Long productId = 1L;
		OffsetDateTime appDate = OffsetDateTime.now();

		//WHEN
		assertThrows(PriceNotFoundException.class, () -> priceController.getPrice(brandId, productId , appDate));
		//THEN
	}

	@Test
	public void getPriceReturnBadRequest()
			throws Exception {

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(priceController).build();

		String validBrandId = "1";
		String validProductId = "35455";
		String validAppDateId = "2020-12-30T23:59:59.000Z";

		mockMvc.perform(get("/price")
						.param("brandId", "wrongBrandId")
						.param("productId", validProductId)
						.param("appDate", validAppDateId))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/price")
						.param("brandId", validBrandId)
						.param("productId", "InvalidProductId")
						.param("appDate", validAppDateId))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/price")
						.param("brandId", validBrandId)
						.param("productId", validProductId)
						.param("appDate", "WrongDate"))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/price")
						.param("productId", validProductId)
						.param("appDate", validAppDateId))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/price")
						.param("brandId", validBrandId)
						.param("appDate", validAppDateId))
				.andExpect(status().isBadRequest());

		mockMvc.perform(get("/price")
						.param("brandId", validBrandId)
						.param("productId", validProductId))
				.andExpect(status().isBadRequest());
	}
}
