package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.BbsDTO;
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
	public boolean modifyFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 수정 modifyFreeBbs,\t {}", dto);
		return dao.modifyFreeBbs(dto);
	}

	@Override
	public boolean oneDelFreeBbs(String seq) {
		log.info("자유게시판 글 단일삭제 oneDelFreeBbs,\t {}", seq);
		return dao.oneDelFreeBbs(seq);
	}

	@Override
	public boolean multiDelFreeBbs(Map<String, String[]> map) {
		log.info("자유게시판 글 다중삭제 multiDelFreeBbs,\t {}", map);
		return dao.multiDelFreeBbs(map);
	}

	@Override
	public BbsDTO detailFreeBbs(BbsDTO dto) {
		log.info("자유게시판 상세글 보기 detailFreeBbs,\t {}", dto);
		return dao.detailFreeBbs(dto);
	}

	@Override
	public boolean upViews(String seq) {
		log.info("자유게시판 조회수 증가 upViews,\t {}", seq);
		return dao.upViews(seq);
	}

	@Override
	public boolean replyFreeBbs(BbsDTO dto) {
		log.info("자유게시판 답글 달기  replyFreeBbs,\t {}", dto);
		return dao.replyFreeBbs(dto);
	}

	@Override
	public List<BbsDTO> userBbsList() {
		log.info("자유게시판 글 목록 조회(유저) userBbsList,\t {}");
		return dao.userBbsList();
	}

	@Override
	public List<BbsDTO> adminBbsList() {
		log.info("자유게시판 글 목록 조회(관리자) adminBbsList,\t {}");
		return dao.adminBbsList();
	}

	@Override
	public List<BbsDTO> userPageListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) userPageListRow,\t {}", rdto);
		return dao.userPageListRow(rdto);
	}

	@Override
	public List<BbsDTO> adminPageListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) adminPageListRow,\t {}", rdto);
		return dao.adminPageListRow(rdto);
	}

	@Override
	public int userBbsTotalList() {
		log.info("게시글 전체 갯수(유저) userBbsTotalList,\t {}");
		return dao.userBbsTotalList();
	}

	@Override
	public int adminBbsTotalList() {
		log.info("게시글 전체 갯수(유저) adminBbsTotalList,\t {}");
		return dao.adminBbsTotalList();
	}

}
