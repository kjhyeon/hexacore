package com.hexa.core.model.mng.inf;

import java.util.List;

import com.hexa.core.dto.DepartmentDTO;

public interface DepartmentIDao {

	public boolean insertDept(DepartmentDTO dto);
	
	public boolean deleteDept(String deptId);
	
	public boolean updateDept(DepartmentDTO dto);
	
	public List<DepartmentDTO> selectChildDeptList(String deptId);
	
}
