package com.min.edu.spring.model;

import java.util.List;

import com.min.edu.dtos.AnswerboardDto;

public interface IBoardDao {
	
	public List<AnswerboardDto> boardList();

}
