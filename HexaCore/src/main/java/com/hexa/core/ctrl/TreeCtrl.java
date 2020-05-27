package com.hexa.core.ctrl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.hexa.core.model.mng.inf.DepartmentIService;
import com.hexa.core.model.mng.inf.EmployeeIService;

@RestController
public class TreeCtrl {

	@Autowired
	private EmployeeIService eService;
	
	@Autowired
	private DepartmentIService dService;
	
	@RequestMapping(value="/testTree.do", method = RequestMethod.GET)
	public Map<String,Object> getTree(){
		Map<String,Object> tree = Maps.newHashMap();
		tree.put("data", "root");
		tree.put("id","0");
		tree.put("state","open");
		tree.put("type","folder");
		tree.put("text","부서 목록");
		tree.put("children", dService.selectCompanyTree(0));
		System.out.println(tree.toString());
		
		return tree;
	}
}
