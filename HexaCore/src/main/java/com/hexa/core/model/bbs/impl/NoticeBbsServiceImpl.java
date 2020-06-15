package com.hexa.core.model.bbs.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.bbs.inf.NoticeBbsIDao;
import com.hexa.core.model.bbs.inf.NoticeBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class NoticeBbsServiceImpl implements NoticeBbsIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NoticeBbsIDao dao;
	
	@Autowired
	private SearchIService sService;
	
	private boolean saveFile(MultipartFile[] files,int seq) {
		log.info("파일 등록,\t {}", Arrays.toString(files));
		boolean isc = false;
		if(files!=null) {
			for (MultipartFile file : files) {
				String saveName = file.getOriginalFilename();
				File dir = new File(ATTACH_PATH);
				String filename = "freeBbs-"+UUID.randomUUID()+"-"+saveName;
				if(dir.isDirectory() == false){
					dir.mkdirs();
				}
				File f = new File(ATTACH_PATH, filename);
				try {
					file.transferTo(f);
					FileDTO dto = new FileDTO();
					dto.setOri_name(file.getOriginalFilename());
					dto.setName(filename);
					dto.setF_size(String.valueOf(file.getSize()));
					dto.setF_path(ATTACH_PATH);
					dto.setCategory(0);
					dto.setSeq(seq);
					isc = dao.insertNoticeBbsFile(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isc;
	}
	
	@Override
	public BbsDTO insertNoticeBbs(BbsDTO dto, MultipartFile[] filename) {
		log.info("공지게시판 글 작성 insertNoticeBbs,\t {}", dto);
		dao.insertNoticeBbs(dto);
		String seq = dao.selectNewNoticeBbs();
		saveFile(filename, Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailNoticeBbs(seq);
		
		sService.addBbsIndex(result, SearchIService.NOTICE);
		
		return result;
	}

	@Override
	public BbsDTO updateModifyNoticeBbs(BbsDTO dto, String[] files, MultipartFile[] filename) {
		log.info("자유게시판 글 수정 updateModifyFreeBbs,\t {}", dto);
		Map<String, Object> map = Maps.newHashMap();
		dao.updateModifyNoticeBbs(dto);
		map.put("files", files);
		map.put("seq", dto.getSeq());
		dao.deleteNoticeBbsFile(map);
		saveFile(filename,dto.getSeq());
		
		BbsDTO result = dao.selectDetailNoticeBbs(String.valueOf(dto.getSeq()));
		
		sService.updateBbsIndex(result, SearchIService.NOTICE);
		
		return result;
	}

	@Override
	public int updateDeleteNoticeBbs(String seq) {
		log.info("공지게시판 글 단일삭제 updateDeleteNoticeBbs,\t {}", seq);
		return dao.updateDeleteNoticeBbs(seq);
	}

	@Override
	public boolean updateMultiDelNoticeBbs(Map<String, String[]> map) {
		log.info("공지게시판 글 다중삭제 updateMultiDelNoticeBbs,\t {}", map);
		return dao.updateMultiDelNoticeBbs(map);
	}

	@Override
	public BbsDTO selectDetailNoticeBbs(String seq) {
		log.info("공지게시판 상세 글 보기 selectDetailNoticeBbs,\t {}", seq);
		return dao.selectDetailNoticeBbs(seq);
	}

	@Override
	public boolean updateViewsNoticeBbs(String seq) {
		log.info("공지게시판 조회수 증가 updateViewsNoticeBbs,\t {}", seq);
		return dao.updateViewsNoticeBbs(seq);
	}

	@Override
	public BbsDTO ReplyNotice(BbsDTO dto, MultipartFile[] filename) {
		log.info("공지게시판 답글작성 ReplyNotice,\t {}", dto, filename);
		dao.updateReplyNoticeBbs(dto);
		boolean iscI = dao.insertReplyNoticeBbs(dto);
		
		String seq = dao.selectNewNoticeBbs();
		saveFile(filename, Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailNoticeBbs(seq);
		sService.addBbsIndex(result, SearchIService.NOTICE);
		
		return iscI?result:null;
	}

	@Override
	public String selectNewNoticeBbs() {
		log.info("공지 게시판 게시글 seq최대값 selectNewNoticeBbs,\t {}");
		return dao.selectNewNoticeBbs();
	}

	@Override
	public List<BbsDTO> selectUserNoticeBbsList() {
		log.info("공지게시판 글 목록 조회(유저) selectUserNoticeBbsList,\t {}");
		return dao.selectUserNoticeBbsList();
	}

	@Override
	public List<BbsDTO> selectAdminNoticeBbsList() {
		log.info("공지게시판 글 목록 조회(관리자) selectAdminNoticeBbsList,\t {}");
		return dao.selectAdminNoticeBbsList();
	}

	@Override
	public List<BbsDTO> selectUserNoticeBbsListRow(RowNumDTO rdto) {
		log.info("공지게시판 페이징 처리(유저) selectUserNoticeBbsListRow,\t {}", rdto);
		return dao.selectUserNoticeBbsListRow(rdto);
	}

	@Override
	public List<BbsDTO> selectAdminNoticeBbsListRow(RowNumDTO rdto) {
		log.info("공지게시판 페이징 처리(관리자) selectAdminNoticeBbsList,\t {}", rdto);
		return dao.selectAdminNoticeBbsListRow(rdto);
	}

	@Override
	public int selectUserNoticeBoardListTotal() {
		log.info("공지게시판 글 전체 갯수(유저) selectUserNoticeBoardListTotal,\t {}");
		return dao.selectUserNoticeBoardListTotal();
	}

	@Override
	public int selectAdminNoticeBoardListTotal() {
		log.info("공지게시판 글 전체 갯수(관리자) selectAdminNoticeBoardListTotal,\t {}");
		return dao.selectAdminNoticeBoardListTotal();
	}

	@Override
	public List<FileDTO> selectNoticeBbsFile(String seq) {
		log.info("공지게시판 파일 조회 selectNoticeBbsFile,\t {}", seq);
		return dao.selectNoticeBbsFile(seq);
	}

	@Override
	public boolean insertNoticeBbsFile(FileDTO fDto) {
		log.info("공지게시판 파일 추가 insertNoticeBbsFile,\t {}", fDto);
		return dao.insertNoticeBbsFile(fDto);
	}

	@Override
	public boolean deleteNoticeBbsFile(Map<String, Object> map) {
		log.info("공지게시판 파일 삭제 deleteNoticeBbsFile,\t {}", map);
		return dao.deleteNoticeBbsFile(map);
	}

}
