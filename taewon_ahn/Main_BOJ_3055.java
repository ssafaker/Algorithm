import java.io.*;
import java.util.*;

public class Main_BOJ_3055 {
    static int R, C;
    static char[][] map;
    static Queue<int[]> waterQ = new ArrayDeque<>();
    static Queue<int[]> hedgehogQ = new ArrayDeque<>();
    static int[] dr = { -1, 1, 0, 0, };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        R = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        map = new char[R][C];

        for (int i = 0; i < R; i++) {
            String line = br.readLine();
            for (int j = 0; j < C; j++) {
                map[i][j] = line.charAt(j);
                if (map[i][j] == 'S') {
                    hedgehogQ.offer(new int[] { i, j });
                } else if (map[i][j] == '*') {
                    waterQ.offer(new int[] { i, j });
                }
            }
        }

        int time = 1;

        while (!hedgehogQ.isEmpty()) {
            // 물 확산 (동일 시간대)
            int waterSize = waterQ.size();
            for (int i = 0; i < waterSize; i++) {
                int[] water = waterQ.poll();
                for (int d = 0; d < 4; d++) {
                    int nr = water[0] + dr[d];
                    int nc = water[1] + dc[d];
                    if (!check(nr, nc))
                        continue;
                    if (map[nr][nc] != '.')
                        continue;
                    map[nr][nc] = '*';
                    waterQ.offer(new int[] { nr, nc });
                }
            }

            // 고슴도치 이동 (동일 시간대)
            int hedgehogSize = hedgehogQ.size();
            for (int i = 0; i < hedgehogSize; i++) {
                int[] hedgephog = hedgehogQ.poll();

                for (int d = 0; d < 4; d++) {
                    int nr = hedgephog[0] + dr[d];
                    int nc = hedgephog[1] + dc[d];
                    if (!check(nr, nc))
                        continue;
                    if (map[nr][nc] != '.' && map[nr][nc] != 'D')
                        continue;

                    if (map[nr][nc] == 'D') {
                        System.out.println(time);
                        return;
                    } else {
                        map[nr][nc] = 'S';
                    }
                    hedgehogQ.offer(new int[] { nr, nc });
                }
            }

            time++;
        }

        System.out.println("KAKTUS");
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < R && c >= 0 && c < C;
    }

}