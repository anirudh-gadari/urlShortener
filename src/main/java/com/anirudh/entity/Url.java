package com.anirudh.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "url_table")
public class Url {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "long_url", nullable = false)
	private String longUrl;

	@Column(name = "short_url", nullable = false, unique = true)
	private String shortUrl;

	public Url() {

	}

	public Url(String longUrl, String shortUrl) {
		this.longUrl = longUrl;
		this.shortUrl = shortUrl;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public void setLongUrl(String longUrl) {
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, longUrl, shortUrl);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Url other = (Url) obj;
		return Objects.equals(id, other.id) && Objects.equals(longUrl, other.longUrl)
				&& Objects.equals(shortUrl, other.shortUrl);
	}

	@Override
	public String toString() {
		return "Url [id=" + id + ", longUrl=" + longUrl + ", shortUrl=" + shortUrl + "]";
	}

}
