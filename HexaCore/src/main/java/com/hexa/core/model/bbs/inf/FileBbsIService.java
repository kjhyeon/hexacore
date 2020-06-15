package com.hexa.core.model.bbs.inf;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;

public interface FileBbsIService {

	/*절대 경로*/
//	public static final String ATTACH_PATH = "C:\\nobrand\\git\\hexacore\\HexaCore\\file";
	
	/*상대 경로*/
	public static final String ATTACH_PATH = "C:\\nobrand\\eclipse_spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\HexaCore\\resource\\file";
	
	// 1. 자료실 글 작성
	public BbsDTO insertFileBbs(BbsDTO dto,MultipartFile[] filename);
	
	// 2. 자료실 글 수정
	public BbsDTO updateModifyFileBbs(BbsDTO dto,String[] files,MultipartFile[] filename);
	
	// 3. 자료실 글 단일삭제
	public int updateDeleteFileBbs(String seq);
	
	// 4. 자료실 글 다중삭제
	public boolean updateMultiDelFileBbs(Map<String, String[]> map);
	
	// 5. 자료실 상세글 보기
	public BbsDTO selectDetailFileBbs(String seq);
	
	// 6. 자료실 조회수 증가
	public boolean updateViewsFileBbs(String seq);
	
	// 7. 자료실 답글 달기(update, insert dao처리)
	public BbsDTO ReplyFile(BbsDTO dto, MultipartFile[] filename);
	
	// ** seq 최대값 갱신 + 자기가 쓴 글 조회
	public String selectNewFileBbs();
	
	// ============================= 자료실 게시판 글 목록 조회 및 페이징 처리 =============================
	
	// 8. 자료실 글 목록 조회(유저)
	public List<BbsDTO> selectUserFileBbsList();
	
	// 9. 자료실 글 목록 조회(관리자)
	public List<BbsDTO> selectAdminFileBbsList();
	
	// 10. 자료실 페이징 처리(유저)
	public List<BbsDTO> selectUserFileBbsListRow(RowNumDTO rdto);
	
	// 11. 자료실 페이징 처리(관리자)
	public List<BbsDTO> selectAdminFileBbsListRow(RowNumDTO rdto);
	
	// 12. 자료실 게시글 전체 갯수(유저)
	public int selectUserFileBoardListTotal();
	
	// 13. 자료실 게시글 전체 갯수(관리자)
	public int selectAdminFileBoardListTotal();
	
	// ============================= 자료실 게시판 파일처리 =============================
	// 14. 자료실 파일 조회
	public List<FileDTO> selectFileBbsFile(String seq);
	// 15. 자료실 파일 추가
	public boolean insertFileBbsFile(FileDTO fDto);
	// 16. 자료실 파일 삭제
	public boolean deleteFileBbsFile(Map<String,Object> map);
	
}
