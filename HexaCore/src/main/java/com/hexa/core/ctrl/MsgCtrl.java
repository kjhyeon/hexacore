package com.hexa.core.ctrl;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;
import com.hexa.core.dto.LoginDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.msg.inf.MessageIService;
import com.hexa.core.model.search.inf.SearchIService;

@Controller
public class MsgCtrl {

	@Autowired
	private MessageIService mService;
	
	@Autowired
	private SearchIService sService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	/*절대 경로*/
//	private static String attach_path = "C:\\nobrand\\git\\hexacore\\HexaCore\\file";
	
	/*상대 경로*/
	private static String attach_path = "C:\\nobrand\\eclipse_spring\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\HexaCore\\resource\\file";
	
	
	@RequestMapping(value = "/msgMain.do", method = RequestMethod.GET)
	public String goMsgMain() {
		log.info("메세지 메인 실행");
		return "msg/msgMain";
	}
	//메세지 보내기 양식
	@RequestMapping(value = "/msgSendForm.do", method = RequestMethod.GET)
	public String msgSendForm() {
		log.info("msgSendForm창 실행");
		return "msg/msgSendForm";
	}
	//메세지 보내기
	@RequestMapping(value = "/insertMessage.do", method = RequestMethod.POST)
	public String msgSend(MessageDTO msdto,MultipartFile[] filename) {
		log.info("insertMessage.do 실행",msdto);
		
		boolean isc = mService.insertMessage(msdto, filename);
		
		return isc?"redirect:/empInfo.do":"../login";
	}
	//메세지 수신함 리스트 조회
	@RequestMapping(value = "/msgReceiveList.do", method = RequestMethod.GET)
	public String msgReceiveList(Model model, SecurityContextHolder session, String page,String keyword,String type) {
		log.info("MsgCtrl /msgReceiveList 실행{}", page);
		if(page==null) {
			page="0";
		}
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		RowNumDTO row = new RowNumDTO();
		if(keyword!=null&&!keyword.equals("")){
			row.setTotal(sService.receiveMsgTotal(keyword, type, dto.getUsername()));
		}else {
			row.setTotal(mService.messageReceiveListTotal(dto.getUsername()));
		}
		row.setPageNum(3);
		row.setListNum(5);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		if(keyword!=null&&!keyword.equals("")) {
			model.addAttribute("lists",sService.receiveMsgSearch(keyword, row, type, dto.getUsername()));
		}else {
			Map<String, Object> map = Maps.newHashMap();
			map.put("receiver_id", dto.getUsername());
			map.put("start", row.getStart());
			map.put("last",row.getLast());
			model.addAttribute("lists", mService.selectReceiveList(map));
		}
		
		model.addAttribute("row", row);
		
		return "msg/msgReceiveList";
	}
	//메세지 발신함 리스트 조회
	@RequestMapping(value = "/msgSendList.do", method = RequestMethod.GET)
	public String msgSendList(Model model, SecurityContextHolder session, String page,String keyword,String type) {
		log.info("MsgCtrl /msgSendList 실행{}", page);
		if(page==null) {
			page="0";
		}
		Authentication auth = session.getContext().getAuthentication();
		LoginDTO dto = (LoginDTO) auth.getPrincipal();
		RowNumDTO row = new RowNumDTO();
		if(keyword!=null&&!keyword.equals("")){
			row.setTotal(sService.senderMsgTotal(keyword, type, dto.getUsername()));
		}else {
			row.setTotal(mService.messageSenderListTotal(dto.getUsername()));
		}
		row.setPageNum(3);
		row.setListNum(5);
		if(row.getLastPage()-1<Integer.parseInt(page)) {
			row.setIndex(row.getLastPage()-1);
		}else if(Integer.parseInt(page)<0) {
			row.setIndex(0);
		}else {
			row.setIndex(Integer.parseInt(page));
		}
		
		if(keyword!=null&&!keyword.equals("")) {
			model.addAttribute("list",sService.senderMsgSearch(keyword, row, type, dto.getUsername()));
		}else {
			Map<String, Object> map = Maps.newHashMap();
			map.put("sender_id", dto.getUsername());
			map.put("start", row.getStart());
			map.put("last",row.getLast());
			model.addAttribute("list", mService.selectSendList(map));
		}
		
		model.addAttribute("row", row);
		
		return "msg/msgSendList";
	}
	//메세지 수신함 다중삭제
	@RequestMapping(value = "/rMultiDel.do", method = RequestMethod.POST)
	public String rMultiDelMessage(String[] chkVal) {
		log.info("MsgCtrl /rMultiDel.do 메세지 수신함 다중삭제 실행");
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("seqs", chkVal);
		return mService.updateMultiDelMessage(map)?"redirect:/msgReceiveList.do":"redirect:/msgReceiveList.do";
	}
	//메세지 수신함 상세보기에서 삭제
	@RequestMapping(value = "/rDel.do", method = RequestMethod.GET)
	public String rDelMessage(String seq) {
		log.info("MsgCtrl /rDel.do 메세지 수신함 상세보기에서 삭제 실행");
		
		return mService.updateDelMessage(seq)?"redirect:/msgReceiveList.do":"redirect:/msgReceiveList.do";
	}
	
	//메세지 수신함에서 메세지 읽음 표시 기능
	@ResponseBody
	@RequestMapping(value = "/updateState.do", method = RequestMethod.POST)
	public String updateStateMessage(String seq) {
		log.info("MsgCtrl updateStateMessage 읽음 표시 실행", seq);
		boolean isc = mService.updateStateMessage(seq);
		
		return "true";
	}
	
}






