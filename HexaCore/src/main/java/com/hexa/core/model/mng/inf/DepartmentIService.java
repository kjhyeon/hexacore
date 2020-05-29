package com.hexa.core.model.mng.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.DepartmentDTO;

public interface DepartmentIService {

	static public final boolean deptTree = false;
	static public final boolean allTree = true;

	public int insertDepartment(DepartmentDTO dto);
	
	public boolean deleteDepartment(int department_id);
	
	public boolean updateDepartment(DepartmentDTO dto);
	
	public boolean moveDepartment(DepartmentDTO dto);
	
	public List<DepartmentDTO> selectDepartmentList(int department_id);
	
	public List<Map<String, Object>> selectCompanyTree(int department_id, boolean mode);
	
	public DepartmentDTO selectDepartment(int department_id);

	public int selectMaxId();
}
