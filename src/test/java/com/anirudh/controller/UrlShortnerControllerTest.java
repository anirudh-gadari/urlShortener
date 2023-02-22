package com.anirudh.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.anirudh.entity.Url;
import com.anirudh.repository.UrlRepository;
import com.anirudh.service.UrlService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UrlShortnerControllerTest {

	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private UrlService urlService;

	@Autowired
	private UrlRepository urlRepository;

	@Test
	public void testShortenUrl() {
		String longUrl = "https://example.com";
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/shorten?url=" + longUrl, requestEntity,
				String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		String outputResponse = responseEntity.getBody();
		assertNotNull(outputResponse);
		StringBuilder sb = new StringBuilder(outputResponse);
		sb.reverse();
		String[] split = sb.toString().split("/");
		Url url = urlRepository.findByShortUrl(new StringBuilder(split[0]).reverse().toString());
		assertNotNull(url);
		assertEquals(longUrl, url.getLongUrl());
	}

	@Test
	public void testAlreadyShortenUrl() {

		String longUrl = "https://example2.com";

		// genereateShortUrl saves shortUrl, longUrl in db
		String shortUrl = urlService.generateShortUrl(longUrl);

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/json");
		HttpEntity<?> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<String> responseEntity = restTemplate.postForEntity("/shorten?url=" + longUrl, requestEntity,
				String.class);
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		String outputResponse = responseEntity.getBody();
		assertNotNull(outputResponse);
		assertTrue(outputResponse.contains(shortUrl));
		StringBuilder sb = new StringBuilder(outputResponse);
		sb.reverse();
		String[] split = sb.toString().split("/");
		assertEquals(shortUrl, new StringBuilder(split[0]).reverse().toString());

	}

	@Test
	public void testNonExistingShortenUrl() {

		String longUrl = "https://example3.com";

		// generateShortUrl method saves shortUrl and longUrl in db
		String shortUrl = urlService.generateShortUrl(longUrl);
		// alter shortUrl to test the non existing shortUrl scenario
		shortUrl = "abc";
		ResponseEntity<String> response = restTemplate.getForEntity("/{shortUrl}", String.class, shortUrl);
		assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(response.getStatusCodeValue(), 404);

	}

}
