package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [낚시왕] 
 *  링크 : [https://www.acmicpc.net/problem/17143]
 *  난이도 : [골드 1]
 *  설명:
 *   - R x C 격자에서 낚시왕이 왼쪽에서 오른쪽으로 이동하며 낚시를 한다.
 *   - 각 칸에는 상어가 있을 수 있고, 상어는 속력(s), 이동방향(d), 크기(z)를 가진다.
 *   - 낚시왕은 현재 열에서 가장 위에 있는 상어를 잡고, 이후 모든 상어가 이동한다.
 *   - 이동 후 같은 칸에 상어가 여러 마리 있으면 가장 큰 상어만 살아남는다.
 *   - 낚시왕이 모든 열을 이동했을 때 잡은 상어 크기의 합을 출력해야 한다.
 *  풀이:
 *   - 시뮬레이션 문제.
 *   - 열을 1부터 C까지 순회하며 `fishing()`으로 상어를 잡고 `move()`로 상어 이동 처리.
 *   - 이동 시 속도 최적화: 상/하 방향은 (R-1)*2, 좌/우 방향은 (C-1)*2 주기로 나머지 연산.
 *   - 이동 중 벽에 닿으면 방향을 반대로 전환.
 *   - 이동이 끝난 뒤 같은 칸의 상어들은 크기 비교 후 가장 큰 상어만 남김.
 */

public class Main_BOJ_17143 {
    static int R, C, M, ans;
    static Shark[][] map;
    static int[] dr = {0, -1, 1, 0, 0}; // 1: 위, 2: 아래, 3: 오른쪽, 4: 왼쪽
    static int[] dc = {0, 0, 0, 1, -1};

    static class Shark {
        int s, d, z;
        public Shark(int s, int d, int z) {
            this.s = s; // 속력
            this.d = d; // 방향
            this.z = z; // 크기
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        map = new Shark[R+1][C+1];

        // 상어 정보 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int r = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            int s = Integer.parseInt(st.nextToken());
            int d = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());
            map[r][c] = new Shark(s, d, z);
        }

        ans = 0;
        // 낚시왕 이동 (왼쪽 → 오른쪽)
        for (int i = 1; i <= C; i++) {
            fishing(i); // 상어 잡기
            move();     // 상어 이동
        }
        System.out.println(ans);
    }

    // 상어 이동 처리
    private static void move() {
        Shark[][] nextMap = new Shark[R+1][C+1];

        for (int r = 1; r <= R; r++) {
            for (int c = 1; c <= C; c++) {
                if (map[r][c] != null) {
                    Shark shark = map[r][c];
                    int speed = shark.s;
                    int dir = shark.d;

                    // 주기 최적화
                    if (dir == 1 || dir == 2) {
                        if (R > 1) speed %= (2 * (R - 1));
                    } else {
                        if (C > 1) speed %= (2 * (C - 1));
                    }

                    int nr = r;
                    int nc = c;

                    // 실제 이동
                    for (int i = 0; i < speed; i++) {
                        if (nr == 1 && dir == 1) dir = 2;
                        else if (nr == R && dir == 2) dir = 1;
                        else if (nc == 1 && dir == 4) dir = 3;
                        else if (nc == C && dir == 3) dir = 4;

                        nr += dr[dir];
                        nc += dc[dir];
                    }

                    shark.d = dir;
                    // 해당 위치에 상어가 있으면 크기 비교 후 큰 상어만 생존
                    if (nextMap[nr][nc] == null || nextMap[nr][nc].z < shark.z) {
                        nextMap[nr][nc] = shark;
                    }
                }
            }
        }
        map = nextMap;
    }

    // 낚시: 해당 열에서 가장 가까운 상어 잡기
    private static void fishing(int col) {
        for (int row = 1; row <= R; row++) {
            if (map[row][col] != null) {
                Shark caughtShark = map[row][col];
                ans += caughtShark.z;
                map[row][col] = null; // 잡힌 상어 제거
                return;
            }
        }
    }
}
