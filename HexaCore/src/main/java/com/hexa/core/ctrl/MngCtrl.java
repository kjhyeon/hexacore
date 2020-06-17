package com.hexa.core.ctrl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FileBbsIService;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.bbs.inf.NoticeBbsIService;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.mng.inf.EmployeeIService;

@Controller
public class MngCtrl {
	
	@Autowired
	private EmployeeIService eService;
	
	@Autowired
	private NoticeBbsIService noticeService;
	
	@Autowired
	private FileBbsIService fileService;
	
	@Autowired
	private EapprIService eapprService;
	
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
		
		boolean isc = eService.insertEmployee(dto,profile_file);
		
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
//		System.out.println("################################3"+eService.selectRank().size());
		return "mng/updateEmployee";
	}
	@RequestMapping(value="/updateEmployee.do",method=RequestMethod.POST)
	public String EmployeeUpdate(EmployeeDTO dto,MultipartFile profile_file, MultipartFile sign_file,SecurityContextHolder session) {
		log.info("Welcome EmployeeUpdate {}/{}/{}", dto,profile_file,sign_file);
		
		boolean isc = eService.updateEmployee(dto,profile_file,sign_file);
		if(isc) {
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO lDto = (LoginDTO) auth.getPrincipal();
			if(lDto.getUsername().equals(dto.getId())) {
				EmployeeDTO eDto = eService.selectEmployee(dto.getId());
				lDto.setDepartment_id(String.valueOf(eDto.getDepartment_id()));
				lDto.setDepartment_name(eDto.getDepartment_name());
				lDto.setE_rank(String.valueOf(eDto.getE_rank()));
				lDto.setE_rank_name(eDto.getE_rank_name());
				lDto.setemail(eDto.getEmail());
				lDto.setProfile_img(eDto.getProfile_img());
				lDto.setName(eDto.getName());
				String auths = null;
				if(dto.getAuth().trim().equalsIgnoreCase("a"))
					auths = "ROLE_ADMIN";
				else
					auths = "ROLE_USER";
				Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
				roles.add(new SimpleGrantedAuthority(auths));
				Authentication newAuth = new UsernamePasswordAuthenticationToken(lDto, auth.getCredentials(), roles);
				session.getContext().setAuthentication(newAuth);
			}
			return "redirect:/employeeList.do";
		}
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
		log.info("유저 로그인/로그아웃 처리 : {}/{}",error,logout);
		if (error != null) {
			log.info("유저 로그인 에러 발생");
			model.addAttribute("msg", "로그인 에러");
		}

		if (logout != null) {
			log.info("유저 로그아웃");
			model.addAttribute("msg", "로그아웃 성공");
		}
		return "../login";
	}

	
	@RequestMapping(value = "/result.do", method = RequestMethod.GET)
	public String maingo(SecurityContextHolder session, Model model) {
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		
		RowNumDTO row = new RowNumDTO();
		row.setTotal(5);
		row.setPageNum(3);
		row.setListNum(5);
		
		List<BbsDTO> noticeList = noticeService.selectUserNoticeBbsListRow(row);
		List<BbsDTO> fileList = fileService.selectUserFileBbsListRow(row);
		Map<String,Object> map = Maps.newHashMap();
		map.put("start", 0);
		map.put("last", 10);
		map.put("id",dto.getUsername());
		map.put("state","777");
		List<DocumentDTO> eDocList = eapprService.selectMyDocList(map);
		model.addAttribute("noticeList", noticeList);
		model.addAttribute("fileList", fileList);
		model.addAttribute("eDocList", eDocList);
		EmployeeDTO emp = eService.selectEmployee(dto.getUsername());
		model.addAttribute("emp", emp);
		
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
	public String empInfoUpdate(EmployeeDTO dto,MultipartFile profile_file, MultipartFile sign_file, SecurityContextHolder session) {
		log.info("Welcome Page empInfoUpdate {}/{}/{}",dto,profile_file.getOriginalFilename(),sign_file.getOriginalFilename());
		
		boolean isc = eService.updateEmployee(dto,profile_file,sign_file);
		if(isc) {
			EmployeeDTO eDto = eService.selectEmployee(dto.getId());
			Authentication auth = session.getContext().getAuthentication();
			LoginDTO lDto = (LoginDTO) auth.getPrincipal();
			lDto.setDepartment_id(String.valueOf(eDto.getDepartment_id()));
			lDto.setDepartment_name(eDto.getDepartment_name());
			lDto.setE_rank(String.valueOf(eDto.getE_rank()));
			lDto.setE_rank_name(eDto.getE_rank_name());
			lDto.setemail(eDto.getEmail());
			lDto.setProfile_img(eDto.getProfile_img());
			lDto.setName(eDto.getName());
			String auths = null;
			if(dto.getAuth().trim().equalsIgnoreCase("a"))
				auths = "ROLE_ADMIN";
			else
				auths = "ROLE_USER";
			Collection<GrantedAuthority> roles = new ArrayList<GrantedAuthority>();
			roles.add(new SimpleGrantedAuthority(auths));
			Authentication newAuth = new UsernamePasswordAuthenticationToken(lDto, auth.getCredentials(), roles);
			session.getContext().setAuthentication(newAuth);
			return "redirect:/empInfo.do";
		}
		else
			return "../../error";
	}
	
	@RequestMapping(value="/mngMain.do")
	public String mngMain() {
		return "mng/mngSideBar";
	}
}
