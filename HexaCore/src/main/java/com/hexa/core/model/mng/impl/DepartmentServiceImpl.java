package com.hexa.core.model.mng.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hexa.core.dto.DepartmentDTO;
import com.hexa.core.dto.EmployeeDTO;
import com.hexa.core.model.mng.inf.DepartmentIDao;
import com.hexa.core.model.mng.inf.DepartmentIService;
import com.hexa.core.model.mng.inf.EmployeeIDao;

@Service
public class DepartmentServiceImpl implements DepartmentIService {

	@Autowired
	private DepartmentIDao dao;
	
	@Autowired
	private EmployeeIDao eDao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public int insertDepartment(DepartmentDTO dto) {
		log.info("DepartmentServiceImpl insertDept : {}",dto);
		dao.insertDepartment(dto);
		return dao.selectMaxId();
	}

	@Override
	public boolean deleteDepartment(int department_id) {
		log.info("DepartmentServiceImpl deleteDept : {}",department_id);
		List<DepartmentDTO> list = dao.selectChildDepartmentList(department_id);
		if(list.size()!=0) {
			for (DepartmentDTO dto : list) {
				deleteDepartment(dto.getDepartment_id());
			}
		}
		eDao.moveEmployee(department_id);
		return dao.deleteDepartment(department_id);
	}

	@Override
	public boolean updateDepartment(DepartmentDTO dto) {
		log.info("DepartmentServiceImpl updateDept : {}",dto);
		return dao.updateDepartment(dto);
	}

	@Override
	public List<DepartmentDTO> selectDepartmentList(int department_id) {
		log.info("DepartmentServiceImpl selectDeptList : {}",department_id);
		return dao.selectChildDepartmentList(department_id);
	}

	@Override
	public List<Map<String, Object>> selectCompanyTree(int department_id,boolean mode) {
		log.info("DepartmentServiceImpl selectCompanyTree");
		List<DepartmentDTO> list = dao.selectChildDepartmentList(department_id);
		List<Map<String,Object>> result = Lists.newArrayList();
		for (DepartmentDTO dto : list) {
			Map<String,Object> children = Maps.newHashMap();
			children.put("id",dto.getDepartment_id());
			children.put("state", "open");
			children.put("text", dto.getName());
			children.put("type", "folder");
			children.put("parent",department_id);
			List<Map<String,Object>> childNodes = selectCompanyTree(dto.getDepartment_id(),mode);
			if(childNodes==null) {
				childNodes = Lists.newArrayList();
			}
			if(mode) {
				List<EmployeeDTO> employees = eDao.selectChildEmployeeList(dto.getDepartment_id());
				if(employees!=null) {
					for (EmployeeDTO eDto : employees) {
						Map<String,Object> employee = Maps.newHashMap();
						employee.put("id",eDto.getId());
						employee.put("text", eDto.getName());
						employee.put("type", "people");
						employee.put("parent", eDto.getDepartment_id());
						Map<String,Object> attr = Maps.newHashMap();
						attr.put("deptname",eDto.getDepartment_name());
						attr.put("e_rank",eDto.getE_rank());
						attr.put("e_rank_name",eDto.getE_rank_name());
						employee.put("li_attr", attr);
						childNodes.add(employee);
					}
				}
			}else {
				Map<String,Object> deptInfo = Maps.newHashMap();
				deptInfo.put("faxnum", dto.getFaxnum());
				deptInfo.put("d_phone", dto.getD_phone());
				children.put("li_attr", deptInfo);
			}
			children.put("children",childNodes);
			result.add(children);
		}
		
		return result;
		
	}

	@Override
	public DepartmentDTO selectDepartment(int department_id) {
		log.info("DepartmentServiceImpl selectDept : {}",department_id);
		return dao.selectDepartment(department_id);
	}

	@Override
	public int selectMaxId() {
		log.info("DepartmentServiceImpl selecMaxId");
		return dao.selectMaxId();
	}

	@Override
	public boolean moveDepartment(DepartmentDTO dto) {
		log.info("DepartmentServiceImpl moveDept : {}",dto);
		return dao.moveDepartment(dto);
	}

}
