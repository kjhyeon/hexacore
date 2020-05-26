package com.hexa.core.model.eappr.inf;

import java.util.List;
import com.hexa.core.dto.DocumentTypeDTO;

public interface EapprIDao {

	/**
	 * 문서 양식 리스트 출력
	 * @return 문서 양식 리스트
	 */
	public List<DocumentTypeDTO> selectDocTypeList();
	
}
