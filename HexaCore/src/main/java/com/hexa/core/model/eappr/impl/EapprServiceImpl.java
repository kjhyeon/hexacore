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

	@Transactional
	@Override
	public boolean insertApprRoot(DocumentDTO Ddto) {
		log.info("deleteApprRoot serviceImpl 실행");
		dao.deleteApprRoot(Integer.toString(Ddto.getSeq()));
		int iscI =0;
			for (int i = 0; i < Ddto.getLists().size(); i++) {
				log.info("insertApprRoot serviceImpl 실행");
				iscI += dao.insertApprRoot(Ddto.getLists().get(i));
			}
		return (iscI>2)?true:false;
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
	public int selectReferDocCount(Map<String, Object> map) {
		log.info("selectReferDocCount ServiceImpl 실행 : {}",map);
		return dao.selectReferDocCount(map);
	}

	@Override
	public int selectApprDocCount(Map<String, Object> map) {
		log.info("selectApprDocCount ServiceImpl 실행 : {}",map);
		return dao.selectApprDocCount(map);
	}

	@Override
	public int selectAllApprDocCount(Map<String, Object> map) {
		log.info("selectNeedApprDocCount ServiceImpl 실행 : {}",map);
		int cnt=0;
		
		log.info("**** 실행 : {}",map);
		if(Integer.parseInt((String)map.get("state"))<5) {
			cnt = dao.selectMyDocCount(map);
		}else {
			switch ((String)map.get("state")) {
				case "6":
					cnt = dao.selectApprDocCount(map);
					break;
				case "7":
					cnt = dao.selectNeedApprDocCount(map);
					break;
				case "8":
					cnt = dao.selectReferDocCount(map);
					break;
				default:
					break;
			}
		}
		return cnt;
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
	public List<DocumentDTO> selectMyDocList(Map<String, Object> map) {
		log.info("selectMyDoc ServiceImpl 실행 : {}",map);
		List<DocumentDTO> lists = null;
		
		log.info("**** 실행 : {}",map);
		if(Integer.parseInt((String)map.get("state"))<5) {
			lists = dao.selectMyDocList(map);
		}else {
			switch ((String)map.get("state")) {
				case "6":
					lists = dao.selectApprMyDoc(map);
					break;
				case "7":
					lists = dao.selectNeedApprDoc(map);
					break;
				case "8":
					lists = dao.selectReferDoc(map);
					break;
				default:
					break;
			}
		}
		return lists;
	}

	@Transactional
	@Override
	public boolean confirmUpdate(Map<String, Object> map) {
		boolean iscD=false;
		boolean iscA=false;
		boolean iscDC=false;
		if(map.get("Ddto")!=null) {
			iscD = dao.updateDoc((DocumentDTO)map.get("Ddto"));
		}
		if(iscD=true && map.get("Adto")!=null) {
			iscA = dao.updateApprChk((ApprovalDTO)map.get("Adto"));
		}
		if((iscD && iscA) ==true && map.get("DCdto")!=null) {
			iscDC = dao.insertComment((DocCommentDTO)map.get("DCdto"));
		}
		return (iscD||iscA||iscDC)?true:false;
	}

	@Override
	public Map<String, Object> selectDocListAll(String id) {
		return dao.selectDocListAll(id);
	}

	@Override
	public boolean updateSaveToAppr(String seq) {
		log.info("updateSaveToAppr ServiceImpl 실행 : {}",seq);
		return dao.updateSaveToAppr(seq);
	}
	
	@Override
	public boolean reportCancel(String seq) {
		log.info("reportCancel ServiceImpl 실행 : {}",seq);
		return dao.reportCancel(seq);
	}

}
