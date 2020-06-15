package com.hexa.core.model.mng.inf;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.dto.RowNumDTO;

public interface EmployeeIService {
	
	public static final String ATTACH_PATH = "../webapps/HexaCore/image/profile";

	public boolean insertEmployee(EmployeeDTO dto,MultipartFile profile_file);
	
	public boolean deleteEmployee(int employee_number);
	
	public boolean updateEmployee(EmployeeDTO dto,MultipartFile profile_file,MultipartFile sign_file);
	
	public List<EmployeeDTO> selectChildEmployeeList(int department_id);
	
	public List<EmployeeDTO> selectEmployeeList(RowNumDTO row);
	
	public int selectEmployeeListSize();
	
	public EmployeeDTO selectEmployee(String id);
	
	public EmployeeDTO selectLoginInfo(String id);
	
	public String selectId(String id);
	
	public List<EmployeeDTO> selectRank();
	
}
