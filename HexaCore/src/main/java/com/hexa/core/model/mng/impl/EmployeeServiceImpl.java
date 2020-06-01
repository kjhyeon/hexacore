package com.hexa.core.model.mng.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;
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
		log.info("EmpServiceImpl updateEmp : {}",dto);
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
	public EmployeeDTO selectEmployee(int employee_number) {
		log.info("EmpServiceImpl selectEmp : {}",employee_number);
		return dao.selectEmployee(employee_number);
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
	
	

}
