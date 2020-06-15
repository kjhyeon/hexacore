package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.model.bbs.inf.NoticeComIDao;
@Repository
public class NoticeComDaoImpl implements NoticeComIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private String NS = "com.hexa.core.notice.noticec.";
	
	@Override
	public List<CommentDTO> selectNoticeCommentList(CommentDTO cDto) {
		log.info("자유게시판 댓글 목록조회 selectNoticeCommentList,\t {}", cDto);
		return session.selectList(NS + "selectNoticeCommentList", cDto);
	}

	@Override
	public boolean insertNoticeComment(CommentDTO cDto) {
		log.info("자유게시판 댓글 작성 insertNoticeComment,\t {}", cDto);
		int n = session.insert(NS + "insertNoticeComment", cDto);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateNoticeComment(String parent_seq) {
		log.info("자유게시판 댓글 수정 updateNoticeComment,\t {}", parent_seq);
		int n = session.update(NS + "updateNoticeComment", parent_seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteNoticeUserComment(Map<String, String> map) {
		log.info("자유게시판 댓글 삭제 deleteNoticeUserComment,\t {}", map);
		int n = session.delete(NS + "deleteNoticeUserComment", map);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteNoticeAdminComment(String seq) {
		log.info("자유게시판 댓글 삭제(관리자) deleteNoticeAdminComment,\t {}", seq);
		int n = session.delete(NS + "deleteNoticeAdminComment", seq);
		return (n>0)?true:false;
	}

	@Override
	public List<CommentDTO> selectNoticeCommentListRow(Map<String, Object> map) {
		log.info("자유게시판 페이징 처리 selectNoticeCommentListRow,\t {}", map);
		return session.selectList(NS + "selectNoticeCommentListRow", map);
	}

	@Override
	public int selectNoticeCommentListTotal(String parent_seq) {
		log.info("자유게시판 댓글 총 갯수 selectNoticeCommentListTotal,\t {}", parent_seq);
		return session.selectOne(NS + "selectNoticeCommentListTotal", parent_seq);
	}

}
