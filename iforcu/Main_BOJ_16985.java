package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : Maaaaaaaaaze (BOJ 16985)
 *  링크   : https://www.acmicpc.net/problem/16985
 *  난이도 : 골드 2
 *  설명   :
 *    - 5x5 크기 판 5개를 자유롭게 회전/순서 변경해 5x5x5 3차원 미로를 구성
 *    - 입구(0,0,0)에서 출구(4,4,4)까지 최단 경로를 찾음
 *    - 판의 회전, 순서, 입구/출구가 막혀있으면 탈출 불가
 *    - 최단 경로가 없으면 -1 출력
 *  풀이   :
 *    - 5! (판 순서) * 4^5 (회전) 모든 경우의 수 완전탐색
 *    - 각 경우마다 BFS로 최단 경로 계산
 *    - 입구/출구가 막혀있거나 경로 없으면 무시, 최소 이동 횟수 갱신
 */

public class Main_BOJ_16985 {
    static int[][][] originBoards = new int[5][5][5];
    static int[][][] maze = new int[5][5][5];
    static int[] boardOrder = new int[5];
    static boolean[] isUsed = new boolean[5];
    static int minDistance = Integer.MAX_VALUE;

    static int[] dz = {1, -1, 0, 0, 0, 0};
    static int[] dy = {0, 0, 1, -1, 0, 0};
    static int[] dx = {0, 0, 0, 0, 1, -1};

    static class Point {
        int z, y, x, dist;

        public Point(int z, int y, int x, int dist) {
            this.z = z;
            this.y = y;
            this.x = x;
            this.dist = dist;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                st = new StringTokenizer(br.readLine());
                for (int k = 0; k < 5; k++) {
                    originBoards[i][j][k] = Integer.parseInt(st.nextToken());
                }
            }
        }

        generateBoardPermutations(0);

        if (minDistance == Integer.MAX_VALUE) {
            System.out.println(-1);
        } else {
            System.out.println(minDistance);
        }
    }

    static void generateBoardPermutations(int depth) {
        if (depth == 5) {
            generateRotations(0, new int[5]);
            return;
        }

        for (int i = 0; i < 5; i++) {
            if (!isUsed[i]) {
                isUsed[i] = true;
                boardOrder[depth] = i;
                generateBoardPermutations(depth + 1);
                isUsed[i] = false;
            }
        }
    }

    static void generateRotations(int depth, int[] rotationConfig) {
        if (depth == 5) {
            buildMaze(rotationConfig);

            if (maze[0][0][0] == 1 && maze[4][4][4] == 1) {
                int distance = bfs();
                if (distance != -1) {
                    minDistance = Math.min(minDistance, distance);
                }
            }
            return;
        }

        for (int i = 0; i < 4; i++) {
            rotationConfig[depth] = i;
            generateRotations(depth + 1, rotationConfig);
        }
    }

    static void buildMaze(int[] rotationConfig) {
        for (int i = 0; i < 5; i++) {
            int originalBoardIndex = boardOrder[i];
            int rotations = rotationConfig[i];
            maze[i] = rotateBoard(originBoards[originalBoardIndex], rotations);
        }
    }

    static int[][] rotateBoard(int[][] board, int rotations) {
        int[][] curBoard = new int[5][5];
        for (int i = 0; i < 5; i++) {
            System.arraycopy(board[i], 0, curBoard[i], 0, 5);
        }

        if (rotations == 0) return curBoard;

        int[][] tempBoard = new int[5][5];
        for (int r = 0; r < rotations; r++) {
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    tempBoard[j][4 - i] = curBoard[i][j];
                }
            }
            for (int i = 0; i < 5; i++) {
                System.arraycopy(tempBoard[i], 0, curBoard[i], 0, 5);
            }
        }
        return curBoard;
    }

    static int bfs() {
        Queue<Point> q = new LinkedList<>();
        boolean[][][] visited = new boolean[5][5][5];

        q.offer(new Point(0, 0, 0, 0));
        visited[0][0][0] = true;

        while (!q.isEmpty()) {
            Point cur = q.poll();

            if (cur.z == 4 && cur.y == 4 && cur.x == 4) {
                return cur.dist;
            }

            if (cur.dist >= minDistance) {
                continue;
            }

            for (int i = 0; i < 6; i++) {
                int nz = cur.z + dz[i];
                int ny = cur.y + dy[i];
                int nx = cur.x + dx[i];

                if (nz < 0 || ny < 0 || nx < 0 || nz >= 5 || ny >= 5 || nx >= 5) continue;
                if (!visited[nz][ny][nx] && maze[nz][ny][nx] == 1) {
                    visited[nz][ny][nx] = true;
                    q.offer(new Point(nz, ny, nx, cur.dist + 1));
                }
            }
        }

        return -1;
    }
}