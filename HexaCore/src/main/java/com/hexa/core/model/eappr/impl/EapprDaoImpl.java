package com.hexa.core.model.eappr.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.model.eappr.inf.EapprIDao;

@Repository
public class EapprDaoImpl implements EapprIDao{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final String NS = "hexa.core.web.edoc.sqls.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<DocumentTypeDTO> docTypeList() {
		log.info("docTypeList daoImpl 실행");
		return sqlSession.selectList(NS+"docTypeList");
	}
}
