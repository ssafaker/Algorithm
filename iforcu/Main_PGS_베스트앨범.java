package iforcu;

import java.util.*;

/*
 * 프로그래머스 - 베스트앨범
 * https://programmers.co.kr/learn/courses/30/lessons/42579
 * 난이도: Level 3
 * 
 * 문제: 각 장르별로 가장 많이 재생된 노래 최대 2개씩을 선정하여 베스트앨범 수록곡을 정하는 문제
 * 
 * 수록 기준:
 * 1. 속한 노래가 많이 재생된 장르를 먼저 수록
 * 2. 장르 내에서 많이 재생된 노래를 먼저 수록
 * 3. 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록
 * 
 * 해결방법:
 * 1. 장르별 총 재생횟수를 계산하여 장르 순서 결정
 * 2. 각 장르 내의 노래들을 재생횟수 내림차순, 고유번호 오름차순으로 정렬
 * 3. 각 장르에서 최대 2곡씩 선정
 */
public class Main_PGS_베스트앨범 {
    // 노래 정보를 담는 클래스 (고유번호, 재생횟수)
    static class Album implements Comparable<Album> {
        int num;    // 고유번호
        int plays;  // 재생횟수
        
        public Album(int num, int plays) {
            this.num = num;
            this.plays = plays;
        }
        
        // 재생횟수 내림차순, 재생횟수가 같으면 고유번호 오름차순
        public int compareTo(Album o) {
            if(this.plays != o.plays) return o.plays - this.plays;
            else return this.num - o.num;
        }
    }
    
    public int[] solution(String[] genres, int[] plays) {
        // 장르별 노래 리스트를 저장하는 맵
        HashMap<String, List<Album>> map = new HashMap<>();
        
        // 장르별 총 재생횟수를 저장하는 맵
        HashMap<String, Integer> genrePlaySum = new HashMap<>();
        
        // 각 노래를 장르별로 분류하고 장르별 총 재생횟수 계산
        for(int i = 0; i < genres.length; i++) {
            Album album = new Album(i, plays[i]);
            map.computeIfAbsent(genres[i], k -> new ArrayList<>()).add(album);
            genrePlaySum.put(genres[i], genrePlaySum.getOrDefault(genres[i], 0) + plays[i]);
        }
        
        // 각 장르 내에서 노래들을 재생횟수 내림차순, 고유번호 오름차순으로 정렬
        for(List<Album> list : map.values()) {
            Collections.sort(list);
        }
        // 장르들을 총 재생횟수 내림차순으로 정렬
        List<String> genreOrder = new ArrayList<>(genrePlaySum.keySet());
        genreOrder.sort((g1, g2) -> genrePlaySum.get(g2) - genrePlaySum.get(g1));
        
        List<Integer> resultList = new ArrayList<>();
        
        // 각 장르에서 최대 2곡씩 선정
        for (String g : genreOrder) {
            List<Album> list = map.get(g);
            // 첫 번째 곡 (가장 많이 재생된 곡)
            if (list.size() > 0) {
                resultList.add(list.get(0).num);
            }
            // 두 번째 곡 (두 번째로 많이 재생된 곡)
            if (list.size() > 1) {
                resultList.add(list.get(1).num);
            }
        }
        
        // 리스트를 배열로 변환하여 반환
        int[] answer = new int[resultList.size()];
        for (int i = 0; i < resultList.size(); i++) {
            answer[i] = resultList.get(i);
        }

        return answer;
    }
}