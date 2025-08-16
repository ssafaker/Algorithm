package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [프로세서 연결하기]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV4suNtaXFEDFAUf]
 *  난이도 : [SW Test 샘플문제]
 *  설명:
 *  - 코어를 가장자리에 전선으로 연결하는 문제
 *  - 코어가 가장자리에 있으면 전선이 연결된걸로 취급해 기존 cores Array 추가 x
 *  - core는 List로 나머지 값은 int로 관리
 *  - 2차원 배열 grid 격자로 구현
 *  풀이:
 *  - DFS + 백트래킹 구현
 *  - core는 연결 혹은 비연결 상태를 가질 수 있고 4방향으로 연결 완전 탐색 (총 5가지 상태)
 *  - 연결 가능성을 먼저 확인하고 진행, 연결이 안되면 스킵
 *  - 로직 진행 후 core 수와 전선 연결 갯수에 따라 정답 찾기
 */ 

public class Main_SEA_1767 {
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};
    static int[][] grid;
    static int N, coreNumber;
    static int ans, onCores;
    static List<int[]> cores;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine().trim());
            grid = new int[N][N];
            cores = new ArrayList<>();
            
            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                    if (grid[i][j] == 1) {
                        if (i > 0 && i < N - 1 && j > 0 && j < N - 1) {
                            cores.add(new int[]{i, j});
                        }
                    }
                }
            }

            coreNumber = cores.size();
            ans = Integer.MAX_VALUE;
            onCores = 0;
            dfs(0, 0, 0);

            System.out.println("#" + test_case + " " + ans);
        }
    }

    static void dfs(int depth, int core, int length) {
        // 가지치기
        if (core + (coreNumber - depth) < onCores) return;

        // 모든 코어를 지나면
        if (depth == coreNumber) {
            if (core > onCores) {
                onCores = core;
                ans = length;
            } else if (core == onCores) {
                ans = Math.min(ans, length);
            }
            return;
        }

        int r = cores.get(depth)[0];
        int c = cores.get(depth)[1];

        for (int d = 0; d < 4; d++) {
            // 연결 가능성 확인 후 가능하면 연결의 경우의 수 진행
            if (isConnectable(r, c, d)) {
                int wire = setWire(r, c, d, 2);
                dfs(depth + 1, core + 1, length + wire);
                setWire(r, c, d, 0); 
            }
        }

        // 연결을 안할 경우의 수
        dfs(depth + 1, core, length);
    }

    // status (2 : 전선 0 : 빈공간) 에 따라 설치 및 삭제 진행 
    static int setWire(int r, int c, int d, int status) {
        int count = 0;
        int nr = r;
        int nc = c;
        while (true) {
            nr += dr[d];
            nc += dc[d];

            if (nr < 0 || nr >= N || nc < 0 || nc >= N) break;
            grid[nr][nc] = status;
            count++;
        }
        return count;
    }

    // 전선 연결 여부 확인
    static boolean isConnectable(int r, int c, int d) {
        int nr = r;
        int nc = c;
        while (true) {
            nr += dr[d];
            nc += dc[d];

            if (nr < 0 || nr >= N || nc < 0 || nc >= N) return true;
            if (grid[nr][nc] != 0) return false;
        }
    }
}
