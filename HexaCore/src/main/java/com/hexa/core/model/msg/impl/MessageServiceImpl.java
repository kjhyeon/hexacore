package com.hexa.core.model.msg.impl;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Lists;
import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.model.msg.inf.MessageIDao;
import com.hexa.core.model.msg.inf.MessageIService;
import com.hexa.core.model.search.inf.SearchIDao;

@Service
public class MessageServiceImpl implements MessageIService {

	@Autowired
	private MessageIDao dao;

	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SearchIDao sDao;
	
	@Override
	public boolean insertMessage(MessageDTO msdto, MultipartFile[] filename) {
		log.info("insertMessage serviceImpl 실행 {}", msdto);
		dao.insertMessage(msdto);
		int seq = dao.selectLatestMessage();
		log.info("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$  {}",filename[0].getOriginalFilename());
		sDao.addMsgIndex(dao.selectDetailMessage(String.valueOf(seq)));
		if(filename[0].getOriginalFilename()!=null && !filename[0].getOriginalFilename().equals("")) {
			return saveFile(filename, seq);
		}
		
		return true;
	}

	@Override
	public List<MessageDTO> selectReceiveList(Map<String, Object> map) {
		log.info("selectReceiveList serviceImpl 실행 {}", map);
		List<MessageDTO> result = Lists.newArrayList();
		for (MessageDTO dto : dao.selectReceiveList(map)) {
			List<FileDTO> fileList = dao.selectFile(dto.getSeq());
			if(fileList!=null&&fileList.size()>0) {
				dto.setFile(fileList.get(0));
			}
			result.add(dto);
		}
		return result;
	}

	@Override
	public List<MessageDTO> selectSendList(Map<String, Object> map) {
		log.info("selectSendList serviceImpl 실행 {}", map);
		List<MessageDTO> result = Lists.newArrayList();
		for (MessageDTO dto : dao.selectSendList(map)) {
			List<FileDTO> fileList = dao.selectFile(dto.getSeq());
			if(fileList!=null&&fileList.size()>0) {
				dto.setFile(fileList.get(0));
			}
			result.add(dto);
		}
		return result;
	}

	@Override
	public MessageDTO selectDetailMessage(String seq) {
		log.info("selectDetailMessage serviceImpl 실행 {}", seq);
		return dao.selectDetailMessage(seq);
	}

	@Override
	public boolean updateDelMessage(String seq) {
		log.info("updateDelMessage serviceImpl 실행 {}", seq);
		sDao.updateMsgIndex(seq);
		return dao.updateDelMessage(seq);
	}

	@Override
	public boolean updateMultiDelMessage(Map<String, String[]> map) {
		log.info("updateMultiDelMessage serviceImpl 실행 {}", map);
		String[] seqs = map.get("seqs");
		for (int i = 0; i < seqs.length; i++) {
			sDao.updateMsgIndex(seqs[i]);
		}
		return dao.updateMultiDelMessage(map);
	}

	@Override
	public boolean updateStateMessage(String seq) {
		log.info("updateStateMessage serviceImpl 실행 {}", seq);
		return dao.updateStateMessage(seq);
	}

	@Override
	public int messageReceiveListTotal(String receiver_id) {
		log.info("messageReceiveListTotal serviceImpl 실행 {}", receiver_id);
		return dao.messageReceiveListTotal(receiver_id);
	}

	@Override
	public int messageSenderListTotal(String sender_id) {
		log.info("messageSenderListTotal serviceImpl 실행 {}", sender_id);
		return dao.messageSenderListTotal(sender_id);
	}
	
	private boolean saveFile(MultipartFile[] files, int seq) {
		boolean isc = false;
		if(files!=null) {
			for (MultipartFile file : files) {
				String saveName = file.getOriginalFilename();
				File dir = new File(ATTACH_PATH);
				String filename = "message-"+UUID.randomUUID()+"-"+saveName;
				if(dir.isDirectory() == false){
					dir.mkdirs();
				}
				if(file.getOriginalFilename()!=null&&!file.getOriginalFilename().equals("")) {
					try {
						File f = new File(ATTACH_PATH, filename);
						file.transferTo(f);
						FileDTO dto = new FileDTO();
						dto.setOri_name(file.getOriginalFilename());
						dto.setName(filename);
						dto.setF_size(String.valueOf(file.getSize()));
						dto.setF_path(ATTACH_PATH);
						dto.setCategory(3);
						dto.setSeq(seq);
						isc = dao.insertFile(dto);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return isc;
	}
	

}
