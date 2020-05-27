package com.hexa.core.model.mng.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.DepartmentDTO;

public interface DepartmentIService {


	public boolean insertDepartment(DepartmentDTO dto);
	
	public boolean deleteDepartment(int department_id);
	
	public boolean updateDepartment(DepartmentDTO dto);
	
	public List<DepartmentDTO> selectDepartmentList(int department_id);
	
	public List<Map<String, Object>> selectCompanyTree(int department_id);
	
	public DepartmentDTO selectDepartment(int department_id);
}
