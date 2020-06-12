package com.hexa.core.model.search.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.msg.inf.MessageIDao;
import com.hexa.core.model.search.inf.SearchIDao;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class SearchService implements SearchIService{

	@Autowired
	private EapprIService eService;

	@Autowired
	private FreeBbsIService frService;
	
	@Autowired
	private MessageIDao mDao;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SearchIDao dao;
	
	@Override
	public void eDocIndex() {
		log.info("SearchService 인덱싱 작업 시작");
		dao.eDocIndex(eService.selectAllDoc());
	}
	@Override
	public void freeBbsIndex() {
		log.info("SearchService 인덱싱 작업 시작");
		dao.freeBbsIndex(frService.selectAdminFreeBbsList());
	}  

	@Override
	public void noticeBbsIndex() {
		// TODO Auto-generated method stub
	}

	@Override
	public void fileBbsIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DocumentDTO> eDocSearch(String keyword,String type,String id,RowNumDTO row) {
		log.info("SearchServcie 전자문서 서치");
		return dao.eDocSearch(keyword,type,id,row);
	}
	@Override
	public List<BbsDTO> freeBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		log.info("SearchService 자유게시판 서치");
		return dao.freeBbsSearch(keyword,type,row,auth);
	}
	@Override
	public List<BbsDTO> noticeBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		log.info("SearchService 공지게시판 서치");
		return dao.noticeBbsSearch(keyword, type, row,auth);
	}
	@Override
	public List<BbsDTO> fileBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		log.info("SearchService 파일게시판 서치");
		return dao.fileBbsSearch(keyword,type,row,auth);
	}
	@Override
	public void addDocIndex(DocumentDTO dto) {
		log.info("SearchService 전자문서 추가 {}",dto);
		dao.addDocIndex(dto);
	}
	@Override
	public void addBbsIndex(BbsDTO dto, String type) {
		log.info("SearchService 게시판 추가 {}/{}",dto,type);
		dao.addBbsIndex(dto, type);
	}
	@Override
	public void updateDocIndex(DocumentDTO dto) {
		log.info("SearchService 전자문서 업데이트 {}",dto);
		dao.updateDocIndex(dto);
	}
	@Override
	public void updateBbsIndex(BbsDTO dto, String type) {
		log.info("SearchService 게시판 업데이트 {}",dto);
		dao.updateBbsIndex(dto, type);
	}
	@Override
	public int eDocTotal(String keyword, String type) {
		return dao.eDocTotal(keyword, type);
	}
	@Override
	public int freeBbsTotal(String keyword, String type,String auth) {
		return dao.freeBbsTotal(keyword, type,auth);
	}
	@Override
	public int fileBbsTotal(String keyword, String type,String auth) {
		return dao.fileBbsTotal(keyword, type,auth);
	}
	@Override
	public int noticeBbsTotal(String keyword, String type,String auth) {
		return dao.noticeBbsTotal(keyword, type,auth);
	}
	@Override
	public void msgIndex() {
		log.info("SearchService Msg 인덱싱 작업 시작");
		dao.msgIndex(mDao.selectAllMsg());
	}
	@Override
	public void addMsgIndex(MessageDTO dto) {
		log.info("SearchService 메시지 추가 {}",dto);
		dao.addMsgIndex(dto);
	}
	@Override
	public List<MessageDTO> receiveMsgSearch(String keyword, RowNumDTO row,String type,String id) {
		return dao.receiveMsgSearch(keyword, row, type,id);
	}
	@Override
	public List<MessageDTO> senderMsgSearch(String keyword, RowNumDTO row,String type,String id) {
		return dao.senderMsgSearch(keyword, row, type, id);
	}
	@Override
	public int receiveMsgTotal(String keyword,String type,String id) {
		return dao.receiveMsgTotal(keyword, type,id);
	}
	@Override
	public int senderMsgTotal(String keyword,String type,String id) {
		return dao.senderMsgTotal(keyword, type,id);
	}
	@Override
	public void updateMsgIndex(MessageDTO dto) {
	}



}
