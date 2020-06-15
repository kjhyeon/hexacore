package com.hexa.core.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.CommentDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FileBbsIService;
import com.hexa.core.model.bbs.inf.FileComIService;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class FileBbsCtrl {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileBbsIService service;
	
	@Autowired
	private SearchIService sService;
	
	@Autowired
	private FileComIService cService;
	
	// 자유게시판 (관리자)목록 조회
	@RequestMapping(value = "/fileBbsMain.do", method = RequestMethod.GET)
	public String fileBbsMain(Model model, BbsDTO dto, SecurityContextHolder session, String page,String keyword,String type) {
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
			row.setTotal(service.selectAdminFileBoardListTotal());
		}else {
			row.setTotal(service.selectUserFileBoardListTotal());
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
			lists = service.selectUserFileBbsListRow(row);
		}else {	
			lists = service.selectAdminFileBbsListRow(row);
		}
		model.addAttribute("lists", lists);	
		return "Bbs/fileBbsMain";
	}
	
	// 자유게시판 새 글 작성.GET
	@RequestMapping(value = "/fileBbsInsert.do", method = RequestMethod.GET)
	public String insertfileBbs(Model model, SecurityContextHolder session, BbsDTO dto) {
		log.info("Welcome insert 글 작성, {}");
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		model.addAttribute("Ldto", Ldto);

		return "Bbs/fileBbsInsert";
	}
		
	// 자유게시판 새 글 작성.POST
	@RequestMapping(value = "/fileBbsInsert.do", method = RequestMethod.POST)
	public String insertFileBbs(String seq, BbsDTO dto, Model model,SecurityContextHolder session, MultipartFile[] filename) {
		log.info("Welcome insert 글 작성완료, {}/{}", dto, filename);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto.setId(Ldto.getUsername());
		dto.setName(Ldto.getName());

		BbsDTO result = service.insertFileBbs(dto,filename);
		List<FileDTO> list = service.selectFileBbsFile(String.valueOf(result.getSeq()));
		model.addAttribute("list", list);
		model.addAttribute("seq",result);
		return result!=null?"redirect:/fileBbsDetail.do?seq="+result.getSeq():"redirect:/logdout.do";
	}
	
	// 자유게시판 글 상세보기.GET
	@RequestMapping(value = "/fileBbsDetail.do", method = RequestMethod.GET)
	public String selectDetailFileBbs(Model model, String page, String seq, SecurityContextHolder session) {
		log.info("Welcome 글 상세보기, {}", seq);
		log.info("Welcome 글 조회수 여부, {}", seq);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();

		Collection<GrantedAuthority> a = Ldto.getAuthorities();
		String auth_check = null;
		for (GrantedAuthority auths : a) {
			auth_check = auths.getAuthority();
		}
		if (page == null) {
			page = "0";
		}

		RowNumDTO row = new RowNumDTO();
		CommentDTO cDto = new CommentDTO();

		row.setTotal(cService.selectFileCommentListTotal(seq));
		row.setListNum(5);
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
		List<CommentDTO> lists = null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parent_seq", seq);
		map.put("start", row.getStart());
		map.put("last", row.getLast());
		lists = cService.selectFileCommentListRow(map);
		model.addAttribute("lists", lists);

		if (!auth_check.trim().equalsIgnoreCase("role_admin")) {
			boolean isc = false;
			isc = service.updateViewsFileBbs(seq);
		}

		cDto.setParent_seq(Integer.parseInt(seq));

		BbsDTO dto = service.selectDetailFileBbs(seq);
		List<FileDTO> list = service.selectFileBbsFile(seq);
		model.addAttribute("seq", dto);
		model.addAttribute("list", list);


		return "Bbs/fileBbsDetail";
	}

	// 자유게시판 글 수정.GET
	@RequestMapping(value = "/fileBbsModify.do", method = RequestMethod.GET)
	public String updateModifyFileBbs(SecurityContextHolder session, Model model, BbsDTO dto, String seq) {
		log.info("Welcome 글 수정 값 가져오기, {}", dto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto = service.selectDetailFileBbs(seq);
		List<FileDTO> list = service.selectFileBbsFile(seq);
		model.addAttribute("list", list);
		if(dto.getId().equals(Ldto.getUsername())) {
			model.addAttribute("dto", dto);
			return "Bbs/fileBbsModify";
		}else{
			return "redirect:/fileBbsMain.do";
		}
	}
	
	// 자유게시판 글 수정.POST
	@RequestMapping(value = "/fileBbsDetail.do", method = RequestMethod.POST)
	public String updateModifyFileBbs(BbsDTO dto, Model model, MultipartFile[] filename,String[] files) {
		log.info("Welcome 글 수정 값 보내기, {},{}", dto,Arrays.toString(files));

		dto = service.updateModifyFileBbs(dto,files,filename);
		String page = "0";
		if(dto!=null) {
			model.addAttribute("seq",dto);
			List<FileDTO> list = service.selectFileBbsFile(String.valueOf(dto.getSeq()));
			model.addAttribute("list", list);

			RowNumDTO row = new RowNumDTO();

			row.setTotal(cService.selectFileCommentListTotal(String.valueOf(dto.getSeq())));
			row.setListNum(5);
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
			List<CommentDTO> lists = null;
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("parent_seq", dto.getSeq());
			map.put("start", row.getStart());
			map.put("last", row.getLast());
			lists = cService.selectFileCommentListRow(map);
			model.addAttribute("lists", lists);
			return "Bbs/fileBbsDetail";
		}else {
			return "redirect:/fileBbsMain.do";
		}
	}
	
	// 자유게시판 글 단일삭제
	@RequestMapping(value = "/fileDel.do", method = RequestMethod.POST)
	public String updateDeleteFileBbs(SecurityContextHolder session, String seq) {
		log.info("Welcome 글 단일삭제 값 보내기, {}", seq);
		service.updateDeleteFileBbs(seq);

		return "redirect:/fileBbsMain.do";
	}
	
	// 자유게시판 글 다중삭제
	@RequestMapping(value = "/fileMultiDel.do", method = RequestMethod.POST)
	public String updateMultiDelFileBbs(String[] chkVal) {
		log.info("Welcome 글 다중삭제 ,\t {}", Arrays.toString(chkVal));
		boolean isc = false;
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", chkVal);
		isc = service.updateMultiDelFileBbs(map);

		return isc?"redirect:/fileBbsMain.do":"redirect:/logout.do";
	}
	
	// 자유게시판 답글 작성.GET
	@RequestMapping(value = "/fileBbsReplyInsert.do", method = RequestMethod.GET)
	public String goInsertFileReplyBbs(SecurityContextHolder session, Model model,String seq) {
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		model.addAttribute("Ldto", Ldto);
		model.addAttribute("seq",seq);

		return "Bbs/fileBbsReplyInsert";
	}
	
	// 자유게시판 답글 작성.POST
	@RequestMapping(value = "/fileBbsReplyInsert.do", method = RequestMethod.POST)
	public String insertFileReplyBbs(BbsDTO dto, SecurityContextHolder session, Model model, MultipartFile[] filename) {
		log.info("Welcome insertFileReplyBbs 답글 작성완료, {}", dto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		dto.setId(Ldto.getUsername());
		dto.setName(Ldto.getName());

		BbsDTO result = service.ReplyFile(dto, filename);
		List<FileDTO> list = service.selectFileBbsFile(String.valueOf(result.getSeq()));
		model.addAttribute("list", list);
		model.addAttribute("seq",result);

		return result!=null?"redirect:/fileBbsDetail.do?seq="+result.getSeq():"redirect:/fileBbsMain.do";
	}
	
	@RequestMapping(value = "/fileDownload.do", method = RequestMethod.GET)
	public void fileDownload(HttpServletResponse resp, FileDTO fDto) throws Exception {
		log.info("#################################3 {}",fDto);
		File file = new File(FreeBbsIService.ATTACH_PATH+"/"+fDto.getName());
		
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		String mimeType = URLConnection.guessContentTypeFromStream(inputStream);

		if (mimeType == null) {
			mimeType = "application/octec-stream";
		}
		resp.setContentType(mimeType);
		resp.setContentLength((int) file.length());
		//헤더에 해당 파일이 첨부 파일임을 명시
		resp.setHeader("Content-Disposition", String.format("attachment; fileName=%s", fDto.getName()));
		log.info(file.getName() + "@@@@@@@@@@@@@@@@@@@@@@@@@@");
		//파일 자체를 웹브라우저에서 읽어들인다. 
		FileCopyUtils.copy(inputStream, resp.getOutputStream());
	}
}
