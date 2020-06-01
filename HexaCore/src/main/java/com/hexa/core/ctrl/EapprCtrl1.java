package com.hexa.core.ctrl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.model.eappr.inf.EapprIService;

@Controller
public class EapprCtrl1 {
	
	@Autowired
	private EapprIService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 전자결재 홈 화면으로 이동
	@RequestMapping(value = "/goEapprHome.do", method = RequestMethod.GET)
	public String EapprHome() {
		return "eappr/eapprHome";
	}
	
	// 문서 양식 리스트 화면으로 이동
	@RequestMapping(value = "/goDocTypeList.do", method = RequestMethod.GET)
	public String DocTypeList(Model model) {
		List<DocumentTypeDTO> list = service.selectDocTypeList();
		model.addAttribute("list", list);
		return "eappr/docTypeList";
	}
	
	// 문서 양식 미리보기 화면으로 이동
	@RequestMapping(value = "/goDocTypePreview.do", method = RequestMethod.GET)
	public String DocTypePreview(String type_seq, Model model) {
		log.info("seq값 확인 : {}", type_seq);
		DocumentTypeDTO dto = service.selectDocType(type_seq);
		log.info("dto 확인 : {}", dto);
		model.addAttribute("dto", dto);
		return "eappr/docTypePreview";
	}
	
	// 문서 작성 화면으로 이동
	@RequestMapping(value = "/goDocWriteForm.do", method = RequestMethod.POST)
	public String DocTypePreview(DocumentTypeDTO dto, Model model) {
		model.addAttribute("dto", dto);
		return "eappr/DocWriteForm";
	}
	
	// 문서 작성
		@RequestMapping(value = "/DocWrite.do", method = RequestMethod.POST)
		public String DocDetail(DocumentDTO dto, Model model) {
			log.info("리스트 확인 : {}", dto.getLists().get(0));
			model.addAttribute("dto", dto);
			return "eappr/docDetail";
		}
}
