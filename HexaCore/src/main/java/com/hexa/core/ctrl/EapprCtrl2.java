package com.hexa.core.ctrl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.mng.inf.EmployeeIService;
@SuppressWarnings("static-access")
@Controller
public class EapprCtrl2 {
	
	@Autowired
	private EapprIService service;
	@Autowired
	private EmployeeIService EService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	//문서함 List 조회 (분기)
	@RequestMapping(value = "/docLists.do", method = RequestMethod.GET,produces = "applicaton/text; charset=UTF-8;")
	public String docLists(Model model,SecurityContextHolder session, String page,String state) {
		 Authentication auth = session.getContext().getAuthentication();
		 LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		    String id = Ldto.getUsername();
		if(page==null) {
			page="0";
		}
		RowNumDTO row = new RowNumDTO();
		Map<String, Object> map = new HashMap<String, Object>();
		List<DocumentDTO> lists =null;
		map.put("id", id);
		map.put("start",row.getStart());
		map.put("last",row.getLast());
		map.put("state",state);
		//문서 list 조회
		lists = service.selectMyDocList(map);
		log.info("문서문서문서{}",lists);
		//결재할 문서 갯수 조회
		int count=service.selectNeedApprDocCount(map);
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
		model.addAttribute("lists",lists);
		model.addAttribute("row", row);
		model.addAttribute("id",id);
		model.addAttribute("number",state);
		return "eappr2/docConfirmLists";
	}
	
	//문서 상세조회
	@Transactional
	@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
	public String updateDoc(Model model,SecurityContextHolder session,ApprovalDTO Adto,String number) {
		log.info("받아온 seq 값: {}", Adto.getSeq());
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		String id = Ldto.getUsername();
		String seq = Integer.toString(Adto.getSeq());
		String attach_path = "C:\\eclipse-spring\\git\\hexacore\\HexaCore\\src\\main\\webapp\\image\\profile\\";
		//믄사 내용 조회
		DocumentDTO Ddto = service.selectDoc(seq);
		//문서 결재선 전체 조회
		List<ApprovalDTO> listsb = service.selectApprRoot(Adto);
		log.info("문서리스트{}",listsb);
		for (int i = 0; i < listsb.size(); i++) {
			if(listsb.get(i).getAppr_sign()!=null) {
				listsb.get(i).setAppr_sign(attach_path+listsb.get(i).getAppr_sign()); 
			}
		}
		//문서 나의 결재선 조회
		Adto.setId(id);
		List<ApprovalDTO> listsa = service.selectApprRoot(Adto);
		log.info("********listsa:{}",listsa);
		log.info("********listsa Size:{}",listsa.size());
		int turn = 0;
		if(listsa!=null && listsa.size()!=0) {
			turn = listsa.get(0).getTurn();
			model.addAttribute("turn",turn);
		}
		log.info("********turn:{}",turn);
		
		//결재자 코멘트 가져오기
		List<DocCommentDTO> lists = service.selectComment(seq);
//		log.info("********commentlists:{}",lists);
//		log.info("********Adto:{}",Adto);
//		log.info("********approvalLine:{}",listsb);
//		log.info("********number:{}", number);
//		log.info("********Ddto:{}", Ddto);
		//해당문서 코멘트 조회
		//독타입 가져오기
		DocumentTypeDTO TDto = service.selectDocType(Integer.toString(Ddto.getType_seq()));
		model.addAttribute("typeDto",TDto);
		model.addAttribute("comment",lists);
		model.addAttribute("Ddto",Ddto);
		model.addAttribute("name",id);
		model.addAttribute("approvalLine",listsb);
		model.addAttribute("number",number);
		return "eappr2/docDetail";
	}	
	
	//문서수정 Ajax
	@ResponseBody
	@RequestMapping(value="/modifyFormDoc.do", method= RequestMethod.POST)
	public Map<String, Object> modifyDoc(Model model,ApprovalDTO Adto) {
		DocumentDTO Ddto = service.selectDoc(Integer.toString(Adto.getSeq()));
		List<ApprovalDTO> listsb = service.selectApprRoot(Adto);
		log.info("********Adto:{}",listsb);
		log.info("********Ddto:{}", Ddto);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title",Ddto.getTitle());
		map.put("content", Ddto.getContent());
//		map.put("typeDto",TDto);
		return map;
	}
	
	//문서 삭제
	@RequestMapping(value="/deleteDoc.do", method= RequestMethod.POST)
	public String deleteDoc(String seq) {
		service.deleteDoc(seq);
		return "docLists.do";//리스트로 보내자
	}
	
	//임시저장
	@Transactional
	@RequestMapping(value="/saveUpDoc.do", method= RequestMethod.POST)
	public String saveUpDoc(DocumentDTO Ddto) {
		log.info("********Ddto:{}", Ddto);
		log.info("********수정DdtoList:{}", Ddto.getLists());
		Ddto.setAppr_turn(0);
		Ddto.setState(0);
		boolean isc = service.updateDoc(Ddto);
		log.info("********DdtoList:{}",Ddto.getLists());
		
		if(Ddto.getLists()!=null) {
			service.insertApprRoot(Ddto);
		}
		return isc?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
	}
	
	//문서승인 Modal Ajax
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/apprDoc.do", method= RequestMethod.POST ,produces = "applicaton/text; charset=UTF-8;")
	@ResponseBody
	public String apprDoc(ApprovalDTO Adto, int appr_turn, int a_turn ,String number, SecurityContextHolder session) {
		JSONObject json = new JSONObject();
		log.info("********Adto:{}",Adto);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		Adto.setId(dto.getUsername());
		List<ApprovalDTO> lists = service.selectApprRoot(Adto);
		log.info("********lists:{}",lists);
		json.put("seq", lists.get(0).getSeq());
		json.put("id", lists.get(0).getId());
		json.put("name", lists.get(0).getName());
		json.put("turn", lists.get(0).getTurn());
		json.put("appr_turn",appr_turn);
		json.put("a_turn",a_turn);
		json.put("number",number);
		log.info("Welcome apprDoc 결과, {}",json.toString());
		return json.toString();
	}

	//비밀번호 Check
	@ResponseBody
	@RequestMapping(value="/checkPassword", method=RequestMethod.POST)
	public String checkPassword(String password , SecurityContextHolder session) {
		log.info("********password:{}",password);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		EmployeeDTO EDto =  EService.selectLoginInfo(dto.getUsername());
		if(passwordEncoder.matches(password, EDto.getPassword())) {
			return "true";
		}else {
			return "false";
		}
		
	}
	
	//승인,반려 기능

	@RequestMapping(value="/confirmDoc.do", method= RequestMethod.POST)
	public String confirmDoc(ApprovalDTO Adto, DocumentDTO Ddto, DocCommentDTO DCdto,String state,SecurityContextHolder session) {
		log.info("********confirm Test Adto:{}",Adto);
		log.info("********confirm Test Ddto:{}",Ddto);
		log.info("********confirm Test DCdto:{}",DCdto);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		DCdto.setName(dto.getName());
		DCdto.setComment_seq(Adto.getTurn());
		Adto.setName(dto.getName());
		Adto.setAppr_sign(service.selectSignImg(dto.getUsername()));
		
		int appr_turn = Ddto.getAppr_turn();
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isc = false;
		if(Adto.getChk().equalsIgnoreCase("T")) {//승인일경우
				if(Adto.getTurn()<3) {
					Ddto.setAppr_turn(appr_turn+1);
					Ddto.setState(2);
					Adto.setAppr_kind("승인");
				}else if(Adto.getTurn()==3){
					Ddto.setState(3);
					Adto.setAppr_kind("승인");
				}
				map.put("Ddto", Ddto);
				map.put("Adto", Adto);
				map.put("DCdto", DCdto);
				isc = service.confirmUpdate(map);
//				service.updateDoc(Ddto);//appr_turn 수정 / state 수정
//				service.updateApprChk(Adto);//chk 업데이트 사인 업데이트
//				service.insertComment(DCdto);//코멘트 입력
				
			}else if(Adto.getChk().equalsIgnoreCase("R")){
				Adto.setAppr_kind("반려");
				Ddto.setState(4);
				log.info("********confirm Test2R Adto:{}",Adto);
				log.info("********confirm Test2R Ddto:{}",Ddto);
				log.info("********confirm Test2R DCdto:{}",DCdto);
				map.put("Ddto", Ddto);
				map.put("Adto", Adto);
				map.put("DCdto", DCdto);
				isc = service.confirmUpdate(map);
//				service.updateDoc(Ddto);
//				service.updateApprChk(Adto);
//				service.insertComment(DCdto);//코멘트 입력
			}
		return isc?"redirect:/docLists.do?state="+state:"redirec:/eApprmain.do";
	}
}
