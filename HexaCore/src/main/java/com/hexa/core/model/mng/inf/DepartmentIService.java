package com.hexa.core.model.mng.inf;

import java.util.Map;

import com.hexa.core.dto.DepartmentDTO;

public interface DepartmentIService {


	public boolean insertDept(DepartmentDTO dto);
	
	public boolean deleteDept(String deptId);
	
	public boolean updateDept(DepartmentDTO dto);
	
	public Map<String, Object> selectDeptList();
	
	
}
