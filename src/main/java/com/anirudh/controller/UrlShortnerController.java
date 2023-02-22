package com.anirudh.controller;

import java.net.URI;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

//import com.anirudh.common.UrlValidator;
import com.anirudh.exception.InvalidUrlException;
import com.anirudh.service.UrlService;

@Controller
public class UrlShortnerController {

	private static final Logger logger = LoggerFactory.getLogger(UrlShortnerController.class);

	@Autowired
	private UrlService urlService;


	@GetMapping("/")
	public ModelAndView homepage() {
		logger.debug("returning from homepage() method to index.html");
		return new ModelAndView("index");
	}

	@PostMapping("/shorten")
	@ResponseBody
	public String shortenUrl(@RequestParam("url") String longUrl) throws Exception {
		logger.info("Received long URL to shorten: {}", longUrl);
		String shortUrl = urlService.shortenUrl(longUrl);
		return "http://localhost:8080/" + shortUrl;
	}

	@GetMapping("/{shortUrl}")
	public RedirectView redirectToOriginalUrl(@PathVariable String shortUrl) throws InvalidUrlException {
		logger.info("Received shortUrl to redirect: {}", shortUrl);
		if (urlService.isShortUrlPresent(shortUrl)) {
			String longUrl = urlService.findLonglUrl(shortUrl);
			if (longUrl != null) {
				logger.info("returning longUrl from databse: {}",longUrl);
				URI uri = URI.create(longUrl);
				String scheme = uri.getScheme();
				if(scheme == null) {
					longUrl = "http://"+longUrl;
				}
				
				return new RedirectView(longUrl);
			}
		}
		logger.info("shortUrl not found.");
		throw new InvalidUrlException("LongUrl does not exist for given input shortUrl");
	}

}
