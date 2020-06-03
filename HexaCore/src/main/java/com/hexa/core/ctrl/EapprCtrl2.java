package com.hexa.core.ctrl;

import java.security.Principal;
import java.util.List;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.model.eappr.inf.EapprIService;

@Controller
public class EapprCtrl2 {
	
	@Autowired
	private EapprIService service;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(value = "/eApprMain.do", method = RequestMethod.GET)
	public String MainTest(Model model) {
		return "eApprMain";
	}
	
	@RequestMapping(value = "/docLists.do", method = RequestMethod.GET)
	public String docLists(Model model, String id) {
		List<DocumentDTO> lists = service.selectNeedApprDoc(id);
		model.addAttribute("lists",lists);
		return "docLists";
	}
		
	@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
	public String updateDoc(Model model, String seq) {
		log.info("받아온 seq 값: {}", seq);
		DocumentDTO Ddto = service.selectDoc(seq);
		model.addAttribute("Ddto",Ddto);
		return "docDetail";
	}	
	
//	@SuppressWarnings("unchecked")
//	@RequestMapping(value="/modifyForm.do", method=RequestMethod.POST)
//	@ResponseBody
//	public String modifyForm(String seq) {
//		JSONObject json = new JSONObject();
//		DocumentDTO dto = service.selectDoc(seq);
//		System.out.println(dto.toString());
//		json.put("seq", seq);
//		json.put("id", dto.getAuthor());
//		json.put("title", dto.getTitle());
//		json.put("content", dto.getContent());
//		return json.toString();
//	}
//	
//	@RequestMapping(value="/modify.do", method=RequestMethod.POST)
//	public String modify(DocumentDTO Ddto) {
//		SimpleDateFormat format1 = new SimpleDateFormat();
//		Date date = new Date();
//		String time1 = format1.format(date);
//		Ddto.setRegdate(time1);
//		Ddto.setState(0);
//		System.out.println(Ddto.toString());
//		boolean isc = service.updateDoc(Ddto);
//		return isc?"redirect:/docDetail.do":"redirect:/docDetail.do";
//	}
	@RequestMapping(value="/modifyDoc.do", method= RequestMethod.POST)
	public String modifyDoc(Model model,String seq) {
		DocumentDTO Ddto = service.selectDoc(seq);
		model.addAttribute("Ddto",Ddto);
		return "ModifyForm";
	}
	
	@RequestMapping(value="/saveDoc.do", method= RequestMethod.POST)
	public String saveDoc(DocumentDTO Ddto) {
		log.info("Ddto 확인용 로그1 : {}", Ddto);
		Ddto.setAppr_turn(1);
		boolean isc = service.updateDoc(Ddto);
		for (int i = 0; i < Ddto.getLists().size(); i++) {
			Ddto.getLists().get(i).setChk("F");
			Ddto.getLists().get(i).setSeq(Ddto.getSeq());
			if(Ddto.getLists()!=null) {
//				service.deleteApprRoot(Ddto.getSeq());
				service.insertApprRoot(Ddto.getLists().get(i));
			}
		}
		return isc?"redirect:/docDetail.do?seq="+Ddto.getSeq():"redirect:/eApprMain.do";
		//임시보관함의 디테일로 보내자11
	}
	
	@RequestMapping(value="/deleteDoc.do", method= RequestMethod.POST)
	public String deleteDoc(String seq) {
		boolean isc = service.deleteDoc(seq);
		return "eApprMain";//리스트로 보내자
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/apprDoc.do", method= RequestMethod.POST ,produces = "applicaton/text; charset=UTF-8;")
	@ResponseBody
	public String apprDoc(ApprovalDTO Adto, int appr_turn,SecurityContextHolder session) {
		JSONObject json = new JSONObject();
		log.info("***********************************{}",Adto);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		Adto.setId(dto.getUsername());
		List<ApprovalDTO> lists = service.selectApprRoot(Adto);
		log.info("***********************************{}",lists);
		json.put("seq", lists.get(0).getSeq());
		json.put("id", lists.get(0).getId());
		json.put("name", lists.get(0).getName());
		json.put("turn", lists.get(0).getTurn());
		json.put("appr_turn",appr_turn);
		log.info("Welcome modifyForm 결과, {}",json.toString());
		return json.toString();
	}
	
	@Transactional
	@RequestMapping(value="/confirmDoc.do", method= RequestMethod.POST)
	public String confirmDoc(ApprovalDTO Adto, DocumentDTO Ddto, DocCommentDTO DCdto, SecurityContextHolder session) {
		log.info("confirm Test{}",Adto);
		log.info("confirm Test{}",Ddto);
		log.info("confirm Test{}",DCdto);
		Authentication auth = session.getContext().getAuthentication();
	    LoginDTO dto = (LoginDTO) auth.getPrincipal();
		DCdto.setName(dto.getName());
		Adto.setName(dto.getName());
		Adto.setAppr_sign("도장");
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
				boolean isc = service.updateDoc(Ddto);//appr_turn 수정 / state 수정
				boolean isc2 = service.updateApprChk(Adto);//chk 업데이트 사인 업데이트
				boolean isc3 = service.insertComment(DCdto);//코멘트 입력
				
			}else if(Adto.getChk().equalsIgnoreCase("R")){
				Adto.setAppr_kind("반려");
				Ddto.setState(4);
				log.info("confirm Test2R{}",Adto);
				log.info("confirm Test2R{}",Ddto);
				log.info("confirm Test2R{}",DCdto);
				boolean isc = service.updateDoc(Ddto);
				boolean isc2 = service.updateApprChk(Adto);
				boolean isc3 = service.insertComment(DCdto);//코멘트 입력
			}
		return "redirect:/docLists.do?id="+Adto.getId();
	}
}
