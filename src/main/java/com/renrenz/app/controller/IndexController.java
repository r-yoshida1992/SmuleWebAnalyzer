package com.renrenz.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.renrenz.app.dto.IndexRequest;
import com.renrenz.app.service.SmuleAnalyzeProcessor;

@Controller
@RequestMapping("/")
public class IndexController {

	@Autowired
	SmuleAnalyzeProcessor smuleAnalyzeProcessor;

	@GetMapping
	public String IndexAction(@ModelAttribute("request") IndexRequest req) {
		return "index";
	}

	@PostMapping
	public String Search(@ModelAttribute("request") IndexRequest req, Model model) {
		smuleAnalyzeProcessor.analyze(req, model);
		return "index";
	}

}
