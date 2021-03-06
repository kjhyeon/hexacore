package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.CommentDTO;
import com.hexa.core.dto.RowNumDTO;

public interface FreeComIService {

	// 1. 자유 게시판 댓글 목록조회
	public List<CommentDTO> selectFreeCommentList(CommentDTO cDto);
	
	// 2. 자유 게시판 댓글 작성
	public boolean insertFreeComment(CommentDTO cDto);
	
	// 3. 자유 게시판 댓글 수정
	public boolean updateFreeComment(String parent_seq);
	
	// 4. 자유 게시판 댓글 삭제(유저)
	public boolean deleteFreeUserComment(Map<String, String> map);
	
	// 5. 자유 게시판 댓글 삭제(관리자)
	public boolean deleteFreeAdminComment(String seq);
	
	// 6. 자유 게시판 페이징 처리
	public List<CommentDTO> selectFreeCommentListRow(Map<String, Object> map);
			
	// 7. 자유 게시판 댓글 총 갯수
	public int selectFreeCommentListTotal(String parent_seq);
}
