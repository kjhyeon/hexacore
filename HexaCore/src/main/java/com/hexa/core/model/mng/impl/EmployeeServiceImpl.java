package com.hexa.core.model.mng.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.model.mng.inf.EmployeeIDao;
import com.hexa.core.model.mng.inf.EmployeeIService;

@Service
public class EmployeeServiceImpl implements EmployeeIService {

	@Autowired
	private EmployeeIDao dao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean insertEmployee(EmployeeDTO dto) {
		log.info("Employee Service insertEmployee : {}: ",dto);
		return dao.insertEmployee(dto);
	}

	@Override
	public boolean deleteEmployee(int employee_number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmployee(EmployeeDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmployeeDTO> selectChildEmployeeList(int department_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeDTO> selectEmployeeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<EmployeeDTO> selectAllEmployeeList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeDTO selectEmployee(int employee_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public EmployeeDTO selectLoginInfo(EmployeeDTO dto) {
		log.info("Login Member Service encryption: "+dto.getPassword());
		String encoryptionPw = dao.selectEmpPw(dto.getId());
		
		String inputPw= dto.getPassword();

		log.info("Login Member Service encryption: "+encoryptionPw);
		log.info("Login Member Service encryption: "+inputPw);

//		//화면에서 얻은 pw를 암호화 시긴 값
//		String enPw = passwordEncoder.encode(inputPw);
//
//		log.info("입력 값 암호화 pw:{}",enPw);
		
		if(passwordEncoder.matches(inputPw, encoryptionPw)) {
			log.info("&&&& 비밀번호 일치 &&&&");
			return dao.selectLoginInfo(dto.getId());
		}else
			return null;
	}

}
