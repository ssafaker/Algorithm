--
--  문제명 : [3월에 태어난 여성 회원 목록 출력하기]
--  링크   : [https://school.programmers.co.kr/learn/courses/30/lessons/131120]
--  난이도 : [Level 2]
--  설명   :
--    - MEMBER_PROFILE 테이블에서 3월에 태어난 여성 회원의 ID, 이름, 성별, 생년월일(yyyy-mm-dd)을 조회
--    - 전화번호(TLNO)가 NULL이 아닌 회원만 포함
--    - MEMBER_ID 오름차순 정렬
--  풀이   :
--    - WHERE절에서 MONTH(DATE_OF_BIRTH)=3, GENDER='W', TLNO IS NOT NULL 조건 사용
--    - LEFT(DATE_OF_BIRTH, 10)로 yyyy-mm-dd 형식 추출
--    - ORDER BY MEMBER_ID
--
SELECT MEMBER_ID, MEMBER_NAME, GENDER, LEFT(DATE_OF_BIRTH, 10) as DATE_OF_BIRTH
FROM MEMBER_PROFILE 
WHERE MONTH(DATE_OF_BIRTH) = 3 AND GENDER = 'W' AND TLNO is not NULL
ORDER BY 1;