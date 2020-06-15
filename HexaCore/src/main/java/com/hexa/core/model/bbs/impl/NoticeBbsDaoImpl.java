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
import com.hexa.core.model.bbs.inf.NoticeBbsIDao;

@Repository
public class NoticeBbsDaoImpl implements NoticeBbsIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private String NS = "com.hexa.core.notice.noticeb.";
	
	@Override
	public boolean insertNoticeBbs(BbsDTO dto) {
		log.info("공지게시판 글 작성 insertNoticeBbs,\t {}", dto);
		int n = session.insert(NS + "insertNoticeBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public int updateModifyNoticeBbs(BbsDTO dto) {
		log.info("공지게시판 글 수정 updateModifyNoticeBbs,\t {}", dto);
		return session.update(NS + "updateModifyNoticeBbs", dto);
	}

	@Override
	public int updateDeleteNoticeBbs(String seq) {
		log.info("공지게시판 글 단일삭제 updateDeleteNoticeBbs,\t {}", seq);
		return session.update(NS + "updateDeleteNoticeBbs", seq);
	}

	@Override
	public boolean updateMultiDelNoticeBbs(Map<String, String[]> map) {
		log.info("공지게시판 글 다중삭제 updateMultiDelNoticeBbs,\t {}", map);
		int n = session.update(NS + "updateMultiDelNoticeBbs", map);
		return (n>0)?true:false;
	}

	@Override
	public BbsDTO selectDetailNoticeBbs(String seq) {
		log.info("공지게시판 글 상세보기 selectDetailNoticeBbs,\t {}", seq);
		return session.selectOne(NS + "selectDetailNoticeBbs", seq);
	}

	@Override
	public boolean updateViewsNoticeBbs(String seq) {
		log.info("공지게시판 글 조회수 증가 updateViewsNoticeBbs,/t {}", seq);
		int n = session.update(NS + "updateViewsNoticeBbs", seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateReplyNoticeBbs(BbsDTO dto) {
		log.info("공지게시판 답글작성 updateReplyNoticeBbs,\t {}", dto);
		int n = session.update(NS + "updateReplyNoticeBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public boolean insertReplyNoticeBbs(BbsDTO dto) {
		log.info("공지게시판 답글 작성완료 insertReplyNoticeBbs,\t {}", dto);
		int n = session.update(NS + "insertReplyNoticeBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public String selectNewNoticeBbs() {
		log.info("공지게시글 seq 최댓값 selectNewNoticeBbs,\t {}");
		return session.selectOne(NS + "selectNewNoticeBbs");
	}

	@Override
	public List<BbsDTO> selectUserNoticeBbsList() {
		log.info("공지게시글 목록 (유저) selectUserNoticeBbsList,\t {}");
		return session.selectList(NS+"selectUserNoticeBbsList");
	}

	@Override
	public List<BbsDTO> selectAdminNoticeBbsList() {
		log.info("공지게시글 목록(관리자 ) selectAdminNoticeBbsList,\t {}");
		return session.selectList(NS + "selectAdminNoticeBbsList");
	}

	@Override
	public List<BbsDTO> selectUserNoticeBbsListRow(RowNumDTO rdto) {
		log.info("공지게시판 글 목록 페이징 처리(유저) selectUserNoticeBbsListRow,\t {}", rdto) ;
		return session.selectList(NS + "selectUserNoticeBbsListRow", rdto);
	}

	@Override
	public List<BbsDTO> selectAdminNoticeBbsListRow(RowNumDTO rdto) {
		log.info("공지게시판 글 목록 페이징 처리(관리자) selectAdminNoticeBbsListRow,\t {}", rdto);
		return session.selectList(NS + "selectAdminNoticeBbsListRow", rdto);
	}

	@Override
	public int selectUserNoticeBoardListTotal() {
		log.info("공지게시판 글 전체 갯수(유저) selectUserNoticeBoardListTotal,\t {}");
		return session.selectOne(NS + "selectUserNoticeBoardListTotal");
	}

	@Override
	public int selectAdminNoticeBoardListTotal() {
		log.info("공지게시판 글 전체 갯수(관리자) selectAdminNoticeBoardListTotal,\t {}"); 
		return session.selectOne(NS + "selectAdminNoticeBoardListTotal");
	}

	@Override
	public List<FileDTO> selectNoticeBbsFile(String seq) {
		log.info("공지게시판 파일 조회 selectNoticeBbsFile,\t {}", seq);
		return session.selectList(NS + "selectNoticeBbsFile", seq);
	}

	@Override
	public boolean insertNoticeBbsFile(FileDTO fdto) {
		log.info("공지게시판 파일 추가 insertNoticeBbsFile,\t {}", fdto);
		int n = session.insert(NS + "insertNoticeBbsFile", fdto);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteNoticeBbsFile(Map<String, Object> map) {
		log.info("공지게시판 파일 삭제 deleteNoticeBbsFile,\t {}", map);
		int n = session.delete(NS + "deleteNoticeBbsFile", map);
		return (n>0)?true:false;
	}

}
