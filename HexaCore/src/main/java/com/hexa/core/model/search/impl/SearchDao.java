package com.hexa.core.model.search.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.FieldType;
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause.Occur;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TermRangeQuery;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
import com.hexa.core.dto.MessageDTO;
import com.hexa.core.dto.RowNumDTO;
import com.hexa.core.model.search.inf.SearchIDao;

@Repository
public class SearchDao implements SearchIDao{

	/*
	 *  QueryScorer scorer = new QueryScorer(q);
    Formatter formatter = new SimpleHTMLFormatter(highlightStartTag, highlightEndTag);
    Highlighter highlighter = new Highlighter(formatter, scorer);
    highlighter.SetTextFragmenter(new SimpleFragmenter(fragmentLength));
    TokenStream stream = new StandardAnalyzer().TokenStream(new StringReader(text));
    return highlighter.GetBestFragments(stream, text, fragmentCount, fragmentSeparator);
	 */
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String INDEX_PATH = "/home/HexaCore/lucene"; //인덱싱 파일이 저장될 공간 지정 나중에 바꿀거임
	
	@Override
	public void eDocIndex(List<DocumentDTO> list) {
		log.info("SearchDao 전자문서 인덱싱 시작");
		indexing(list, "/eDoc", false);
	}

	@Override
	public void freeBbsIndex(List<BbsDTO> list) {
		log.info("SearchDao 자유게시판 인덱싱 시작");
		indexing(list, "/freeBbs", true);
	}

	@Override
	public void noticeBbsIndex(List<BbsDTO> list) {
		log.info("SearchDao 공지사항게시판 인덱싱 시작");
		indexing(list, "/noticeBbs", true);
	}

	@Override
	public void fileBbsIndex(List<BbsDTO> list) {
		log.info("SearchDao 파일게시판 인덱싱 시작");
		indexing(list, "/fileBbs", true);
	}


	@Override
	public List<DocumentDTO> eDocSearch(String keyword,String type,String id,RowNumDTO row) {
		log.info("SearchDao 전자문서 서치 {}",keyword);
		FSDirectory directory;
		List<DocumentDTO> list = Lists.newArrayList(); //서치 결과 담을 리스트
		if(row==null) {
			row = new RowNumDTO();
			row.setListNum(5);
			row.setIndex(0);
		}
		try {
			String indexPath = INDEX_PATH+"/eDoc";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return null;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return null;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴
			
			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();
		
			BooleanQuery query = createQuery(type, keyword,analyzer,false,id,null); //찾을 키워드로 쿼리 생성
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			if(total>0) {
				TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과 상위 5개를 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				row.setTotal(total);
				System.out.println(row.getStart() + " ~ " + row.getLast());
				for(int i = row.getStart()-1; i < row.getLast(); i++)	//읽어온 문서를 페ㅇ징처리
				{
					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					DocumentDTO item = new DocumentDTO();	//담아올 객체 생성
					item.setSeq(Integer.parseInt(doc.get("seq")));
					String title = doc.get("title");
					String con = doc.get("content");
					Pattern SCRIPTS = Pattern.compile("<([^'\"]|\"[^\"]*\"|'[^']*')*?>",Pattern.DOTALL);
					Matcher m = SCRIPTS.matcher(con);
					con = m.replaceAll("").replaceAll("&([^'\\\"]|\\\"[^\\\"]*\\\"|'[^']*')*?;", " ");
					item.setTitle(title);
					int idx = con.indexOf(keyword);
					if((type.trim().equals("content")&&con.length()>30)||(type.trim().equals("title/con")&&con.length()>30)&&idx>-1) {
						if(idx-15>0) {
							con = "ㆍㆍㆍ"+con.substring(idx-15, idx+15)+"ㆍㆍㆍ";
						}else {
							if(idx+30>con.length()) {
								con = con.substring(idx);
							}else {
								con = con.substring(idx, idx+30)+"ㆍㆍㆍ";
							}
						}
					}else if(con.length()>30){
						con = con.substring(0, 30)+"ㆍㆍㆍ";
					}
					item.setContent(con);
					item.setAuthor(doc.get("author"));
					item.setRegdate(doc.get("regdate"));
					item.setState(Integer.parseInt(doc.get("state")));
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
					if(total-1==i) {
						break;
					}
				}
			}
			reader.close();
			analyzer.close();   
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return null;
	}

	@Override
	public List<BbsDTO> freeBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		return bbsSearch("freeBbs", keyword, type,row,auth);
	}

	@Override
	public List<BbsDTO> noticeBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		return bbsSearch("noticeBbs", keyword, type,row,auth);
	}

	@Override
	public List<BbsDTO> fileBbsSearch(String keyword,String type,RowNumDTO row,String auth) {
		return bbsSearch("fileBbs", keyword, type,row,auth);
	}

	public List<BbsDTO> bbsSearch(String kind,String keyword,String type,RowNumDTO row,String auth){
		log.info("SearchDao 게시판 서치 {}",keyword);
		FSDirectory directory;
		List<BbsDTO> list = Lists.newArrayList(); //서치 결과 담을 리스트
		if(row==null) {
			row = new RowNumDTO();
			row.setListNum(5);
			row.setIndex(0);
		}
		try {
			String indexPath = INDEX_PATH+"/"+kind;
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",indexPath);
				return null;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",indexPath);
				return null;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴
			
			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();
			BooleanQuery query = createQuery(type, keyword,analyzer,true,null,auth); //찾을 키워드로 쿼리 생성			
			SortField sortField = null;	// 정렬용 필드
			SortField sortField2 = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_root", SortField.Type.INT, reverse);
			sortField2 = new SortField("sorted_reply_seq", SortField.Type.INT, !reverse);
			Sort sort = new Sort(sortField,sortField2);

			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			row.setTotal(total);
			if(total>0) {
				TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과를 갯수만큼 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				System.out.println(row.getStart() + " ~ " + row.getLast());
				SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"searcher_key\">", "</span>");
				for(int i = row.getStart()-1; i < row.getLast(); i++)	//읽어온 문서들 갯수만큼 반복
				{

					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					BbsDTO item = new BbsDTO();	//담아올 객체 생성
					String title = doc.get("title");
					String con = doc.get("content");
					Pattern SCRIPTS = Pattern.compile("<([^'\"]|\"[^\"]*\"|'[^']*')*?>",Pattern.DOTALL);
					Matcher m = SCRIPTS.matcher(con);
					con = m.replaceAll("").replaceAll("&([^'\\\"]|\\\"[^\\\"]*\\\"|'[^']*')*?;", " ");
					int idx = con.indexOf(keyword);
					if((type.trim().equals("content")&&con.length()>30)||(type.trim().equals("title/con")&&con.length()>30)&&idx>-1) {
						if(idx-15>0) {
							if(idx+15>con.length()) {
								con = "ㆍㆍㆍ"+con.substring(idx-15);
							}else {
								con = "ㆍㆍㆍ"+con.substring(idx-15, idx+15)+"ㆍㆍㆍ";
							}
						}else {
							if(idx+30>con.length()) {
								con = con.substring(idx);
							}else {
								con = con.substring(idx, idx+30)+"ㆍㆍㆍ";
							}
						}
					}else if(con.length()>30){
						con = con.substring(0, 30)+"ㆍㆍㆍ";
					}
					item.setSeq(Integer.parseInt(doc.get("seq")));
					item.setTitle(title);
					item.setContent(con);
					item.setName(doc.get("name"));
					item.setId(doc.get("id"));
					item.setRegdate(doc.get("regdate"));
					item.setState(Integer.parseInt(doc.get("state")));
					item.setRoot(Integer.parseInt(doc.get("root")));
					item.setReply_seq(Integer.parseInt(doc.get("reply_seq")));
					item.setBbs_depth(Integer.parseInt(doc.get("bbs_depth")));
					item.setViews(Integer.parseInt(doc.get("views")));
					int c_count = doc.get("c_count")==null?0:Integer.parseInt(doc.get("c_count"));
					item.setC_count(c_count);
					int f_count = doc.get("f_count")==null?0:Integer.parseInt(doc.get("f_count"));
					item.setF_count(f_count);
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
					if(total-1==i) {
						break;
					}
				}
			}
			reader.close();
			analyzer.close();   
			return list;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return null;
	}
	
	public void indexing(List list, String path, boolean isBbs) {
		//폴더검색 없으면 만듬
		File indexFolder = new File(INDEX_PATH+path);
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}

		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+path));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
			//기존 인덱스 파일 삭제
			writer.deleteAll();
			 
			//게시판과 전자결재문서 구분
			//저장할 데이터 목록 가져오기
			if(isBbs) {
				for(Object obj : list){
					BbsDTO dto = (BbsDTO) obj;
					Document doc = createDoc(null, dto, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
					writer.addDocument(doc);
				}
			}else {
				for(Object obj : list){
					DocumentDTO dto = (DocumentDTO) obj;
					Document doc = createDoc(dto, null, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
					writer.addDocument(doc);
				}
			}

			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addDocIndex(DocumentDTO dto) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/eDoc"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
			//저장할 데이터 목록 가져오기
			Document doc = createDoc(dto, null, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
			
			writer.addDocument(doc);
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void addBbsIndex(BbsDTO dto, String type) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/"+type));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
			//저장할 데이터 목록 가져오기
			Document doc = createDoc(null, dto, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
			
			writer.addDocument(doc);
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}				
	}

	@Override
	public void updateDocIndex(DocumentDTO dto) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/eDoc"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

			Document doc = createDoc(dto, null, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
			
			writer.updateDocument(new Term("seq", String.valueOf(dto.getSeq())), doc);
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	@Override
	public void updateBbsIndex(BbsDTO dto, String type) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/"+type));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

			Document doc = createDoc(null, dto, getFieldType()); // 문서 생성하고 필드명을 지정하여 그에 따른 데이터를 추가
			
			writer.updateDocument(new Term("seq", String.valueOf(dto.getSeq())), doc);
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	private Document createDoc(DocumentDTO docDto, BbsDTO bbsDto,FieldType fieldType) {
		Document doc = new Document();
		if(docDto!=null) {
			doc.add(new NumericDocValuesField("sorted_seq", docDto.getSeq()));
			doc.add(new Field("seq", String.valueOf(docDto.getSeq()), fieldType));
			doc.add(new Field("author", docDto.getAuthor(), fieldType));
			doc.add(new Field("title", docDto.getTitle(), fieldType));
			doc.add(new Field("content", docDto.getContent(), fieldType));
			if(docDto.getRegdate()==null) {
				docDto.setRegdate(new Date().toString());
			}
			doc.add(new Field("regdate", docDto.getRegdate(),fieldType));
			doc.add(new Field("state", String.valueOf(docDto.getState()), fieldType));
		}else if(bbsDto!=null){
			doc.add(new NumericDocValuesField("sorted_root", bbsDto.getRoot()));
			doc.add(new NumericDocValuesField("sorted_reply_seq", bbsDto.getReply_seq()));
			doc.add(new Field("seq", String.valueOf(bbsDto.getSeq()), fieldType));
			doc.add(new Field("id", bbsDto.getId(), fieldType));
			doc.add(new Field("name", bbsDto.getName(), fieldType));
			doc.add(new Field("title", bbsDto.getTitle(), fieldType));
			doc.add(new Field("content", bbsDto.getContent(), fieldType));
			doc.add(new Field("regdate", bbsDto.getRegdate(), fieldType));
			doc.add(new Field("root", String.valueOf(bbsDto.getRoot()), fieldType));
			doc.add(new Field("reply_seq", String.valueOf(bbsDto.getReply_seq()), fieldType));
			doc.add(new Field("bbs_depth", String.valueOf(bbsDto.getBbs_depth()), fieldType));
			doc.add(new Field("views", String.valueOf(bbsDto.getViews()), fieldType));
			doc.add(new Field("state", String.valueOf(bbsDto.getState()), fieldType));
			doc.add(new Field("c_count", String.valueOf(bbsDto.getC_count()), fieldType));
			doc.add(new Field("f_count", String.valueOf(bbsDto.getF_count()), fieldType));
		}
		return doc;
	}
	
	private FieldType getFieldType() {
		FieldType fieldType = new FieldType();
		fieldType.setIndexOptions(org.apache.lucene.index.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);//인덱스 포함 여부
		fieldType.setStored(true);//검색시 출력 여부
		fieldType.setTokenized(true);//필드 토큰화
		return fieldType;
	}
	
	private BooleanQuery createQuery(String category,String keyword,Analyzer analyzer,boolean isBbs,String id,String auth) {
		if(category==null)
			category = "title";
		BooleanQuery query = null;
		try {
			Query qq = null;
			Query state=null;
			Query idquery = null;
			if(isBbs&&auth.trim().equalsIgnoreCase("role_user")) {
				state = new QueryBuilder(analyzer).createBooleanQuery("state", "0");
			}else if(!isBbs){
				
				BytesRef lower = new BytesRef(Integer.toBinaryString(0));
				BytesRef upper = new BytesRef(Integer.toBinaryString(5));
				state = new TermRangeQuery("state", lower, upper, true, false);
				idquery = new QueryBuilder(analyzer).createBooleanQuery("author",id);
			}
			if(category.trim().equals("title")) {
				String[] field = {"title"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("content")) {
				String[] field = {"content"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("author")) {
				String field = null;
				if(isBbs) {
					field = "id";
					}
				else {
					field = "author";
					}
				QueryParser parser = new QueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("title/con")) {
				String[] field = {"title","content"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}
			
			if(isBbs&&auth.trim().equalsIgnoreCase("role_user")) {
				query = new BooleanQuery.Builder().add(qq, Occur.MUST).add(state, Occur.MUST).build();
			}else if(isBbs) {
				query = new BooleanQuery.Builder().add(qq, Occur.MUST).build();
			}else{
				query = new BooleanQuery.Builder().add(qq, Occur.MUST).add(state, Occur.MUST).add(idquery, Occur.MUST).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}

	@Override
	public int eDocTotal(String keyword, String type) {
		FSDirectory directory;
		try {
			String indexPath = INDEX_PATH+"/eDoc";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return 0;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return 0;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createQuery(type, keyword,analyzer,false,null,null); //찾을 키워드로 쿼리 생성
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과 상위 5개를 가져옴
			System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
			System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
			reader.close();
			analyzer.close();   
			return total;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return 0;
	}

	@Override
	public int freeBbsTotal(String keyword, String type,String auth) {
		return bbsTotal(keyword, type, "/freeBbs",auth);
	}

	@Override
	public int fileBbsTotal(String keyword, String type,String auth) {
		return bbsTotal(keyword, type, "/fileBbs",auth);
	}

	@Override
	public int noticeBbsTotal(String keyword, String type,String auth) {
		return bbsTotal(keyword, type, "/noticeBbs",auth);
	}
	
	private int bbsTotal(String keyword, String type,String category,String auth) {
		FSDirectory directory;
		try {
			String indexPath = INDEX_PATH+category;
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return 0;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return 0;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createQuery(type, keyword,analyzer,true,null,auth); //찾을 키워드로 쿼리 생성
			SortField sortField = null;	// 정렬용 필드
			SortField sortField2 = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_root", SortField.Type.INT, reverse);
			sortField2 = new SortField("sorted_reply_seq", SortField.Type.INT, !reverse);
			Sort sort = new Sort(sortField,sortField2);
			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과 상위 5개를 가져옴
			System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
			System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
			reader.close();
			analyzer.close();   
			return total;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return 0;
	}

	@Override
	public void msgIndex(List<MessageDTO> list) {
		File indexFolder = new File(INDEX_PATH+"/Message");
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}

		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/Message"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
			//기존 인덱스 파일 삭제
			writer.deleteAll();
			FieldType fieldType = getFieldType();
			//저장할 데이터 목록 가져오기
			for(MessageDTO dto : list){
				Document doc = new Document();
				doc.add(new NumericDocValuesField("sorted_seq", dto.getSeq()));
				doc.add(new Field("seq", String.valueOf(dto.getSeq()), fieldType));
				doc.add(new Field("sender_id", dto.getSender_id(), fieldType));
				doc.add(new Field("receiver_id", dto.getReceiver_id(), fieldType));
				doc.add(new Field("title", dto.getTitle(), fieldType));
				doc.add(new Field("content", dto.getContent(), fieldType));
				doc.add(new Field("regdate", dto.getRegdate(),fieldType));
				doc.add(new Field("state", String.valueOf(dto.getState()), fieldType));
				writer.addDocument(doc);
			}
			
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void addMsgIndex(MessageDTO dto) {
		File indexFolder = new File(INDEX_PATH+"/Message");
		if(indexFolder.isDirectory() == false){
			indexFolder.mkdirs();
		}

		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/Message"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();

			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

			FieldType fieldType = getFieldType();
			//저장할 데이터 목록 가져오기
			Document doc = new Document();
			doc.add(new NumericDocValuesField("sorted_seq", dto.getSeq()));
			doc.add(new Field("seq", String.valueOf(dto.getSeq()), fieldType));
			doc.add(new Field("sender_id", dto.getSender_id(), fieldType));
			doc.add(new Field("receiver_id", dto.getReceiver_id(), fieldType));
			doc.add(new Field("title", dto.getTitle(), fieldType));
			doc.add(new Field("content", dto.getContent(), fieldType));
			doc.add(new Field("regdate", dto.getRegdate(),fieldType));
			doc.add(new Field("state", String.valueOf(dto.getState()), fieldType));
			writer.addDocument(doc);
			
			writer.commit();
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<MessageDTO> receiveMsgSearch(String keyword, RowNumDTO row,String type,String id) {
		FSDirectory directory;
		if(row==null) {
			row = new RowNumDTO();
			row.setListNum(5);
			row.setIndex(0);
		}
		List<MessageDTO> list = Lists.newArrayList();
		try {
			String indexPath = INDEX_PATH+"/Message";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return null;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return null;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createMsgQuery(type, keyword,analyzer,true,id); //찾을 키워드로 쿼리 생성
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			log.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%% {}",total);
			if(total>0) {
				TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과 상위 5개를 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				row.setTotal(total);
				System.out.println(row.getStart() + " ~ " + row.getLast());
				for(int i = row.getStart()-1; i < row.getLast(); i++)	//읽어온 문서를 페ㅇ징처리
				{
					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					MessageDTO item = new MessageDTO();	//담아올 객체 생성
					item.setSeq(Integer.parseInt(doc.get("seq")));
					item.setTitle(doc.get("title"));
					item.setContent(doc.get("content"));
					item.setReceiver_id(doc.get("receiver_id"));
					item.setSender_id(doc.get("sender_id"));
					item.setRegdate(doc.get("regdate"));
					item.setState(Integer.parseInt(doc.get("state")));
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
					if(total-1==i) {
						break;
					}
				}
			}
			reader.close();
			analyzer.close();   
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return null;
	}

	@Override
	public List<MessageDTO> senderMsgSearch(String keyword, RowNumDTO row,String type,String id) {
		FSDirectory directory;
		if(row==null) {
			row = new RowNumDTO();
			row.setListNum(5);
			row.setIndex(0);
		}
		List<MessageDTO> list = Lists.newArrayList();
		try {
			String indexPath = INDEX_PATH+"/Message";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return null;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return null;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createMsgQuery(type, keyword,analyzer,false,id); //찾을 키워드로 쿼리 생성
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			Document doc = new Document();	//찾아온 결과를 읽어올 문서
			int total = searcher.count(query);
			if(total>0) {
				TopFieldDocs results = searcher.search(query,total,sort);	//해당하는 결과 상위 5개를 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				row.setTotal(total);
				System.out.println(row.getStart() + " ~ " + row.getLast());
				for(int i = row.getStart()-1; i < row.getLast(); i++)	//읽어온 문서를 페ㅇ징처리
				{
					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					MessageDTO item = new MessageDTO();	//담아올 객체 생성
					item.setSeq(Integer.parseInt(doc.get("seq")));
					item.setTitle(doc.get("title"));
					item.setContent(doc.get("content"));
					item.setReceiver_id(doc.get("receiver_id"));
					item.setSender_id(doc.get("sender_id"));
					item.setRegdate(doc.get("regdate"));
					item.setState(Integer.parseInt(doc.get("state")));
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
					if(total-1==i) {
						break;
					}
				}
			}
			reader.close();
			analyzer.close();   
			return list;
			
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return null;
	}

	@Override
	public int receiveMsgTotal(String keyword,String type,String id) {
		FSDirectory directory;
		try {
			String indexPath = INDEX_PATH+"/Message";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return 0;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return 0;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createMsgQuery(type, keyword,analyzer,true,id); //찾을 키워드로 쿼리 생성
			int total = searcher.count(query);
			reader.close();
			analyzer.close();   
			return total;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return 0;
	}

	@Override
	public int senderMsgTotal(String keyword,String type,String id) {
		FSDirectory directory;
		try {
			String indexPath = INDEX_PATH+"/Message";
			File indexFolder = new File(indexPath);
			if(indexFolder.isDirectory() == false){ //폴더가 있는지 탐색해서 없을경우
				log.info("Lucene Search : NOT FOUND FOLDER {}",INDEX_PATH);
				return 0;
			}
			System.out.println(indexFolder.list().length); 
			if(indexFolder.list().length == 0){ //폴더 내 파일이 없을경우
				log.info("Lucene Search : NOT FOUND FILE IN {}",INDEX_PATH);
				return 0;
			}
			directory = FSDirectory.open(Paths.get(indexPath)); //경로에 있는 폴더를 연다
			IndexReader reader = DirectoryReader.open(directory); //디렉터리 안 파일들을 읽어옴

			IndexSearcher searcher = new IndexSearcher(reader);	//읽어온 파일들을 서칭할 서쳐
			Analyzer analyzer = new StandardAnalyzer();

			BooleanQuery query = createMsgQuery(type, keyword,analyzer,false,id); //찾을 키워드로 쿼리 생성
			int total = searcher.count(query);
			reader.close();
			analyzer.close();   
			return total;
		}catch (Exception e) {
			e.printStackTrace();
			log.error("전자문서 검색중에 에러 발생");
		}
		return 0;
	}
	
	private BooleanQuery createMsgQuery(String category,String keyword,Analyzer analyzer,boolean isReceiveList,String id) {
		if(category==null)
			category = "title";
		BooleanQuery query = null;
		try {
			Query qq = null;
			Query state=null;
			Query idquery = null;
			if(isReceiveList) {
				BytesRef lower = new BytesRef(Integer.toBinaryString(0));
				BytesRef upper = new BytesRef(Integer.toBinaryString(1));
				state = new TermRangeQuery("state", lower, upper, true, true);
				idquery = new QueryParser("receiver_id",analyzer).parse(id);
			}else {
				idquery = new QueryParser("sender_id",analyzer).parse(id);
			}
			
			if(category.trim().equals("title")) {
				String[] field = {"title"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("content")) {
				String[] field = {"content"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("author")) {
				String field = null;
				if(isReceiveList) {
					field = "sender_id";
				}else {
					field = "receiver_id";
				}
				QueryParser parser = new QueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}else if(category.trim().equals("title/con")) {
				String[] field = {"title","content"};
				MultiFieldQueryParser parser = new MultiFieldQueryParser(field, analyzer);
				qq = parser.parse(keyword+"*");
			}
			
			if(isReceiveList) {
				query = new BooleanQuery.Builder().add(qq, Occur.MUST).add(idquery, Occur.MUST).add(state, Occur.MUST).build();
			}else {
				query = new BooleanQuery.Builder().add(qq, Occur.MUST).add(idquery, Occur.MUST).build();
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return query;
	}

	@Override
	public void updateMsgIndex(MessageDTO dto) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/Message"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);

			FieldType fieldType = getFieldType();
			//저장할 데이터 목록 가져오기
			Document doc = new Document();
			doc.add(new NumericDocValuesField("sorted_seq", dto.getSeq()));
			doc.add(new Field("seq", String.valueOf(dto.getSeq()), fieldType));
			doc.add(new Field("sender_id", dto.getSender_id(), fieldType));
			doc.add(new Field("receiver_id", dto.getReceiver_id(), fieldType));
			doc.add(new Field("title", dto.getTitle(), fieldType));
			doc.add(new Field("content", dto.getContent(), fieldType));
			doc.add(new Field("regdate", dto.getRegdate(),fieldType));
			doc.add(new Field("state", String.valueOf(dto.getState()), fieldType));
			writer.updateDocument(new Term("seq",String.valueOf(dto.getSeq())), doc);
			writer.close();
			analyzer.close();
			directory.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
