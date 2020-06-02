package com.hexa.core.ctrl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.core.dto.DocumentDTO;
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
		//임시보관함의 디테일로 보내자
	}
	
	@RequestMapping(value="/deleteDoc.do", method= RequestMethod.POST)
	public String deleteDoc(String seq) {
		boolean isc = service.deleteDoc(seq);
		return "eApprMain";//리스트로 보내자
	}
	
	@RequestMapping(value="/apprDoc.do", method= RequestMethod.POST)
	@ResponseBody
	public String apprDoc(String seq) {
		return null;
	}
	
}
