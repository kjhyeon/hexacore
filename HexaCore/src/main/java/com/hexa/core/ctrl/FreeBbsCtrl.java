package com.hexa.core.ctrl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.ls.LSInput;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class FreeBbsCtrl {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*절대 경로*/
//	private static String attach_path = "C:\\nobrand\\git\\hexacore\\HexaCore\\file";
	
	/*상대 경로*/
	private static String attach_path = "C:\\nobrand\\eclipse_spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\HexaCore\\resource\\file";
	
	@Autowired
	private FreeBbsIService service;
	
	@Autowired
	private SearchIService sService;
	
	// 파일
	public static String saveFile(MultipartFile file) {
		String saveName = file.getOriginalFilename();
		
		File dir = new File(attach_path);
		if(dir.isDirectory() == false){
			dir.mkdirs();
		}
		File f = new File(attach_path, saveName);
		try {
			file.transferTo(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "freeBbs_"+saveName+"_"+UUID.randomUUID();
	}
	
	// 자유게시판 (관리자)목록 조회
	@RequestMapping(value = "/bbsMain.do", method = RequestMethod.GET)
	public String bbsMain(Model model, BbsDTO dto, SecurityContextHolder session, String page,String keyword,String type) {
		log.info("Welcome List 목록조회, {}", new Date());
		if(page==null) {
			page="0";
		}
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		Collection<GrantedAuthority> a = Ldto.getAuthorities();
		String auth_check = null;
		for (GrantedAuthority auths : a) {
			auth_check = auths.getAuthority();
		}
		RowNumDTO row = new RowNumDTO();
		if(keyword!=null&&!keyword.equals("")) {
			row.setTotal(sService.freeBbsTotal(keyword, type,auth_check));
			model.addAttribute("keyword",keyword);
			model.addAttribute("type",type);
		}else if(auth_check.trim().equalsIgnoreCase("role_admin")){
			row.setTotal(service.selectAdminBoardListTotal());
		}else {
			row.setTotal(service.selectUserBoardListTotal());
		}
		row.setListNum(10);
		row.setPageNum(5);
		row.setIndex(Integer.parseInt(page));
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		model.addAttribute("row",row);
		List<BbsDTO> lists = null;
		if(keyword!=null&&!keyword.equals("")) {
			lists = sService.freeBbsSearch(keyword, type, row,auth_check);
		}else if(!auth_check.trim().equalsIgnoreCase("role_admin")) {
			lists = service.selectUserBbsListRow(row);
		}else {	
			lists = service.selectAdminBbsListRow(row);
		}
		model.addAttribute("lists", lists);	
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
	public String insertfreeBbs(String seq, BbsDTO dto, Model model,SecurityContextHolder session,MultipartFile filename) {
		log.info("Welcome insert 글 작성완료, {}/{}", dto,filename);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto.setId(Ldto.getUsername());
		dto.setName(Ldto.getName());
		
		boolean isc = false;
		isc = service.insertFreeBbs(dto);
		if(filename!=null&&!filename.isEmpty()) {
			saveFile(filename);
		}
		if(isc) {
			BbsDTO result = service.selectDetailFreeBbs(service.selectNewBbs());
			model.addAttribute("seq",result);
			sService.addBbsIndex(result, "freeBbs");
		}
		
		
		return isc?"Bbs/bbsDetail":"redirect:/logdout.do";
	}
	
	// 자유게시판 글 상세보기.GET
	@RequestMapping(value = "/bbsDetail.do", method = RequestMethod.GET)
	public String selectDetailFreeBbs(Model model, String seq, SecurityContextHolder session) {
		log.info("Welcome 글 상세보기, {}", seq);
		log.info("Welcome 글 조회수 여부, {}", seq);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		
		Collection<GrantedAuthority> a = Ldto.getAuthorities();
		String auth_check = null;
		for (GrantedAuthority auths : a) {
			auth_check = auths.getAuthority();
		}
		
		
		if (!auth_check.trim().equalsIgnoreCase("role_admin")) {
			boolean isc = false;
			isc = service.updateViewsBbs(seq);
		}
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
	
	@RequestMapping(value = "/freeBbsReplyInsert.do", method = RequestMethod.GET)
	public String goInsertReplyBbs(SecurityContextHolder session, Model model,String seq) {
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		
		model.addAttribute("Ldto", Ldto);
		model.addAttribute("seq",seq);
		return "Bbs/freeBbsReplyInsert";
	}
	
	@RequestMapping(value = "/freeBbsReplyInsert.do", method = RequestMethod.POST)
	public String insertReplyBbs(BbsDTO dto, SecurityContextHolder session, Model model) {
		log.info("Welcome insertReplyBbs 답글 작성완료, {}", dto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto.setId(Ldto.getUsername());
		dto.setName(Ldto.getName());

		boolean isc = false;
		isc = service.ReplyBbs(dto);
		
		if(isc) {
			BbsDTO result = service.selectDetailFreeBbs(service.selectNewBbs());
			model.addAttribute("seq",result);
			sService.addBbsIndex(result, "freeBbs");
		}
		
		return isc?"redirect:/bbsMain.do":"redirect:/logout";
	}
	
	
	
}










