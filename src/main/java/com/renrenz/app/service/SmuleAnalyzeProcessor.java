package com.renrenz.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.renrenz.app.dto.IndexRequest;
import com.renrenz.app.dto.IndexResponse;
import com.renrenz.app.dto.SmuleApiResponse;

@Service
public class SmuleAnalyzeProcessor {

	private RestTemplate restTemplate = new RestTemplate();

	public void analyze(IndexRequest req, Model model) {
		IndexResponse res = new IndexResponse();
		List<IndexResponse.Content> contains = new ArrayList<>();
		apiCall(req, contains);
		res.setContents(contains);
		model.addAttribute("contents", res.getContents());
	}

	private void apiCall(IndexRequest req, List<IndexResponse.Content> contains) {
		try {
			Integer offset = 0;
			while (offset < 50) {
				String url = new StringBuilder("https://www.smule.com/s/profile/performance/").append(req.getOwnerId())
						.append("/sing?offset=").append(offset).toString();
				SmuleApiResponse response = restTemplate.getForObject(url, SmuleApiResponse.class);
				response.getList().forEach(e -> {
					IndexResponse.Content content = new IndexResponse.Content();
					content.setArtist(e.getArtist());
					content.setSongTitle(e.getTitle());
					content.setOwner(e.getOwner().getHandle());
					content.setJoiner(e.getOther() != null ? e.getOther().getLabel() : null);
					content.setWeb_url("https://www.smule.com" + e.getWeb_url());
					content.setCover_url(e.getCover_url());
					content.setEnsemble_type(e.getEnsemble_type());
					contains.add(content);
				});
				offset = response.getNext_offset();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
