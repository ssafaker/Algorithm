package iforcu;

import java.util.*;
import java.io.*;

/*
 * 문제명: [보호 필름]
 * 링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5V1SYKAaUDFAWu]
 * 난이도 : [모의 SW 역량테스트]
 * 설명: 보호 필름 두께에 대한 완전 탐색, 순열을 통해 최소한의 약품 투입 횟수를 구하는 문제
 * 풀이: 
 * - 순열과 백트래킹을 이용해 DFS 완전 탐색 실행
 * - 각 층에 대해 3가지 상태 (0, 1, 원본)으로 나누어 탐색함.
 */

public class Main_SEA_2112 {
    static StringTokenizer st;
    static int D, W, K;
    static int[][] film;
    static int[][] originalFilm;
    static int leastCount;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
         
        for(int test_case = 1; test_case <= T; test_case++)
        {
            st = new StringTokenizer(br.readLine());
            D = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            
            // 원본 필름값과 완전 탐색을 위한 필름 배열
            film = new int[D][W];
            originalFilm = new int[D][W];
            for (int i = 0; i < D; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    film[i][j] = Integer.parseInt(st.nextToken());
                    originalFilm[i][j] = film[i][j];
                }
            }

            // 최소 횟수 초기화
            if (checkPerformance()) {
                leastCount = 0;
            } else {
                leastCount = Integer.MAX_VALUE;
                solve(0, 0);
            }
            System.out.println("#"+test_case+" "+leastCount);
        }
    }
 
    static void solve(int depth, int cnt) {
        // 횟수가 최소값을 넘어가면 중단
        if(cnt >= leastCount) {
            return;
        }
        
        // 모든 층을 탐색하여 할당 시 count을 비교
        if(depth == D) {
            if(checkPerformance()) leastCount = Math.min(leastCount, cnt);
            return;
        }
        
        //1. 원본 상태로 유지
        solve(depth + 1, cnt);
        
        //2. A약품 투입 - 원본 temp 저장
        int[] temp = Arrays.copyOf(film[depth], W);
        Arrays.fill(film[depth], 0);
        solve(depth + 1, cnt + 1);
        film[depth] = temp;
        
        //3 . B약품 투입 - 원본 temp 저장
        temp = Arrays.copyOf(film[depth], W);
        Arrays.fill(film[depth], 1);
        solve(depth + 1, cnt + 1);
        film[depth] = temp;
    }
 
    public static boolean checkPerformance() {
        if (K == 1) return true;
 
        for (int j = 0; j < W; j++) {
            boolean isPass = false; // 각 열에 대한 검사
            int count = 1;
            
            for (int i = 1; i < D; i++) {
                // 만약 현재 층 값이 이전 층과 같다면 count 증가 아니면 1로 유지
                if (film[i][j] == film[i - 1][j]) count++;
                else count = 1;
                // 만약 count가 K 이상이면 해당 열은 통과
                if (count >= K) {
                    isPass = true;
                    break;
                }
            }
 
            if (!isPass) return false;
        }
        return true;
    }
}
