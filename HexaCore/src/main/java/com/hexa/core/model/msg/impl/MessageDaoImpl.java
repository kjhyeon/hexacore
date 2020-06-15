package com.hexa.core.model.msg.impl;
import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hexa.core.dto.FileDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.msg.inf.MessageIDao;

@Repository
public class MessageDaoImpl implements MessageIDao {

	private final String NS = "com.hexa.core.msg.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean insertMessage(MessageDTO msdto) {
		log.info("insertMessage daoImpl 실행 {}", msdto);
		int cnt = sqlSession.insert(NS+"insertMessage", msdto);
		return (cnt>0)?true:false;
	}

	@Override
	public List<MessageDTO> selectReceiveList(Map<String, Object> map) {
		log.info("selectReceiveList daoImpl 실행 {}", map);
		return sqlSession.selectList(NS+"selectReceiveList", map);
	}

	@Override
	public List<MessageDTO> selectSendList(Map<String, Object> map) {
		log.info("selectSendList daoImpl 실행 {}", map);
		return sqlSession.selectList(NS+"selectSendList", map);
	}

	@Override
	public MessageDTO selectDetailMessage(String seq) {
		log.info("selectDetailMessage daoImpl 실행 {}", seq);
		return sqlSession.selectOne(NS+"selectDetailMessage", seq);
	}

	@Override
	public boolean updateDelMessage(String seq) {
		log.info("updateDelMessage daoImpl 실행 {}", seq);
		int n = sqlSession.update(NS+"updateDelMessage", seq);
		return (n>0)?true:false;
	}

	@Override
	public boolean updateMultiDelMessage(Map<String, String[]> map) {
		log.info("updateMultiDelMessage daoImpl 실행 {}", map);
		int a = sqlSession.update(NS+"updateMultiDelMessage", map);
		return (a>0)?true:false;
	}

	@Override
	public boolean updateStateMessage(String seq) {
		log.info("updateStateMessage daoImpl 실행 {}", seq);
		int b = sqlSession.update(NS+"updateStateMessage", seq);
		return (b>0)?true:false;
	}

	@Override
	public int messageReceiveListTotal(String receiver_id) {
		log.info("messageReceiveListTotal daoImpl 실행 {}", receiver_id);
		return sqlSession.selectOne(NS+"messageReceiveListTotal", receiver_id);
	}

	@Override
	public int messageSenderListTotal(String sender_id) {
		log.info("messageSenderListTotal daoImpl 실행 {}", sender_id);
		return sqlSession.selectOne(NS+"messageSenderListTotal", sender_id);
	}

	@Override
	public int selectLatestMessage() {
		log.info("selectLatestMessage daoImpl 실행 ");
		return sqlSession.selectOne(NS+"selectLatestMessage");
	}

	@Override
	public boolean insertFile(FileDTO dto) {
		log.info("inserFile daoImpl 실행 : {} ",dto);
		return sqlSession.insert(NS+"insertFile",dto)>0?true:false;
	}

	@Override
	public List<FileDTO> selectFile(int seq) {
		log.info("selectFile daoImpl 실행 : {} ", seq);
		return sqlSession.selectList(NS+"selectFile",seq);
	}

	@Override
	public List<MessageDTO> selectAllMsg() {
		log.info("selectAllMsg daoImpl 실행 ");
		return sqlSession.selectList(NS+"selectAllMsg");
	}

	@Override
	public int selectNewMsgCount(String receiver_id) {
		log.info("selectNewMsgCount daoImpl 실행 ");
		return sqlSession.selectOne(NS+"selectNewMsgCount",receiver_id);
	}
	

}
