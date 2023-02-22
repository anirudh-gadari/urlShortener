package com.anirudh.service;

import java.util.Random;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anirudh.entity.Url;
import com.anirudh.repository.UrlRepository;

@Service
public class UrlService {

	private static final Logger logger = LoggerFactory.getLogger(UrlService.class);
	private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_";
	private static final int BASE = ALPHABET.length();

	@Autowired
	private UrlRepository urlRepository;

	public String shortenUrl(String longUrl) {
		// return shortUrl if it already exists in database, else generate new one
		Url url = urlRepository.findByLongUrl(longUrl);
		if (url != null) {
			logger.info("fetching existing shortenedUrl:{} found for longUrl:{}", url.getShortUrl(), longUrl);
			return url.getShortUrl();
		}
		return generateShortUrl(longUrl);
	}

	public String generateShortUrl(String longUrl) {
		logger.info("generating shortUrl for input longUrl: {}", longUrl);
		String shortUrl = createShortUrl();
		// generate unique shortUrl which is not present in db
		while (urlRepository.findByShortUrl(shortUrl) != null) {
			shortUrl = createShortUrl();
		}
		// save the generated shortUrl before returning to user
		Url url = new Url();
		url.setLongUrl(longUrl);
		url.setShortUrl(shortUrl);
		logger.debug("saving generated url to db :{}", url);
		urlRepository.saveAndFlush(url);
		return shortUrl;
	}

	private String createShortUrl() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		// generate shortUrl of size between 1 to 8 characters
		for (int i = 0; i < random.nextInt(9); i++) {
			int randomIndex = random.nextInt(BASE);
			char c = ALPHABET.charAt(randomIndex);
			sb.append(c);
		}
		String shortUrl = sb.toString();
		logger.debug("shortUrl created: {}", shortUrl);
		return shortUrl;
	}

	public String findLonglUrl(String shortUrl) {
		logger.debug("calling findLonglUrl from DB...");
		Url url = urlRepository.findByShortUrl(shortUrl);
		if (url != null) {
			String longUrl = url.getLongUrl();
			logger.info("Long URL: {}", longUrl);
			return longUrl;
		}
		return null;
	}

	public boolean isShortUrlPresent(String shortUrl) {
		Url url = urlRepository.findByShortUrl(shortUrl);
		return url != null ? true : false;
	}
}
