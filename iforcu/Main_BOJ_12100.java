package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [2048 (Easy)]
 * 난이도: [Gold 1]
 * 링크: [https://www.acmicpc.net/problem/12100]
 *
 * 설명:
 *  - 2048 게임을 최대 5번 이동시켜서 만들 수 있는 가장 큰 블록을 찾는 문제.
 *  - 블록은 같은 수가 만나면 합쳐지며, 합쳐진 블록은 같은 이동에서 다시 합쳐질 수 없음.
 *
 * 풀이:
 * 1. DFS + 시뮬레이션
 *    - depth: 이동 횟수 (최대 5)
 *    - 매 이동마다 상/하/좌/우 4가지 방향으로 보드 상태를 갱신
 *    - 5번 이동 후 가장 큰 블록 값을 갱신
 *
 * 2. 구현 세부사항
 *    - move(): 보드를 특정 방향으로 이동 & 합치기
 *    - copyMap(): DFS에서 원본 보드를 보존하기 위해 깊은 복사
 *    - MaxBlock(): 현재 보드에서 최댓값 탐색
 */
public class Main_BOJ_12100 {
    static int N, max_block;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        int[][] map = new int[N][N];

        // 보드 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        max_block = 0;
        dfs(0, map); // DFS 시작

        System.out.println(max_block);
    }

    // DFS: depth = 이동 횟수
    static void dfs(int depth, int[][] map) {
        if(depth == 5) {
            max_block = Math.max(max_block, MaxBlock(map));
            return;
        }

        for (int d = 0; d < 4; d++) { // 4가지 방향
            int[][] newMap = copyMap(map);
            move(newMap, d);
            dfs(depth + 1, newMap);
        }
    }

    // 보드 이동 & 합치기
    static void move(int[][] map, int d) {
        switch (d) {
            case 0: // 위로 이동
                for (int c = 0; c < N; c++) {
                    Queue<Integer> q = new LinkedList<>();
                    for (int r = 0; r < N; r++) {
                        if (map[r][c] != 0) {
                            q.add(map[r][c]);
                            map[r][c] = 0;
                        }
                    }
                    int r_idx = 0;
                    while (!q.isEmpty()) {
                        int block = q.poll();
                        if (map[r_idx][c] == 0) {
                            map[r_idx][c] = block;
                        } else if (map[r_idx][c] == block) {
                            map[r_idx][c] *= 2;
                            r_idx++;
                        } else {
                            r_idx++;
                            map[r_idx][c] = block;
                        }
                    }
                }
                break;

            case 1: // 아래로 이동
                for (int c = 0; c < N; c++) {
                    Queue<Integer> q = new LinkedList<>();
                    for (int r = N - 1; r >= 0; r--) {
                        if (map[r][c] != 0) {
                            q.add(map[r][c]);
                            map[r][c] = 0;
                        }
                    }
                    int r_idx = N - 1;
                    while (!q.isEmpty()) {
                        int block = q.poll();
                        if (map[r_idx][c] == 0) {
                            map[r_idx][c] = block;
                        } else if (map[r_idx][c] == block) {
                            map[r_idx][c] *= 2;
                            r_idx--;
                        } else {
                            r_idx--;
                            map[r_idx][c] = block;
                        }
                    }
                }
                break;

            case 2: // 왼쪽으로 이동
                for (int r = 0; r < N; r++) {
                    Queue<Integer> q = new LinkedList<>();
                    for (int c = 0; c < N; c++) {
                        if (map[r][c] != 0) {
                            q.add(map[r][c]);
                            map[r][c] = 0;
                        }
                    }
                    int c_idx = 0;
                    while (!q.isEmpty()) {
                        int block = q.poll();
                        if (map[r][c_idx] == 0) {
                            map[r][c_idx] = block;
                        } else if (map[r][c_idx] == block) {
                            map[r][c_idx] *= 2;
                            c_idx++;
                        } else {
                            c_idx++;
                            map[r][c_idx] = block;
                        }
                    }
                }
                break;

            case 3: // 오른쪽으로 이동
                for (int r = 0; r < N; r++) {
                    Queue<Integer> q = new LinkedList<>();
                    for (int c = N - 1; c >= 0; c--) {
                        if (map[r][c] != 0) {
                            q.add(map[r][c]);
                            map[r][c] = 0;
                        }
                    }
                    int c_idx = N - 1;
                    while (!q.isEmpty()) {
                        int block = q.poll();
                        if (map[r][c_idx] == 0) {
                            map[r][c_idx] = block;
                        } else if (map[r][c_idx] == block) {
                            map[r][c_idx] *= 2;
                            c_idx--;
                        } else {
                            c_idx--;
                            map[r][c_idx] = block;
                        }
                    }
                }
                break;
        }
    }

    // 현재 보드에서 가장 큰 블록 찾기
    static int MaxBlock(int[][] map) {
        int max = 0;
        for (int[] arr : map) {
            for (int i : arr) {
                max = Math.max(max, i);
            }
        }
        return max;
    }

    // 보드 복사 (깊은 복사)
    static int[][] copyMap(int[][] map) {
        int[][] newMap = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newMap[i][j] = map[i][j];
            }
        }
        return newMap;
    }
}
