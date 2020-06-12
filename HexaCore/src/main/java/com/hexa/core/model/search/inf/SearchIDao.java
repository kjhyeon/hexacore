package com.hexa.core.model.search.inf;

import java.util.List;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;

public interface SearchIDao {

	public void eDocIndex(List<DocumentDTO> list);
	public void freeBbsIndex(List<BbsDTO> list);
	public void noticeBbsIndex(List<BbsDTO> list);
	public void fileBbsIndex(List<BbsDTO> list);
	public void msgIndex(List<MessageDTO> list);
	
	public void addDocIndex(DocumentDTO dto);
	public void addBbsIndex(BbsDTO dto,String type);
	public void addMsgIndex(MessageDTO dto);
	
	public void updateDocIndex(DocumentDTO dto);
	public void updateBbsIndex(BbsDTO dto,String type);
	public void updateMsgIndex(String seq);
	
	public List<DocumentDTO> eDocSearch(String keyword,String type,String id,RowNumDTO row);
	public List<BbsDTO> freeBbsSearch(String keyword,String type,RowNumDTO row,String auth);
	public List<BbsDTO> noticeBbsSearch(String keyword,String type,RowNumDTO row,String auth);
	public List<BbsDTO> fileBbsSearch(String keyword,String type,RowNumDTO row,String auth);
	
	public List<MessageDTO> receiveMsgSearch(String keyword,RowNumDTO row,String type,String id);
	public List<MessageDTO> senderMsgSearch(String keyword,RowNumDTO row,String type,String id);
	
	
	public int eDocTotal(String keyword,String type);
	public int freeBbsTotal(String keyword,String type,String auth);
	public int fileBbsTotal(String keyword,String type,String auth);
	public int noticeBbsTotal(String keyword,String type,String auth);
	
	public int receiveMsgTotal(String keyword,String type,String id);
	public int senderMsgTotal(String keyword,String type,String id);
}
