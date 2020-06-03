package com.hexa.core.model.eappr.impl;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
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
	public DocumentTypeDTO selectDocType(String type_seq) {
		log.info("selectDocType serviceImpl 실행");
		return dao.selectDocType(type_seq);
	}
	
	@Override
	public boolean insertNewDoc(DocumentDTO dto) {
		log.info("insertNewDoc serviceImpl 실행");
		return dao.insertNewDoc(dto);
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
		return dao.deleteApprRoot(seq);
	}

	@Override
	public boolean insertApprRoot(ApprovalDTO Adto) {
		log.info("insertApprRoot serviceImpl 실행");
		return dao.insertApprRoot(Adto);
	}

	@Override
	public List<ApprovalDTO> selectApprRoot(ApprovalDTO Adto) {
		log.info("selectApprRoot serviceImpl 실행");
		return dao.selectApprRoot(Adto);
	}

	@Override
	public boolean insertFile(DocFileDTO DFdto) {
		log.info("insertFile serviceImpl 실행");
		return dao.insertFile(DFdto);
	}

	@Override
	public boolean deleteFile(String seq) {
		log.info("deleteFIle serviceImpl 실행");
		return dao.deleteFile(seq);
	}

	@Override
	public boolean updateApprChk(ApprovalDTO Adto) {
		log.info("updateApprChk serviceImpl 실행");
		return dao.updateApprChk(Adto);
	}

	@Override
	public boolean insertComment(DocCommentDTO DCdto) {
		log.info("insertComment serviceImpl 실행");
		return dao.insertComment(DCdto);
	}

	@Override
	public boolean updateDocTurn(ApprovalDTO Adto) {
		log.info("updateDocTurn serviceImpl 실행");
		return dao.updateDocTurn(Adto);
	}

	@Override
	public boolean insertDocType(DocumentTypeDTO DTdto) {
		log.info("insertDocType serviceImpl 실행");
		return dao.insertDocType(DTdto);
	}

	@Override
	public String selectNewDoc() {
		log.info("selectNewDoc serviceImpl 실행");
		return dao.selectNewDoc();
	}

	@Override
	public boolean deleteDoc(String seq) {
		log.info("deleteDoc serviceImpl 실행 seq : {}", seq);
		return dao.deleteDoc(seq);
	}

	@Override
	public List<DocumentDTO> selectNeedApprDoc(String id) {
		log.info("selectNeedApprDoc serviceImpl 실행 id : {}", id);
		return dao.selectNeedApprDoc(id);
	}

	@Override
	public List<DocumentDTO> selectAllDoc() {
		log.info("selectAllDoc ServiceImpl 실행");
		return dao.selectAllDoc();
	}

}
