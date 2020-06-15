package com.hexa.core.ctrl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.CommentDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.model.bbs.inf.FileComIService;

@Controller
public class FileComCtrl {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileComIService service;
	
	@RequestMapping(value = "/fileCommentWrite.do", method = RequestMethod.POST)
	public String insertFileComment(CommentDTO cDto, SecurityContextHolder session) {
		log.info("Welcome insertFileComment,\t {}",cDto);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		cDto.setId(Ldto.getUsername());
		cDto.setName(Ldto.getName());
		
		boolean isc = false;
		isc = service.insertFileComment(cDto);
		
		return isc?"redirect:/fileBbsDetail.do?seq="+cDto.getParent_seq():"redirect:/fileBbsMain.do";
	}
	
	@RequestMapping(value = "/fileCommentDelete.do", method = RequestMethod.GET)
	public String deleteFileComment(String seq, BbsDTO bDto, SecurityContextHolder session, String parent_seq) {
		log.info("Welcome deleteFileComment,\t {}", seq);
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		seq = String.valueOf(bDto.getSeq());
		
		Collection<GrantedAuthority> a = Ldto.getAuthorities();
		String auth_check = null;
		for (GrantedAuthority auths : a) {
			auth_check = auths.getAuthority();
		}

		boolean isc = false;
		
		if(auth_check.trim().equalsIgnoreCase("role_admin")) {
			isc = service.deleteFileAdminComment(seq);
		}else {
			Map<String, String> map = new HashMap<String, String>();
			map.put("seq", seq);
			map.put("id", Ldto.getUsername());
			isc = service.deleteFileUserComment(map);
		}
		return isc?"redirect:/fileBbsDetail.do?seq="+parent_seq:"redirect:/fileBbsMain.do";
	}
}
