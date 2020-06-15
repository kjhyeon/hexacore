package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.CommentDTO;

public interface FileComIService {

	// 1. 자료실 댓글 목록조회
	public List<CommentDTO> selectFileCommentList(CommentDTO cDto);
	
	// 2. 자료실 댓글 작성
	public boolean insertFileComment(CommentDTO cDto);
	
	// 3. 자료실 댓글 수정
	public boolean updateFileComment(String parent_seq);
	
	// 4. 자료실 댓글 삭제(유저)
	public boolean deleteFileUserComment(Map<String, String> map);
	
	// 5. 자료실 댓글 삭제(관리자)
	public boolean deleteFileAdminComment(String seq);
	
	// 6. 자료실 페이징 처리
	public List<CommentDTO> selectFileCommentListRow(Map<String, Object> map);
			
	// 7. 자료실 댓글 총 갯수
	public int selectFileCommentListTotal(String parent_seq);
	
}
