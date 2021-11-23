package com.renrenz.app.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.renrenz.app.dto.IndexRequest;
import com.renrenz.app.dto.IndexResponse;
import com.renrenz.app.dto.IndexResponse.ContentsType;
import com.renrenz.app.dto.SmuleApiResponse;

@Service
public class SmuleAnalyzeProcessor {

	private RestTemplate restTemplate = new RestTemplate();

	public void analyze(IndexRequest req, Model model) {
		if (!req.getOwnerId().isBlank() && !req.getOtherId().isBlank()) {
			List<IndexResponse.Content> contents1 = buildContent(req.getOwnerId());
			List<IndexResponse.Content> contents2 = buildContent(req.getOtherId());
			List<IndexResponse.Content> result = new ArrayList<>();
			contents1.parallelStream().forEach(c -> {
				if (c.getJoiner() != null) {
					if (req.getOtherId().equals(c.getOwner()) || req.getOtherId().equals(c.getJoiner())) {
						if (!c.getIsLabel()) {
							result.add(c);
						}
					}
				}
			});
			contents2.parallelStream().forEach(c -> {
				if (c.getJoiner() != null) {
					if (req.getOwnerId().equals(c.getOwner()) || req.getOwnerId().equals(c.getJoiner())) {
						if (!c.getContents_type().equals(ContentsType.LIVE_JAM.toString()) && !c.getIsLabel()) {
							result.add(c);
						}
					}
				}
			});
			result.sort((e1, e2) -> e2.getCreated_at().compareTo(e1.getCreated_at()));
			model.addAttribute("contents", new IndexResponse(result).getContents());
		} else {
			if (!req.getOwnerId().isBlank()) {
				model.addAttribute("contents", new IndexResponse(buildContent(req.getOwnerId())).getContents());
			}
			if (!req.getOtherId().isBlank()) {
				model.addAttribute("contents", new IndexResponse(buildContent(req.getOtherId())).getContents());
			}
		}
	}

	private List<IndexResponse.Content> buildContent(String ownerId) {
		List<IndexResponse.Content> contents = new ArrayList<>();
		try {
			List<Integer> offsets = new ArrayList<>();
			for (int i = 0; i < 3000; i += 25) {
				offsets.add(i);
			}
			if (!ownerId.isBlank()) {
				offsets.parallelStream().forEach(os -> fetchContents(ownerId, os, contents));
			}
			contents.sort((e1, e2) -> e2.getCreated_at().compareTo(e1.getCreated_at()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contents;
	}

	private void fetchContents(String ownerId, Integer offset, List<IndexResponse.Content> contents) {
		String url = new StringBuilder("https://www.smule.com/s/profile/performance/").append(ownerId)
				.append("/sing?offset=").append(offset).toString();
		System.out.println(url);
		SmuleApiResponse response = restTemplate.getForObject(url, SmuleApiResponse.class);
		response.getList().forEach(e -> {
			IndexResponse.Content content = new IndexResponse.Content();
			// アーティスト名
			content.setArtist(e.getArtist());
			// 曲名
			content.setSongTitle(e.getTitle());
			// 主
			content.setOwner(e.getOwner().getHandle());
			// joinした人
			content.setJoiner(joinerSetting(e));
			// url(リンク用)
			content.setWeb_url("https://www.smule.com" + e.getWeb_url());
			// カバー写真
			content.setCover_url(e.getCover_url());
			// 種別
			content.setEnsemble_type(e.getEnsemble_type());
			// 作品の種類
			ContentsType contentsType = judgeContentsType(e);
			content.setContents_type(contentsType.toString());
			// ラベルで表示するかどうか
			content.setIsLabel(EnumSet
					.of(ContentsType.OC, ContentsType.GROUP_OC, ContentsType.VIDEO_OC, ContentsType.GROUP_VIDEO_OC)
					.contains(contentsType));
			// 作成日
			String created_at = e.getCreated_at();
			try {
				content.setCreated_at(new SimpleDateFormat("yyyy-MM-ddHH:mm:ss")
						.parse(created_at.substring(0, 10) + created_at.substring(11, 19)));
				content.setCreated_at_str(new SimpleDateFormat("yyyy/MM/dd").format(content.getCreated_at()));
			} catch (ParseException ex) {
				ex.printStackTrace();
			}
			contents.add(content);
		});
	}

	/**
	 * コンテンツ種別を判定する
	 */
	private ContentsType judgeContentsType(SmuleApiResponse.Content content) {
		boolean isDuet = "DUET".equals(content.getEnsemble_type());
		boolean isGroup = "GROUP".equals(content.getEnsemble_type());
		boolean isCfire = "CFIRE".equals(content.getEnsemble_type());
		boolean isSolo = "SOLO".equals(content.getEnsemble_type());
		if ("audio".equals(content.getType())) {
			if (isDuet) {
				return content.getSeed() ? ContentsType.OC : ContentsType.JOIN;
			}
			if (isGroup) {
				return ContentsType.GROUP_OC;
			}
			if (isSolo) {
				return ContentsType.SOLO;
			}
		}
		if ("video".equals(content.getType())) {
			if (isDuet) {
				return content.getSeed() ? ContentsType.VIDEO_OC : ContentsType.VIDEO_JOIN;
			}
			if (isGroup) {
				return ContentsType.GROUP_VIDEO_OC;
			}
			if (isSolo) {
				return ContentsType.VIDEO_SOLO;
			}
			if (isCfire) {
				return ContentsType.LIVE_JAM;
			}
		}
		return ContentsType.UNKNOWN;
	}

	/**
	 * Joinerを設定
	 */
	private String joinerSetting(SmuleApiResponse.Content content) {
		ContentsType type = judgeContentsType(content);
		switch (type) {
		case JOIN:
		case GROUP_OC:
		case GROUP_VIDEO_OC:
		case VIDEO_JOIN:
			return content.getOther() != null ? content.getOther().getLabel() : "aaaa";
		case OC:
		case VIDEO_OC:
			return content.getPerformed_by();
		case LIVE_JAM:
			return content.getOther_performers().isEmpty() ? null : content.getOther_performers().get(0).getHandle();
		default:
			return null;
		}
	}

}
