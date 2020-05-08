package com.min.edu.spring.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.min.edu.dtos.AnswerboardDto;

@Service
public class BoardServiceImpl implements IBoardService {
	
	@Autowired
	private IBoardDao iBoardDao;

	@Override
	public List<AnswerboardDto> boardList() {
		return iBoardDao.boardList();
	}

}
