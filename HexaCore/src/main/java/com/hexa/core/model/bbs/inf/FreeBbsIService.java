package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;

public interface FreeBbsIService {

	/*절대 경로*/
//	public static final String ATTACH_PATH = "C:\\nobrand\\git\\hexacore\\HexaCore\\file";
	
	/*상대 경로*/
	public static final String ATTACH_PATH = "/home/HexaCore/files/upload";
	
	// 1. 자유게시판 글 작성
	public BbsDTO insertFreeBbs(BbsDTO dto,MultipartFile[] filename);
	
	// 2. 자유게시판 글 수정
	public BbsDTO updateModifyFreeBbs(BbsDTO dto,String[] files,MultipartFile[] filename);
	
	// 3. 자유게시판 글 단일삭제
	public int updateDeleteFreeBbs(String seq);
	
	// 4. 자유게시판 글 다중삭제
	public boolean updateMultiDelFreeBbs(Map<String, String[]> map);
	
	// 5. 자유게시판 상세글 보기
	public BbsDTO selectDetailFreeBbs(String seq);
	
	// 6. 자유게시판 조회수 증가
	public BbsDTO updateViewsBbs(String seq);
	
	// 7. 자유게시판 답글 달기(update, insert dao처리)
	public BbsDTO ReplyBbs(BbsDTO dto, MultipartFile[] filename);
	
	// ** seq 최대값 갱신 + 자기가 쓴 글 조회
	public String selectNewBbs();
	
	// ============================================================
	
	// 8. 자유게시판 글 목록 조회(유저)
	public List<BbsDTO> selectUserFreeBbsList();
	
	// 9. 자유게시판 글 목록 조회(관리자)
	public List<BbsDTO> selectAdminFreeBbsList();
	
	// 10. 자유게시판 페이징 처리(유저)
	public List<BbsDTO> selectUserBbsListRow(RowNumDTO rdto);
	
	// 11. 자유게시판 페이징 처리(관리자)
	public List<BbsDTO> selectAdminBbsListRow(RowNumDTO rdto);
	
	// 12. 게시글 전체 갯수(유저)
	public int selectUserBoardListTotal();
	
	// 13. 게시글 전체 갯수(관리자)
	public int selectAdminBoardListTotal();
	
	// =============================================================
	// 14. 파일 조회
	public List<FileDTO> selectFile(String seq);
	// 15. 파일 추가
	public boolean insertFile(FileDTO fDto);
	// 16. 파일 삭제
	public boolean deleteFile(Map<String,Object> map);
}
