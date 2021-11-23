package com.renrenz.app.dto;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
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
		String contents_type;
		Date created_at;
		String created_at_str;
		Boolean isLabel;
	}

	public enum ContentsType {
		OC, JOIN, VIDEO_OC, VIDEO_JOIN, GROUP_OC, GROUP_VIDEO_OC, SOLO, VIDEO_SOLO, LIVE_JAM, UNKNOWN
	}

}
