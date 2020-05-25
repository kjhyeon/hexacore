--완료1.문서 수정	DB에서 해당 문서 조회 쿼리 작성 및 테스트
	--1-1.해당 문서 조회
	SELECT SEQ, AUTHOR, TITLE, CONTENT, TYPE_SEQ, STATE, REGDATE, APPR_TURN 
		FROM DOCUMENT
			WHERE SEQ='화면SEQ';
	/*--1-2 문서 수정
	UPDATE DOCUMENT
		SET TITLE = '개발팀_김정현_휴가신청서' , CONTENT ='휴가신청합니다'
			WHERE SEQ=1;
	--1-3 결재 루트 업데이트 -->
	UPDATE APPROVAL
		SET ID = '입력 아이디' , TURN = '입력순서'
			WHERE SEQ=1
			*/
	UPDATE (SELECT d.TITLE AS TITLE, d.CONTENT AS CONTENT, a.ID AS ID, a.TURN AS TURN FROM DOCUMENT d , APPROVAL a WHERE d.SEQ =1 AND d.SEQ = a.SEQ)
	SET TITLE = '화면', CONTENT ='화면', ID = '화면', TURN = 1;
----------------------------------------------------------------------------
--2.문서 상신 취소	결재 루트 삭제 하는 쿼리 작성 및 테스트
	--2-1.해당 문서 조회
	SELECT SEQ, AUTHOR, TITLE, CONTENT, TYPE_SEQ, STATE, REGDATE, APPR_TURN 
		FROM DOCUMENT
			WHERE SEQ=1;
	--2-2.해당문서 결재중인지 체크
	SELECT ID , CHK , TURN
		FROM DOCUMENT d , APPROVAL a
			WHERE d.SEQ = a.SEQ
		ORDER BY TURN;
	--2-3.해당 문서 상신 취소
	UPDATE DOCUMENT
		SET STATE =1 --반려
			WHERE SEQ = 0;
	-- 결재루트 지우는쿼리 
	DELETE FROM APPROVAL
		WHERE SEQ = '';
--완료3.파일 업로드		DB에 첨부파일 저장 하는 쿼리 작성 및 테스트
	INSERT INTO FILES (NAME, SEQ, F_PATH, ORI_NAME, F_SIZE, CATEGORY)
		VALUES('화면에서 받은이름','화면에서 받은문서 SEQ';, '전달받을 파일 위치UUID','원본파일 이름',1111,0);
	
--완료4.업로드한 파일 삭제		DB에 저장되어있는 첨부파일 영구 삭제 하는 쿼리 작성 및 테스트
	DELETE FROM FILES
		WHERE SEQ= '화면에서 받은 SEQ';;
	
--5.문서 승인	결재자 승인시 다음 결재자에게 결재 권한이 주어지는 쿼리 작성 및 테스트(최종 결재자가 승인 후 결재완료 처리)
	UPDATE APPROVAL
		SET CHK ='T'
			WHERE SEQ ='화면에서 받은 SEQ'; AND ID = '세션 아이디';
	--5-1	 문서함 TURN 업데이트
	UPDATE DOCUMENT
		SET APPR_TURN = (SELECT MAX(APPR_TURN) FROM DOCUMENT)+1
			WHERE SEQ ='화면에서 받은 SEQ';
	--5-2 	문서함 STATE 업데이트 (결재중)
	UPDATE DOCUMENT
		SET STATE =2
			WHERE SEQ = '화면에서 받은 SEQ';
	--5-3	문서 최종 승인권자 승인 시 문서함 STATE 업데이트
	UPDATE DOCUMENT
		SET STATE =3 --승인
			WHERE SEQ ='화면에서 받은 SEQ';
		
--6.문서 반려	결재자가 반려하면 문서의 상태가 변경되는 쿼리 작성 및 테스트
	UPDATE DOCUMENT
		SET STATE =1 --반려
			WHERE SEQ = '화면에서 받은 SEQ';
--완료7.문서 양식 추가	관리자가 문서양식을 추가할 수 있는 쿼리 작성 및 테스트
INSERT INTO DOCUMENT_TYPE (TYPE_SEQ , NAME, CONTENT, CATEGORY)
	VALUES((SELECT MAX(TYPE_SEQ) FROM DOCUMENT_TYPE)+1, '화면에서 받은 제목', '테스트화면에서 만든양식', '화면에서 받은 카테고리 번호');

--완료8. 결재자 루트 상태 체크
SELECT ID , CHK , TURN
	FROM DOCUMENT d , APPROVAL a
		WHERE d.SEQ = a.SEQ AND d.seq=2
	ORDER BY TURN;
