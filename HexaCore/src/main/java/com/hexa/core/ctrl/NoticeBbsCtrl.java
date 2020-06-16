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
import com.hexa.core.model.bbs.inf.NoticeBbsIService;
import com.hexa.core.model.bbs.inf.NoticeComIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class NoticeBbsCtrl {

private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private NoticeBbsIService service;
	
	@Autowired
	private SearchIService sService;
	
	@Autowired
	private NoticeComIService cService;
	
	// 공지게시판 (관리자)목록 조회
		@RequestMapping(value = "/noticeBbsMain.do", method = RequestMethod.GET)
		public String noticeBbsMain(Model model, BbsDTO dto, SecurityContextHolder session, String page,String keyword,String type) {
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
				row.setTotal(sService.noticeBbsTotal(keyword, type,auth_check));
				model.addAttribute("keyword",keyword);
				model.addAttribute("type",type);
			}else if(auth_check.trim().equalsIgnoreCase("role_admin")){
				row.setTotal(service.selectAdminNoticeBoardListTotal());
			}else {
				row.setTotal(service.selectUserNoticeBoardListTotal());
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
				lists = sService.noticeBbsSearch(keyword, type, row,auth_check);
			}else if(!auth_check.trim().equalsIgnoreCase("role_admin")) {
				lists = service.selectUserNoticeBbsListRow(row);
			}else {	
				lists = service.selectAdminNoticeBbsListRow(row);
			}
			model.addAttribute("lists", lists);
			
			return "Bbs/noticeBbsMain";
		}
		// 공지게시판 새 글 작성.GET
		@RequestMapping(value = "/noticeBbsInsert.do", method = RequestMethod.GET)
		public String insertnoticeBbs(Model model, SecurityContextHolder session, BbsDTO dto) {
			log.info("Welcome insert 글 작성, {}");
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
			model.addAttribute("Ldto", Ldto);
			
			return "Bbs/noticeBbsInsert";
		}
	
		
		// 공지게시판 새 글 작성.POST
		@RequestMapping(value = "/noticeBbsInsert.do", method = RequestMethod.POST)
		public String insertnoticeBbs(String seq, BbsDTO dto, Model model,SecurityContextHolder session, MultipartFile[] filename) {
			log.info("Welcome insert 글 작성완료, {}/{}", dto, filename);
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
			dto.setId(Ldto.getUsername());
			dto.setName(Ldto.getName());
			
			BbsDTO result = service.insertNoticeBbs(dto,filename);
			List<FileDTO> list = service.selectNoticeBbsFile(String.valueOf(result.getSeq()));
			model.addAttribute("list", list);
			model.addAttribute("seq",result);
			return result!=null?"redirect:/noticeBbsDetail.do?seq="+result.getSeq():"redirect:/logdout.do";
		}
		
		// 공지게시판 글 상세보기.GET
		@RequestMapping(value = "/noticeBbsDetail.do", method = RequestMethod.GET)
		public String selectDetailnoticeBbs(Model model, String page, String seq, SecurityContextHolder session) {
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
			
			row.setTotal(cService.selectNoticeCommentListTotal(seq));
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
			lists = cService.selectNoticeCommentListRow(map);
			model.addAttribute("lists", lists);
			
			BbsDTO dto = null;
			if (!auth_check.trim().equalsIgnoreCase("role_admin")) {
				dto =service.updateViewsNoticeBbs(seq);
			}else {
				dto = service.selectDetailNoticeBbs(seq);
			}
			
			cDto.setParent_seq(Integer.parseInt(seq));
			
			List<FileDTO> list = service.selectNoticeBbsFile(seq);
			model.addAttribute("seq", dto);
			model.addAttribute("list", list);
			
			
			return "Bbs/noticeBbsDetail";
		}
		
		// 공지게시판 글 수정.GET
		@RequestMapping(value = "/noticeBbsModify.do", method = RequestMethod.GET)
		public String updateModifynoticeBbs(SecurityContextHolder session, Model model, BbsDTO dto, String seq) {
			log.info("Welcome 글 수정 값 가져오기, {}", dto);
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
			dto = service.selectDetailNoticeBbs(seq);
			List<FileDTO> list = service.selectNoticeBbsFile(seq);
			model.addAttribute("list", list);
			if(dto.getId().equals(Ldto.getUsername())) {
				model.addAttribute("dto", dto);
				return "Bbs/noticeBbsModify";
			}else{
				return "redirect:/noticeBbsMain.do";
			}
		}
		
		// 공지게시판 글 수정.POST
		@RequestMapping(value = "/noticeBbsDetail.do", method = RequestMethod.POST)
		public String updateModifynoticeBbs(BbsDTO dto, Model model, MultipartFile[] filename,String[] files) {
			log.info("Welcome 글 수정 값 보내기, {},{}", dto,Arrays.toString(files));
			
			dto = service.updateModifyNoticeBbs(dto,files,filename);
			String page = "0";
			if(dto!=null) {
				model.addAttribute("seq",dto);
				List<FileDTO> list = service.selectNoticeBbsFile(String.valueOf(dto.getSeq()));
				model.addAttribute("list", list);
						
				RowNumDTO row = new RowNumDTO();
				
				row.setTotal(cService.selectNoticeCommentListTotal(String.valueOf(dto.getSeq())));
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
				lists = cService.selectNoticeCommentListRow(map);
				model.addAttribute("lists", lists);
				return "Bbs/noticeBbsDetail";
			}else {
				return "redirect:/noticeBbsMain.do";
			}
		}
		
		// 공지게시판 글 단일삭제
		@RequestMapping(value = "/Noticedel.do", method = RequestMethod.POST)
		public String updateDeletenoticeBbs(SecurityContextHolder session, String seq) {
			log.info("Welcome 글 단일삭제 값 보내기, {}", seq);
			service.updateDeleteNoticeBbs(seq);
			return "redirect:/noticeBbsMain.do";
		}
		
		// 공지게시판 글 다중삭제
		@RequestMapping(value = "/NoticeMultiDel.do", method = RequestMethod.POST)
		public String updateMultiDelnoticeBbs(String[] chkVal) {
			log.info("Welcome 글 다중삭제 ,\t {}", Arrays.toString(chkVal));
			boolean isc = false;
			Map<String, String[]> map = new HashMap<String, String[]>();
			map.put("seqs", chkVal);
			isc = service.updateMultiDelNoticeBbs(map);
			
			return isc?"redirect:/noticeBbsMain.do":"redirect:/logout.do";
		}
		
		
		// 공지게시판 답글 작성.GET
		@RequestMapping(value = "/noticeBbsReplyInsert.do", method = RequestMethod.GET)
		public String goInsertReplyBbs(SecurityContextHolder session, Model model,String seq) {
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
			model.addAttribute("Ldto", Ldto);
			model.addAttribute("seq",seq);
			
			return "Bbs/noticeBbsReplyInsert";
		}
		
		// 공지게시판 답글 작성.POST
		@RequestMapping(value = "/noticeBbsReplyInsert.do", method = RequestMethod.POST)
		public String insertReplyBbs(BbsDTO dto, SecurityContextHolder session, Model model, MultipartFile[] filename) {
			log.info("Welcome insertReplyBbs 답글 작성완료, {}", dto);
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
			dto.setId(Ldto.getUsername());
			dto.setName(Ldto.getName());

			BbsDTO result = service.ReplyNotice(dto, filename);
			List<FileDTO> list = service.selectNoticeBbsFile(String.valueOf(result.getSeq()));
			model.addAttribute("list", list);
			model.addAttribute("seq",result);
			
			return result!=null?"redirect:/noticeBbsDetail.do?seq="+result.getSeq():"redirect:/noticeBbsMain.do";
		}
		
		// 공지게시판 파일 다운로드
		@RequestMapping(value = "/Noticedownload.do", method = RequestMethod.GET)
		public void fileDownload(HttpServletResponse resp, FileDTO fDto) throws Exception {
			log.info("#################################3 {}",fDto);
			File file = new File(NoticeBbsIService.ATTACH_PATH+"/"+fDto.getName());
			
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

