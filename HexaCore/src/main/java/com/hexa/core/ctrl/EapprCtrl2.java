package com.hexa.core.ctrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.model.eappr.inf.EapprIService;

@Controller
public class EapprCtrl2 {
	
	@Autowired
	private EapprIService service;
	
	
	@RequestMapping(value = "/MainTest.do", method = RequestMethod.GET)
	public String MainTest(Model model) {
		DocumentDTO dto = service.selectDoc("1");
		model.addAttribute("dto", dto);
		return "DOCMain";
	}
	
		
	@RequestMapping(value = "/updateDoc.do", method = RequestMethod.GET)
	public String updateDoc(Model model) {
		DocumentDTO Ddto = service.selectDoc("1");
		Ddto.setSeq(1);
		Ddto.setAppr_turn(0);
		Ddto.setTitle("테스트중");
		Ddto.setContent("컨텐츠");
		boolean isc = service.updateDoc(Ddto);
		model.addAttribute("Ddto", Ddto);
		return "DOCMain2";
	}	
}
