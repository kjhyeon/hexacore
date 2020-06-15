package com.hexa.core.model.mng.impl;

import java.io.File;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.mng.inf.EmployeeIDao;
import com.hexa.core.model.mng.inf.EmployeeIService;

@Service
public class EmployeeServiceImpl implements EmployeeIService {

	@Autowired
	private EmployeeIDao dao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean insertEmployee(EmployeeDTO dto,MultipartFile profile_file) {
		log.info("Employee Service insertEmployee : {}: ",dto);
		dto = saveFile(dto, profile_file, "profile_");
		return dao.insertEmployee(dto);
	}

	@Override
	public boolean deleteEmployee(int employee_number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmployee(EmployeeDTO dto,MultipartFile profile_file,MultipartFile sign_file) {
		log.info("EmpServiceImpl updateEmp : {}",dto);
		dto = saveFile(dto, profile_file, "profile_");
		dto = saveFile(dto, sign_file,"sign_");
		return dao.updateEmployee(dto);
	}

	@Override
	public List<EmployeeDTO> selectChildEmployeeList(int department_id) {
		log.info("EmpServiceImpl selectChildEmpList : {}",department_id);
		return dao.selectChildEmployeeList(department_id);
	}

	@Override
	public List<EmployeeDTO> selectEmployeeList(RowNumDTO row) {
		log.info("EmpServiceImpl selectEmpList : {}");
		return dao.selectEmployeeList(row);
	}

	@Override
	public int selectEmployeeListSize() {
		log.info("EmpServiceImpl selectEmpListSize : {}");
		return dao.selectEmployeeListSize();
	}

	@Override
	public EmployeeDTO selectEmployee(String id) {
		log.info("EmpServiceImpl selectEmp : {}",id);
		return dao.selectEmployee(id);
	}

	@Override
	public EmployeeDTO selectLoginInfo(String id) {
		log.info("Security Login get UserInfo : {}",id);
		return dao.selectLoginInfo(id);
	}

	@Override
	public String selectId(String id) {
		log.info("EmpServiceImpl selectId : {}",id);
		return dao.selectId(id);
	}

	@Override
	public List<EmployeeDTO> selectRank() {
		log.info("EmpServiceImpl selectRank : {}");
		return dao.selectRank();
	}
	
	private EmployeeDTO saveFile(EmployeeDTO dto, MultipartFile file,String prefix) {
		log.info("파일 저장 : {} 에 {}",dto.getId(), file.getOriginalFilename());
		
		File indexFolder = new File(ATTACH_PATH);
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}
		if(file!=null&&!file.getOriginalFilename().trim().equals("")) {
			try {
				String saveName = prefix+UUID.randomUUID()+"_"+file.getOriginalFilename();
				File f = new File(ATTACH_PATH,saveName);
				file.transferTo(f);
				if(prefix.trim().equalsIgnoreCase("profile_")) {
					dto.setProfile_img(saveName);
				}else {
					dto.setSign_img(saveName);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else {
			if(prefix.trim().equalsIgnoreCase("profile_")) {
				dto.setProfile_img("");
			}else {
				dto.setSign_img("");
			}
		}
		
		return dto;
	}

}
