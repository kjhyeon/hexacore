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
import com.hexa.core.model.bbs.inf.FreeBbsIDao;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class FreeBbsServiceImpl implements FreeBbsIService {

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FreeBbsIDao dao;
	
	@Autowired
	private SearchIService sService;
	
	@Override
	public BbsDTO insertFreeBbs(BbsDTO dto,MultipartFile[] filename) {
		log.info("자유게시판 글 작성 insertFreeBbs,\t {}", dto);
		dao.insertFreeBbs(dto);
		String seq = dao.selectNewBbs();
		saveFile(filename,Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailFreeBbs(seq);
		
		sService.addBbsIndex(result, SearchIService.FREE);
		
		return result;
	}

	@Override
	public BbsDTO updateModifyFreeBbs(BbsDTO dto,String[] files,MultipartFile[] filename) {
		log.info("자유게시판 글 수정 updateModifyFreeBbs,\t {}", dto);
		Map<String, Object> map = Maps.newHashMap();
		dao.updateModifyFreeBbs(dto);
		map.put("files", files);
		map.put("seq", dto.getSeq());
		dao.deleteFile(map);
		saveFile(filename,dto.getSeq());
		
		BbsDTO result = dao.selectDetailFreeBbs(String.valueOf(dto.getSeq()));
		
		sService.updateBbsIndex(result, SearchIService.FREE);
		
		return result;
	}

	@Override
	public int updateDeleteFreeBbs(String seq) {
		log.info("자유게시판 글 단일삭제 updateDeleteFreeBbs,\t {}", seq);
		return dao.updateDeleteFreeBbs(seq);
	}

	@Override
	public boolean updateMultiDelFreeBbs(Map<String, String[]> map) {
		log.info("자유게시판 글 다중삭제 updateMultiDelFreeBbs,\t {}", map);
		return dao.updateMultiDelFreeBbs(map);
	}

	@Override
	public BbsDTO selectDetailFreeBbs(String seq) {
		log.info("자유게시판 상세 글 보기 selectDetailFreeBbs,\t {}", seq);
		return dao.selectDetailFreeBbs(seq);
	}

	@Override
	public BbsDTO updateViewsBbs(String seq) {
		log.info("자유게시판 조회수 증가 updateViewsBbs,\t {}", seq);
		dao.updateViewsBbs(seq);
		BbsDTO dto = dao.selectDetailFreeBbs(seq);
		sService.updateBbsIndex(dto, SearchIService.FREE);
		return dto;
	}

	@Override
	public BbsDTO ReplyBbs(BbsDTO dto, MultipartFile[] filename) {
		log.info("자유게시판 답글 달기  ReplyBbs,\t {}", dto);
		dao.updateReplyBbs(dto);
		boolean iscI = dao.insertReplyBbs(dto);
		// 트랜잭셔널을 위해 거는 것들
		String seq = dao.selectNewBbs();
		saveFile(filename, Integer.parseInt(seq));
		BbsDTO result = dao.selectDetailFreeBbs(seq);
		sService.addBbsIndex(result, SearchIService.FREE);
		
		return iscI?result:null;
	}

	@Override
	public List<BbsDTO> selectUserFreeBbsList() {
		log.info("자유게시판 글 목록 조회(유저) selectUserFreeBbsList,\t {}");
		return dao.selectUserFreeBbsList();
	}

	@Override
	public List<BbsDTO> selectAdminFreeBbsList() {
		log.info("자유게시판 글 목록 조회(관리자) selectAdminFreeBbsList,\t {}");
		return dao.selectAdminFreeBbsList();
	}

	@Override
	public List<BbsDTO> selectUserBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) selectUserBbsListRow,\t {}", rdto);
		return dao.selectUserBbsListRow(rdto);
	}

	@Override
	public List<BbsDTO> selectAdminBbsListRow(RowNumDTO rdto) {
		log.info("자유게시판 페이징 처리(유저) selectAdminBbsListRow,\t {}", rdto);
		return dao.selectAdminBbsListRow(rdto);
	}

	@Override
	public int selectUserBoardListTotal() {
		log.info("게시글 전체 갯수(유저) selectUserBoardListTotal,\t {}");
		return dao.selectUserBoardListTotal();
	}

	@Override
	public int selectAdminBoardListTotal() {
		log.info("게시글 전체 갯수(유저) selectAdminBoardListTotal,\t {}");
		return dao.selectAdminBoardListTotal();
	}

	@Override
	public String selectNewBbs() {
		log.info("게시글 seq최대값 selectNewBbs");
		return dao.selectNewBbs();
	}

	@Override
	public List<FileDTO> selectFile(String seq) {
		log.info("파일업로드 selectFile,\t {}", seq);
		return dao.selectFile(seq);
	}

	@Override
	public boolean insertFile(FileDTO fDto) {
		log.info("파일 추가 insertFile,\t {}", fDto);
		return dao.insertFile(fDto);
	}

	@Override
	public boolean deleteFile(Map<String,Object> map) {
		log.info("파일 삭제,\t {}", map);
		return false;
	}

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
					isc = dao.insertFile(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isc;
	}
	

}
