package com.hexa.core.model.mng.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Maps;
import com.hexa.core.dto.DepartmentDTO;
import com.hexa.core.model.mng.inf.DepartmentIDao;

@Repository
public class DepartmentDaoImpl implements DepartmentIDao {

	@Autowired
	private SqlSessionTemplate session;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String NS = "com.hexa.core.dept.";
	
	@Override
	public boolean insertDepartment(DepartmentDTO dto) {
		log.info("DepartmentDao insertDept : {}",dto);
		return session.insert(NS+"insertDepartment", dto)>0?true:false;
	}

	@Override
	public boolean deleteDepartment(int department_id) {
		log.info("DepartmentDao deleteDept : {}",department_id);
		return session.insert(NS+"deleteDepartment", department_id)>0?true:false;
	}

	@Override
	public boolean updateDepartment(DepartmentDTO dto) {
		log.info("DepartmentDao updateDept: {}",dto);
		return session.update(NS+"updateDepartment", dto)>0?true:false;
	}

	@Override
	public List<DepartmentDTO> selectChildDepartmentList(int department_id) {
		log.info("DepartmentDao selectDeptList: {}",department_id);
		Map<String,Object> map = Maps.newHashMap();
		map.put("dept_id", String.valueOf(department_id));
		return session.selectList(NS+"selectDepartmentList",map);
	}

	@Override
	public DepartmentDTO selectDepartment(int department_id) {
		log.info("DepartmentDao selectDept: {}",department_id);
		return session.selectOne(NS+"selectDepartment",department_id);
	}

	@Override
	public int selectMaxId() {
		log.info("DepartmentDao selectMaxId");
		return session.selectOne(NS+"selectMaxId");
	}

	@Override
	public boolean moveDepartment(DepartmentDTO dto) {
		log.info("DepartmentDao moveDept: {}",dto);
		return session.update(NS+"moveDepartment",dto)>0?true:false;
	}


}
