package com.hexa.core.ctrl;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.dto.LoginDTO;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class SearchCtrl {

	@Autowired
	private SearchIService service;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/testBbsSearch.do", method = RequestMethod.GET)
	public String searchTest(String keyword, Model model,String type, SecurityContextHolder session) {
		log.info("WelcomePage TestBbsSearch : {}/{}",keyword,type);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		if(type==null) {
			type="title";
		}
//		model.addAttribute("list",service.eDocSearch(keyword,type,dto.getUsername()));
		
		return "searchTest";
	}
	
	@RequestMapping(value="/testBbsIndex.do", method = RequestMethod.GET)
	public String searchTest() {
		log.info("WelcomePage TestBbsIndex ");
		service.eDocIndex();
		service.freeBbsIndex();
		return null;
	}
	
	@RequestMapping(value="/totalSearch.do", method = RequestMethod.GET)
	public String Totalsearch(String keyword, Model model, String type, SecurityContextHolder session) {
		log.info("WelcomePage TotalSearch : {}/{}",keyword,type);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		if(type==null) {
			type="title";
		}
		String role = null;
		Collection<GrantedAuthority> a = dto.getAuthorities();
		for (GrantedAuthority auths : a) {
			role=auths.getAuthority();
		}
		model.addAttribute("keyword", keyword);
		model.addAttribute("type",type);
		model.addAttribute("eDocList",service.eDocSearch(keyword,type,dto.getUsername(),null));
//		model.addAttribute("noticeBbsList", service.noticeBbsSearch(keyword, type));
		model.addAttribute("freeBbsList", service.freeBbsSearch(keyword, type,null,role));
//		model.addAttribute("fileBbsList", service.fileBbsSearch(keyword, type));
		model.addAttribute("msgList", service.receiveMsgSearch(keyword,null, type,dto.getUsername()));
		return "search/searchResult";
	}
	
	@RequestMapping(value="/totalIndex.do", method = RequestMethod.GET)
	public String totalIndex() {
		log.info("WelcomePage totalIndex ");
		service.eDocIndex();
		service.freeBbsIndex();
		service.noticeBbsIndex();
		service.fileBbsIndex();
		service.msgIndex();
		return null;
	}
}
