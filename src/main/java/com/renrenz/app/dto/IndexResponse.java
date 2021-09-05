package com.renrenz.app.dto;

import java.util.List;

import lombok.Data;

@Data
public class IndexResponse {
	List<Content> contents;
	
	@Data
	public static class Content {
		String songTitle;
		String artist;
		String web_url;
		String cover_url;
		String owner;
		String joiner;
		String ensemble_type;
	}

}
