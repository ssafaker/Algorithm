/*
 *  문제명 : 서울에 위치한 식당 목록 출력하기
 *  링크   : https://school.programmers.co.kr/learn/courses/30/lessons/131118
 *  난이도 : Level 4
 *  설명   :
 *    - REST_INFO, REST_REVIEW 테이블에서 서울에 위치한 식당의 ID, 이름, 음식 종류, 즐겨찾기수, 주소, 리뷰 평균점수 조회
 *    - 리뷰 평균점수는 소수점 3번째 자리에서 반올림, 평균점수 내림차순, 동점시 즐겨찾기수 내림차순 정렬
 *  풀이   :
 *    - WHERE절에서 ADDRESS가 '서울'로 시작하는 식당만 필터링
 *    - JOIN으로 리뷰 테이블과 결합, GROUP BY로 식당별 집계
 *    - ROUND(AVG(REVIEW_SCORE), 2)로 평균점수 계산
 *    - ORDER BY SCORE DESC, 즐겨찾기수 DESC
 */

SELECT a.REST_ID 
    , a.REST_NAME
    , a.FOOD_TYPE
    , a.FAVORITES
    , a.ADDRESS
    , ROUND(AVG(b.REVIEW_SCORE), 2) AS SCORE
FROM REST_INFO AS a 
JOIN REST_REVIEW AS b ON a.REST_ID = b.REST_ID
WHERE a.ADDRESS like '서울%'
GROUP BY REST_ID
ORDER BY SCORE DESC