package com.min.edu.spring.model;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.min.edu.dtos.AnswerboardDto;

@Repository
public class BoardDaoImpl implements IBoardDao {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private final String NS = "spring.answerboard.";
	
	@Autowired
	private SqlSessionTemplate sqlSession;
	
	@Override
	public List<AnswerboardDto> boardList() {
		logger.info("전체글 보기 BoardDaoImpl");
		return sqlSession.selectList(NS+"boardList");
	}

}
