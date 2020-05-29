package com.hexa.core.model.eappr.inf;

import java.util.List;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;

public interface EapprIDao {

	/**
	 * 문서 양식 리스트 출력
	 * @return 문서 양식 리스트
	 */
	public List<DocumentTypeDTO> docTypeList();
	/**
	 * 해당 문서내용 조회
	 * @param seq 문서 seq
	 * @return 문서 내용
	 */
	public DocumentDTO selectDoc(String seq);
	
	/**
	 * 문서 수정사항 업데이트
	 * @param Ddto 문서 seq, 제목, 내용, 문서양식 seq
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
}
