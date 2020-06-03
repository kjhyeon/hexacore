package com.hexa.core.model.search.inf;

import java.util.List;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;

public interface SearchIService {

	public void eDocIndex();
	public void freeBbsIndex();
	public void noticeBbsIndex();
	public void fileBbsIndex();
	
	public List<DocumentDTO> eDocSearch(String keyword,String type);
	public List<BbsDTO> freeBbsSearch(String keyword,String type);
	public List<BbsDTO> noticeBbsSearch(String keyword,String type);
	public List<BbsDTO> fileBbsSearch(String keyword,String type);
}
