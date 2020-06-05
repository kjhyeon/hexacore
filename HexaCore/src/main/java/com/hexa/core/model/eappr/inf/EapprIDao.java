package com.hexa.core.model.eappr.inf;

import java.util.List;
import java.util.Map;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;

public interface EapprIDao {

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
	public boolean insertNewDoc(DocumentDTO dto);
	
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
	 * 문서 수정사항 업데이트
	 * @param DocumentDTO 문서 seq, appr_turn,(title, content)
	 * @return True : 성공 / False 실패
	 */
	public boolean updateDoc(DocumentDTO Ddto);
	
	/**
	 * 결재 루트 삭제
	 * @param seq 문서 seq
	 * @return True : 성공 / False 실패
	 */
	public boolean deleteApprRoot(String seq);
	
	/**
	 * 결재루트 입력
	 * @param ApprovalDto 결재선 seq, id, name, duty, turn, appr_kind
	 * @return True : 성공 / False 실패
	 */
	public boolean insertApprRoot(ApprovalDTO Adto);
	
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
	 * 결재상태 chk 업데이트
	 * @param Adto chk, appr_sign, seq, id
	 * @return True : 성공 / False 실패
	 */
	public boolean updateApprChk(ApprovalDTO Adto);
	
	/**
	 * 코멘트 입력
	 * @param DCdto seq, turn, id, name, content
	 * @return True : 성공 / False 실패
	 */
	public boolean insertComment(DocCommentDTO DCdto);
	
	/**
	 * 코멘트 조회
	 * @param seq 해당 문서 번호
	 * @return commentList
	 */
	public List<DocCommentDTO> selectComment(String seq);
	
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
	 * 결재문서함 조회
	 * @param id
	 * @return DocumentDTO
	 */
	public List<DocumentDTO> selectNeedApprDoc(Map<String, Object> map);
	
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
	 * 결재자 서명 가져오기
	 * @param id
	 * @return 이미지파일경로, 이름
	 */
	public String selectSignImg(String id);
	
	/**
	 * 상신문서함의 문서 총 개수
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
	public int selectReferDocCount(String id);
	
	/**
	 * 결재문서함에 있는 문서의 총 개수
	 * @param id
	 * @return 문서 개수
	 */
	public int selectApprDocCount(String id);
	
	/**
	 * 결재문서함에 결재해야 할 문서의 개수
	 * @param id
	 * @return 문서 개수
	 */
	public int selectNeedApprDocCount(String id);
	
	/**
	 * 문서 양식 총 개수
	 * @return 개수
	 */
	public int selectDocTypeListSize();
	
}
