package com.hexa.core.model.mng.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.model.mng.inf.EmployeeIDao;

@Repository
public class EmployeeDaoImpl implements EmployeeIDao{

	@Autowired
	private SqlSessionTemplate session;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String NS = "com.hexa.core.emp.";
	
	@Override
	public boolean insertEmployee(EmployeeDTO dto) {
		log.info("EmployeeDAO insertEmployee : {}",dto);
		String enPw = passwordEncoder.encode(dto.getPassword());
		dto.setPassword(enPw);
		return session.insert(NS+"insertEmployee", dto)>0?true:false;
	}

	@Override
	public boolean deleteEmployee(String employee_number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmployee(EmployeeDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<EmployeeDTO> selectChildEmployeeList(String deplartment_id) {
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
	public EmployeeDTO selectEmployee(String employee_number) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String selectEmpPw(String id) {
		log.info("EmployeeDAO selectEmpPw : {}",id);
		return session.selectOne(NS+"selectEmpPw",id);
	}

	@Override
	public EmployeeDTO selectLoginInfo(String id) {
		log.info("EmployeeDAO selectLoginInfo : {}",id);
		return session.selectOne(NS+"selectLoginInfo", id);
	}

}
