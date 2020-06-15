package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.model.bbs.inf.FreeComIDao;

@Repository
public class FreeComDaoImpl implements FreeComIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private String NS = "com.hexa.core.free.freec.";
	
	@Override
	public List<CommentDTO> selectFreeCommentList(CommentDTO cDto) {
		log.info("자유게시판 댓글 목록조회 selectFreeCommentList,\t {}", cDto);
		return session.selectList(NS + "selectFreeCommentList", cDto);
	}

	@Override
	public boolean insertFreeComment(CommentDTO cDto) {
		log.info("자유게시판 댓글 작성 insertFreeComment,\t {}", cDto);
		int n = session.insert(NS + "insertFreeComment", cDto);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateFreeComment(String parent_seq) {
		log.info("자유게시판 댓글 수정 updateFreeComment,\t {}", parent_seq);
		int n = session.update(NS + "updateFreeComment", parent_seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFreeUserComment(Map<String, String> map) {
		log.info("자유게시판 댓글 삭제 deleteFreeUserComment,\t {}", map);
		int n = session.delete(NS + "deleteFreeUserComment", map);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFreeAdminComment(String seq) {
		log.info("자유게시판 댓글 삭제(관리자) deleteFreeAdminComment,\t {}", seq);
		int n = session.delete(NS + "deleteFreeAdminComment", seq);
		return (n>0)?true:false;
	}

	@Override
	public List<CommentDTO> selectFreeCommentListRow(Map<String, Object> map) {
		log.info("자유게시판 페이징 처리 selectFreeCommentListRow,\t {}", map);
		return session.selectList(NS + "selectFreeCommentListRow", map);
	}

	@Override
	public int selectFreeCommentListTotal(String parent_seq) {
		log.info("자유게시판 댓글 총 갯수 selectFreeCommentListTotal,\t {}", parent_seq);
		return session.selectOne(NS + "selectFreeCommentListTotal", parent_seq);
	}

}
