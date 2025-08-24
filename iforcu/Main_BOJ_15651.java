package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [N과 M (3)]
 *  링크 : [https://www.acmicpc.net/problem/15651]
 *  난이도 : [실버 3]
 *  설명:
 *  - 1부터 N까지 자연수 중에서 M개를 고른 수열을 모두 구하는 문제.
 *  - 같은 수를 여러 번 선택 가능 (중복 허용).
 *  - 고른 수열은 사전 순으로 출력해야 함.
 *  풀이:
 *  - DFS(백트래킹)으로 길이가 M인 수열을 구성.
 *  - depth(현재 위치)가 M이 되면 수열 완성 → 출력 버퍼에 저장.
 *  - 중복 선택이 가능하므로 단순히 1 ~ N까지 반복문으로 모든 경우 탐색.
 *  - 출력이 많으므로 StringBuilder를 활용하여 성능 개선.
 */

public class Main_BOJ_15651 {
    static int N, M;
    static int[] select;              // 현재 선택한 수열 저장
    static StringBuilder sb = new StringBuilder(); // 출력 버퍼

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        select = new int[M];
        dfs(0); // 깊이 우선 탐색 시작
        System.out.println(sb);
    }

    // DFS로 길이 M의 수열 생성
    static void dfs(int depth) {
        // M개를 모두 선택했을 경우 → 출력 버퍼에 추가
        if(depth == M) {
            for (int i : select) sb.append(i).append(" ");
            sb.append("\n");
            return;
        }
        
        // 1 ~ N까지 모든 수를 선택 가능 (중복 허용)
        for (int i = 1; i <= N; i++) {
            select[depth] = i;    // 현재 위치에 숫자 선택
            dfs(depth + 1);       // 다음 위치로 이동
        }
    }
}
