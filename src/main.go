package main

import (
	"encoding/json"
	"fmt"
	"io/ioutil"
	"net/http"
	"strconv"
	"sync"
)

func main() {
	var wg sync.WaitGroup
	var list []Recording
	for i := 0; i < 3000; i += 25 {
		wg.Add(1)
		go func() {
			defer wg.Done()
			list = append(list, fetch(i))
		}()
	}
	wg.Wait()
	for _, recording := range list {
		for _, s := range recording.List {
			fmt.Println(s.Key + " : " + s.Title)
		}
	}
}

func fetch(offset int) Recording {
	res, _ := http.Get("https://www.smule.com/Ren_ren_z/performances/json?offset=" + strconv.Itoa(offset))
	defer res.Body.Close()

	body, _ := ioutil.ReadAll(res.Body)
	var recording Recording
	json.Unmarshal(body, &recording)

	return recording
}

type Recording struct {
	List []struct {
		RecID            interface{} `json:"rec_id"`
		Poi              interface{} `json:"poi"`
		Key              string      `json:"key"`
		PerformanceKey   string      `json:"performance_key"`
		JoinLink         interface{} `json:"join_link"`
		Type             string      `json:"type"`
		Title            string      `json:"title"`
		Artist           interface{} `json:"artist"`
		Message          string      `json:"message"`
		CreatedAt        string      `json:"created_at"`
		ExpireAt         string      `json:"expire_at"`
		Seed             bool        `json:"seed"`
		Closed           bool        `json:"closed"`
		EnsembleType     string      `json:"ensemble_type"`
		ChildCount       int         `json:"child_count"`
		AppUID           string      `json:"app_uid"`
		ArrType          interface{} `json:"arr_type"`
		ArrKey           string      `json:"arr_key"`
		SongID           interface{} `json:"song_id"`
		SongLength       interface{} `json:"song_length"`
		PerfStatus       string      `json:"perf_status"`
		ArtistTwitter    interface{} `json:"artist_twitter"`
		MediaURL         interface{} `json:"media_url"`
		VideoMediaURL    interface{} `json:"video_media_url"`
		VideoMediaMp4URL interface{} `json:"video_media_mp4_url"`
		CoverURL         string      `json:"cover_url"`
		WebURL           string      `json:"web_url"`
		SongInfoURL      interface{} `json:"song_info_url"`
		Stats            struct {
			TotalPerformers          int    `json:"total_performers"`
			TruncatedOtherPerformers string `json:"truncated_other_performers"`
			TotalListens             int    `json:"total_listens"`
			TruncatedListens         string `json:"truncated_listens"`
			TotalLoves               int    `json:"total_loves"`
			TruncatedLoves           string `json:"truncated_loves"`
			TotalComments            int    `json:"total_comments"`
			TruncatedComments        string `json:"truncated_comments"`
			TotalCommenters          int    `json:"total_commenters"`
			TotalGifts               int    `json:"total_gifts"`
			TruncatedGifts           string `json:"truncated_gifts"`
		} `json:"stats"`
		PerformedBy    string `json:"performed_by"`
		PerformedByURL string `json:"performed_by_url"`
		Owner          struct {
			AccountID    int     `json:"account_id"`
			Handle       string  `json:"handle"`
			PicURL       string  `json:"pic_url"`
			Price        float64 `json:"price"`
			Discount     float64 `json:"discount"`
			URL          string  `json:"url"`
			IsVip        bool    `json:"is_vip"`
			IsVerified   bool    `json:"is_verified"`
			VerifiedType string  `json:"verified_type"`
		} `json:"owner"`
		OtherPerformers []struct {
			AccountID    int    `json:"account_id"`
			Handle       string `json:"handle"`
			PicURL       string `json:"pic_url"`
			URL          string `json:"url"`
			IsVip        bool   `json:"is_vip"`
			IsVerified   bool   `json:"is_verified"`
			VerifiedType string `json:"verified_type"`
		} `json:"other_performers"`
		Duet struct {
			AccountID    int    `json:"account_id"`
			Handle       string `json:"handle"`
			PicURL       string `json:"pic_url"`
			URL          string `json:"url"`
			IsVip        bool   `json:"is_vip"`
			IsVerified   bool   `json:"is_verified"`
			VerifiedType string `json:"verified_type"`
		} `json:"duet"`
		Other struct {
			PicURL       string      `json:"pic_url"`
			URL          string      `json:"url"`
			VerifiedType string      `json:"verified_type"`
			Label        string      `json:"label"`
			Vip          bool        `json:"vip"`
			ID           int         `json:"id"`
			VerifiedUrls interface{} `json:"verified_urls"`
		} `json:"other"`
		Featured   bool        `json:"featured"`
		Rm         interface{} `json:"rm"`
		Private    bool        `json:"private"`
		LyricVideo interface{} `json:"lyric_video"`
		Lyrics     interface{} `json:"lyrics"`
		Segments   interface{} `json:"segments"`
	} `json:"list"`
	NextOffset int `json:"next_offset"`
}
