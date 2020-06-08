package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIDao;
import com.hexa.core.model.bbs.inf.FreeBbsIService;

@Service
public class FreeBbsServiceImpl implements FreeBbsIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FreeBbsIDao dao;
	
	@Override
	public boolean insertFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 작성 insertFreeBbs,\t {}", dto);
		return dao.insertFreeBbs(dto);
	}

	@Override
	public int updateModifyFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 수정 updateModifyFreeBbs,\t {}", dto);
		return dao.updateModifyFreeBbs(dto);
	}

	@Override
	public int updateDeleteFreeBbs(String seq) {
		log.info("자유게시판 글 단일삭제 updateDeleteFreeBbs,\t {}", seq);
		return dao.updateDeleteFreeBbs(seq);
	}

	@Override
	public boolean updateMultiDelFreeBbs(Map<String, String[]> map) {
		log.info("자유게시판 글 다중삭제 updateMultiDelFreeBbs,\t {}", map);
		return dao.updateMultiDelFreeBbs(map);
	}

	@Override
	public BbsDTO selectDetailFreeBbs(String seq) {
		log.info("자유게시판 상세글 보기 selectDetailFreeBbs,\t {}", seq);
		return dao.selectDetailFreeBbs(seq);
	}

	@Override
	public boolean updateViewsBbs(String seq) {
		log.info("자유게시판 조회수 증가 updateViewsBbs,\t {}", seq);
		return dao.updateViewsBbs(seq);
	}

	@Override
	@Transactional
	public boolean ReplyBbs(BbsDTO dto) {
		log.info("자유게시판 답글 달기  ReplyBbs,\t {}", dto);
		boolean iscU = dao.updateReplyBbs(dto);
		boolean iscI = dao.insertReplyBbs(dto);
		return (iscU&&iscI)?true:false;
	}

	@Override
	public List<BbsDTO> selectUserFreeBbsList() {
		log.info("자유게시판 글 목록 조회(유저) selectUserFreeBbsList,\t {}");
		return dao.selectUserFreeBbsList();
	}

	@Override
	public List<BbsDTO> selectAdminFreeBbsList() {
		log.info("자유게시판 글 목록 조회(관리자) selectAdminFreeBbsList,\t {}");
		return dao.selectAdminFreeBbsList();
	}

	@Override
	public List<BbsDTO> selectUserBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) selectUserBbsListRow,\t {}", rdto);
		return dao.selectUserBbsListRow(rdto);
	}

	@Override
	public List<BbsDTO> selectAdminBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) selectAdminBbsListRow,\t {}", rdto);
		return dao.selectAdminBbsListRow(rdto);
	}

	@Override
	public int selectUserBoardListTotal() {
		log.info("게시글 전체 갯수(유저) selectUserBoardListTotal,\t {}");
		return dao.selectUserBoardListTotal();
	}

	@Override
	public int selectAdminBoardListTotal() {
		log.info("게시글 전체 갯수(유저) selectAdminBoardListTotal,\t {}");
		return dao.selectAdminBoardListTotal();
	}

	@Override
	public String selectNewBbs() {
		log.info("게시글 seq최대값 selectNewBbs");
		return dao.selectNewBbs();
	}

	@Override
	public List<FileDTO> selectFile(String seq) {
		log.info("파일업로드 selectFile,\t {}", seq);
		return dao.selectFile(seq);
	}

	@Override
	public boolean insertFile(String seq) {
		log.info("파일 추가 insertFile,\t {}", seq);
		return dao.insertFile(seq);
	}

	@Override
	public boolean deleteFile(String seq) {
		log.info("파일 삭제,\t {}", seq);
		return dao.deleteFile(seq);
	}

	
	

}
