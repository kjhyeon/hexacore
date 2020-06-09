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
import com.hexa.core.model.bbs.inf.FreeBbsIDao;

@Repository
public class FreeBbsDaoImpl implements FreeBbsIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	private String NS = "com.hexa.core.notice.freeb.";
	
	@Override
	public boolean insertFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 작성 insertFreeBbs,\t {}", dto);
		int n = session.insert(NS + "insertFreeBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public int updateModifyFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 수정 updateModifyFreeBbs,\t {}", dto);
		return session.update(NS + "updateModifyFreeBbs", dto);
	}

	@Override
	public int updateDeleteFreeBbs(String seq) {
		log.info("자유게시판 글 단일삭제 updateDeleteFreeBbs,\t {}", seq);
		return session.update(NS + "updateDeleteFreeBbs", seq); 
	}

	@Override
	public boolean updateMultiDelFreeBbs(Map<String, String[]> map) {
		log.info("자유게시판 글 다중삭제 updateMultiDelFreeBbs,\t {}", map);
		int n = session.update(NS + "updateMultiDelFreeBbs", map);
		return (n>0)?true:false;
	}

	@Override
	public BbsDTO selectDetailFreeBbs(String seq) {
		log.info("자유게시판 글 상세보기 selectDetailFreeBbs,\t {}", seq);
		return session.selectOne(NS + "selectDetailFreeBbs", seq);
	}

	@Override
	public boolean updateViewsBbs(String seq) {
		log.info("자유게시판 조회수 증가 updateViewsBbs,\t {}", seq);
		int n = session.update(NS + "updateViewsBbs", seq);
		return (n>=0)?true:false;
	}

	@Override
	public boolean updateReplyBbs(BbsDTO dto) {
		log.info("자유게시판 updateReplyBbs,\t {}", dto);
		int n = session.update(NS + "updateReplyBbs", dto);
		return (n>=0)?true:false;
	}
	@Override
	public boolean insertReplyBbs(BbsDTO dto) {
		log.info("자유게시판 답글작성 insertReplyBbs,\t {}", dto);
		int n = session.insert(NS + "insertReplyBbs", dto);
		return (n>0)?true:false;
	}

	@Override
	public List<BbsDTO> selectUserFreeBbsList() {
		log.info("자유게시판 글 목록조회(유저) selectUserFreeBbsList,\t {}");
		return session.selectList(NS + "selectUserFreeBbsList");
	}

	@Override
	public List<BbsDTO> selectAdminFreeBbsList() {
		log.info("자유게시판 글 목록 조회(관리자) selectAdminFreeBbsList,\t {}");
		return session.selectList(NS + "selectAdminFreeBbsList");
	}

	@Override
	public List<BbsDTO> selectUserBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) selectAdminFreeBbsList,\t {}", rdto);
		return session.selectList(NS+"selectUserBbsListRow",rdto);
	}

	@Override
	public List<BbsDTO> selectAdminBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(관리자) selectAdminBbsListRow,\t {}", rdto);
		return session.selectList(NS+"selectAdminBbsListRow",rdto);
	}

	@Override
	public int selectUserBoardListTotal() {
		log.info("자유게시판 글 전체 갯수(유저) selectUserBoardListTotal,\t {}");
		return session.selectOne(NS+"selectUserBoardListTotal");
	}

	@Override
	public int selectAdminBoardListTotal() {
		log.info("자유게시판 글 전체 갯수(유저) selectAdminBoardListTotal,\t {}");
		return session.selectOne(NS+"selectAdminBoardListTotal");
	}

	@Override
	public String selectNewBbs() {
		log.info("게시글 seq 최대값 selectNewBbs");
		return session.selectOne(NS + "selectNewBbs");
	}

	@Override
	public List<FileDTO> selectFile(String seq) {
		log.info("파일 조회 selectFile,\t {}", seq);
		return session.selectList(NS + "selectFile", seq);
	}

	@Override
	public boolean insertFile(FileDTO fDto) {
		log.info("파일 추가 insertFile,\t {}", fDto);
		int n = session.insert(NS + "insertFile", fDto);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFile(String seq) {
		log.info("파일 삭제 deleteFile,\t {}", seq);
		int n = session.delete(NS + "deleteFile", seq);
		return (n>0)?true:false;
	}

}
