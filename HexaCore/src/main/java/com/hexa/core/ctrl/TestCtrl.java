package com.hexa.core.ctrl;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestCtrl {
	
	@RequestMapping(value = "/test.do", method = RequestMethod.GET)
	public String Test(String ckedit) {
		
		return "test/test";
	}

	@RequestMapping(value= "/maintest.do", method = RequestMethod.GET)
	public String mainTest() {
		return "MainTest";
	}
}
