package com.hexa.core.ctrl;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
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
import com.hexa.core.dto.CalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.cal.inf.Calendar_IService;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.mng.inf.EmployeeIService;
import com.hexa.core.model.msg.inf.MessageIService;
@SuppressWarnings("static-access")
@Controller
public class EapprCtrl2 {
	
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
		return "eappr2/docConfirmLists";
	}
	
	//문서 상세조회
	@Transactional
	@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
	public String updateDoc(Model model,SecurityContextHolder session,ApprovalDTO Adto) {
		log.info("받아온 seq 값: {}", Adto.getSeq());
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		String id = Ldto.getUsername();
		String seq = Integer.toString(Adto.getSeq());
		//도장 주소 결합
		String attach_path = "C:\\eclipse-spring\\git\\hexacore\\HexaCore\\src\\main\\webapp\\image\\profile\\";
		attach_path +=Adto.getAppr_sign();
		//믄사 내용 조회
		DocumentDTO Ddto = service.selectDoc(seq);
		//문서 결재선 전체 조회
		List<ApprovalDTO> apprList = service.selectApprRoot(Adto);
		log.info("문서리스트{}",apprList);
		for (int i = 0; i < apprList.size(); i++) {
			if(apprList.get(i).getAppr_sign()!=null) {
				apprList.get(i).setAppr_sign(attach_path+apprList.get(i).getAppr_sign()); 
			}
		}
		
		//문서 나의 결재선 조회
		Adto.setId(id);
		List<ApprovalDTO> listsa = service.selectApprRoot(Adto);
		log.info("********listsa:{}",listsa);
		int turn = 0;
		if(listsa!=null && listsa.size()!=0) {
			turn = listsa.get(0).getTurn();
			model.addAttribute("turn",turn);
		}
		log.info("********turn:{}",turn);
		
		//결재자 코멘트 가져오기
		List<DocCommentDTO> lists = service.selectComment(seq);
		//독타입 가져오기
		DocumentTypeDTO TDto = service.selectDocType(Integer.toString(Ddto.getType_seq()));
		model.addAttribute("typeDto",TDto);
		model.addAttribute("comment",lists);
		model.addAttribute("Ddto",Ddto);
		model.addAttribute("name",id);
		model.addAttribute("apprList",apprList);
		return "eappr2/docDetail";
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
		return isc?"docLists.do?state="+state:"redirect:/eApprMain.do";
	}
	
	//임시저장
	@Transactional
	@RequestMapping(value="/saveUpDoc.do", method= RequestMethod.POST)
	public String saveUpDoc(DocumentDTO Ddto) {
		log.info("********Ddto:{}", Ddto);
		Ddto.setAppr_turn(0);
		Ddto.setState(0);
		boolean isc = service.updateDoc(Ddto);
		boolean isc2=false;
		if(isc==true) {
			if(Ddto.getLists()!=null) {
				isc2 = service.insertApprRoot(Ddto);
			}
		}
		return (isc||isc2)?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
	}
	
	@RequestMapping(value="/upApprDoc.do", method=RequestMethod.GET)
	public String upApprDoc(String seq,String state) {
		if(state.equalsIgnoreCase("0")) {
			service.updateSaveToAppr(seq);
		}else {
			service.reportCancel(seq);
		}
		return "redirect:/docDetail.do?seq="+seq;
	}
	//문서승인 Modal
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/apprDoc.do", method= RequestMethod.POST ,produces = "applicaton/text; charset=UTF-8;")
	@ResponseBody
	public String apprDoc(ApprovalDTO Adto, DocumentDTO Ddto, SecurityContextHolder session) {
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
		json.put("appr_turn",Ddto.getAppr_turn());
		json.put("a_turn",Ddto.getA_turn());
		json.put("number",Ddto.getState());
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
	public String confirmDoc(ApprovalDTO Adto, DocumentDTO Ddto, DocCommentDTO DCdto) {
		log.info("********confirm Test Adto:{}",Adto);
		log.info("********confirm Test Ddto:{}",Ddto);
		log.info("********confirm Test DCdto:{}",DCdto);
		Adto.setAppr_sign(service.selectSignImg(Adto.getId()));
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean isc = false;
		if(Adto.getChk().equalsIgnoreCase("T")) {//승인일경우
			Adto.setAppr_kind("승인");
				if(Adto.getTurn()<3) {
					Ddto.setAppr_turn(Ddto.getAppr_turn()+1);
					Ddto.setState(2);
				}else if(Adto.getTurn()==3){
					Ddto.setState(3);
				}
		}else if(Adto.getChk().equalsIgnoreCase("R")){
				Adto.setAppr_kind("반려");
				Ddto.setState(4);
			}
			map.put("Ddto", Ddto);
			map.put("Adto", Adto);
			map.put("DCdto", DCdto);
		isc = service.confirmUpdate(map);
		return isc?"redirect:/docLists.do?state=7":"redirec:/eApprmain.do";
	}
	

	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/getCals.do", method = RequestMethod.POST)
	public JSONObject getEventsCal(CalDTO CDto) {
		Map<String, Object> map = new HashMap<String, Object>();
		JSONArray jLists = new JSONArray();
		JSONObject jdto = new JSONObject();
		
		log.info("---------{}",CDto);
		map.put("start",CDto.getStart_date());
		map.put("end",CDto.getEnd_date());
		log.info("꺅{}",map);
		List<CalDTO> lists = CService.selectEventsCal(map);
		log.info("꺅2{}",lists);
		
		
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
	
	@RequestMapping(value = "/addEventCals.do", method= RequestMethod.POST)
	public String addEventCals(CalDTO CDto,Principal principal) {
		log.info("addEventCals{}",CDto);
		CDto.setId(principal.getName());
		int n = CService.insertEventsCal(CDto);
		return (n>0)?"redirect:/goEapprHome.do":"redirect:/goEapprHome.do";
	}
	
	@RequestMapping(value = "/deleteCal.do", method= RequestMethod.POST)
	public String deleteCal(String title) {
		log.info("삭제{}",title);
		int n = CService.deleteEventsCal(title);
		return (n>0)?"redirect:/goEapprHome.do":"redirect:/goEapprHome.do";
	}
}
