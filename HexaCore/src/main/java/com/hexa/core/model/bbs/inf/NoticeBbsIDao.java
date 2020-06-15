package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;

public interface NoticeBbsIDao {

			// 1. 공지게시판 글 작성
			public boolean insertNoticeBbs(BbsDTO dto);
			
			// 2. 공지게시판 글 수정
			public int updateModifyNoticeBbs(BbsDTO dto);
			
			// 3. 공지게시판 글 단일삭제
			public int updateDeleteNoticeBbs(String seq);
			
			// 4. 공지게시판 글 다중삭제
			public boolean updateMultiDelNoticeBbs(Map<String, String[]> map);
			
			// 5. 공지게시판 상세글 보기
			public BbsDTO selectDetailNoticeBbs(String seq);
			
			// 6. 공지게시판 조회수 증가
			public boolean updateViewsNoticeBbs(String seq);
			
			// 7. 공지게시판 답글 달기
			public boolean updateReplyNoticeBbs(BbsDTO dto);
			public boolean insertReplyNoticeBbs(BbsDTO dto);
			
			// ** seq 최대값 갱신 + 자기가 쓴 글 조회
			public String selectNewNoticeBbs();
			
			// ============================================================
			
			// 8. 공지게시판 글 목록 조회(유저)
			public List<BbsDTO> selectUserNoticeBbsList();
			
			// 9. 공지게시판 글 목록 조회(관리자)
			public List<BbsDTO> selectAdminNoticeBbsList();
			
			// 10. 공지게시판 페이징 처리(유저)
			public List<BbsDTO> selectUserNoticeBbsListRow(RowNumDTO rdto);
			
			// 11. 공지게시판 페이징 처리(관리자)
			public List<BbsDTO> selectAdminNoticeBbsListRow(RowNumDTO rdto);
			
			// 12. 게시글 전체 갯수(유저)
			public int selectUserNoticeBoardListTotal();
			
			// 13. 게시글 전체 갯수(관리자)
			public int selectAdminNoticeBoardListTotal();
			// =================================================================
			// 14. 파일 조회
			public List<FileDTO> selectNoticeBbsFile(String seq);
			// 15. 파일 추가
			public boolean insertNoticeBbsFile(FileDTO fdto);
			// 16. 파일 삭제
			public boolean deleteNoticeBbsFile(Map<String,Object> map);
}
