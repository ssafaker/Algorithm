package iforcu;

import java.io.*;
import java.util.*;

/*
 * 문제명: [핀볼 게임]
 * 링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRF8s6ezEDFAUo]
 * 난이도 : [모의 SW 역량테스트]
 * 설명: 핀볼 게임에서 주어진 맵에 따라 점수를 계산하는
 * 풀이: 
 * - 벽에 대한 계산을 함 int[][] wall.
 * - while문 시작시 무조건 dir 방향 이동 이후 바깥에 나가면 방향을 바꾸고 continue
 * - 이후 벽 or 웜홀에 따른 방향 전환 및 점수 계산
 * - 웜홀은 리스트로 관리
 * 비고 : 17번에서 런타임 에러발생 - N뒤에 공백이 발생해서 .trim()을 사용하여 해결함.
 */

public class Main_SEA_5650 {
    static int N;
    static int[][] map;
    static int[] dr = {-1, 0, 1, 0}; // 0: up, 1: right, 2: down, 3: left
    static int[] dc = {0, 1, 0, -1};
    static List<List<int[]>> wormhole;
    static int score;
    static final int[][] wall = {
        {}, 
        {2, 3, 1, 0}, 
        {1, 3, 0, 2},
        {3, 2, 0, 1}, 
        {2, 0, 3, 1}, 
        {}
    };

	public static void main(String args[]) throws Exception
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine().trim());
            map = new int[N][N]; 

            wormhole = new ArrayList<>();
            for (int i = 0; i < 11; i++) {
                wormhole.add(new ArrayList<>());
            }

            for (int i = 0; i < N; i++) {
                StringTokenizer st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());

                    if (map[i][j] > 5 && map[i][j] < 11) {
                        wormhole.get(map[i][j]).add(new int[] {i, j});
                    }
                }
            }

            score = 0;
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (map[i][j] == 0) {
                        for (int d = 0; d < 4; d++) {
                            int curScore = start(i, j, d);
                            score = Math.max(score, curScore);
                        }
                    }
                }
            }
            System.out.println("#"+test_case+" "+score);
        }
	}
    static int start(int sr, int sc, int sd) {
        int r = sr;
        int c = sc;
        int d = sd;
        int count = 0;

        while (true) {
            r += dr[d];
            c += dc[d];

            if (!isValid(r, c)) { 
                d = (d + 2) % 4; 
                count++;
                continue;
            }

            if ((r == sr && c == sc) || map[r][c] == -1) return count;
    
            if (1 <= map[r][c] && map[r][c] <= 5) {
                if (map[r][c] == 5) d = (d + 2) % 4;
                else d = wall[map[r][c]][d];
                count++;
            } else if (map[r][c] >= 6 && map[r][c] <= 10) {
                int[] wormholePos = wormhole.get(map[r][c]).get(0);
                if (wormholePos[0] == r && wormholePos[1] == c) {
                    wormholePos = wormhole.get(map[r][c]).get(1);
                }
                r = wormholePos[0];
                c = wormholePos[1];
            }
        }
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}
