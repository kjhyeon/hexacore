package com.hexa.core.model.mng.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;
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
	public boolean deleteEmployee(int employee_number) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateEmployee(EmployeeDTO dto) {
		log.info("EmployeeDAO updateEmployee : {}",dto);
		if(dto.getPassword()!=null) {
			String enPw = passwordEncoder.encode(dto.getPassword());
			dto.setPassword(enPw);
		}
		return session.update(NS+"updateEmployee", dto)>0?true:false;
	}

	@Override
	public List<EmployeeDTO> selectChildEmployeeList(int department_id) {
		log.info("EmpDaoImpl selectChildEmplList : {}",department_id);
		return session.selectList(NS+"selectChildEmployeeList", department_id);
	}

	@Override
	public List<EmployeeDTO> selectEmployeeList(RowNumDTO row) {
		log.info("EmpDaoImpl selectEmplList : {}");
		return session.selectList(NS+"selectEmployeeList",row);
	}

	@Override
	public int selectEmployeeListSize() {
		log.info("EmpDaoImpl selectAllEmplList : {}");
		return (Integer)session.selectOne(NS+"selectEmployeeListSize");
	}

	@Override
	public EmployeeDTO selectEmployee(String id) {
		log.info("EmployeeDAO selectEmployee : {}",id);
		return session.selectOne(NS+"selectEmployee", id);
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

	@Override
	public boolean moveEmployee(int department_id) {
		log.info("EmployeeDAO moveEmp : {}",department_id);
		return session.update(NS+"moveEmployee",department_id)>0?true:false;
	}

	@Override
	public String selectId(String id) {
		log.info("EmployeeDAO selectId : {}",id);
		return session.selectOne(NS+"selectEmpId", id);
	}

	@Override
	public List<EmployeeDTO> selectRank() {
		log.info("EmployeeDAO selectRank : {}");
		return session.selectList(NS+"selectRank");
	}
	
	

}
