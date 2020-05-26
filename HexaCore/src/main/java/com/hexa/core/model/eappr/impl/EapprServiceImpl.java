package com.hexa.core.model.eappr.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.model.eappr.inf.EapprIDao;
import com.hexa.core.model.eappr.inf.EapprIService;

@Service
public class EapprServiceImpl implements EapprIService{

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private EapprIDao dao;

	@Override
	public List<DocumentTypeDTO> selectDocTypeList() {
		log.info("selectDocTypeList serviceImpl 실행");
		return dao.selectDocTypeList();
	}

	@Override
	public String selectDocType(String type_seq) {
		return null;
	}
}
