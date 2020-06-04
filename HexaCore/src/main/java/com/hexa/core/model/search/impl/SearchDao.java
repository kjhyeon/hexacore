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
import org.apache.lucene.document.NumericDocValuesField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.xml.builders.BooleanQueryBuilder;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.FuzzyQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;
import org.apache.lucene.search.SortField;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopFieldDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.hexa.core.dto.BbsDTO;
import com.hexa.core.dto.DocumentDTO;
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
	
	private final String INDEX_PATH = "C:/eclipse-spring/lucene"; //인덱싱 파일이 저장될 공간 지정 나중에 바꿀거임
	
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
	public List<DocumentDTO> eDocSearch(String keyword,String type) {
		log.info("SearchDao 전자문서 서치 {}",keyword);
		FSDirectory directory;
		List<DocumentDTO> list = Lists.newArrayList(); //서치 결과 담을 리스트
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
			
			MultiFieldQueryParser parser= null;
			if(type.trim().equals("title")) {
				String[] field = {"title"};
				parser = new MultiFieldQueryParser(field, analyzer);
			}else if(type.trim().equals("content")) {
				String[] field = {"content"};
				parser = new MultiFieldQueryParser(field, analyzer);
			}else if(type.trim().equals("author")) {
				String[] field = {"author"};
				parser = new MultiFieldQueryParser(field, analyzer);
			}else if(type.trim().equals("title/con")) {
				String[] field = {"title","content"};
				System.out.println("title/con");
				parser = new MultiFieldQueryParser(field, analyzer);
			}
			
			Query query = parser.parse(keyword+"*");	//찾을 키워드
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			if(true)
			{
				Document doc = new Document();	//찾아온 결과를 읽어올 문서
				TopFieldDocs results = searcher.search(query,10,sort);	//해당하는 결과 상위 5개를 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				
//				SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"searcher_key\">", "</span>");
				for(int i = 0; i < results.scoreDocs.length; i++)	//읽어온 문서들 갯수만큼 반복
				{
					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					DocumentDTO item = new DocumentDTO();	//담아올 객체 생성
					item.setSeq(Integer.parseInt(doc.get("seq")));
					item.setTitle(doc.get("title"));
					item.setContent(doc.get("content"));
					item.setAuthor(doc.get("name"));
					item.setRegdate(doc.get("regdate"));
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
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
	public List<BbsDTO> freeBbsSearch(String keyword,String type) {
		return bbsSearch("freeBbs", keyword, type);
	}

	@Override
	public List<BbsDTO> noticeBbsSearch(String keyword,String type) {
		return bbsSearch("noticeBbs", keyword, type);
	}

	@Override
	public List<BbsDTO> fileBbsSearch(String keyword,String type) {
		return bbsSearch("fileBbs", keyword, type);
	}

	public List<BbsDTO> bbsSearch(String kind,String keyword,String type){
		log.info("SearchDao 게시판 서치 {}",keyword);
		FSDirectory directory;
		List<BbsDTO> list = Lists.newArrayList(); //서치 결과 담을 리스트
		try {
			String indexPath = INDEX_PATH+kind;
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
//			QueryParser parser = null;
			MultiFieldQueryParser parser= null;
			if(type.trim().equals("title")) {
				String[] field = {"title"};
				parser = new MultiFieldQueryParser(field, analyzer);
//				parser = new QueryParser("title", analyzer); //찾을 컬럼
			}else if(type.trim().equals("content")) {
				String[] field = {"content"};
				parser = new MultiFieldQueryParser(field, analyzer);
//				parser = new QueryParser("content", analyzer); //찾을 컬럼
			}else if(type.trim().equals("author")) {
				String[] field = {"name"};
				parser = new MultiFieldQueryParser(field, analyzer);
//				parser = new QueryParser("author", analyzer); //찾을 컬럼
			}else if(type.trim().equals("title/con")) {
				String[] field = {"title","content"};
				System.out.println("title/con");
				parser = new MultiFieldQueryParser(field, analyzer);
//				parser = new QueryParser("title", analyzer); //찾을 컬럼
			}
			
			Query query = parser.parse(keyword+"*");	//찾을 키워드
			
			SortField sortField = null;	// 정렬용 필드
			boolean reverse = true;	//역정렬용 플래그
			sortField = new SortField("sorted_seq", SortField.Type.INT, reverse);
			Sort sort = new Sort(sortField);
			if(true)
			{
				Document doc = new Document();	//찾아온 결과를 읽어올 문서
				TopFieldDocs results = searcher.search(query,6,sort);	//해당하는 결과 상위 5개를 가져옴
				System.out.println("hits : "+results.totalHits); //검색어랑 맞는 갯수
				System.out.println("docLength : "+results.scoreDocs.length); // 검색된 갯수
				
//				SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<span class=\"searcher_key\">", "</span>");
				for(int i = 0; i < results.scoreDocs.length; i++)	//읽어온 문서들 갯수만큼 반복
				{
					doc = searcher.doc(results.scoreDocs[i].doc);	//찾은 결과 1행을 담읆 문서
					BbsDTO item = new BbsDTO();	//담아올 객체 생성
					item.setSeq(Integer.parseInt(doc.get("seq")));
					item.setTitle(doc.get("title"));
					item.setContent(doc.get("content"));
					item.setName(doc.get("name"));
					item.setId(doc.get("id"));
					item.setRegdate(doc.get("regdate"));
					System.out.println(item.toString());
					list.add(item);	//결과로 보낼 리스트에 담음
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
			IndexWriter writer = getWriter(true, directory, analyzer);
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
			IndexWriter writer = getWriter(false, directory, analyzer);

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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateDocIndex(DocumentDTO dto) {
		FSDirectory directory;
		try {
			//인덱싱된 파일을 내보낼 경로를 얻음
			directory = FSDirectory.open(Paths.get(INDEX_PATH+"/eDoc"));

			//한글 형태소 분석기
			Analyzer analyzer = new StandardAnalyzer();
			IndexWriter writer = getWriter(false, directory,analyzer);

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
		// TODO Auto-generated method stub
		
	}

	private Document createDoc(DocumentDTO docDto, BbsDTO bbsDto,FieldType fieldType) {
		Document doc = new Document();
		if(docDto!=null) {
			doc.add(new NumericDocValuesField("sorted_seq", docDto.getSeq()));
			doc.add(new Field("seq", String.valueOf(docDto.getSeq()), fieldType));
			doc.add(new Field("author", docDto.getAuthor(), fieldType));
			doc.add(new Field("title", docDto.getTitle(), fieldType));
			doc.add(new Field("content", docDto.getContent(), fieldType));
			doc.add(new Field("regdate", docDto.getRegdate(), fieldType));
			doc.add(new Field("state", String.valueOf(docDto.getState()), fieldType));
		}else if(bbsDto!=null){
			doc.add(new NumericDocValuesField("sorted_seq", bbsDto.getSeq()));
			doc.add(new Field("seq", String.valueOf(bbsDto.getSeq()), fieldType));
			doc.add(new Field("id", bbsDto.getId(), fieldType));
			doc.add(new Field("name", bbsDto.getName(), fieldType));
			doc.add(new Field("title", bbsDto.getTitle(), fieldType));
			doc.add(new Field("content", bbsDto.getContent(), fieldType));
			doc.add(new Field("regdate", bbsDto.getRegdate(), fieldType));
			doc.add(new Field("state", String.valueOf(bbsDto.getState()), fieldType));
		}
		return null;
	}
	
	private FieldType getFieldType() {
		FieldType fieldType = new FieldType();
		fieldType.setIndexOptions(org.apache.lucene.index.IndexOptions.DOCS_AND_FREQS_AND_POSITIONS);//인덱스 포함 여부
		fieldType.setStored(true);//검색시 출력 여부
		fieldType.setTokenized(true);//필드 토큰화
		return fieldType;
	}
	
	private IndexWriter getWriter(boolean isCreate, Directory directory, Analyzer analyzer) {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(analyzer);
		if(isCreate) {
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
		}else {
			indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
		}
		try {
			IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
			return writer;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
