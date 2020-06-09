package com.hexa.core.ctrl;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class EapprCtrl1 {
	
	@Autowired
	private EapprIService service;
	@Autowired
	private SearchIService sService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// 전자결재 홈 화면으로 이동
	@RequestMapping(value = "/goEapprHome.do", method = RequestMethod.GET)
	public String EapprHome(Principal scInfo, Model model) {
		String userId = scInfo.getName();
		int count1 = service.selectReportCount(userId);
		model.addAttribute("count1", count1);
		for (int i = 0; i < 5; i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", userId);
			map.put("state", i);
			int count =service.selectMyDocCount(map);
			model.addAttribute("count"+(i+2), count);
		}
		int count7 = service.selectReferDocCount(userId);
		int count8 = service.selectApprDocCount(userId);
		int count9 = service.selectNeedApprDocCount(userId);
		model.addAttribute("count7", count7);
		model.addAttribute("count8", count8);
		model.addAttribute("count9", count9);
		List<DocumentTypeDTO> list = service.selectDocTypeList();
		model.addAttribute("list", list);
		
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
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/goDocTypePreview.do", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String DocTypePreview(String type_seq, Model model) {
		log.info("seq값 확인 : {}", type_seq);
		DocumentTypeDTO dto = service.selectDocType(type_seq);
		log.info("dto 확인 : {}", dto);
		model.addAttribute("dto", dto);
		JSONObject json = new JSONObject();
		json.put("content", dto.getContent());
		json.put("type_seq", dto.getType_seq());
		return json.toString();
	}
	
	// 문서 작성 화면으로 이동
	@RequestMapping(value = "/goDocWriteForm.do", method = RequestMethod.GET)
	public String DocWriteForm(String type_seq, Model model) {
		DocumentTypeDTO dto = service.selectDocType(type_seq);
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
		sService.addDocIndex(newDto);
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
	public String DocTypeMng(Model model, String page) {
		
		if(page==null) {
			page="0";
		}
		RowNumDTO row = new RowNumDTO();
		row.setTotal(service.selectDocTypeListSize());
		row.setPageNum(3);
		row.setListNum(10);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		List<DocumentTypeDTO> list = service.selectDocTypeListP(row);
		model.addAttribute("list", list);
		model.addAttribute("row", row);
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
		log.info("##########################seq 확인 : {}", seq);
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
	
	// 상신문서함 목록으로 이동
	@RequestMapping(value = "/goMyDocList.do", method = RequestMethod.GET)
	public String MyDocList(Principal userId, int state, String page, Model model) {
		log.info("####################3아이디 확인 {}",userId);
		log.info("####################3아이디 확인 {}",state);
		log.info("####################3아이디 확인 {}",page);
		if(page==null) {
			page="0";
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId.getName());
		map.put("state", state);
		int count = service.selectMyDocCount(map);
		RowNumDTO row = new RowNumDTO();
		row.setTotal(count);
		row.setPageNum(3);
		row.setListNum(10);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		map.put("start", row.getStart());
		map.put("last", row.getLast());
		List<DocumentDTO> list = service.selectMyDocList(map);
		model.addAttribute("list", list);
		model.addAttribute("row", row);
		model.addAttribute("state", state);
		return "eappr/myDocList";
	}
	
}