package com.hexa.core.model.eappr.inf;

import java.util.List;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;

public interface EapprIDao {

	/**
	 * 문서 양식 리스트 출력
	 * @return 문서 양식 리스트
	 */
	public List<DocumentTypeDTO> selectDocTypeList();

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
}
