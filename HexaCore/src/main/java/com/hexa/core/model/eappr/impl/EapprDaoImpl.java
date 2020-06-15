
package com.hexa.core.model.eappr.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.eappr.inf.EapprIDao;

@Repository
public class EapprDaoImpl implements EapprIDao{

	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	private final String NS = "hexa.core.web.eappr.sqls.";
	
	@Override
	public List<DocumentTypeDTO> selectDocTypeList() {
		log.info("selectDocTypeList daoImpl 실행");
		return sqlSession.selectList(NS+"selectDocTypeList");
	}
	
	@Override
	public List<DocumentTypeDTO> selectDocTypeListP(RowNumDTO row) {
		log.info("selectDocTypeListP daoImpl 실행");
		return sqlSession.selectList(NS+"selectDocTypeListP", row);
	}
	
	@Override
	public boolean insertNewDoc(DocumentDTO dto) {
		log.info("insertNewDoc daoImpl 실행");
		return sqlSession.insert(NS+"insertNewDoc", dto)>0?true:false;
	}
	
	@Override
	public DocumentTypeDTO selectDocType(String type_seq) {
		log.info("selectDocType daoImpl 실행");
		return sqlSession.selectOne(NS+"selectDocType", type_seq);
	}

	@Override
	public DocumentDTO selectDoc(String seq) {
		log.info("selectDoc daoImpl 실행 : {}",seq);
		return sqlSession.selectOne(NS+"selectDoc", seq);
	}

	@Override
	public boolean updateDoc(DocumentDTO Ddto) {
		log.info("updateDoc daoImpl 실행 : {}",Ddto);
		int n = sqlSession.update(NS+"updateDoc",Ddto);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteApprRoot(String seq) {
		log.info("deleteApprRoot daoImpl 실행 : {}",seq);
		int n = sqlSession.delete(NS+"deleteApprRoot",seq);
		return (n>0)?true:false;
	}

	@Override
	public int insertApprRoot(ApprovalDTO Adto) {
		log.info("insertApprRoot daoImpl 실행 : {}",Adto);
		int n = sqlSession.insert(NS+"insertApprRoot",Adto);
		return (n>0)?1:0;
	}

	@Override
	public List<ApprovalDTO> selectApprRoot(ApprovalDTO Adto) {
		log.info("selectApprRoot daoImpl 실행 : {}",Adto);
		return sqlSession.selectList(NS+"selectApprRoot",Adto);
	}

	@Override
	public boolean insertFile(DocFileDTO DFdto) {
		log.info("insertFile daoImpl 실행 : {}",DFdto);
		int n = sqlSession.insert(NS+"insertFile",DFdto);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFile(String seq) {
		log.info("deleteFile daoImpl 실행 : {}",seq);
		int n = sqlSession.insert(NS+"deleteFile",seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateApprChk(ApprovalDTO Adto) {
		log.info("updateApprChk daoImpl 실행 : {}",Adto);
		int n = sqlSession.insert(NS+"updateApprChk",Adto);
		return (n>0)?true:false;
	}

	@Override
	public boolean insertComment(DocCommentDTO DCdto) {
		log.info("insertComment daoImpl 실행 : {}",DCdto);
		int n = sqlSession.insert(NS+"insertComment",DCdto);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateDocTurn(ApprovalDTO Adto) {
		log.info("updateDocTurn daoImpl 실행 : {}",Adto);
		int n = sqlSession.insert(NS+"updateDocTurn",Adto);
		return (n>0)?true:false;
	}

	@Override
	public boolean insertDocType(DocumentTypeDTO DTdto) {
		log.info("insertDocType daoImpl 실행 : {}",DTdto);
		int n = sqlSession.insert(NS+"insertDocType",DTdto);
		return (n>0)?true:false;
	}

	@Override
	public String selectNewDoc() {
		log.info("selectNewDoc daoImpl 실행");
		return sqlSession.selectOne(NS+"selectNewDoc");
	}

	@Override
	public boolean deleteDoc(String seq) {
		log.info("deleteDoc DaoImpl 실행 seq : {}", seq);
		return sqlSession.delete(NS+"deleteDoc", seq)>0?true:false;
	}
	
	@Override
	public List<DocumentDTO>  selectNeedApprDoc(Map<String, Object> map) {
		log.info("selectNeedApprDoc DaoImpl 실행 map : {}", map);
		 List<DocumentDTO> lists = sqlSession.selectList(NS+"selectNeedApprDoc",map);
		return lists;
	}

	@Override
	public List<DocumentDTO> selectAllDoc() {
		log.info("selectAllDoc DaoImpl 실행");
		return sqlSession.selectList(NS+"selectAllDoc");
	}

	@Override
	public String selectNewDocType() {
		log.info("selectNewDocType DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectNewDocType");
	}

	@Override
	public boolean deleteDocType(String seq) {
		log.info("deleteDocType DaoImpl 실행");
		return sqlSession.delete(NS+"deleteDocType", seq)>0?true:false;
	}

	@Override
	public boolean updateDocType(DocumentTypeDTO dto) {
		log.info("updateDocType DaoImpl 실행");
		return sqlSession.update(NS+"updateDocType", dto)>0?true:false;
	}

	@Override
	public String selectSignImg(String id) {
		log.info("selectSignImg DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectSignImg", id);
	}

	@Override
	public List<DocCommentDTO> selectComment(String seq) {
		log.info("selectComment DaoImpl 실행");
		return sqlSession.selectList(NS+"selectComment",seq);
	}

	@Override
	public int selectReportCount(String id) {
		log.info("selectReportCount DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectReportCount", id);
	}

	@Override
	public int selectMyDocCount(Map<String, Object> map) {
		log.info("selectMyDocCount DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectMyDocCount", map);
	}

	@Override
	public int selectReferDocCount(Map<String, Object> map) {
		log.info("selectReferDocCount DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectReferDocCount", map);
	}

	@Override
	public int selectApprDocCount(Map<String, Object> map) {
		log.info("selectApprDocCount DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectApprDocCount", map);
	}

	@Override
	public int selectNeedApprDocCount(Map<String, Object> map) {
		log.info("selectNeedApprDocCount DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectNeedApprDocCount", map);
	}

	@Override
	public int selectDocTypeListSize() {
		log.info("selectDocTypeListSize DaoImpl 실행");
		return sqlSession.selectOne(NS+"selectDocTypeListSize");
	}

	@Override
	public List<DocumentDTO> searchDoc(String word) {
		log.info("searchDoc DaoImpl 실행");
		return null;
	}

	@Override
	public List<DocumentDTO> selectApprMyDoc(Map<String, Object> map) {
		log.info("searchDoc DaoImpl 실행 : {}",map);
		return sqlSession.selectList(NS+"selectApprMyDoc",map);
	}

	@Override
	public List<DocumentDTO> selectMyDocList(Map<String, Object> map) {
		log.info("selectMyDoc DaoImpl 실행 : {}",map);
		return sqlSession.selectList(NS+"selectMyDocList", map);
	}

	@Override
	public List<DocumentDTO> selectReferDoc(Map<String, Object> map) {
		log.info("selectReferDoc DaoImpl 실행 : {}",map);
		return sqlSession.selectList(NS+"selectReferDoc", map);
	}

	@Override
	public Map<String, Object> selectDocListAll(String id) {
		return sqlSession.selectOne(NS+"selectDocListAll",id);
	}

	@Override
	public boolean updateSaveToAppr(String seq) {
		log.info("updateSaveToAppr DaoImpl 실행 : {}",seq);
		return sqlSession.update(NS+"updateSaveToAppr", seq)>0?true:false;
	}
	
	@Override
	public boolean reportCancel(String seq) {
		log.info("reportCancel DaoImpl 실행 : {}",seq);
		return sqlSession.update(NS+"reportCancel", seq)>0?true:false;
	}

}
