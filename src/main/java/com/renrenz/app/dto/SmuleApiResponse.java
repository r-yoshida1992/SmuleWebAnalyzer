package com.renrenz.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class SmuleApiResponse {
	@JsonProperty("list")
	public List<Content> list;
	@JsonProperty("next_offset")
	Integer next_offset;

	@Data
	public static class Content {
		@JsonProperty("rec_id")
		String rec_id;
		@JsonProperty("poi")
		String poi;
		@JsonProperty("key")
		String key;
		@JsonProperty("performance_key")
		String performance_key;
		@JsonProperty("join_link")
		String join_link;
		@JsonProperty("type")
		String type;
		@JsonProperty("title")
		String title;
		@JsonProperty("artist")
		String artist;
		@JsonProperty("message")
		String message;
		@JsonProperty("created_at")
		String created_at;
		@JsonProperty("expire_at")
		String expire_at;
		@JsonProperty("seed")
		Boolean seed;
		@JsonProperty("closed")
		Boolean closed;
		@JsonProperty("ensemble_type")
		String ensemble_type;
		@JsonProperty("child_count")
		Integer child_count;
		@JsonProperty("app_uid")
		String app_uid;
		@JsonProperty("arr_type")
		String arr_type;
		@JsonProperty("arr_key")
		String arr_key;
		@JsonProperty("song_id")
		String song_id;
		@JsonProperty("song_length")
		String song_length;
		@JsonProperty("perf_status")
		String perf_status;
		@JsonProperty("artist_twitter")
		String artist_twitter;
		@JsonProperty("media_url")
		String media_url;
		@JsonProperty("video_media_url")
		String video_media_url;
		@JsonProperty("video_media_mp4_url")
		String video_media_mp4_url;
		@JsonProperty("cover_url")
		String cover_url;
		@JsonProperty("web_url")
		String web_url;
		@JsonProperty("song_info_url")
		String song_info_url;
		@JsonProperty("stats")
		Stats stats;
		@JsonProperty("performed_by")
		String performed_by;
		@JsonProperty("performed_by_url")
		String performed_by_url;
		@JsonProperty("owner")
		Owner owner;
		@JsonProperty("other_performers")
		List<OtherPerformers> other_performers;
		@JsonProperty("duet")
		Object duet;
		@JsonProperty("other")
		Other other;
		@JsonProperty("featured")
		Boolean featured;
		@JsonProperty("rm")
		String rm;
		@JsonProperty("private")
		Boolean isPrivate;
		@JsonProperty("lyric_video")
		String lyric_video;
		@JsonProperty("lyrics")
		String lyrics;
	}

	@Data
	public static class Stats {
		@JsonProperty("total_performers")
		Integer total_performers;
		@JsonProperty("truncated_other_performers")
		String truncated_other_performers;
		@JsonProperty("total_listens")
		Integer total_listens;
		@JsonProperty("truncated_listens")
		String truncated_listens;
		@JsonProperty("total_loves")
		Integer total_loves;
		@JsonProperty("truncated_loves")
		String truncated_loves;
		@JsonProperty("total_comments")
		Integer total_comments;
		@JsonProperty("truncated_comments")
		String truncated_comments;
		@JsonProperty("total_commenters")
		Integer total_commenters;
		@JsonProperty("total_gifts")
		Integer total_gifts;
		@JsonProperty("truncated_gifts")
		String truncated_gifts;
	}

	@Data
	public static class Owner {
		@JsonProperty("account_id")
		Long account_id;
		@JsonProperty("handle")
		String handle;
		@JsonProperty("pic_url")
		String pic_url;
		@JsonProperty("price")
		Double price;
		@JsonProperty("discount")
		Double discount;
		@JsonProperty("url")
		String url;
		@JsonProperty("is_vip")
		Boolean is_vip;
		@JsonProperty("is_verified")
		Boolean is_verified;
		@JsonProperty("verified_type")
		String verified_type;
	}

	@Data
	public static class OtherPerformers {
		@JsonProperty("account_id")
		Long account_id;
		@JsonProperty("handle")
		String handle;
		@JsonProperty("pic_url")
		String pic_url;
		@JsonProperty("url")
		String url;
		@JsonProperty("is_vip")
		Boolean is_vip;
		@JsonProperty("is_verified")
		Boolean is_verified;
		@JsonProperty("verified_type")
		String verified_type;
	}

	@Data
	public static class Other {
		@JsonProperty("pic_url")
		String pic_url;
		@JsonProperty("url")
		String url;
		@JsonProperty("label")
		String label;
		@JsonProperty("vip")
		Boolean vip;
		@JsonProperty("id")
		String id;
	}

}
