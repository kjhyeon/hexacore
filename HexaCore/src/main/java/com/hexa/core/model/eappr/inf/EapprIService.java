package com.hexa.core.model.eappr.inf;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;

public interface EapprIService {
	
	public static final String ATTACH_PATH = "/home/HexaCore/files/edoc";
	public static final String APPR_SIGN = "/home/HexaCore/image/profile";
	
	/**
	 * 문서 양식 리스트 출력
	 * @return 문서 양식 리스트
	 */
	public List<DocumentTypeDTO> selectDocTypeList();
	
	/**
	 * 문서 양식 리스트 출력 (페이징)
	 * @return 문서 양식 리스트
	 */
	public List<DocumentTypeDTO> selectDocTypeListP(RowNumDTO row);

	/**
	 * 문서 양식 미리보기
	 * @param type_seq
	 * @return 문서 양식
	 */
	public DocumentTypeDTO selectDocType(String type_seq);
	
	/**
	 * 문서 작성
	 * @return 성공T 실패F
	 */
	public int insertNewDoc(DocumentDTO dto, MultipartFile[] filename);
	
	/**
	 * 방금 작성한 문서의 seq 가져오기
	 * @return seq
	 */
	public String selectNewDoc();
	
	/**
	 * 해당 문서내용 조회
	 * @param seq 문서 seq
	 * @return 문서 내용
	 */
	public DocumentDTO selectDoc(String seq);
	
	/**
	 * 해당 문서 결재루트 조회
	 * @param ApprovalDto seq : 문서번호, (id = 조회할 사람)
	 * @return	ApprovalDto
	 */
	public List<ApprovalDTO> selectApprRoot(ApprovalDTO Adto);
	
	/**
	 * 파일 저장
	 * @param DFdto seq, name, f_path, ori_name, f_size
	 * @return True : 성공 / False 실패
	 */
	public boolean insertFile(DocFileDTO DFdto);
	
	/**
	 * 파일 삭제
	 * @param seq 문서 seq
	 * @return True : 성공 / False 실패
	 */
	public boolean deleteFile(String seq);
	
	/**
	 * 문서 상태 업데이트
	 * @param Adto seq, state (appr_turn= 결재시사용)
	 * @return True : 성공 / False 실패
	 */
	public boolean updateDocTurn(ApprovalDTO Adto);
	
	/**
	 * 문서양식 입력
	 * @param DTdto name, content, category
	 * @return  True : 성공 / False 실패
	 */
	public boolean insertDocType(DocumentTypeDTO DTdto);

	/**
	 * 문서 삭제
	 * @param seq
	 * @return 성공여부
	 */
	public boolean deleteDoc(String seq);
	
	/**
	 * 모든 문서 조회
	 * @return 모든 문서
	 */
	public List<DocumentDTO> selectAllDoc();
	
	/**
	 * 작성한 문서 양식의 SEQ 가져오기
	 * @return seq
	 */
	public String selectNewDocType();
	
	/**
	 * 문서 양식 삭제
	 * @param seq
	 * @return 성공여부
	 */
	public boolean deleteDocType(String seq);
	
	/**
	 * 문서 양식 수정
	 * @param dto
	 * @return 성공여부
	 */
	public boolean updateDocType(DocumentTypeDTO dto);
	
	/**
	 * 상신문서함의 문서 개수
	 * @param id
	 * @return 상신문서함의 문서 개수 
	 */
	public int selectReportCount(String id);
	
	/**
	 * 기안한 문서 상태별 문서 개수
	 * @param map : id, state
	 * @return 문서 개수
	 */
	public int selectMyDocCount(Map<String, Object> map);
	
	/**
	 * 참조문서함 문서 개수
	 * @param id
	 * @return 문서 개수
	 */
	public int selectReferDocCount(Map<String, Object> map);
	
	/**
	 * 결재문서함에 있는 문서의 총 개수
	 * @param id
	 * @return 문서 개수
	 */
	public int selectApprDocCount(Map<String, Object> map);
	
	/**
	 * 각 문서함 별 갯수 조회
	 * @param map (id,state)
	 * @return 문서 개수
	 */
	public int selectAllApprDocCount(Map<String, Object> map);
	
	/**
	 * 문서 양식 총 개수
	 * @return 개수
	 */
	public int selectDocTypeListSize();
	
	/**
	 * 문서 검색기능
	 * @param word
	 * @return 검색된 문서
	 */
	public List<DocumentDTO> searchDoc(String word);
	
	/**
	 * 각 문서함 갯수
	 * @param id
	 * @return List
	 */
	public Map<String, Object> selectDocListAll(String id);
	
	/**
	 * 문서 조회
	 * @param map
	 * @return 문서 리스트
	 */
	public List<DocumentDTO> selectMyDocList(Map<String, Object> map);
	
	/**
	 * 문서 승인/반려 기능
	 * @param map
	 * @return TRUE : 성공 / FALSE : 실패
	 */
	public boolean confirmUpdate(Map<String,Object> map);
	
	/**
	 * 업로드한 파일 저장 쿼리
	 * @param dto
	 * @return TRUE : 성공 / FALSE : 실패
	 */
	public boolean insertDocFile(DocFileDTO dto);
	
	/**
	 * 첨부된 파일 조회
	 * @param seq
	 * @return 파일Dto
	 */
	public List<DocFileDTO> selectDocFile(String seq);
	
	/**
	 * 디테일 화면 service
	 * @param ADto
	 * @return
	 */
	public Map<String,Object> goDetail(ApprovalDTO ADto);
	
	/**
	 * 상신/취소 기능
	 * @param Ddto
	 * @return  TRUE : 성공 / FALSE : 실패
	 */
	public boolean upApprDoc(DocumentDTO Ddto);
	
	/**
	 * 임시저장 기능
	 * @param Ddto
	 */
	public boolean saveUpDoc(DocumentDTO Ddto,MultipartFile[] filename);
	
}
