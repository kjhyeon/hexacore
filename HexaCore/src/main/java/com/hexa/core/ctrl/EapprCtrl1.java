package com.hexa.core.ctrl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.model.eappr.inf.EapprIService;

@Controller
public class EapprCtrl1 {
	
	@Autowired
	private EapprIService service;
	
	// 전자결재 홈 화면으로 이동
	@RequestMapping(value = "/goEapprHome.do", method = RequestMethod.GET)
	public String EapprHome() {
		return "eappr/eapprHome";
	}
	
	// 문서 양식 리스트 화면으로 이동
	@RequestMapping(value = "/goDocTypeList.do", method = RequestMethod.GET)
	public String DocTypeList(Model model) {
		List<DocumentTypeDTO> list = service.docTypeList();
		model.addAttribute("list", list);
		return "eappr/docTypeList";
	}
	
	// 문서 양식 미리보기 화면으로 이동
	@RequestMapping(value = "/goDocTypePreview.do", method = RequestMethod.GET)
	public String DocTypePreview() {
		return "eappr/docTypeList";
	}
}
