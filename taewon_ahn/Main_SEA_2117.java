import java.io.*;
import java.util.*;

public class Main_SEA_2117 {
    static int N, M, maxHouseCnt;
    static int[][] map;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            map = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            maxHouseCnt = 0;
            // 모든 중심점에 대해 탐색
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    findMaxHouse(i, j);
                }
            }

            sb.append("#").append(tc).append(" ").append(maxHouseCnt).append("\n");
        }
        System.out.print(sb);
    }

    private static void findMaxHouse(int r, int c) {
        for (int k = 1; k <= 2 * N - 2; k++) {
            int houseCnt = 0;

            // 마름모 범위 집 수 계산
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    int dist = Math.abs(r - i) + Math.abs(c - j);
                    if (dist < k && map[i][j] == 1) {
                        houseCnt++;
                    }
                }
            }

            int profit = M * houseCnt - ((k * k) + ((k - 1) * (k - 1)));
            if (profit >= 0 && houseCnt > maxHouseCnt) {
                maxHouseCnt = houseCnt;
            }
        }
    }
}