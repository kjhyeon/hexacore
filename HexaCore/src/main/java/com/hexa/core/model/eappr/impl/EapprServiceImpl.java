package com.hexa.core.model.eappr.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;
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
	public List<DocumentTypeDTO> selectDocTypeListP(RowNumDTO row) {
		log.info("selectDocTypeListP serviceImpl 실행");
		return dao.selectDocTypeListP(row);
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

//	@Override
//	public boolean deleteApprRoot(String seq) {
//		return
//	}

	@Transactional
	@Override
	public boolean insertApprRoot(ApprovalDTO Adto) {
		log.info("deleteApprRoot serviceImpl 실행");
		boolean iscD = dao.deleteApprRoot(Integer.toString(Adto.getSeq()));
		log.info("insertApprRoot serviceImpl 실행");
		boolean iscI = dao.insertApprRoot(Adto);
		return (iscD||iscI)?true:false;
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
	public List<DocumentDTO> selectNeedApprDoc(Map<String, Object> map) {
		log.info("selectNeedApprDoc serviceImpl 실행 id : {}", map);
		return dao.selectNeedApprDoc(map);
	}

	@Override
	public List<DocumentDTO> selectAllDoc() {
		log.info("selectAllDoc ServiceImpl 실행");
		return dao.selectAllDoc();
	}

	@Override
	public String selectNewDocType() {
		log.info("selectNewDocType ServiceImpl 실행");
		return dao.selectNewDocType();
	}

	@Override
	public boolean deleteDocType(String seq) {
		log.info("deleteDocType ServiceImpl 실행 : {}",seq);
		return dao.deleteDocType(seq);
	}

	@Override
	public boolean updateDocType(DocumentTypeDTO dto) {
		log.info("updateDocType ServiceImpl 실행 : {}",dto);
		return dao.updateDocType(dto);
	}

	@Override
	public String selectSignImg(String id) {
		log.info("selectSignImg ServiceImpl 실행 : {}",id);
		return dao.selectSignImg(id);
	}

	@Override
	public List<DocCommentDTO> selectComment(String seq) {
		log.info("selectComment ServiceImpl 실행 : {}",seq);
		return dao.selectComment(seq);
	}

	@Override
	public int selectReportCount(String id) {
		log.info("selectReportCount ServiceImpl 실행 : {}",id);
		return dao.selectReportCount(id);
	}

	@Override
	public int selectMyDocCount(Map<String, Object> map) {
		log.info("selectMyDocCount ServiceImpl 실행 : {}",map);
		return dao.selectMyDocCount(map);
	}

	@Override
	public int selectReferDocCount(String id) {
		log.info("selectReferDocCount ServiceImpl 실행 : {}",id);
		return dao.selectReferDocCount(id);
	}

	@Override
	public int selectApprDocCount(String id) {
		log.info("selectApprDocCount ServiceImpl 실행 : {}",id);
		return dao.selectApprDocCount(id);
	}

	@Override
	public int selectNeedApprDocCount(String id) {
		log.info("selectNeedApprDocCount ServiceImpl 실행 : {}",id);
		return dao.selectNeedApprDocCount(id);
	}

	@Override
	public int selectDocTypeListSize() {
		log.info("selectDocTypeListSize ServiceImpl 실행");
		return dao.selectDocTypeListSize();
	}

	@Override
	public List<DocumentDTO> searchDoc(String word) {
		log.info("searchDoc ServiceImpl 실행 : {}",word);
		return dao.searchDoc(word);
	}

	@Override
	public List<DocumentDTO> selectApprMyDoc(Map<String, Object> map) {
		log.info("selectApprMyDoc ServiceImpl 실행 : {}",map);
		return dao.selectApprMyDoc(map);
	}

	@Override
	public List<DocumentDTO> selectMyDocList(Map<String, Object> map) {
		log.info("selectMyDoc ServiceImpl 실행 : {}",map);
		return dao.selectMyDocList(map);
	}

	@Override
	public List<DocumentDTO> selectReferDoc(Map<String, Object> map) {
		log.info("selectReferDoc ServiceImpl 실행 : {}",map);
		return dao.selectReferDoc(map);
	}

	@Override
	public List<DocumentDTO> selectDocListAll(String id) {
		log.info("selectDocListAll ServiceImpl 실행 : {}",id);
		return dao.selectDocListAll(id);
	}

}
