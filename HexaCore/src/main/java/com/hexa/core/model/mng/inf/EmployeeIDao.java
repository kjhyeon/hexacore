package com.hexa.core.model.mng.inf;

import java.util.List;

import com.hexa.core.dto.EmployeeDTO;

public interface EmployeeIDao {

	public boolean insertEmployee(EmployeeDTO dto);
	
	public boolean deleteEmployee(String employee_number);
	
	public boolean updateEmployee(EmployeeDTO dto);
	
	public List<EmployeeDTO> selectChildEmployeeList(String deplartment_id);
	
	public List<EmployeeDTO> selectEmployeeList();
	
	public List<EmployeeDTO> selectAllEmployeeList();
	
	public EmployeeDTO selectEmployee(String employee_number);
	
	public String selectEmpPw(String id);
	
	public EmployeeDTO selectLoginInfo(String id);
	
}
