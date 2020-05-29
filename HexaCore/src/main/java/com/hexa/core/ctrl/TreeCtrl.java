package com.hexa.core.ctrl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.Maps;
import com.hexa.core.dto.DepartmentDTO;
import com.hexa.core.model.mng.inf.DepartmentIService;
import com.hexa.core.model.mng.inf.EmployeeIService;

@RestController
public class TreeCtrl {

	@Autowired
	private EmployeeIService eService;
	
	@Autowired
	private DepartmentIService dService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/deptTree.do", method = RequestMethod.POST)
	public Map<String,Object> deptTree(){
		Map<String,Object> tree = Maps.newHashMap();
		tree.put("data", "root");
		tree.put("id","0");
		tree.put("state","open");
		tree.put("type","folder");
		tree.put("text","부서 목록");
		tree.put("children", dService.selectCompanyTree(0,dService.deptTree));
		System.out.println(tree.toString());
		
		return tree;
	}
	
	@RequestMapping(value="/allTree.do", method = RequestMethod.POST)
	public Map<String,Object> allTree(){
		Map<String,Object> tree = Maps.newHashMap();
		tree.put("data", "root");
		tree.put("id","0");
		tree.put("state","open");
		tree.put("type","folder");
		tree.put("text","사원 목록");
		tree.put("children", dService.selectCompanyTree(0,dService.allTree));
		System.out.println(tree.toString());
		
		return tree;
	}
	
	@RequestMapping(value="/createDept.do", method = RequestMethod.POST)
	public String createDept(DepartmentDTO dto) {
		log.info("####### ajax tree create : {} ######",dto);
		System.out.println(dto);
		String dept_id = String.valueOf(dService.insertDepartment(dto));
		return dept_id;
	}
	
	@RequestMapping(value="/deleteDept.do", method = RequestMethod.POST)
	public String deleteDept(String department_id) {
		log.info("####### ajax tree delete : {} ######",department_id);
		dService.deleteDepartment(Integer.parseInt(department_id));
		return "성공";
	}
	
	@RequestMapping(value="/updateDept.do", method = RequestMethod.POST)
	public String updateDept(DepartmentDTO dto) {
		log.info("####### ajax tree update : {} ######",dto);
		dService.updateDepartment(dto);
		return "성공";
	}
	
	@RequestMapping(value="/moveDept.do", method = RequestMethod.POST)
	public String moveDept(DepartmentDTO dto) {
		log.info("####### ajax tree move : {} ######",dto);
		dService.moveDepartment(dto);
		return "성공";
	}
}
