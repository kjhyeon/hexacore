package com.hexa.core.ctrl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
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
	
	
	@RequestMapping(value = "/eApprMain.do", method = RequestMethod.GET)
	public String MainTest(Model model) {
		return "eApprMain";
	}
	
		
	@RequestMapping(value = "/docDetail.do", method = RequestMethod.GET)
	public String updateDoc(Model model, String seq) {
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
	@RequestMapping(value="/modifyForm.do", method= RequestMethod.GET)
	public String modifyForm(Model model,String seq) {
		DocumentDTO Ddto = service.selectDoc(seq);
		model.addAttribute("Ddto",Ddto);
		return "ModifyForm";
	}
	
	@RequestMapping(value="/saveDoc.do", method= RequestMethod.GET)
	public String saveDoc(DocumentDTO Ddto) {
		Ddto.setAppr_turn(1);
		boolean isc = service.updateDoc(Ddto);
		System.out.println("*****************************"+Ddto);
		return isc?"redirect:/docDetail.do":"redirect:/docDetail.do";
	}
	
	
	
}
