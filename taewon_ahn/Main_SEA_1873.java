
import java.io.*;
import java.util.*;

public class Main_SEA_1873 {
    static int H, W, N, dir, curR, curC;
    static char[][] map;
    static int[] dr = { -1, 0, 1, 0 };
    static int[] dc = { 0, 1, 0, -1 };
    static String tank = "^>v<";

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());

        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            H = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            map = new char[H][W];
            for (int r = 0; r < H; r++) {
                String line = br.readLine();
                for (int c = 0; c < W; c++) {
                    char temp = line.charAt(c);
                    int tankIdx = tank.indexOf(temp);
                    if (tankIdx >= 0) {
                        dir = tankIdx;
                        curR = r;
                        curC = c;
                    }
                    map[r][c] = temp;
                }
            }

            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            String line = br.readLine();
            for (char c : line.toCharArray()) {
                simulate(c);
            }

            StringBuilder sb = new StringBuilder();
            sb.append("#").append(tc).append(" ");
            for (int i = 0; i < H; i++) {
                for (int j = 0; j < W; j++) {
                    sb.append(map[i][j]);
                }
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }

    static void simulate(char c) {
        switch (c) {
            case 'S':
                shoot();
                break;
            case 'U':
                move(0);
                break;
            case 'D':
                move(2);
                break;
            case 'L':
                move(3);
                break;
            case 'R':
                move(1);
                break;
        }
    }

    static void move(int d) {
        dir = d;
        map[curR][curC] = tank.charAt(dir); // 현위치 회전

        int nr = curR + dr[d];
        int nc = curC + dc[d];
        if (check(nr, nc) && map[nr][nc] == '.') {
            map[nr][nc] = tank.charAt(dir);
            map[curR][curC] = '.';
            curR = nr;
            curC = nc;
        }
    }

    static void shoot() {
        int step = 1;
        while (true) {
            int nr = curR + dr[dir] * step;
            int nc = curC + dc[dir] * step;

            if (!check(nr, nc))
                break;
            else if (map[nr][nc] == '*') { // 벽돌
                map[nr][nc] = '.';
                break;
            } else if (map[nr][nc] == '#') { // 강철
                break;
            }

            step++;
        }
    }

    static boolean check(int nr, int nc) {
        return 0 <= nr && nr < H && 0 <= nc && nc < W;
    }
}
