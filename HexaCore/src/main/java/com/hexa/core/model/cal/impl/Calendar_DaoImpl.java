package com.hexa.core.model.cal.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.CalDTO;
import com.hexa.core.model.cal.inf.Calendar_IDao;

@Repository
public class Calendar_DaoImpl implements Calendar_IDao{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final String NS="com.hexa.core.cal.";
	
	@Override
	public List<CalDTO> selectEventsCal(Map<String, Object> map) {
		log.info("selectEventsCal : {}",map);
		return sqlSession.selectList(NS+"selectEventsCal",map);
	}

	@Override
	public int insertEventsCal(CalDTO CDto) {
		log.info("insertEventsCal : {}",CDto);
		return sqlSession.insert(NS+"insertEventsCal",CDto);
	}

	@Override
	public int deleteEventsCal(String title) {
		log.info("deleteEventsCal : {}",title);
		return sqlSession.delete(NS+"deleteEventsCal",title);
	}

}
