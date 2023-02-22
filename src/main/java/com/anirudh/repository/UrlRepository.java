package com.anirudh.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.anirudh.entity.Url;

public interface UrlRepository extends JpaRepository<Url, Long> {

	Url findByLongUrl(String longUrl);

	Url findByShortUrl(String shortUrl);

}
