package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FileBbsIDao;

@Repository
public class FileBbsDaoImpl implements FileBbsIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private final String NS = "com.hexa.core.file.fileb.";
	
	@Override
	public boolean insertFileBbs(BbsDTO dto) {
		log.info("자료실 글 작성 insertFileBbs,\t {}", dto);
		int n = session.insert(NS + "insertFileBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public int updateModifyFileBbs(BbsDTO dto) {
		log.info("자료실 글 수정 updateModifyFileBbs,\t {}", dto);
		return session.update(NS + "updateModifyFileBbs", dto);
	}

	@Override
	public int updateDeleteFileBbs(String seq) {
		log.info("자료실 글 단일삭제 updateDeleteFileBbs,\t {}", seq);
		return session.update(NS + "updateDeleteFileBbs", seq);
	}

	@Override
	public boolean updateMultiDelFileBbs(Map<String, String[]> map) {
		log.info("자료실 글 다중삭제 updateMultiDelFileBbs,\t {}", map);
		int n = session.update(NS + "updateMultiDelFileBbs", map);
		return (n>0)?true:false;
	}

	@Override
	public BbsDTO selectDetailFileBbs(String seq) {
		log.info("자료실 글 상세보기 selectDetailFileBbs,\t {}", seq);
		return session.selectOne(NS + "selectDetailFileBbs", seq);
	}

	@Override
	public boolean updateViewsFileBbs(String seq) {
		log.info("자료실 글 조회수 증가 updateViewsFileBbs,/t {}", seq);
		int n = session.update(NS + "updateViewsFileBbs", seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateReplyFileBbs(BbsDTO dto) {
		log.info("자료실 답글작성 updateReplyFileBbs,\t {}", dto);
		int n = session.update(NS + "updateReplyFileBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public boolean insertReplyFileBbs(BbsDTO dto) {
		log.info("자료실 답글 작성완료 insertReplyFileBbs,\t {}", dto);
		int n = session.update(NS + "insertReplyFileBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public String selectNewFileBbs() {
		log.info("자료실 seq 최댓값 selectNewFileBbs,\t {}");
		return session.selectOne(NS + "selectNewFileBbs");
	}

	@Override
	public List<BbsDTO> selectUserFileBbsList() {
		log.info("자료실 목록 (유저) selectUserFileBbsList,\t {}");
		return session.selectList(NS+"selectUserFileBbsList");
	}

	@Override
	public List<BbsDTO> selectAdminFileBbsList() {
		log.info("자료실 목록(관리자 ) selectAdminFileBbsList,\t {}");
		return session.selectList(NS + "selectAdminFileBbsList");
	}

	@Override
	public List<BbsDTO> selectUserFileBbsListRow(RowNumDTO rdto) {
		log.info("자료실 글 목록 페이징 처리(유저) selectUserFileBbsListRow,\t {}", rdto) ;
		return session.selectList(NS + "selectUserFileBbsListRow", rdto);
	}

	@Override
	public List<BbsDTO> selectAdminFileBbsListRow(RowNumDTO rdto) {
		log.info("자료실 글 목록 페이징 처리(관리자) selectAdminFileBbsListRow,\t {}", rdto);
		return session.selectList(NS + "selectAdminFileBbsListRow", rdto);
	}

	@Override
	public int selectUserFileBoardListTotal() {
		log.info("자료실 글 전체 갯수(유저) selectUserFileBoardListTotal,\t {}");
		return session.selectOne(NS + "selectUserFileBoardListTotal");
	}

	@Override
	public int selectAdminFileBoardListTotal() {
		log.info("자료실 글 전체 갯수(관리자) selectAdminFileBoardListTotal,\t {}"); 
		return session.selectOne(NS + "selectAdminFileBoardListTotal");
	}

	@Override
	public List<FileDTO> selectFileBbsFile(String seq) {
		log.info("자료실 파일 조회 selectFileBbsFile,\t {}", seq);
		return session.selectList(NS + "selectFileBbsFile", seq);
	}

	@Override
	public boolean insertFileBbsFile(FileDTO fdto) {
		log.info("자료실 파일 추가 insertFileBbsFile,\t {}", fdto);
		int n = session.insert(NS + "insertFileBbsFile", fdto);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFileBbsFile(Map<String, Object> map) {
		log.info("자료실 파일 삭제 deleteFileBbsFile,\t {}", map);
		int n = session.delete(NS + "deleteFileBbsFile", map);
		return (n>0)?true:false;
	}

}
