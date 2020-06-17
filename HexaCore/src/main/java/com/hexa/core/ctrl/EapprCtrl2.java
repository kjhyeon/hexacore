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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.CalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.cal.inf.Calendar_IService;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.mng.inf.EmployeeIService;
import com.hexa.core.model.msg.inf.MessageIService;
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
		return "eappr2/docConfirmLists";
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
