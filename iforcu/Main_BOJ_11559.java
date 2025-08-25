package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [Puyo Puyo]
 *  링크 : [https://www.acmicpc.net/problem/11559]
 *  난이도 : [골드 4]
 *  설명:
 *  - 같은 색 뿌요가 4개 이상 연결되면 터지고, 위의 뿌요들이 중력에 의해 아래로 떨어짐.
 *  - 여러 그룹이 동시에 터질 수 있으며, 연쇄가 발생하면 이를 모두 카운트해야 함.
 *  - 최종적으로 일어나는 연쇄의 횟수를 출력하는 문제.
 *  
 *  풀이:
 *  - BFS 탐색으로 같은 색 뿌요 그룹을 찾음.
 *  - 그룹 크기가 4 이상이면 해당 위치를 제거 대상으로 표시.
 *  - 모든 제거 후, 중력 로직을 적용하여 뿌요를 아래로 떨어뜨림.
 *  - 더 이상 터질 그룹이 없을 때까지 반복하며, 반복 횟수(연쇄 횟수)를 출력.
 */

public class Main_BOJ_11559 {
    static final int ROW = 12; // 행 크기
    static final int COL = 6;  // 열 크기
    static char[][] map = new char[ROW][COL];
    static boolean[][] visited;
    static int[] dx = {-1, 1, 0, 0}; // 상하좌우
    static int[] dy = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        // 맵 입력 받기
        for (int i = 0; i < ROW; i++) {
            String line = br.readLine();
            for (int j = 0; j < COL; j++) {
                map[i][j] = line.charAt(j);
            }
        }

        int chain = 0; // 연쇄 횟수

        // 더 이상 터질 그룹이 없을 때까지 반복
        while (true) {
            visited = new boolean[ROW][COL];
            boolean exploded = false;
            List<int[]> toRemove = new ArrayList<>();

            // 모든 칸 탐색
            for (int i = 0; i < ROW; i++) {
                for (int j = 0; j < COL; j++) {
                    if (map[i][j] != '.' && !visited[i][j]) {
                        List<int[]> group = bfs(i, j, map[i][j]); // 같은 색 그룹 탐색
                        if (group.size() >= 4) { // 4개 이상이면 제거
                            exploded = true;
                            toRemove.addAll(group);
                        }
                    }
                }
            }

            if (!exploded) break; // 더 이상 터질 그룹이 없으면 종료

            // 제거 처리
            for (int[] pos : toRemove) {
                map[pos[0]][pos[1]] = '.';
            }

            // 중력 적용
            applyGravity();

            chain++; // 연쇄 횟수 증가
        }

        System.out.println(chain);
    }

    // BFS로 같은 색 그룹 탐색
    static List<int[]> bfs(int x, int y, char color) {
        Queue<int[]> q = new LinkedList<>();
        List<int[]> group = new ArrayList<>();
        visited[x][y] = true;
        q.add(new int[]{x, y});
        group.add(new int[]{x, y});

        while (!q.isEmpty()) {
            int[] cur = q.poll();
            for (int d = 0; d < 4; d++) {
                int nx = cur[0] + dx[d];
                int ny = cur[1] + dy[d];
                if (nx < 0 || nx >= ROW || ny < 0 || ny >= COL) continue;
                if (!visited[nx][ny] && map[nx][ny] == color) {
                    visited[nx][ny] = true;
                    q.add(new int[]{nx, ny});
                    group.add(new int[]{nx, ny});
                }
            }
        }
        return group;
    }

    // 중력 적용: 위에 있는 뿌요들을 아래로 떨어뜨림
    static void applyGravity() {
        for (int col = 0; col < COL; col++) {
            int empty = ROW - 1; // 맨 아래 인덱스
            for (int row = ROW - 1; row >= 0; row--) {
                if (map[row][col] != '.') {
                    char temp = map[row][col];
                    map[row][col] = '.';
                    map[empty][col] = temp;
                    empty--;
                }
            }
        }
    }
}
