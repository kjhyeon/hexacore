package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.model.bbs.inf.FileComIDao;

@Repository
public class FileComDaoImpl implements FileComIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	
	private final String NS = "com.hexa.core.file.filec.";
	
	@Override
	public List<CommentDTO> selectFileCommentList(CommentDTO cDto) {
		log.info("자료실 댓글 목록조회 selectFileCommentList,\t {}", cDto);
		return session.selectList(NS + "selectFileCommentList", cDto);
	}

	@Override
	public boolean insertFileComment(CommentDTO cDto) {
		log.info("자료실 댓글 작성 insertFileComment,\t {}", cDto);
		int n = session.insert(NS + "insertFileComment", cDto);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateFileComment(String parent_seq) {
		log.info("자료실 댓글 수정 updateFileComment,\t {}", parent_seq);
		int n = session.update(NS + "updateFileComment", parent_seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFileUserComment(Map<String, String> map) {
		log.info("자료실 댓글 삭제 deleteFileUserComment,\t {}", map);
		int n = session.delete(NS + "deleteFileUserComment", map);
		return (n>0)?true:false;
	}

	@Override
	public boolean deleteFileAdminComment(String seq) {
		log.info("자료실 댓글 삭제(관리자) deleteFileAdminComment,\t {}", seq);
		int n = session.delete(NS + "deleteFileAdminComment", seq);
		return (n>0)?true:false;
	}

	@Override
	public List<CommentDTO> selectFileCommentListRow(Map<String, Object> map) {
		log.info("자료실 페이징 처리 selectFileCommentListRow,\t {}", map);
		return session.selectList(NS + "selectFileCommentListRow", map);
	}

	@Override
	public int selectFileCommentListTotal(String parent_seq) {
		log.info("자료실 댓글 총 갯수 selectFileCommentListTotal,\t {}", parent_seq);
		return session.selectOne(NS + "selectFileCommentListTotal", parent_seq);
	}

}
