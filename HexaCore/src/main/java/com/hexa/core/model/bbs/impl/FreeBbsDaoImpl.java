package com.hexa.core.model.bbs.impl;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIDao;

@Repository
public class FreeBbsDaoImpl implements FreeBbsIDao {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SqlSessionTemplate session;
	private String NS = "com.hexa.core.notice.freeb.";
	
	@Override
	public boolean insertFreeBbs(BbsDTO dto) {
		log.info("자유게시판 글 작성 insertFreeBbs,\t {}", dto);
		int n = session.insert(NS + "insertFreeBbs", dto);
		return (n>0)?false:true;
	}

	@Override
	public boolean modifyFreeBbs(BbsDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean oneDelFreeBbs(String seq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean multiDelFreeBbs(Map<String, String[]> map) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BbsDTO detailFreeBbs(BbsDTO dto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean upViews(String seq) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean replyFreeBbs(BbsDTO dto) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<BbsDTO> userBbsList() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BbsDTO> adminBbsList() {
		log.info("자유게시판 글 목록 조회(관리자) adminBbsList,\t {}");
		return session.selectList(NS + "adminBbsList");
	}

	@Override
	public List<BbsDTO> userPageListRow(RowNumDTO rdto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<BbsDTO> adminPageListRow(RowNumDTO rdto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int userBbsTotalList() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int adminBbsTotalList() {
		// TODO Auto-generated method stub
		return 0;
	}

}
