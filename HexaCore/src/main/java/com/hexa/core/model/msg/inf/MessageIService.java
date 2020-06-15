package com.hexa.core.model.msg.inf;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;

public interface MessageIService {

//	public static final String ATTACH_PATH = "C:\\nobrand\\git\\hexacore\\HexaCore\\file";
	
	/*상대 경로*/
	public static final String ATTACH_PATH = "C:\\nobrand\\eclipse_spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\HexaCore\\resource\\file";
	
	/**
	 * 메세지 보내기
	 * @param msdto
	 * @return 성공여부
	 */
	public boolean insertMessage(MessageDTO msdto, MultipartFile[] filename);
	
	/**
	 * 메세지 수신함 조회
	 * @param receiver_id
	 * @return 메세지 수신함
	 */
	public List<MessageDTO> selectReceiveList(Map<String, Object> map);
	
	/**
	 * 메세지 발신함 조회
	 * @param sender_id
	 * @return 메세지 발신함
	 */
	public List<MessageDTO> selectSendList(Map<String, Object> map);
	
	/**
	 * 수신한 메세지 상세보기
	 * @param msdto
	 * @return 수신한 메세지 내용
	 */
	public MessageDTO selectDetailMessage(String seq);
	
	/**
	 * 수신한 메세지 삭제
	 * @param seq
	 * @return 삭제 성공여부
	 */
	public boolean updateDelMessage(String seq);
	
	/**
	 * 메세지 다중삭제
	 * @param map
	 * @return 다중삭제 성공여부
	 */
	public boolean updateMultiDelMessage(Map<String, String[]> map);
	
	/**
	 * 메세지 확인하면 읽음 표시
	 * @param seq
	 * @return 읽음표시 성공여부
	 */
	public boolean updateStateMessage(String seq);

	/**
	 * 수신한 메세지의 전체 갯수
	 * @return 메세지의 전체 갯수
	 */
	public int messageReceiveListTotal(String receiver_id);
	
	// 발신한 메세지의 전체 갯수
	public int messageSenderListTotal(String sender_id);
	
	// 새로운 메세지 알림기능
	public int selectNewMsgCount(String receiver_id);
	
}
