package com.hexa.core.ctrl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URLConnection;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.CalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.cal.inf.Calendar_IService;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.mng.inf.EmployeeIService;
import com.hexa.core.model.msg.inf.MessageIService;

@Controller
public class EapprCtrl {
	
	@Autowired
	private EapprIService service;
	@Autowired
	private EmployeeIService EService;
	@Autowired
	private Calendar_IService CService;
	@Autowired
	private MessageIService MService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
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
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", userId);
		map.put("start",1);
		map.put("last",5);
		map.put("state", "7");
		List<DocumentDTO> lists =service.selectMyDocList(map);
		//임시저장문서0127
		model.addAttribute("lists",lists);
		model.addAttribute("id",userId);
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
	
	//알림 카운트
		@ResponseBody
		@RequestMapping(value="/needCnt",method=RequestMethod.POST)
		public Map<String, Object> needCnt(Principal principal,int beforeCnt,int beforeMCnt) {
			String userId = principal.getName();
			Map<String,Object> docCount = service.selectDocListAll(userId); // 결재 필요 문서 개수 가져오기
			int msgCount = MService.selectNewMsgCount(userId);
			int eapprCnt = Integer.parseInt(docCount.get("COUNT3").toString());
			Map<String, Object> map = new HashMap<String, Object>();
			if(beforeCnt<eapprCnt) {
				map.put("eapprCnt",docCount.get("COUNT3").toString());
			}else {
				map.put("eapprCnt",Integer.toString(beforeCnt));
			}
			if(beforeMCnt<msgCount) {
				map.put("msgCnt", msgCount);
			}else {
				map.put("msgCnt", beforeMCnt);
			}
			return map;
		}
		
		//문서함 List 조회 (분기)
		@RequestMapping(value = "/docLists.do", method = RequestMethod.GET,produces = "applicaton/text; charset=UTF-8;")
		public String docLists(Model model,Principal principal, String page,String state) {
			    String id = principal.getName();
			if(page==null) {
				page="0";
			}
			RowNumDTO row = new RowNumDTO();
			Map<String, Object> map = new HashMap<String, Object>();
			List<DocumentDTO> lists =null;
				map.put("id", id);
				map.put("state",state);
			log.info("카운트{}",state);
			
			//문서갯수 조회
			int count=service.selectAllApprDocCount(map);
				row.setTotal(count);
				row.setPageNum(3);
				row.setListNum(15);
			if(row.getLastPage()-1<Integer.parseInt(page)) {
				row.setIndex(row.getLastPage()-1);
			}else if(Integer.parseInt(page)<0) {
				row.setIndex(0);
			}else {
				row.setIndex(Integer.parseInt(page));
			}
				map.put("start",row.getStart());
				map.put("last",row.getLast());
				
			//문서 list 조회
			lists = service.selectMyDocList(map);
			
			model.addAttribute("lists",lists);
			model.addAttribute("row", row);
			model.addAttribute("id",id);
			model.addAttribute("state",state);
			return "eappr/docLists";
		}
		
		//문서 상세조회
		@Transactional
		@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
		public String updateDoc(Model model,Principal principal,ApprovalDTO ADto) {
			String userId = principal.getName();
			ADto.setId(userId);
			Map<String, Object> map = service.goDetail(ADto);
			model.addAttribute("turn",map.get("turn"));
			model.addAttribute("typeDto",map.get("typeDto"));
			model.addAttribute("comment",map.get("comment"));
			model.addAttribute("Ddto",map.get("Ddto"));
			model.addAttribute("name",userId);
			model.addAttribute("apprList",map.get("apprList"));
			model.addAttribute("flist", map.get("flist"));
			return "eappr/docDetail";
		}	
		
		//문서수정 Ajax
		@ResponseBody
		@RequestMapping(value="/modifyFormDoc.do", method= RequestMethod.POST)
		public Map<String, Object> modifyDoc(Model model,ApprovalDTO Adto) {
			DocumentDTO Ddto = service.selectDoc(Integer.toString(Adto.getSeq()));
			log.info("********Ddto:{}", Ddto);
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("title",Ddto.getTitle());
			map.put("content", Ddto.getContent());
			return map;
		}
		
		//문서 삭제
		@RequestMapping(value="/deleteDoc.do", method= RequestMethod.GET)
		public String deleteDoc(String seq,String state) {
			boolean isc = service.deleteDoc(seq);
			return isc?"redirect:/docLists.do?state="+state:"redirect:/eApprMain.do";
		}
		
		//임시저장
		@Transactional
		@RequestMapping(value="/saveUpDoc.do", method= RequestMethod.POST)
		public String saveUpDoc(DocumentDTO Ddto, MultipartFile[] filename) {
			log.info("********Ddto:{}", Ddto);
			Ddto.setState(0);
			Ddto.setAppr_turn(1);
			boolean isc=false;
			if(Ddto.getLists().size()>2) {
				isc = service.saveUpDoc(Ddto,filename);
			}
			return (isc)?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
		}
		
		//상세보기 _상신/취소 기능
		@RequestMapping(value="/upApprDoc.do", method=RequestMethod.POST)
		public String upApprDoc(DocumentDTO Ddto) {
			boolean isc = service.upApprDoc(Ddto);
			return (isc)?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
		}
		
		//수정_상신
		@RequestMapping(value="/reportDoc.do",method=RequestMethod.POST)
		public String reportDoc(DocumentDTO Ddto,MultipartFile[] filename) {
			Ddto.setState(1);
			Ddto.setAppr_turn(1);
			boolean isc = service.saveUpDoc(Ddto,filename);
			return (isc)?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
		}
		
		//문서 승인 modal
		@SuppressWarnings("unchecked")
		@RequestMapping(value="/apprDoc.do", method= RequestMethod.POST ,produces = "applicaton/text; charset=UTF-8;")
		@ResponseBody
		public String apprDoc(ApprovalDTO Adto, DocumentDTO Ddto, Principal principal) {
			JSONObject json = new JSONObject();
			log.info("********Adto:{}",Adto);
			Adto.setId(principal.getName());
			List<ApprovalDTO> lists = service.selectApprRoot(Adto);
			log.info("********lists:{}",lists);
			json.put("seq", lists.get(0).getSeq());
			json.put("id", lists.get(0).getId());
			json.put("name", lists.get(0).getName());
			json.put("turn", lists.get(0).getTurn());
			json.put("appr_turn",Ddto.getAppr_turn());
			json.put("a_turn",Ddto.getA_turn());
			json.put("number",Ddto.getState());
			log.info("Welcome apprDoc 결과, {}",json.toString());
			return json.toString();
		}

		//비밀번호 Check
		@ResponseBody
		@RequestMapping(value="/checkPassword", method=RequestMethod.POST)
		public String checkPassword(String password , Principal principal) {
			log.info("********password:{}",password);
			EmployeeDTO EDto =  EService.selectLoginInfo(principal.getName());
			if(passwordEncoder.matches(password, EDto.getPassword())) {
				return "true";
			}else {
				return "false";
			}
			
		}
		
		//승인,반려 기능
		@RequestMapping(value="/confirmDoc.do", method= RequestMethod.POST)
		public String confirmDoc(ApprovalDTO Adto, DocumentDTO Ddto, DocCommentDTO DCdto) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("Ddto", Ddto);
			map.put("Adto", Adto);
			map.put("DCdto", DCdto);
			boolean isc = service.confirmUpdate(map);
			return isc?"redirect:/docLists.do?state=7":"redirec:/eApprmain.do";
		}
		
		//캘린더 리스트 Ajax
		@SuppressWarnings("unchecked")
		@ResponseBody
		@RequestMapping(value="/getCals.do", method = RequestMethod.POST)
		public JSONObject getEventsCal(CalDTO CDto) {
			log.info("---------{}",CDto);
			Map<String, Object> map = new HashMap<String, Object>();
			JSONArray jLists = new JSONArray();
			JSONObject jdto = new JSONObject();
			map.put("start",CDto.getStart_date());
			map.put("end",CDto.getEnd_date());
			List<CalDTO> lists = CService.selectEventsCal(map);
			
			for(CalDTO Cdtos : lists) { 
				JSONObject json = new JSONObject();
				json.put("id", Cdtos.getId());
				json.put("start",Cdtos.getStart_date());
				json.put("end",Cdtos.getEnd_date());
				json.put("title",Cdtos.getTitle());
				jLists.add(json);
			}
			jdto.put("lists", jLists);
			log.info("꺅2{}",jLists);
			
			return jdto;
		}
		
		//이벤트추가
		@RequestMapping(value = "/addEventCals.do", method= RequestMethod.POST)
		public String addEventCals(CalDTO CDto,Principal principal) {
			log.info("addEventCals{}",CDto);
			CDto.setId(principal.getName());
			int n = CService.insertEventsCal(CDto);
			return (n>0)?"redirect:/result.do":"redirect:/result.do";
		}
		
		//이벤트 삭제
		@RequestMapping(value = "/deleteCal.do", method= RequestMethod.POST)
		public String deleteCal(String title) {
			log.info("삭제{}",title);
			int n = CService.deleteEventsCal(title);
			return (n>0)?"redirect:/result.do":"redirect:/result.do";
		}
	
}