/*
 *  문제명 : 강원도에 위치한 생산공장 목록 출력하기
 *  링크   : https://school.programmers.co.kr/learn/courses/30/lessons/131112
 *  난이도 : Level 2
 *  설명   :
 *    - FOOD_FACTORY 테이블에서 강원도에 위치한 식품공장의 공장 ID, 공장 이름, 주소를 조회
 *    - 결과는 공장 ID 기준 오름차순 정렬
 *    - ADDRESS 컬럼의 앞 3글자가 '강원도'인지 확인
 *  풀이   :
 *    - WHERE절에서 LEFT(ADDRESS, 3) = '강원도' 조건 사용
 *    - SELECT로 공장 ID, 이름, 주소 조회
 *    - ORDER BY 1로 공장 ID 오름차순 정렬
 */

SELECT FACTORY_ID, FACTORY_NAME, ADDRESS
FROM FOOD_FACTORY
WHERE LEFT(ADDRESS,3) = '강원도'
ORDER BY 1;