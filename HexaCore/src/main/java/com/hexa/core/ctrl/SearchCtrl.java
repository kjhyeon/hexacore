package com.hexa.core.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class SearchCtrl {

	@Autowired
	private SearchIService service;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/testBbsSearch.do", method = RequestMethod.GET)
	public String searchTest(String keyword, Model model,String type) {
		log.info("WelcomePage TestBbsSearch : {}",keyword);
		if(type==null) {
			type="title";
		}
		model.addAttribute("list",service.eDocSearch(keyword,type));
		
		return "searchTest";
	}
	
	@RequestMapping(value="/testBbsIndex.do", method = RequestMethod.GET)
	public String searchTest() {
		log.info("WelcomePage TestBbsIndex ");
		service.eDocIndex();
		return null;
	}
}