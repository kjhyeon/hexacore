package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.RowNumDTO;

public interface FreeBbsIService {

	// 1. 자유게시판 글 작성
	public boolean insertFreeBbs(BbsDTO dto);
	
	// 2. 자유게시판 글 수정
	public boolean modifyFreeBbs(BbsDTO dto);
	
	// 3. 자유게시판 글 단일삭제
	public boolean oneDelFreeBbs(String seq);
	
	// 4. 자유게시판 글 다중삭제
	public boolean multiDelFreeBbs(Map<String, String[]> map);
	
	// 5. 자유게시판 상세글 보기
	public BbsDTO detailFreeBbs(BbsDTO dto);
	
	// 6. 자유게시판 조회수 증가
	public boolean upViews(String seq);
	
	// 7. 자유게시판 답글 달기
	public boolean replyFreeBbs(BbsDTO dto);
	
	// ============================================================
	
	// 8. 자유게시판 글 목록 조회(유저)
	public List<BbsDTO> userBbsList();
	
	// 9. 자유게시판 글 목록 조회(관리자)
	public List<BbsDTO> adminBbsList();
	
	// 10. 자유게시판 페이징 처리(유저)
	public List<BbsDTO> userPageListRow(RowNumDTO rdto);
	
	// 11. 자유게시판 페이징 처리(관리자)
	public List<BbsDTO> adminPageListRow(RowNumDTO rdto);
	
	// 12. 게시글 전체 갯수(유저)
	public int userBbsTotalList();
	
	// 13. 게시글 전체 갯수(관리자)
	public int adminBbsTotalList();
	
	
}
