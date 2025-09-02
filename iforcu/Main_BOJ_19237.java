package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [어른 상어]
 *  링크 : [https://www.acmicpc.net/problem/19237]
 *  난이도 : [Gold 1]
 *  설명:
 *  - N×N 격자에 M마리 상어가 있고, 각 칸에는 상어가 냄새를 남김.
 *  - 냄새는 K초 후 사라짐. 같은 칸에 여러 상어가 오면 가장 번호가 작은 상어만 생존.
 *  - 상어는 "현재 방향"에 따라 우선순위 방향표를 따라 이동.
 *  - 1번 상어만 남을 때까지 걸린 시간을 구하되, 1000초 넘으면 -1 출력.
 *
 *  풀이:
 *  - grid[r][c][0]: 해당 칸의 상어 번호, grid[r][c][1]: 냄새 남은 시간 관리.
 *  - Shark 객체에 위치(r,c), 방향, 우선순위 dirP 저장.
 *  - 매 턴마다 순서:
 *    1) 모든 상어 이동 (냄새 없는 칸 → 자기 냄새 칸 우선).
 *    2) 모든 칸 냄새 감소, 시간 0이면 제거.
 *    3) 상어 충돌 처리 (번호 큰 상어 제거).
 *    4) 살아남은 상어가 새로운 냄새 남김.
 *  - 상어 리스트가 1개 남을 때까지 반복.
 */

public class Main_BOJ_19237 {
    static int N, M, K;
    static int[][][] grid; // [r][c][0]: 상어 번호, [r][c][1]: 냄새 남은 시간
    static List<Shark> sharks;
    static Shark[] sharkArr;
    static int[] dr = {0, -1, 1, 0, 0}; // 상(1), 하(2), 좌(3), 우(4)
    static int[] dc = {0, 0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        sharks = new ArrayList<>();
        grid = new int[N][N][2];
        sharkArr = new Shark[M + 1];

        // 초기 상어 배치 및 냄새 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int temp = Integer.parseInt(st.nextToken());
                if (temp != 0) {
                    Shark s = new Shark(i, j, temp);
                    sharks.add(s);
                    sharkArr[temp] = s;
                    grid[i][j][0] = temp;
                    grid[i][j][1] = K;
                }
            }
        }

        Collections.sort(sharks);

        // 초기 방향 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < sharks.size(); i++) {
            sharks.get(i).dir = Integer.parseInt(st.nextToken());
        }

        // 각 상어의 방향 우선순위 입력
        for (int k = 1; k <= M; k++) {
            for (int i = 1; i <= 4; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 1; j <= 4; j++) {
                    sharkArr[k].dirP[i][j] = Integer.parseInt(st.nextToken());
                }
            }
        }

        int time = 0;
        while (sharks.size() > 1) {
            time++;
            if (time > 1000) {
                time = -1;
                break;
            }

            // 1. 모든 상어 이동
            for (Shark shark : sharks) {
                shark.move();
            }

            // 2. 냄새 시간 감소
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (grid[i][j][1] > 0) {
                        grid[i][j][1]--;
                        if (grid[i][j][1] == 0) {
                            grid[i][j][0] = 0;
                        }
                    }
                }
            }

            // 3. 충돌 처리 (번호 작은 상어만 생존)
            Shark[][] posMap = new Shark[N][N];
            List<Shark> survivors = new ArrayList<>();

            for (Shark shark : sharks) {
                Shark exist = posMap[shark.r][shark.c];
                if (exist == null) {
                    posMap[shark.r][shark.c] = shark;
                } else {
                    if (shark.number < exist.number) {
                        posMap[shark.r][shark.c] = shark;
                    }
                }
            }
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (posMap[i][j] != null) {
                        survivors.add(posMap[i][j]);
                    }
                }
            }
            sharks = survivors;

            // 4. 살아남은 상어 냄새 남기기
            for (Shark shark : sharks) {
                grid[shark.r][shark.c][0] = shark.number;
                grid[shark.r][shark.c][1] = K;
            }
        }

        System.out.println(time);
    }

    static class Shark implements Comparable<Shark> {
        int r, c, number, dir;
        int[][] dirP; // [현재 방향][우선순위] = 다음 방향

        Shark(int r, int c, int number) {
            this.r = r;
            this.c = c;
            this.number = number;
            this.dirP = new int[5][5];
        }

        // 상어 이동
        public void move() {
            int curD = this.dir;

            // 1. 아무 냄새 없는 칸 우선 탐색
            for (int i = 1; i <= 4; i++) {
                int nextDir = this.dirP[curD][i];
                int nr = this.r + dr[nextDir];
                int nc = this.c + dc[nextDir];

                if (isValid(nr, nc) && grid[nr][nc][0] == 0) {
                    this.r = nr;
                    this.c = nc;
                    this.dir = nextDir;
                    return;
                }
            }

            // 2. 자기 냄새가 있는 칸 탐색
            for (int i = 1; i <= 4; i++) {
                int nextDir = this.dirP[curD][i];
                int nr = this.r + dr[nextDir];
                int nc = this.c + dc[nextDir];

                if (isValid(nr, nc) && grid[nr][nc][0] == this.number) {
                    this.r = nr;
                    this.c = nc;
                    this.dir = nextDir;
                    return;
                }
            }
        }

        public int compareTo(Shark o) {
            return this.number - o.number;
        }
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}