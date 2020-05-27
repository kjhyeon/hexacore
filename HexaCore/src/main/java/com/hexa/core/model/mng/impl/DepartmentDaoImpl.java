package com.hexa.core.model.mng.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hexa.core.dto.DepartmentDTO;
import com.hexa.core.model.mng.inf.DepartmentIDao;

@Repository
public class DepartmentDaoImpl implements DepartmentIDao {

	@Override
	public boolean insertDept(DepartmentDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteDept(String deptId) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateDept(DepartmentDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<DepartmentDTO> selectChildDeptList(String deptId) {
		// TODO Auto-generated method stub
		return null;
	}

}
