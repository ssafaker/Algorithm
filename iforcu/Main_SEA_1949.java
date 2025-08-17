package iforcu;

import java.util.*;
import java.io.*;

/*
 *  문제명: [등산로 조성]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV5PoOKKAPIDFAUq]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - N*N 크기의 지형 지도에서 가장 높은 봉우리부터 출발하는 가장 긴 등산로를 찾는 문제
 *  - 이동은 상하좌우로만 가능하며, 반드시 낮은 지형으로만 이동
 *  - 한 번만 최대 K 깊이까지 지형을 깎아 이동 가능
 *  - 여러 봉우리에서 출발할 수 있고, 최종적으로 만들 수 있는 가장 긴 길이를 출력
 *  풀이:
 *  - DFS + 백트래킹으로 모든 경로 탐색
 *  - visited 배열로 현재 경로 방문 여부 체크
 *  - DFS 상태는 (좌표 r,c), 현재 길이 depth, 공사 사용 여부 usedCut
 *  - 다음 이동 후보:
 *      1. 현재보다 낮은 지형 → 그대로 이동
 *      2. 현재보다 높거나 같은 지형 → 공사를 사용하지 않았고, K 깊이 이하로 깎으면 이동 가능
 *  - DFS 종료 시 ans 갱신 (최대 길이)
 *  - 입력받으면서 최대 높이 봉우리 좌표를 peaks 리스트에 저장 → DFS 시작점으로 사용
 */


public class Main_SEA_1949 {
    static int N, K, ans;
    static int[][] map;
    static boolean[][] visited;
    static List<int[]> peaks;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            visited = new boolean[N][N];
            int maxHeight = 0;
            peaks = new ArrayList<>();

            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                    // 가장 큰 봉우리를 찾아 List에 저장
                    if (map[i][j] > maxHeight) {
                        maxHeight = map[i][j];
                        peaks.clear();      
                        peaks.add(new int[]{i, j});
                    } else if (map[i][j] == maxHeight) {
                        peaks.add(new int[]{i, j});
                    }
                }
            }

            ans = 0;
            for (int[] peak : peaks) {
                visited[peak[0]][peak[1]] = true;
                dfs(peak[0], peak[1], 1, false);
                visited[peak[0]][peak[1]] = false;
            }

            System.out.println("#"+test_case+" "+ans);
        }
    }

    static void dfs(int r, int c, int depth,boolean usedCut) {
        ans = Math.max(ans, depth);

        for (int d = 0; d < 4; d++) {
            int nr = r + dr[d];
            int nc = c + dc[d];
            if(!isValid(nr, nc)) continue;
            if(visited[nr][nc]) continue;
            // 다음 이동 경로로 그냥 지나갈 수 있는 경우
            if (map[nr][nc] < map[r][c]) {
                visited[nr][nc] = true;
                dfs(nr, nc, depth + 1, usedCut);
                visited[nr][nc] = false;
            }
            // 다음 이동 경로를 깍아야될 경우
            else if (!usedCut && map[nr][nc] >= map[r][c]) {
                int requiredCut = map[nr][nc] - map[r][c] + 1;
                if (requiredCut <= K) { 
                    int originalHeight = map[nr][nc];
                    map[nr][nc] = map[r][c] - 1; 
                    visited[nr][nc] = true;
                    dfs(nr, nc, depth + 1, true);
                    visited[nr][nc] = false;
                    map[nr][nc] = originalHeight;
                }
            }
        }
    }

    static boolean isValid(int r, int c){
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
