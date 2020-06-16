package com.hexa.core.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.eappr.inf.EapprIService;

@Controller
public class EapprCtrl1 {
	
	@Autowired
	private EapprIService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	// ------------------------------ 전자결재 메인, 홈 화면 관련 컨트롤러 ------------------------------
	
	// 전자결재 메인 화면으로 이동(사이드바 관련 데이터 가져오기)
	@RequestMapping(value="/goEapprMain.do")
	public String mngMain(Model model) {
		List<DocumentTypeDTO> docTypeList = service.selectDocTypeList(); // 문서 양식 리스트 가져오기
		model.addAttribute("docTypeList", docTypeList);
		return "eappr/eapprMain";
	}
	
	// 전자결재 홈 화면에 출력할 데이터 가져오기(iframe 내에 데이터 가져오기)
	@RequestMapping(value = "/goEapprHome.do", method = RequestMethod.GET)
	public String EapprHome(Principal scInfo, Model model) {
		String userId = scInfo.getName();
		Map<String, Object> docCounts = service.selectDocListAll(userId); // 각 문서함의 문서 개수들 가져오기
		model.addAttribute("docCounts",docCounts);
		return "eappr/eapprHome";
	}
	
	// 문서 양식 미리보기 (Ajax)
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/goDocTypePreview.do", method = RequestMethod.GET, produces = "application/text; charset=utf8")
	public String DocTypePreview(String type_seq, Model model) {
		DocumentTypeDTO dto = service.selectDocType(type_seq); // 문서 양식 가져오기
		model.addAttribute("dto", dto);
		JSONObject json = new JSONObject();
		json.put("content", dto.getContent());
		json.put("type_seq", dto.getType_seq());
		return json.toString();
	}
	
	// ------------------------------ 문서 작성 관련 컨트롤러 ------------------------------
	
	// 문서 작성 화면으로 이동
	@RequestMapping(value = "/goDocWriteForm.do", method = RequestMethod.GET)
	public String DocWriteForm(String type_seq, Model model) {
		DocumentTypeDTO dto = service.selectDocType(type_seq); // 문서 양식 가져오기
		model.addAttribute("typeDto", dto);
		return "eappr/docWriteForm";
	}
	
	// 작성된 문서 DB에 저장하고 작성 글 상세보기 화면으로 이동
	@RequestMapping(value = "/DocWrite.do", method = RequestMethod.POST)
	public String DocDetail(DocumentDTO dto, Model model, MultipartFile[] filename) {
		int seq = service.insertNewDoc(dto, filename); // 문서 내용, 결재자 루트, 첨부파일 DB에 저장, 새로 작성된 전자문서 SEQ값 반환
		return "redirect:/docDetail.do?seq="+seq;
	}
	
	// 결재자, 참조자 선택하기 위한 트리(팝업 화면에서 이동)
	@RequestMapping(value = "/goApprTree.do", method = RequestMethod.GET)
	public String ApprTree() {
		return "eappr/apprTree";
	}
	
	// ------------------------------ 문서 양식 관리 관련 컨트롤러 ------------------------------
	
	// 문서 양식 관리로 이동(문서 양식 리스트 출력)
	@RequestMapping(value = "/goDocTypeMng.do", method = RequestMethod.GET)
	public String DocTypeMng(Model model, String page) {
		
		// 문서 양식 리스트 페이징 처리
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
		List<DocumentTypeDTO> list = service.selectDocTypeListP(row); // 페이지에 해당하는 양식 리스트 가져오기
		model.addAttribute("list", list);
		model.addAttribute("row", row);
		return "eappr/docTypeMng";
	}
	
	// 문서 양식 추가 화면으로 이동
	@RequestMapping(value = "/goDocTypeWriteForm.do", method = RequestMethod.GET)
	public String DocTypeMng() {
		return "eappr/docTypeWriteForm";
	}
	
	// 추가한 문서 양식 DB에 저장하고 추가한 양식 상세보기 화면으로 이동
	// ------------------------------ 서비스 너무 많음 한개로 합치기
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
		DocumentTypeDTO dto = service.selectDocType(seq); // 문서 양식 정보 가져오기
		model.addAttribute("dto", dto);
		return "eappr/docTypeDetail";
	}
	
	// 문서 양식 삭제
	@RequestMapping(value = "/deleteDocType.do", method = RequestMethod.GET)
	public String DeleteDocType(String seq) {
		service.deleteDocType(seq); // 해당 문서 양식 DB에서 삭제
		return "redirect:/goDocTypeMng.do";
	}
	
	// 문서 양식 수정 화면으로 이동
	@RequestMapping(value = "/goDocTypeModifyForm.do", method = RequestMethod.GET)
	public String DocTypeModifyForm(String type_seq, Model model) {
		DocumentTypeDTO dto = service.selectDocType(type_seq);
		model.addAttribute("dto", dto);
		return "eappr/docTypeModifyForm";
	}
	
	// 문서 양식 수정하고 수정한 문서 양식 화면으로 이동
	@RequestMapping(value = "/DocTypeUpdate.do", method = RequestMethod.POST)
	public String UpdateDocType(DocumentTypeDTO dto, Model model) {
		service.updateDocType(dto); // 수정한 문서 양식의 데이터 DB에서 수정
		model.addAttribute("dto", dto);
		return "redirect:/goDocTypeDetail.do?seq="+dto.getType_seq();
	}
	
	// 파일 다운로드
	@RequestMapping(value = "/fdownload.do", method = RequestMethod.GET)
	public void fileDownload(HttpServletResponse resp, DocFileDTO fDto) throws Exception {
		log.info("#################################3 {}",fDto);
		File file = new File(EapprIService.ATTACH_PATH+"/"+fDto.getName());
		
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