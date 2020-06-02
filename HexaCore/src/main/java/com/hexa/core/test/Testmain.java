package com.hexa.core.test;


import com.hexa.core.dto.BbsDTO;
import com.hexa.core.model.bbs.impl.FreeBbsServiceImpl;
import com.hexa.core.model.bbs.inf.FreeBbsIService;

public class Testmain {

	
	public static void main(String[] args) {
		BbsDTO dto = new BbsDTO();
		System.out.println(dto);
		
		FreeBbsIService service = new FreeBbsServiceImpl();
		
		
		 
	}
}
