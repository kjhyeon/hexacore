package com.hexa.core.model.mng.inf;

import java.util.List;

import com.hexa.core.dto.EmployeeDTO;

public interface EmployeeIDao {

	public boolean insertEmployee(EmployeeDTO dto);
	
	public boolean deleteEmployee(int employee_number);
	
	public boolean updateEmployee(EmployeeDTO dto);
	
	public List<EmployeeDTO> selectChildEmployeeList(int department_id);
	
	public List<EmployeeDTO> selectEmployeeList();
	
	public List<EmployeeDTO> selectAllEmployeeList();
	
	public EmployeeDTO selectEmployee(int employee_number);
	
	public String selectEmpPw(String id);
	
	public EmployeeDTO selectLoginInfo(String id);
	
	public boolean moveEmployee(int department_id);
}
