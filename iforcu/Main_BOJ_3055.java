package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [탈출]
 *  링크 : [https://www.acmicpc.net/problem/3055]
 *  난이도 : [골드 4]
 *  설명: 
 *   - 티떱숲에서 고슴도치가 비버의 굴로 피해야 하는 문제.
 *   - 물이 매분마다 사방으로 확산되며, 고슴도치는 물에 잠기기 전에 이동해야 함.
 *   - 돌('X')과 비버굴('D')은 물에 잠기지 않음.
 *  풀이:
 *   - 시간별로 물의 확산 상태를 미리 계산하여 3차원 배열에 저장.
 *   - BFS를 사용해 고슴도치의 이동 경로 탐색.
 *   - 각 시간대에 물에 잠기지 않은 빈 공간으로만 이동 가능.
 */

public class Main_BOJ_3055 {
    static int R, C;
    static char[][][] map;
    static int startR, startC, endR, endC;
    static List<int[]> waterList;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[200][R][C];
        waterList = new ArrayList<>();

        // 초기 맵 상태 입력 및 시작점, 도착점 찾기
        for (int i = 0; i < R; i++) {
            String s = br.readLine();
            char[] c = s.toCharArray();
            for (int j = 0; j < C; j++) {
                map[0][i][j] = c[j];
                if(map[0][i][j] == 'D') {
                    endR = i;
                    endC = j;
                } else if(map[0][i][j] == 'S') {
                    startR = i;
                    startC = j;
                } 
            }
        }
        mapWater(); // 시간별 물 확산 미리 계산
        System.out.println(bfs()); // BFS로 최단 경로 탐색
    }

    // 시간별로 물의 확산을 미리 계산
    static void mapWater() {
        for (int time = 1; time < 200; time++) {
            for (int i = 0; i < R; i++) {
                for (int j = 0; j < C; j++) {
                    if(map[time-1][i][j] == '*') {
                        map[time][i][j] = '*';
                        // 물이 4방향으로 확산
                        for (int d = 0; d < 4; d++) {
                            int nr = i + dr[d];
                            int nc = j + dc[d];
                            if (!isValid(nr, nc)) continue;
                            if(map[time-1][nr][nc] == '.' || map[time-1][nr][nc] == 'S') {
                                map[time][nr][nc] = '*';
                            }
                        }
                    } else if(map[time-1][i][j] == 'S') {
                        map[time][i][j] = '.';
                    } else if(map[time][i][j] != '*') {
                        map[time][i][j] = map[time-1][i][j];
                    }
                }
            }
        }
    }

    // BFS로 고슴도치의 최단 경로 탐색
    static String bfs() {
        boolean[][] visited = new boolean[R][C];
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {startR, startC, 0});
        visited[startR][startC] = true;

        while (!queue.isEmpty()) {
            int[] temp = queue.poll();
            int r = temp[0];
            int c = temp[1];
            int t = temp[2];

            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];
                int nextT = t + 1;
                if(!isValid(nr, nc)) continue;
                
                // 비버굴 도착 시 시간 반환
                if(nr == endR && nc == endC) return String.valueOf(nextT);
                
                // 다음 시간에 물에 잠기지 않은 빈 공간만 이동 가능
                if(map[nextT][nr][nc] == '.' && !visited[nr][nc]) {
                    visited[nr][nc] = true;
                    queue.offer(new int[] {nr, nc, nextT});
                }
            }
        }
        return "KAKTUS";
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }
}
