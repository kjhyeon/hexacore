package com.hexa.core.ctrl;

import java.io.File;
import java.security.Principal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.mng.inf.DepartmentIService;
import com.hexa.core.model.mng.inf.EmployeeIService;

@Controller
public class MngCtrl {

	@Autowired
	private EmployeeIService eService;
	
	@Autowired
	private DepartmentIService dService;
	
	private final String attach_path = "C:\\eclipse-spring\\git\\hexacore\\HexaCore\\src\\main\\webapp\\image\\profile";
	
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
	
	@RequestMapping(value="/allTreeMsg.do", method = RequestMethod.GET)
	public String goMsgTree(){
		return "mng/allTreeForMsg";
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
	public String EmployeeInsert(EmployeeDTO dto, MultipartFile profile_file) {
		log.info("Welcome EmployeeInsert {}/{}", dto,profile_file);
		
		File indexFolder = new File(attach_path);
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}
		if(profile_file!=null&&!profile_file.getOriginalFilename().trim().equals("")) {
			try {
				String saveName = "profile_"+UUID.randomUUID()+"_"+profile_file.getOriginalFilename();
				File f = new File(attach_path,saveName);
				profile_file.transferTo(f);
				dto.setProfile_img(saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
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
	public String goEmpUpdateForm(String id, Model model) {
		log.info("Welcome goEmpUpdateForm {}", id);
		model.addAttribute("dto",eService.selectEmployee(id));
		model.addAttribute("ranks",eService.selectRank());
		System.out.println("################################3"+eService.selectRank().size());
		return "mng/updateEmployee";
	}
	@RequestMapping(value="/updateEmployee.do",method=RequestMethod.POST)
	public String EmployeeUpdate(EmployeeDTO dto,MultipartFile profile_file, MultipartFile sign_file) {
		log.info("Welcome EmployeeInsert {}/{}/{}", dto,profile_file,sign_file);
		
		File indexFolder = new File(attach_path);
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}
		if(profile_file!=null&&!profile_file.getOriginalFilename().trim().equals("")) {
			try {
				String saveName = "profile_"+UUID.randomUUID()+"_"+profile_file.getOriginalFilename();
				File f = new File(attach_path,saveName);
				profile_file.transferTo(f);
				dto.setProfile_img(saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(sign_file!=null&&!sign_file.getOriginalFilename().trim().equals("")) {
			try {
				String saveName = "sign_"+UUID.randomUUID()+"_"+sign_file.getOriginalFilename();
				File f = new File(attach_path,saveName);
				sign_file.transferTo(f);
				dto.setSign_img(saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		boolean isc = eService.updateEmployee(dto);
		if(isc)
			return "redirect:/employeeList.do";
		else
			return "../../error";
	}

	@RequestMapping(value="/employeeList.do",method=RequestMethod.GET)
	public String EmployeeList(Model model, String page) {
		log.info("Welcome page EmployList.do");
		if(page==null) {
			page="0";
		}
		RowNumDTO row = new RowNumDTO();
		row.setTotal(eService.selectEmployeeListSize());
		row.setPageNum(3);
		row.setListNum(10);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		model.addAttribute("list", eService.selectEmployeeList(row));
		model.addAttribute("row", row);
		
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
		return "home";
	}
	
	@RequestMapping(value="/empInfo.do", method = RequestMethod.GET)
	public String empInfo(SecurityContextHolder session, Model model) {
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		log.info("Welcome Page empInfo {}",dto);
		EmployeeDTO emp = eService.selectEmployee(dto.getUsername());
		if(emp!=null) {
			model.addAttribute("dto",emp);
			return "empInfo";
		}else {
			return "../../error";
		}
	}

	@RequestMapping(value="/empInfoUpdate.do", method = RequestMethod.GET)
	public String goToEmpInfoUpdate(SecurityContextHolder session, Model model) {
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		log.info("Welcome Page empInfo {}",dto);
		EmployeeDTO emp = eService.selectEmployee(dto.getUsername());
		if(emp!=null) {
			model.addAttribute("dto",emp);
			return "empInfoUpdate";
		}else {
			return "../../error";
		}
	}
	
	@RequestMapping(value="/empInfoUpdate.do", method = RequestMethod.POST)
	public String empInfoUpdate(EmployeeDTO dto,MultipartFile profile_file, MultipartFile sign_file) {
		log.info("Welcome Page empInfoUpdate {}/{}/{}",dto,profile_file.getOriginalFilename(),sign_file.getOriginalFilename());
		File indexFolder = new File(attach_path);
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}
		if(profile_file.getOriginalFilename()!=null&&!profile_file.getOriginalFilename().equals("")) {
			try {
				String saveName = "profile_"+UUID.randomUUID()+"_"+profile_file.getOriginalFilename();
				File f = new File(attach_path,saveName);
				profile_file.transferTo(f);
				dto.setProfile_img(saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(sign_file.getOriginalFilename()!=null&&!sign_file.getOriginalFilename().equals("")) {
			try {
				String saveName = "sign_"+UUID.randomUUID()+"_"+sign_file.getOriginalFilename();
				File f = new File(attach_path,saveName);
				sign_file.transferTo(f);
				dto.setSign_img(saveName);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		boolean isc = eService.updateEmployee(dto);
		if(isc)
			return "redirect:/empInfo.do";
		else
			return "../../error";
	}
	
	@RequestMapping(value="/mngMain.do")
	public String mngMain() {
		return "mng/mngSideBar";
	}
}
