package com.hexa.core.model.search.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexOptions;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.model.bbs.inf.FreeBbsIService;
import com.hexa.core.model.eappr.impl.EapprServiceImpl;
import com.hexa.core.model.eappr.inf.EapprIService;
import com.hexa.core.model.search.inf.SearchIDao;
import com.hexa.core.model.search.inf.SearchIService;

@Service
public class SearchService implements SearchIService{

	@Autowired
	private EapprIService eService;

	@Autowired
	private FreeBbsIService frService;
	
	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SearchIDao dao;
	
	@Override
	public void eDocIndex() {
		log.info("SearchService 인덱싱 작업 시작");
		dao.eDocIndex(eService.selectAllDoc());
	}
	@Override
	public void freeBbsIndex() {
		log.info("SearchService 인덱싱 작업 시작");
		dao.freeBbsIndex(frService.adminBbsList());
	}

	@Override
	public void noticeBbsIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fileBbsIndex() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<DocumentDTO> eDocSearch(String keyword,String type) {
		log.info("SearchServcie 전자문서 서치");
		return dao.eDocSearch(keyword,type);
	}
	@Override
	public List<BbsDTO> freeBbsSearch(String keyword,String type) {
		log.info("SearchService 자유게시판 서치");
		return dao.freeBbsSearch(keyword,type);
	}
	@Override
	public List<BbsDTO> noticeBbsSearch(String keyword,String type) {
		log.info("SearchService 공지게시판 서치");
		return dao.noticeBbsSearch(keyword,type);
	}
	@Override
	public List<BbsDTO> fileBbsSearch(String keyword,String type) {
		log.info("SearchService 파일게시판 서치");
		return dao.fileBbsSearch(keyword,type);
	}


}
