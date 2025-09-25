package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [탈옥]
 *  링크   : [https://www.acmicpc.net/problem/9376]
 *  난이도 : [플래티넘4]
 *  설명   :
 *    - 평면도에 표시된 감옥에서 두 명의 죄수를 탈출시키기 위해 열어야 하는 문의 최소 개수 구하기
 *    - 빈 공간 '.', 벽 '*', 문 '#', 죄수 위치 '$'로 구성
 *    - 감옥 밖에서 시작해 두 죄수와 만나는 경로를 모두 고려해야 함
 *  풀이   :
 *    - 감옥 밖, 죄수1, 죄수2에서 각각 BFS로 모든 위치까지의 최소 문 개수 계산
 *    - 세 경로의 합에서 문 위치는 중복 열림이므로 2번 빼줌
 *    - 모든 위치에 대해 최소 합을 구해 정답 출력
 */

public class Main_BOJ_9376 {
    static int h, w;
    static char[][] map;
    static final int[] dr = {0, 0, 1, -1};
    static final int[] dc = {1, -1, 0, 0};

    static class Point {
        int r, c;

        Point(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());

        while (T-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            map = new char[h + 2][w + 2];
            List<Point> startPoints = new ArrayList<>();
            startPoints.add(new Point(0, 0));

            // 감옥 평면도 입력 및 죄수 위치 저장
            for (int i = 0; i < h + 2; i++) {
                Arrays.fill(map[i], '.');
            }

            for (int i = 1; i <= h; i++) {
                String line = br.readLine();
                for (int j = 1; j <= w; j++) {
                    map[i][j] = line.charAt(j - 1);
                    if (map[i][j] == '$') {
                        startPoints.add(new Point(i, j));
                    }
                }
            }

            // 각 시작점에서 BFS로 최소 문 개수 계산
            int[][] distFromOutside = bfs(startPoints.get(0));
            int[][] distFromP1 = bfs(startPoints.get(1));
            int[][] distFromP2 = bfs(startPoints.get(2));

            int minDoors = Integer.MAX_VALUE;

            // 모든 위치에 대해 세 경로의 합에서 중복 문 처리 후 최소값 갱신
            for (int i = 0; i < h + 2; i++) {
                for (int j = 0; j < w + 2; j++) {
                    if(distFromOutside[i][j] != -1 && distFromP1[i][j] != -1 && distFromP2[i][j] != -1){
                        if (map[i][j] == '*') {
                        continue;
                        }

                        int currentSum = distFromOutside[i][j] + distFromP1[i][j] + distFromP2[i][j];

                        if (map[i][j] == '#') {
                            currentSum -= 2;
                        }

                        minDoors = Math.min(minDoors, currentSum);
                    }
                }
            }
            sb.append(minDoors).append("\n");
        }
        // 결과 출력
        System.out.print(sb);
    }

    // 0-1 BFS로 각 위치까지의 최소 문 개수 계산
    private static int[][] bfs(Point start) {
        int[][] dist = new int[h + 2][w + 2];
        for (int i = 0; i < h + 2; i++) {
            Arrays.fill(dist[i], -1);
        }

        Deque<Point> deque = new ArrayDeque<>();
        deque.addFirst(start);
        dist[start.r][start.c] = 0;

        while (!deque.isEmpty()) {
            Point current = deque.pollFirst();

            for (int i = 0; i < 4; i++) {
                int nr = current.r + dr[i];
                int nc = current.c + dc[i];

                if (nr < 0 || nc < 0 || nr >= h + 2 || nc >= w + 2) continue;
                if (map[nr][nc] == '*' || dist[nr][nc] != -1) continue;

                if (map[nr][nc] == '#') {
                    dist[nr][nc] = dist[current.r][current.c] + 1;
                    deque.addLast(new Point(nr, nc));
                } else {
                    dist[nr][nc] = dist[current.r][current.c];
                    deque.addFirst(new Point(nr, nc));
                }
            }
        }
        return dist;
    }
}