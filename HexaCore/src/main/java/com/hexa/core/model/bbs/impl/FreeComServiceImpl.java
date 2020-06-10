package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FreeComIDao;
import com.hexa.core.model.bbs.inf.FreeComIService;

@Service
public class FreeComServiceImpl implements FreeComIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FreeComIDao dao;
	
	@Override
	public List<CommentDTO> selectFreeCommentList(CommentDTO cDto) {
		log.info("자유게시판 댓글 목록조회 selectFreeCommentList,\t {}", cDto);
		return dao.selectFreeCommentList(cDto);
	}

	@Override
	public boolean insertFreeComment(CommentDTO cDto) {
		log.info("자유게시판 댓글 작성 insertFreeComment,\t {}", cDto);
		return dao.insertFreeComment(cDto);
	}

	@Override
	public boolean updateFreeComment(String parent_seq) {
		log.info("자유게시판 댓글 수정 updateFreeComment,\t {}", parent_seq);
		return dao.updateFreeComment(parent_seq);
	}

	@Override
	public boolean deleteFreeUserComment(Map<String, String> map) {
		log.info("자유게시판 댓글 삭제 deleteFreeUserComment,\t {}", map);
		return dao.deleteFreeUserComment(map);
	}

	@Override
	public boolean deleteFreeAdminComment(String seq) {
		log.info("자유게시판 댓글 삭제(관리자) deleteFreeAdminComment,\t {}",seq); 
		return dao.deleteFreeAdminComment(seq);
	}

	@Override
	public List<CommentDTO> selectFreeCommentListRow(Map<String, Object> map) {
		log.info("자유게시판 페이징 처리 selectFreeComment,\t {}", map);
		return dao.selectFreeCommentListRow(map);
	}

	@Override
	public int selectFreeCommentListTotal(String parent_seq) {
		log.info("자유게시판 댓글 총 갯수 selectFreeCommentListTotal,\t {}", parent_seq);
		return dao.selectFreeCommentListTotal(parent_seq);
	}
	
	

}
