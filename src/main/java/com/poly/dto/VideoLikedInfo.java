package com.poly.dto;

import java.math.BigInteger;

public class VideoLikedInfo {

	private Integer videoId;
	private String tilte;
	private String href;
	private float totalLike;
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public String getTilte() {
		return tilte;
	}
	public void setTilte(String tilte) {
		this.tilte = tilte;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public float getTotalLike() {
		return totalLike;
	}
	public void setTotalLike(float f) {
		this.totalLike = f;
	}

	
	
}
