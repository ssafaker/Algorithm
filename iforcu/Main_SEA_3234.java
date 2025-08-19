package iforcu;

import java.util.*;
import java.io.*;

/*
 * 문제명: [준환이의 양팔저울]
 * 링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWAe7XSKfUUDFAUw]
 * 난이도 : [D4]
 * 설명: 양팔저울에 올라갈 완전 탐색 문제
 * 풀이: DFS 백트래킹 + 조합을 이용하여 모든 경우의 수 탐색
 */

public class Main_SEA_3234 {
    static int N, count;
    static int[] weights;
    static boolean[] visited;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());
         
        for(int test_case = 1; test_case <= T; test_case++)
        {
            N = Integer.parseInt(br.readLine().trim());
            weights = new int[N];
            visited = new boolean[N];
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                weights[i] = Integer.parseInt(st.nextToken());
            }
 
            count = 0;
            permute(0,0,0);
             
            System.out.println("#"+test_case+" "+count);
        }
    }
 
    static void permute(int idx, int left, int right) {
        // idx : 현재 선택된 물건의 인덱스
        // left : 왼쪽 저울의 무게
        // right : 오른쪽 저울의 무게

        // right가 left보다 크면 탐색 중지
        if (right > left) return;

        // 모든 물건을 선택하면 count 증가
        if (idx == N) {
            count++;
            return;
        }
 
        // idx에 따른 물건 선택에 대한 탐색
        for (int i = 0; i < N; i++) {
            if (!visited[i]) {
                visited[i] = true;
                //1. 왼쪽 저울에 모든 물건을 올리는 경우부터 시작
                permute(idx + 1, left + weights[i], right);
                //2. 오른쪽 저울에 모든 물건을 올리는 경우로 끝남
                permute(idx + 1, left, right + weights[i]);
                visited[i] = false;
            }
        }
    }
}
