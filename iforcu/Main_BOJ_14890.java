package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [경사로]
 * 난이도: [Gold 3]
 * 링크: [https://www.acmicpc.net/problem/14890]
 * 설명:
 *  N×N 지도에서 가로와 세로 방향으로 길을 만들 수 있는지 판별하는 문제.
 *  높이가 1 차이날 때 경사로(L 길이)를 설치할 수 있으며,
 *  경사로가 겹치거나 범위를 벗어나면 안 됨.
 * 풀이:
 *  - 각 행/열을 1차원 배열로 가져와 check 함수로 판별.
 *  - 인접한 칸의 높이 차이가 0이면 통과.
 *  - 높이 차이가 1일 경우:
 *    - 오르막: 이전 칸에 L만큼 경사로 설치 가능한지 확인.
 *    - 내리막: 이후 칸에 L만큼 경사로 설치 가능한지 확인.
 *  - 설치된 경사로는 visited 배열로 체크.
 *  - 모든 행/열 검사 후 가능한 길의 수를 출력.
 */
public class Main_BOJ_14890 {
    static int N, L, ans;
    static int[][] map;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        map = new int[N][N];
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        ans = 0;
        for (int i = 0; i < N; i++) {
            if (check(map[i])) ans++;
        }

        for (int j = 0; j < N; j++) {
            int[] colLine = new int[N];
            for (int i = 0; i < N; i++) {
                colLine[i] = map[i][j];
            }
            if (check(colLine)) ans++;
        }

        System.out.println(ans);
    }

    static boolean check(int[] line) {
        boolean[] visited = new boolean[N];

        for (int i = 1; i < N; i++) {
            int diff = line[i] - line[i - 1];
            if (diff == 0) continue;
            if (Math.abs(diff) > 1) return false;

            if (diff == 1) { // 오르막
                for (int j = i - 1; j >= i - L; j--) {
                    if (j < 0 || line[j] != line[i - 1] || visited[j]) return false;
                    visited[j] = true;
                }
            } else { // 내리막
                for (int j = i; j < i + L; j++) {
                    if (j >= N || line[j] != line[i] || visited[j]) return false;
                    visited[j] = true;
                }
                i += (L - 1);
            }
        }
        return true;
    }
}
