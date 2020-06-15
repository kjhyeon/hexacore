package com.hexa.core.model.cal.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.CalDTO;
import com.hexa.core.model.cal.inf.Calendar_IDao;
import com.hexa.core.model.cal.inf.Calendar_IService;

@Service
public class Calendar_ServiceImpl implements Calendar_IService{
	@Autowired
	private Calendar_IDao dao;
	private Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public List<CalDTO> selectEventsCal(Map<String, Object> map) {
		log.info("selectEventsCal : {}",map);
		return dao.selectEventsCal(map);
	}
	@Override
	public int insertEventsCal(CalDTO CDto) {
		log.info("insertEventsCal : {}",CDto);
		return dao.insertEventsCal(CDto);
	}

	
}
