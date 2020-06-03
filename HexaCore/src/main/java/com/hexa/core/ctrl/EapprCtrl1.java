package com.hexa.core.ctrl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
		model.addAttribute("typeDto", dto);
		return "eappr/DocWriteForm";
	}
	
	// 문서 작성
	@Transactional
	@RequestMapping(value = "/DocWrite.do", method = RequestMethod.POST)
	public String DocDetail(DocumentDTO dto, Model model) {
		service.insertNewDoc(dto);
		String seq = service.selectNewDoc();
		int sseq = Integer.parseInt(seq);
		for (int i = 0; i < dto.getLists().size(); i++) {
			dto.getLists().get(i).setSeq(sseq);
			service.insertApprRoot(dto.getLists().get(i));
		}
		DocumentDTO newDto = service.selectDoc(seq);
		model.addAttribute("dto", newDto);
		return "eappr/docDetail";
	}
	
	// 결재루트 지정 트리로 가기
	@RequestMapping(value = "/goApprTree.do", method = RequestMethod.GET)
	public String ApprTree() {
		return "eappr/apprTree";
	}
	
	// 문서 양식 관리 탭
	@RequestMapping(value = "/goDocTypeMng.do", method = RequestMethod.GET)
	public String DocTypeMng(Model model) {
		List<DocumentTypeDTO> list = service.selectDocTypeList();
		model.addAttribute("list", list);
		return "eappr/docTypeMng";
	}
	
	// 문서 양식 추가 화면으로 이동
	@RequestMapping(value = "/goDocTypeWriteForm.do", method = RequestMethod.GET)
	public String DocTypeMng() {
		return "eappr/docTypeWriteForm";
	}
	
	// 문서 양식 추가
	@Transactional
	@RequestMapping(value = "/writeDocType.do", method = RequestMethod.POST)
	public String WriteDocType(DocumentTypeDTO dto, Model model) {
		service.insertDocType(dto);
		String seq = service.selectNewDocType();
		DocumentTypeDTO tDto = service.selectDocType(seq);
		model.addAttribute("dto", tDto);
		return "eappr/docTypeDetail";
	}
	
	// 문서 양식 상세보기
	@RequestMapping(value = "/goDocTypeDetail.do", method = RequestMethod.GET)
	public String DocTypeDetail(String seq, Model model) {
		DocumentTypeDTO dto = service.selectDocType(seq);
		model.addAttribute("dto", dto);
		return "eappr/docTypeDetail";
	}
	
	// 문서 양식 삭제
	@RequestMapping(value = "/deleteDocType.do", method = RequestMethod.GET)
	public String DeleteDocType(String seq) {
		service.deleteDocType(seq);
		return "redirect:/goDocTypeMng.do";
	}
	
	// 문서 양식 수정 화면으로 이동
	@RequestMapping(value = "/goDocTypeModifyForm.do", method = RequestMethod.POST)
	public String DocTypeModifyForm(DocumentTypeDTO dto, Model model) {
		model.addAttribute("dto", dto);
		return "eappr/docTypeModifyForm";
	}
	
	// 문서 양식 수정
	@RequestMapping(value = "/DocTypeUpdate.do", method = RequestMethod.POST)
	public String UpdateDocType(DocumentTypeDTO dto, Model model) {
		service.updateDocType(dto);
		model.addAttribute("dto", dto);
		return "redirect:/goDocTypeDetail.do?seq="+dto.getType_seq();
	}
	
}