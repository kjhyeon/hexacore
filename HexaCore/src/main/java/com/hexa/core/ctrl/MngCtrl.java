package com.hexa.core.ctrl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.model.mng.inf.DepartmentIService;
import com.hexa.core.model.mng.inf.EmployeeIService;

@Controller
public class MngCtrl {

	@Autowired
	private EmployeeIService eService;
	
	@Autowired
	private DepartmentIService dService;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value="/deptTree.do", method = RequestMethod.GET)
	public String goDeptTree(){
		return "mng/deptTree";
	}
	
	@RequestMapping(value="/allTree.do", method = RequestMethod.GET)
	public String goAllTree(){
		return "mng/allTree";
	}
	
	@RequestMapping(value="/deptMngTree.do", method = RequestMethod.GET)
	public String goMngTree(){
		return "mng/deptMngTree";
	}
	
	@RequestMapping(value="/updateDepartment.do",method=RequestMethod.GET)
	public String goToDeptMng() {
		
		return "mng/updateDepartment";
	}
	
	
	@RequestMapping(value="/insertEmployee.do",method=RequestMethod.GET)
	public String goEmpInsertForm() {
		
		return "mng/insertEmployee";
	}
	
	@RequestMapping(value="/insertEmployee.do",method=RequestMethod.POST)
	public String EmployeeInsert(EmployeeDTO dto) {
		log.info("Welcome EmployeeInsert {}", dto);
		if(dto.getProfile_img()==null) {
			dto.setProfile_img("");
		}
		boolean isc = eService.insertEmployee(dto);
		
		if(isc)
			return "redirect:/employeeList.do";
		else
			return "../../error";
	}
	
	@ResponseBody
	@RequestMapping(value = "/idChk.do", method = RequestMethod.POST)
	public String login(String id) {
		log.info("welcome idChk : {}",id);
		String chkId = eService.selectId(id);
		if(chkId!=null) {
			return "false";
		}
		return "true";
	}
	
	@RequestMapping(value="/updateEmployee.do",method=RequestMethod.GET)
	public String goEmpUpdateForm(String employee_number, Model model) {
		log.info("Welcome goEmpUpdateForm {}", employee_number);
		model.addAttribute("dto",eService.selectEmployee(Integer.parseInt(employee_number)));
		model.addAttribute("ranks",eService.selectRank());
		System.out.println("################################3"+eService.selectRank().size());
		return "mng/updateEmployee";
	}
	
	@RequestMapping(value="/updateEmployee.do",method=RequestMethod.POST)
	public String EmployeeUpdate(EmployeeDTO dto) {
		log.info("Welcome EmployeeInsert {}", dto);
//		if(dto.getProfile_img()==null) {
//			dto.setProfile_img("");
//		}
		boolean isc = eService.updateEmployee(dto);
		
		if(isc)
			return "mng/insertEmployee";
		else
			return "../../error";
	}

	@RequestMapping(value="/employeeList.do",method=RequestMethod.GET)
	public String EmployeeList(Model model) {
		log.info("Welcome page EmployList.do");
		model.addAttribute("list", eService.selectEmployeeList());
		
		return "mng/employeeList";
	}
	
	@RequestMapping(value = "/loginPage.do", method = RequestMethod.GET)
	public String login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout, Model model) {

		if (error != null) {
			model.addAttribute("msg", "로그인 에러");
		}

		if (logout != null) {
			model.addAttribute("msg", "로그아웃 성공");
		}
		return "../login";
	}


	@RequestMapping(value = "/result.do", method = RequestMethod.GET)
	public String maingo() {
		return "main";
	}

}
