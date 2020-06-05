package com.hexa.core.ctrl;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

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
import org.w3c.dom.ls.LSInput;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class FreeBbsCtrl {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FreeBbsIService service;
	
	@Autowired
	private SearchIService sService;
	
	// 자유게시판 (관리자)목록 조회
	@RequestMapping(value = "/bbsMain.do", method = RequestMethod.GET)
	public String bbsMain(Model model,boolean auth_check, BbsDTO dto, SecurityContextHolder session) {
		log.info("Welcome List 목록조회, {}", new Date());
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		log.info("●●●●●●●●●● auth_check{}", auth_check);
		
		if(auth_check == false) {
			List<BbsDTO> lists = service.selectUserFreeBbsList();
			model.addAttribute("lists", lists);
		}else {	
			List<BbsDTO> lists = service.selectAdminFreeBbsList();
			model.addAttribute("lists", lists);
		}
		return "bbsMain";
	}
	
	
	
	// 자유게시판 새 글 작성.GET
	@RequestMapping(value = "/freeBbsInsert.do", method = RequestMethod.GET)
	public String insertfreeBbs(Model model, SecurityContextHolder session, BbsDTO dto) {
		log.info("Welcome insert 글 작성, {}");
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		model.addAttribute("Ldto", Ldto);
		
//		dto.getUsername(); → userId
//		dto.setId(Ldto.getUsername());
//		service.insertFreeBbs(dto);
		
		return "Bbs/freeBbsInsert";
	}
	
	// 자유게시판 새 글 작성.POST
	@RequestMapping(value = "/freeBbsInsert.do", method = RequestMethod.POST)
	public String insertfreeBbs(String seq, BbsDTO dto, Model model,SecurityContextHolder session) {
		log.info("Welcome insert 글 작성완료, {}", dto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto.setId(Ldto.getUsername());
		dto.setName(Ldto.getName());
		boolean isc = false;
		isc = service.insertFreeBbs(dto);
		
		if(isc) {
			BbsDTO result = service.selectDetailFreeBbs(service.selectNewBbs(seq));
			model.addAttribute("seq",result);
			sService.addBbsIndex(result, "freeBbs");
		}
		
		return isc?"Bbs/bbsDetail":"redirect:/logdout.do";
	}
	
	// 자유게시판 글 상세보기.GET
	@RequestMapping(value = "/bbsDetail.do", method = RequestMethod.GET)
	public String selectDetailFreeBbs(Model model, String seq) {
		log.info("Welcome 글 상세보기, {}", seq);
		
		BbsDTO dto = service.selectDetailFreeBbs(seq);
		model.addAttribute("seq", dto);
		
		return "Bbs/bbsDetail";
	}
	
	// 자유게시판 글 수정.GET
	@RequestMapping(value = "/freeBbsModify.do", method = RequestMethod.GET)
	public String updateModifyFreeBbs(SecurityContextHolder session, Model model, BbsDTO dto, String seq) {
		log.info("Welcome 글 수정 값 가져오기, {}", dto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto = service.selectDetailFreeBbs(seq);
		if(dto.getId().equals(Ldto.getUsername())) {
			model.addAttribute("dto", dto);
			return "Bbs/freeBbsModify";
		}else{
			return "redirect:/bbsMain.do";
		}
	}
	
	// 자유게시판 글 수정.POST
	@RequestMapping(value = "/bbsDetail.do", method = RequestMethod.POST)
	public String updateModifyFreeBbs(BbsDTO dto, String seq) {
		log.info("Welcome 글 수정 값 보내기, {}", dto);
		
		int n = service.updateModifyFreeBbs(dto);
		
		if (n>0) {
			sService.updateBbsIndex(dto, "freeBbs");
			return "redirect:/bbsDetail.do?seq="+seq;
		}else {
			return "redirect:/bbsMain.do";
		}
	}
	
	// 자유게시판 글 단일삭제
	@RequestMapping(value = "/del.do", method = RequestMethod.POST)
	public String updateDeleteFreeBbs(SecurityContextHolder session, String seq) {
		log.info("Welcome 글 단일삭제 값 보내기, {}", seq);
		int n = service.updateDeleteFreeBbs(seq);
		
		if (n>0) {
			return "redirect:/bbsMain.do";
		}else {
			return "redirect:/bbsTest.do";
		}
	}
	
	// 자유게시판 글 다중삭제
	@RequestMapping(value = "/multiDel.do", method = RequestMethod.POST)
	public String updateMultiDelFreeBbs(String[] chkVal) {
		log.info("Welcome 글 다중삭제 ,\t {}", Arrays.toString(chkVal));
		boolean isc = false;
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", chkVal);
		isc = service.updateMultiDelFreeBbs(map);
		
		return isc?"redirect:/bbsMain.do":"redirect:/logout.do";
	}
	
	
	public String insertReplyBbs(BbsDTO dto) {
		return null;
	}
	
}










