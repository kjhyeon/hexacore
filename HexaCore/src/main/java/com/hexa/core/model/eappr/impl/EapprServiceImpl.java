package com.hexa.core.model.eappr.impl;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.ApprovalDTO;
import com.hexa.core.dto.DocCommentDTO;
import com.hexa.core.dto.DocFileDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.DocumentTypeDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.eappr.inf.EapprIDao;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class EapprServiceImpl implements EapprIService{

	@Autowired
	private EapprIDao dao;
	
	@Autowired
	private SearchIService sService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public List<DocumentTypeDTO> selectDocTypeList() {
		log.info("selectDocTypeList serviceImpl 실행");
		return dao.selectDocTypeList();
	}
	
	@Override
	public List<DocumentTypeDTO> selectDocTypeListP(RowNumDTO row) {
		log.info("selectDocTypeListP serviceImpl 실행");
		return dao.selectDocTypeListP(row);
	}
	
	@Override
	public DocumentTypeDTO selectDocType(String type_seq) {
		log.info("selectDocType serviceImpl 실행");
		return dao.selectDocType(type_seq);
	}
	
	@Transactional
	@Override
	public int insertNewDoc(DocumentDTO dto, MultipartFile[] filename) {
		log.info("insertNewDoc serviceImpl 실행");
		dao.insertNewDoc(dto);
		String seq = dao.selectNewDoc();
		int intSeq = Integer.parseInt(seq);
		saveDocFile(filename, intSeq);
		dto.setSeq(intSeq);
		for (int i = 0; i < dto.getLists().size(); i++) {
			dto.getLists().get(i).setSeq(intSeq);
		}
		for (int i = 0; i < dto.getLists().size(); i++) {
			log.info("insertApprRoot serviceImpl 실행");
			dao.insertApprRoot(dto.getLists().get(i));
		}
		DocumentDTO newDto = selectDoc(seq);
		sService.addDocIndex(newDto);
		return intSeq;
	}
	
	@Override
	public DocumentDTO selectDoc(String seq) {
		log.info("selectDoc serviceImpl 실행");
		return dao.selectDoc(seq);
	}

	@Override
	public List<ApprovalDTO> selectApprRoot(ApprovalDTO Adto) {
		log.info("selectApprRoot serviceImpl 실행");
		return dao.selectApprRoot(Adto);
	}

	@Override
	public boolean insertFile(DocFileDTO DFdto) {
		log.info("insertFile serviceImpl 실행");
		return dao.insertFile(DFdto);
	}

	@Override
	public boolean deleteFile(String seq) {
		log.info("deleteFIle serviceImpl 실행");
		return dao.deleteFile(seq);
	}

	@Override
	public boolean insertDocType(DocumentTypeDTO DTdto) {
		log.info("insertDocType serviceImpl 실행");
		return dao.insertDocType(DTdto);
	}

	@Override
	public String selectNewDoc() {
		log.info("selectNewDoc serviceImpl 실행");
		return dao.selectNewDoc();
	}

	@Override
	public boolean deleteDoc(String seq) {
		log.info("deleteDoc serviceImpl 실행 seq : {}", seq);
		return dao.deleteDoc(seq);
	}

	@Override
	public List<DocumentDTO> selectAllDoc() {
		log.info("selectAllDoc ServiceImpl 실행");
		return dao.selectAllDoc();
	}

	@Override
	public String selectNewDocType() {
		log.info("selectNewDocType ServiceImpl 실행");
		return dao.selectNewDocType();
	}

	@Override
	public boolean deleteDocType(String seq) {
		log.info("deleteDocType ServiceImpl 실행 : {}",seq);
		return dao.deleteDocType(seq);
	}

	@Override
	public boolean updateDocType(DocumentTypeDTO dto) {
		log.info("updateDocType ServiceImpl 실행 : {}",dto);
		return dao.updateDocType(dto);
	}

	@Override
	public int selectReportCount(String id) {
		log.info("selectReportCount ServiceImpl 실행 : {}",id);
		return dao.selectReportCount(id);
	}

	@Override
	public int selectMyDocCount(Map<String, Object> map) {
		log.info("selectMyDocCount ServiceImpl 실행 : {}",map);
		return dao.selectMyDocCount(map);
	}

	@Override
	public int selectReferDocCount(Map<String, Object> map) {
		log.info("selectReferDocCount ServiceImpl 실행 : {}",map);
		return dao.selectReferDocCount(map);
	}

	@Override
	public int selectApprDocCount(Map<String, Object> map) {
		log.info("selectApprDocCount ServiceImpl 실행 : {}",map);
		return dao.selectApprDocCount(map);
	}

	@Override
	public int selectAllApprDocCount(Map<String, Object> map) {
		log.info("selectNeedApprDocCount ServiceImpl 실행 : {}",map);
		int cnt=0;
		
		log.info("**** 실행 : {}",map);
		if(Integer.parseInt((String)map.get("state"))<5) {
			cnt = dao.selectMyDocCount(map);
		}else {
			switch ((String)map.get("state")) {
				case "6":
					cnt = dao.selectApprDocCount(map);
					break;
				case "7":
					cnt = dao.selectNeedApprDocCount(map);
					break;
				case "8":
					cnt = dao.selectReferDocCount(map);
					break;
				default:
					break;
			}
		}
		return cnt;
	}

	@Override
	public int selectDocTypeListSize() {
		log.info("selectDocTypeListSize ServiceImpl 실행");
		return dao.selectDocTypeListSize();
	}

	@Override
	public List<DocumentDTO> selectMyDocList(Map<String, Object> map) {
		log.info("selectMyDoc ServiceImpl 실행 : {}",map);
		List<DocumentDTO> lists = null;
		
		log.info("**** 실행 : {}",map);
		String state = (String)map.get("state");
		if(Integer.parseInt(state)<5||state.equals("777")) {
			lists = dao.selectMyDocList(map);
		}else {
			switch (state) {
				case "6":
					lists = dao.selectApprMyDoc(map);
					break;
				case "7":
					lists = dao.selectNeedApprDoc(map);
					break;
				case "8":
					lists = dao.selectReferDoc(map);
					break;
				default:
					break;
			}
		}
		return lists;
	}

	@Transactional
	@Override
	public boolean confirmUpdate(Map<String, Object> map) {
		log.info("********confirm Test Adto:{}",map.get("Adto"));
		log.info("********confirm Test Ddto:{}",map.get("Ddto"));
		log.info("********confirm Test DCdto:{}",map.get("DCdto"));
		ApprovalDTO Adto = (ApprovalDTO) map.get("Adto");
		DocumentDTO Ddto = (DocumentDTO) map.get("Ddto");
		DocCommentDTO DCdto = (DocCommentDTO) map.get("DCdto");
		Adto.setAppr_sign(dao.selectSignImg(Adto.getId()));
		if(Adto.getChk().equalsIgnoreCase("T")) {//승인일경우
			Adto.setAppr_kind("승인");
				if(Adto.getTurn()<3) {
					Ddto.setAppr_turn(Ddto.getAppr_turn()+1);
					Ddto.setState(2);
				}else if(Adto.getTurn()==3){
					Ddto.setState(3);
				}
		}else if(Adto.getChk().equalsIgnoreCase("R")){
				Adto.setAppr_kind("반려");
				Ddto.setState(4);
			}
		boolean iscD=false;
		boolean iscA=false;
		boolean iscDC=false;
		if(map.get("Ddto")!=null) {
			iscD = dao.updateDoc(Ddto);
		}
		if(iscD=true && map.get("Adto")!=null) {
			iscA = dao.updateApprChk(Adto);
		}
		if((iscD && iscA) ==true && DCdto!=null) {
			iscDC = dao.insertComment(DCdto);
		}
		return (iscD||iscA||iscDC)?true:false;
	}

	@Override
	public Map<String, Object> selectDocListAll(String id) {
		log.info("selectDocListAll {}", id);
		return dao.selectDocListAll(id);
	}

	public boolean saveDocFile(MultipartFile[] files,int seq) {
		log.info("파일 등록,\t {}", Arrays.toString(files));
		boolean isc = false;
		if(files!=null) {
			for (MultipartFile file : files) {
				String saveName = file.getOriginalFilename();
				File dir = new File(ATTACH_PATH);
				String filename = "eappr-"+UUID.randomUUID()+"-"+saveName;
				if(dir.isDirectory() == false){
					dir.mkdirs();
				}
				File f = new File(ATTACH_PATH, filename);
				try {
					file.transferTo(f);
					DocFileDTO dto = new DocFileDTO();
					dto.setOri_name(file.getOriginalFilename());
					dto.setName(filename);
					dto.setF_size(String.valueOf(file.getSize()));
					dto.setF_path(ATTACH_PATH);
					dto.setSeq(seq);
					isc = dao.insertDocFile(dto);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return isc;
	}

	@Override
	public boolean insertDocFile(DocFileDTO dto) {
		log.info("파일 추가 insertDocFile, {}", dto);
		return dao.insertDocFile(dto);
	}

	@Override
	public List<DocFileDTO> selectDocFile(String seq) {
		log.info("파일 추가 selectDocFile, {}", seq);
		return dao.selectDocFile(seq);
	}
	
	@Transactional
	@Override
	public Map<String, Object> goDetail(ApprovalDTO ADto) {
		log.info("goDetail, {}", ADto);
		log.info("받아온 seq 값: {}", ADto.getSeq());
		String seq = Integer.toString(ADto.getSeq());
		String userId = ADto.getId();
		String sign = dao.selectSignImg(userId);
		Map<String, Object> map = new HashMap<String, Object>();
		
		//믄사 내용 조회
		DocumentDTO Ddto = dao.selectDoc(seq);
		//문서 결재선 전체 조회
		ADto.setId(null);
		List<ApprovalDTO> apprList = dao.selectApprRoot(ADto);
		log.info("문서리스트{}",apprList);
		for (int i = 0; i < apprList.size(); i++) {
			if(apprList.get(i).getAppr_sign()!=null) {
				apprList.get(i).setAppr_sign(APPR_SIGN+"/"+apprList.get(i).getAppr_sign()); 
			}
		}
		//문서 나의 결재선 조회
		ADto.setId(userId);
		List<ApprovalDTO> listsa = dao.selectApprRoot(ADto);
		log.info("********listsa:{}",listsa);
		int turn = 0;
		if(listsa!=null && listsa.size()!=0) {
			turn = listsa.get(0).getTurn();
			map.put("turn",turn);
		}
		log.info("********turn:{}",turn);
		
		//결재자 코멘트 가져오기
		List<DocCommentDTO> lists = dao.selectComment(seq);
		//독타입 가져오기
		DocumentTypeDTO TDto = dao.selectDocType(Integer.toString(Ddto.getType_seq()));
		map.put("typeDto",TDto);
		map.put("comment",lists);
		map.put("Ddto",Ddto);
		map.put("apprList",apprList);
		
		// 첨부파일 가져오기
		List<DocFileDTO> flist = dao.selectDocFile(seq);
		map.put("flist", flist);
		return map;
	}

	@Override
	public boolean upApprDoc(DocumentDTO Ddto) {
		String seq = Integer.toString(Ddto.getSeq());
		log.info("********upApprDoc{}",Ddto);
		boolean isc = false;
		if(Ddto.getState()==0) {
			isc = dao.updateSaveToAppr(seq);
		}else {
			isc = dao.reportCancel(seq);
		}
		return (isc)?true:false;
	}

	@Transactional
	@Override
	public boolean saveUpDoc(DocumentDTO Ddto,MultipartFile[] filename) {
		boolean isc = dao.updateDoc(Ddto);
		boolean isc2=true;
		boolean isc3=true;
		if(filename.length!=0) {
		isc2 = dao.deleteFile(Integer.toString(Ddto.getSeq()));
		isc3 = saveDocFile(filename, Ddto.getSeq());
		}
		int iscI =0;
		if(isc==true) {
			if(Ddto.getLists()!=null) {
				dao.deleteApprRoot(Integer.toString(Ddto.getSeq()));
					for (int i = 0; i < Ddto.getLists().size(); i++) {
						log.info("insertApprRoot serviceImpl 실행");
						iscI += dao.insertApprRoot(Ddto.getLists().get(i));
					}
			}
		}
		log.info("{}{}{}",iscI,isc2,isc3);
		return (iscI>2&&isc2&&isc3)?true:false;
	}

}
