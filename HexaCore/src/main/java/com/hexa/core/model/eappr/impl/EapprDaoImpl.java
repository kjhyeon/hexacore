package com.hexa.core.model.eappr.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocumentDTO;
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
	
	@Override
	public DocumentDTO selectDoc(String seq) {
		log.info("selectDoc daoImpl 실행 : {}",seq);
		return sqlSession.selectOne(NS+"selectDoc");
	}

	@Override
	public boolean updateDoc(DocumentDTO Ddto) {
		log.info("updateDoc daoImpl 실행 : {}",Ddto);
		int n = sqlSession.update(NS+"selectDoc");
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteApprRoot(String seq) {
		log.info("deleteApprRoot daoImpl 실행 : {}",seq);
		int n = sqlSession.delete(NS+"deleteApprRoot");
		return (n>0)?true:false;
	}

	@Override
	public boolean insertApprRoot(ApprovalDTO Adto) {
		log.info("insertApprRoot daoImpl 실행 : {}",Adto);
		int n = sqlSession.insert(NS+"insertApprRoot");
		return (n>0)?true:false;
	}
}
