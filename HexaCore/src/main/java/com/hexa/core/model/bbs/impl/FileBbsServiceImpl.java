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
import com.hexa.core.model.bbs.inf.FileBbsIDao;
import com.hexa.core.model.bbs.inf.FileBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class FileBbsServiceImpl implements FileBbsIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileBbsIDao dao;
	
	@Autowired
	private SearchIService sService;
	
	// saveFile 
	private boolean saveFile(MultipartFile[] files,int seq) {
		log.info("파일 등록,\t {}", Arrays.toString(files));
		boolean isc = false;
		if(files!=null) {
			for (MultipartFile file : files) {
				String saveName = file.getOriginalFilename();
				File dir = new File(ATTACH_PATH);
				String filename = "fileBbs-"+UUID.randomUUID()+"-"+saveName;
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
					isc = dao.insertFileBbsFile(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isc;
	}
	
	@Override
	public BbsDTO insertFileBbs(BbsDTO dto, MultipartFile[] filename) {
		log.info("자료실 글 작성 insertFileBbs,\t {}", dto);
		dao.insertFileBbs(dto);
		String seq = dao.selectNewFileBbs();
		saveFile(filename, Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailFileBbs(seq);
		
		sService.addBbsIndex(result, SearchIService.FILE);
		
		return result;
	}

	@Override
	public BbsDTO updateModifyFileBbs(BbsDTO dto, String[] files, MultipartFile[] filename) {
		log.info("자료실 글 수정 updateModifyFileBbs,\t {}", dto);
		Map<String, Object> map = Maps.newHashMap();
		dao.updateModifyFileBbs(dto);
		map.put("files", files);
		map.put("seq", dto.getSeq());
		dao.deleteFileBbsFile(map);
		saveFile(filename,dto.getSeq());
		
		BbsDTO result = dao.selectDetailFileBbs(String.valueOf(dto.getSeq()));
		
		sService.updateBbsIndex(result, SearchIService.FILE);
		
		return result;
	}

	@Override
	public int updateDeleteFileBbs(String seq) {
		log.info("자료실 글 단일삭제 updateDeleteFileBbs,\t {}", seq);
		return dao.updateDeleteFileBbs(seq);
	}

	@Override
	public boolean updateMultiDelFileBbs(Map<String, String[]> map) {
		log.info("자료실 글 다중삭제 updateMultiDelFileBbs,\t {}", map);
		return dao.updateMultiDelFileBbs(map);
	}

	@Override
	public BbsDTO selectDetailFileBbs(String seq) {
		log.info("자료실 상세 글 보기 selectDetailFileBbs,\t {}", seq);
		return dao.selectDetailFileBbs(seq);
	}

	@Override
	public BbsDTO updateViewsFileBbs(String seq) {
		log.info("자료실 조회수 증가 updateViewsFileBbs,\t {}", seq);
		dao.updateViewsFileBbs(seq);
		BbsDTO dto = dao.selectDetailFileBbs(seq);
		sService.updateBbsIndex(dto, SearchIService.FILE);
		return dto;
	}

	@Override
	public BbsDTO ReplyFile(BbsDTO dto, MultipartFile[] filename) {
		log.info("자료실 답글작성 ReplyFile,\t {}", dto, filename);
		dao.updateReplyFileBbs(dto);
		boolean iscI = dao.insertReplyFileBbs(dto);
		
		String seq = dao.selectNewFileBbs();
		saveFile(filename, Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailFileBbs(seq);
		sService.addBbsIndex(result, SearchIService.FILE);
		
		return iscI?result:null;
	}

	@Override
	public String selectNewFileBbs() {
		log.info("자료실 seq최대값 selectNewFileBbs,\t {}");
		return dao.selectNewFileBbs();
	}

	@Override
	public List<BbsDTO> selectUserFileBbsList() {
		log.info("자료실 글 목록 조회(유저) selectUserFileBbsList,\t {}");
		return dao.selectUserFileBbsList();
	}

	@Override
	public List<BbsDTO> selectAdminFileBbsList() {
		log.info("자료실 글 목록 조회(관리자) selectAdminFileBbsList,\t {}");
		return dao.selectAdminFileBbsList();
	}

	@Override
	public List<BbsDTO> selectUserFileBbsListRow(RowNumDTO rdto) {
		log.info("자료실 페이징 처리(유저) selectUserFileBbsListRow,\t {}", rdto);
		return dao.selectUserFileBbsListRow(rdto);
	}

	@Override
	public List<BbsDTO> selectAdminFileBbsListRow(RowNumDTO rdto) {
		log.info("자료실 페이징 처리(관리자) selectAdminFileBbsListRow,\t {}", rdto);
		return dao.selectAdminFileBbsListRow(rdto);
	}

	@Override
	public int selectUserFileBoardListTotal() {
		log.info("자료실 글 전체 갯수(유저) selectUserFileBoardListTotal,\t {}");
		return dao.selectUserFileBoardListTotal();
	}

	@Override
	public int selectAdminFileBoardListTotal() {
		log.info("자료실 글 전체 갯수(관리자) selectAdminFileBoardListTotal,\t {}");
		return dao.selectAdminFileBoardListTotal();
	}

	@Override
	public List<FileDTO> selectFileBbsFile(String seq) {
		log.info("자료실 파일 조회 selectFileBbsFile,\t {}", seq);
		return dao.selectFileBbsFile(seq);
	}

	@Override
	public boolean insertFileBbsFile(FileDTO fDto) {
		log.info("자료실 파일 추가 insertFileBbsFile,\t {}", fDto);
		return dao.insertFileBbsFile(fDto);
	}

	@Override
	public boolean deleteFileBbsFile(Map<String, Object> map) {
		log.info("공지게시판 파일 삭제 deleteFileBbsFile,\t {}", map);
		return dao.deleteFileBbsFile(map);
	}

}
