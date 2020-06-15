package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.model.bbs.inf.NoticeComIDao;
import com.hexa.core.model.bbs.inf.NoticeComIService;

@Service
public class NoticeComServiceImpl implements NoticeComIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeComIDao dao;
	
	@Override
	public List<CommentDTO> selectNoticeCommentList(CommentDTO cDto) {
		log.info("자유게시판 댓글 목록조회 selectNoticeCommentList,\t {}", cDto);
		return dao.selectNoticeCommentList(cDto);
	}

	@Override
	public boolean insertNoticeComment(CommentDTO cDto) {
		log.info("자유게시판 댓글 작성 insertNoticeComment,\t {}", cDto);
		return dao.insertNoticeComment(cDto);
	}

	@Override
	public boolean updateNoticeComment(String parent_seq) {
		log.info("자유게시판 댓글 수정 updateNoticeComment,\t {}", parent_seq);
		return dao.updateNoticeComment(parent_seq);
	}

	@Override
	public boolean deleteNoticeUserComment(Map<String, String> map) {
		log.info("자유게시판 댓글 삭제 deleteNoticeUserComment,\t {}", map);
		return dao.deleteNoticeUserComment(map);
	}

	@Override
	public boolean deleteNoticeAdminComment(String seq) {
		log.info("자유게시판 댓글 삭제(관리자) deleteNoticeAdminComment,\t {}",seq); 
		return dao.deleteNoticeAdminComment(seq);
	}

	@Override
	public List<CommentDTO> selectNoticeCommentListRow(Map<String, Object> map) {
		log.info("자유게시판 페이징 처리 selectNoticeComment,\t {}", map);
		return dao.selectNoticeCommentListRow(map);
	}

	@Override
	public int selectNoticeCommentListTotal(String parent_seq) {
		log.info("자유게시판 댓글 총 갯수 selectNoticeCommentListTotal,\t {}", parent_seq);
		return dao.selectNoticeCommentListTotal(parent_seq);
	}
	
	

}
