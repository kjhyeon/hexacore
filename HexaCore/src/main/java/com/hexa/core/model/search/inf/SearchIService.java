package com.hexa.core.model.search.inf;

import java.util.List;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.RowNumDTO;

public interface SearchIService {

	static final String DOC = "eDoc";
	static final String FREE = "freeBbs";
	static final String NOTICE = "noticeBbs";
	static final String FILE = "fileBbs";
	
	public void eDocIndex();
	public void freeBbsIndex();
	public void noticeBbsIndex();
	public void fileBbsIndex();
	
	/**
	 * 전자문서 추가
	 * @param dto 전자문서dto
	 * @return
	 */
	public void addDocIndex(DocumentDTO dto);
	
	/**
	 * 게시글 추가
	 * @param dto 게시판dto
	 * @param 게시판종류
	 * @return
	 */
	public void addBbsIndex(BbsDTO dto,String type);
	
	/**
	 * 전자문서 업데이트
	 * @param dto 전자문서dto
	 * @return
	 */
	public void updateDocIndex(DocumentDTO dto);
	
	/**
	 * 파일게시판 업데이트
	 * @param dto 게시판dto
	 * @param type 게시판종류
	 * @return
	 */
	public void updateBbsIndex(BbsDTO dto,String type);
	
	
	/**
	 * 전자문서 서치
	 * @param keyword 검색어
	 * @param type 검색종류
	 * @return
	 */
	public List<DocumentDTO> eDocSearch(String keyword,String type,String id,RowNumDTO row);
	
	/**
	 * 자유게시판 서치
	 * @param keyword 검색어
	 * @param type 검색종류
	 * @return
	 */
	public List<BbsDTO> freeBbsSearch(String keyword,String type,RowNumDTO row);
	
	/**
	 * 공지게시판 서치
	 * @param keyword 검색어
	 * @param type 검색종류
	 * @return
	 */
	public List<BbsDTO> noticeBbsSearch(String keyword,String type,RowNumDTO row);
	
	/**
	 * 파일게시판 서치
	 * @param keyword 검색어
	 * @param type 검색종류
	 * @return
	 */
	public List<BbsDTO> fileBbsSearch(String keyword,String type,RowNumDTO row);
	
	public int eDocTotal(String keyword,String type);
	public int freeBbsTotal(String keyword,String type);
	public int fileBbsTotal(String keyword,String type);
	public int noticeBbsTotal(String keyword,String type);
	
}
