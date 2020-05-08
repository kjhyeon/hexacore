package com.min.edu.spring;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.min.edu.dtos.AnswerboardDto;
import com.min.edu.spring.model.IBoardService;

@Controller
public class SpringHelloCtrl {
	
	@Autowired
	private IBoardService iBoardService;
	
	// boardList.do
	@RequestMapping(value = "/boardList.do", method = RequestMethod.GET)
	public String boardList(Model model) {
		List<AnswerboardDto> lists = iBoardService.boardList();
		model.addAttribute("lists", lists);
		return "boardList";
	}
	
	@RequestMapping(value="/hello.do", method= {RequestMethod.POST, RequestMethod.GET})
	public String helloDo() {
		return "springHello";
	}

	@RequestMapping(value="/springhello", method = RequestMethod.GET)
	public String hello(String name, Model model) {
		System.out.println(name);
		model.addAttribute("val", name);
		return "springHello";
	}
	
}
