package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [컨닝]
 *  링크   : [https://www.acmicpc.net/problem/1014]
 *  난이도 : [플래티넘4]
 *  설명   :
 *    - N x M 교실에서 학생들이 컨닝하지 않도록 최대한 많은 학생을 배치하는 문제
 *    - 각 학생은 좌우, 대각선 위(왼/오) 네 방향의 친구 답지를 볼 수 있음
 *    - 일부 자리는 앉을 수 없음('x'), 나머지는 앉을 수 있음('.')
 *    - 각 테스트케이스마다 최대 학생 수를 구해야 함
 *  풀이   :
 *    - 앉을 수 있는 자리를 이분 매칭 그래프로 모델링
 *    - 홀수/짝수 열을 나눠 인접한 자리끼리 간선 연결
 *    - DFS를 이용한 최대 매칭으로 컨닝이 불가능한 최대 학생 수 계산
 */

public class Main_BOJ_1014 {
    static ArrayList<Integer>[] adj;
    static int[] match;
    static boolean[] visited;
    static char[][] classroom;
    static int N, M;
    static int[] dr = {-1, -1, 0, 0, 1, 1};
    static int[] dc = {-1, 1, -1, 1, -1, 1};

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int C = Integer.parseInt(br.readLine());

        for (int test_case = 0; test_case < C; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            classroom = new char[N][M];
            int totalSeats = 0;
            // 교실 정보 입력 및 앉을 수 있는 자리 개수 세기
            for (int i = 0; i < N; i++) {
                String s = br.readLine();
                char[] c = s.toCharArray();
                for (int j = 0; j < M; j++) {
                    classroom[i][j] = c[j];
                    if(classroom[i][j] == '.') totalSeats++;
                }
            }

            int[][] seatId = new int[N][M];
            int curId = 0;
            // 각 자리별 고유 번호 부여
            for (int j = 0; j < M; j++) {
                for (int i = 0; i < N; i++) {
                    if(classroom[i][j] == '.') {
                        seatId[i][j] = curId++;
                    }
                }
            }

            adj  = new ArrayList[totalSeats];
            for (int i = 0; i < totalSeats; i++) {
                adj[i] = new ArrayList<>();
            }
            // 홀수 열의 자리에서 인접한 자리로 간선 연결
            for (int j = 0; j < M; j+=2) {
                for (int i = 0; i < N; i++) {
                    if(classroom[i][j] == '.') {
                        int u = seatId[i][j];

                        for (int d = 0; d < 6; d++) {
                            int nr = i + dr[d];
                            int nc = j + dc[d];

                            if(nr >= 0 && nr < N && nc >= 0 && nc < M && classroom[nr][nc] == '.') {
                                int v = seatId[nr][nc];
                                adj[u].add(v);
                            }
                        }
                    }
                }
            }

            match = new int[totalSeats];
            Arrays.fill(match, -1);
            int matchingCount = 0;

            // 최대 매칭 계산 (홀수 열 기준)
            for (int j = 0; j < M; j+=2) {
                for (int i = 0; i < N; i++) {
                    if(classroom[i][j] == '.') {
                        int u = seatId[i][j];
                        visited = new boolean[totalSeats];

                        if(dfs(u)) matchingCount++;
                    }
                }
            }

            // 최대 학생 수 = 전체 자리 - 매칭 수
            sb.append(totalSeats - matchingCount).append("\n");
        }
        // 결과 출력
        System.out.println(sb.toString());
    }

    // 이분 매칭용 DFS
    private static boolean dfs(int u) {
        if(visited[u]) return false;
        visited[u] = true;
        for (int v : adj[u]) {
            if(match[v] == -1 || dfs(match[v])) {
                match[v] = u;
                return true;
            }
        }
        return false;
    }
}