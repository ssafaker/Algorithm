package iforcu;

import java.util.*;

/*
 *  문제명 : [K번째수]
 *  링크   : [https://school.programmers.co.kr/learn/courses/30/lessons/42748]
 *  난이도 : [Level 1]
 *  설명   :
 *    - 주어진 배열에서 (i, j, k) 쿼리마다 부분 배열 array[i-1..j-1]을 잘라 정렬 후 k번째 수를 구해 결과 배열로 반환
 *    - commands 길이만큼 결과를 생성
 *  풀이   :
 *    - 각 명령에 대해 시작/끝 인덱스를 0-based로 변환 후 부분 배열을 복사
 *    - 부분 배열 정렬 후 k번째 원소 선택
 *    - 시간복잡도: 각 쿼리당 O(L log L) (L = j-i+1), 전체는 Σ O(L log L)
 */

public class Main_PGS_K번째수 {
    // commands 배열의 각 (i, j, k)에 대해 규칙에 따른 k번째 수를 구해 answer에 저장
    public int[] solution(int[] array, int[][] commands) {
        int[] answer = new int[commands.length];
        for(int i = 0; i < commands.length ; i++) {
            // 1-based 인덱스를 0-based로 변환
            int start = commands[i][0] - 1;
            int end = commands[i][1] - 1;
            int idx = commands[i][2] - 1;
            
            // 부분 배열 추출 및 정렬
            int[] arr = cut(array, start, end);
            Arrays.sort(arr);
            
            // 정렬된 부분 배열에서 k번째(0-based idx) 값 저장
            answer[i] = arr[idx];
        }
        return answer;
    }
    
    // array[start..end] 구간을 복사하여 새 배열 반환
    public int[] cut(int[] array, int start, int end) {
        int  n = end - start + 1;
        int[] arr = new int[n];
        for(int i = 0; i < n ; i++) {
            arr[i] = array[start + i];
        }
        return arr;
    }
}
