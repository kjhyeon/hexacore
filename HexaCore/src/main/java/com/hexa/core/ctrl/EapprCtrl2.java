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
	
	
	@RequestMapping(value = "/eApprMain.do", method = RequestMethod.GET)
	public String MainTest(Model model) {
		return "eappr2/eApprMain";
	}
	
	@RequestMapping(value = "/docLists.do", method = RequestMethod.GET,produces = "applicaton/text; charset=UTF-8;")
	public String docLists(Model model,SecurityContextHolder session, String page,String number) {
		 Authentication auth = session.getContext().getAuthentication();
		 LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		    String id = Ldto.getUsername();
		if(page==null) {
			page="0";
		}
		RowNumDTO row = new RowNumDTO();
		row.setTotal(service.selectNeedApprDocCount(id));
		row.setPageNum(3);
		row.setListNum(10);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		map.put("start",row.getStart());
		map.put("last",row.getLast());
		List<DocumentDTO> lists =null;
		if(number.equalsIgnoreCase("1")) {
		lists = service.selectNeedApprDoc(map);
		log.info("********lists:{}",lists);
		}else if(number.equalsIgnoreCase("2")) {
		lists = service.selectApprMyDoc(map);
		log.info("********lists:{}",lists);
		}
		model.addAttribute("lists",lists);
		model.addAttribute("row", row);
		model.addAttribute("id",id);
		model.addAttribute("number",number);
		return "eappr2/docConfirmLists";
	}
	
	@Transactional
	@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
	public String updateDoc(Model model,SecurityContextHolder session,ApprovalDTO Adto,String number) {
		log.info("받아온 seq 값: {}", Adto.getSeq());
		String seq = Integer.toString(Adto.getSeq());
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
		String id = Ldto.getUsername();
		DocumentDTO Ddto = service.selectDoc(seq);
		List<ApprovalDTO> listsb = service.selectApprRoot(Adto);
		Adto.setId(id);
		List<ApprovalDTO> listsa = service.selectApprRoot(Adto);
		int turn = 0;
		log.info("********listsa Size:{}",listsa.size());
		if(listsa!=null && listsa.size()!=0) {
			turn = listsa.get(0).getTurn();
			model.addAttribute("turn",turn);
		}
		log.info("********turn:{}",turn);
		log.info("********Adto:{}",Adto);
		log.info("********listsa:{}",listsa);
		List<DocCommentDTO> lists = service.selectComment(seq);
		model.addAttribute("comment",lists);
		model.addAttribute("Ddto",Ddto);
		model.addAttribute("name",id);
		model.addAttribute("approvalLine",listsb);
		log.info("********number:{}", number);
		model.addAttribute("number",number);
		log.info("********Ddto:{}", Ddto);
		return "eappr2/docDetail";
	}	
	
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value="/modifyFormDoc.do", method= RequestMethod.POST,produces = "applicaton/text; charset=UTF-8;")
	public String modifyDoc(Model model,String seq,SecurityContextHolder session) {
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO Ldto = (LoginDTO) auth.getPrincipal();
	    String id = Ldto.getUsername();
		DocumentDTO Ddto = service.selectDoc(seq);
		model.addAttribute("Ddto",Ddto);
		log.info("********Ddto:{}", Ddto);
		JSONObject json = new JSONObject();
		json.put("id",id);
		json.put("seq",Ddto.getSeq());
		json.put("author",Ddto.getAuthor());
		json.put("title",Ddto.getTitle());
		json.put("regdate",Ddto.getRegdate());
		json.put("content", Ddto.getContent());
		return json.toString();
	}
	
	@RequestMapping(value="/deleteDoc.do", method= RequestMethod.POST)
	public String deleteDoc(String seq) {
		service.deleteDoc(seq);
		return "docLists.do";//리스트로 보내자
	}
	@Transactional
	@RequestMapping(value="/saveDoc.do", method= RequestMethod.POST)
	public String saveDoc(DocumentDTO Ddto) {
		log.info("********Ddto:{}", Ddto);
		Ddto.setAppr_turn(1);
		boolean isc = service.updateDoc(Ddto);
		log.info("********DdtoList:{}",Ddto.getLists());
		
		if(Ddto.getLists()!=null) {
			for (int i = 0; i < Ddto.getLists().size(); i++) {
				Ddto.getLists().get(i).setChk("F");
				Ddto.getLists().get(i).setSeq(Ddto.getSeq());
					service.deleteApprRoot(Integer.toString(Ddto.getSeq()));
					service.insertApprRoot(Ddto.getLists().get(i));
			}
		}
		return isc?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
		//임시보관함의 디테일로 보내자11
	}
	
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
	
	@Transactional
	@RequestMapping(value="/confirmDoc.do", method= RequestMethod.POST)
	public String confirmDoc(ApprovalDTO Adto, DocumentDTO Ddto, DocCommentDTO DCdto,String number,SecurityContextHolder session) {
		log.info("********confirm Test Adto:{}",Adto);
		log.info("********confirm Test Ddto:{}",Ddto);
		log.info("********confirm Test DCdto:{}",DCdto);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		DCdto.setName(dto.getName());
		Adto.setName(dto.getName());
		Adto.setAppr_sign(service.selectSignImg(dto.getUsername()));
		DCdto.setComment_seq(Adto.getTurn());
		int appr_turn = Ddto.getAppr_turn();
			if(Adto.getChk().equalsIgnoreCase("T")) {//승인일경우
				if(Adto.getTurn()<3) {
					Ddto.setAppr_turn(appr_turn+1);
					Ddto.setState(2);
					Adto.setAppr_kind("승인");
				}else if(Adto.getTurn()==3){
					Ddto.setState(3);
					Adto.setAppr_kind("승인");
				}
				service.updateDoc(Ddto);//appr_turn 수정 / state 수정
				service.updateApprChk(Adto);//chk 업데이트 사인 업데이트
				service.insertComment(DCdto);//코멘트 입력
				
			}else if(Adto.getChk().equalsIgnoreCase("R")){
				Adto.setAppr_kind("반려");
				Ddto.setState(4);
				log.info("********confirm Test2R Adto:{}",Adto);
				log.info("********confirm Test2R Ddto:{}",Ddto);
				log.info("********confirm Test2R DCdto:{}",DCdto);
				service.updateDoc(Ddto);
				service.updateApprChk(Adto);
				service.insertComment(DCdto);//코멘트 입력
			}
		return "redirect:/docLists.do?number="+number;
	}
}
