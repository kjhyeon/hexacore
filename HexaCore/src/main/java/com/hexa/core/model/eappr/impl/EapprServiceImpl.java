package com.hexa.core.model.eappr.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.model.eappr.inf.EapprIDao;
import com.hexa.core.model.eappr.inf.EapprIService;

@Service
public class EapprServiceImpl implements EapprIService{

	@Autowired
	private EapprIDao dao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<DocumentTypeDTO> selectDocTypeList() {
		log.info("selectDocTypeList serviceImpl 실행");
		return dao.selectDocTypeList();
	}

	@Override
	public DocumentDTO selectDoc(String seq) {
		log.info("selectDoc serviceImpl 실행");
		return dao.selectDoc(seq);
	}

	@Override
	public boolean updateDoc(DocumentDTO Ddto) {
		log.info("updateDoc serviceImpl 실행");
		return dao.updateDoc(Ddto);
	}

	@Override
	public boolean deleteApprRoot(String seq) {
		log.info("deleteApprRoot serviceImpl 실행");
		return false;
	}

	@Override
	public boolean insertApprRoot(ApprovalDTO Adto) {
		log.info("insertApprRoot serviceImpl 실행");
		return false;
	}
}
