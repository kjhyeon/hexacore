package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.model.bbs.inf.FileComIDao;
import com.hexa.core.model.bbs.inf.FileComIService;

@Service
public class FileComServiceImpl implements FileComIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileComIDao dao;
	
	@Override
	public List<CommentDTO> selectFileCommentList(CommentDTO cDto) {
		log.info("자료실 댓글 목록조회 selectFileCommentList,\t {}", cDto);
		return dao.selectFileCommentList(cDto);
	}

	@Override
	public boolean insertFileComment(CommentDTO cDto) {
		log.info("자료실 댓글 작성 insertFileComment,\t {}", cDto);
		return dao.insertFileComment(cDto);
	}

	@Override
	public boolean updateFileComment(String parent_seq) {
		log.info("자료실 댓글 수정 updateFileComment,\t {}", parent_seq);
		return dao.updateFileComment(parent_seq);
	}

	@Override
	public boolean deleteFileUserComment(Map<String, String> map) {
		log.info("자료실 댓글 삭제(유저) deleteFileUserComment,\t {}", map);
		return dao.deleteFileUserComment(map);
	}

	@Override
	public boolean deleteFileAdminComment(String seq) {
		log.info("자료실 댓글 삭제(관리자) deleteFileAdminComment,\t {}",seq); 
		return dao.deleteFileAdminComment(seq);
	}

	@Override
	public List<CommentDTO> selectFileCommentListRow(Map<String, Object> map) {
		log.info("자료실 페이징 처리 selectFileCommentListRow,\t {}", map);
		return dao.selectFileCommentListRow(map);
	}

	@Override
	public int selectFileCommentListTotal(String parent_seq) {
		log.info("자료실 댓글 총 갯수 selectFileCommentListTotal,\t {}", parent_seq);
		return dao.selectFileCommentListTotal(parent_seq);
	}

}
