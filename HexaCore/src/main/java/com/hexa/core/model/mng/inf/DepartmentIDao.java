package com.hexa.core.model.mng.inf;

import java.util.List;

import com.hexa.core.dto.DepartmentDTO;

public interface DepartmentIDao {

	public boolean insertDepartment(DepartmentDTO dto);
	
	public boolean deleteDepartment(int department_id);
	
	public boolean updateDepartment(DepartmentDTO dto);
	
	public List<DepartmentDTO> selectChildDepartmentList(int department_id);
	
	public DepartmentDTO selectDepartment(int department_id);

	public int selectMaxId();
}
