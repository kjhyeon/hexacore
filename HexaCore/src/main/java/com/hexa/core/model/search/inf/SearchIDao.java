package com.hexa.core.model.search.inf;

import java.util.List;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.RowNumDTO;

public interface SearchIDao {

	public void eDocIndex(List<DocumentDTO> list);
	public void freeBbsIndex(List<BbsDTO> list);
	public void noticeBbsIndex(List<BbsDTO> list);
	public void fileBbsIndex(List<BbsDTO> list);
	
	public void addDocIndex(DocumentDTO dto);
	public void addBbsIndex(BbsDTO dto,String type);
	
	public void updateDocIndex(DocumentDTO dto);
	public void updateBbsIndex(BbsDTO dto,String type);
	
	public List<DocumentDTO> eDocSearch(String keyword,String type,String id,RowNumDTO row);
	public List<BbsDTO> freeBbsSearch(String keyword,String type,RowNumDTO row);
	public List<BbsDTO> noticeBbsSearch(String keyword,String type,RowNumDTO row);
	public List<BbsDTO> fileBbsSearch(String keyword,String type,RowNumDTO row);
	
	public int eDocTotal(String keyword,String type);
	public int freeBbsTotal(String keyword,String type);
	public int fileBbsTotal(String keyword,String type);
	public int noticeBbsTotal(String keyword,String type);
}
