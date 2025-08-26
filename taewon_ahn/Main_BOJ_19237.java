import java.io.*;
import java.util.*;

public class Main_BOJ_19237 {
    static int N, M, k;
    static int[] dr = { 0, -1, 1, 0, 0 };
    static int[] dc = { 0, 0, 0, -1, 1 };
    static Smell[][] smells;
    static Shark[] sharks;
    static int[][][] directions;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());

        sharks = new Shark[M + 1];
        smells = new Smell[N][N];
        directions = new int[M + 1][5][4]; // 상어 id, 상하좌우 (1~4), 우선순위 방향

        // 초기 상어 & 냄새
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int id = Integer.parseInt(st.nextToken());
                if (id > 0) {
                    sharks[id] = new Shark(id, i, j);
                    smells[i][j] = new Smell(id, k);
                }
            }
        }

        // 상어 방향 등록
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= M; i++) {
            sharks[i].dir = Integer.parseInt(st.nextToken());
        }

        // 상어 우선순위 등록
        for (int i = 1; i <= M; i++) {
            for (int d = 1; d <= 4; d++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < 4; j++) {
                    directions[i][d][j] = Integer.parseInt(st.nextToken());
                }
            }
        }

        int time = 0;
        while (!isFinish() && time <= 1000) {
            // 상어 이동
            for (int i = 1; i <= M; i++) {
                boolean canMove = false;
                Shark s = sharks[i];
                if (!s.isAlive)
                    continue;
                // 우선순위에 따라 인접한 냄새없는 칸 찾기
                for (int d : directions[s.id][s.dir]) {
                    int nr = s.r + dr[d];
                    int nc = s.c + dc[d];
                    if (check(nr, nc) && smells[nr][nc] == null) {
                        canMove = true;
                        s.r = nr;
                        s.c = nc;
                        s.dir = d;
                        break;
                    }
                }

                if (!canMove) {
                    for (int d : directions[s.id][s.dir]) {
                        int nr = s.r + dr[d];
                        int nc = s.c + dc[d];
                        if (check(nr, nc) && smells[nr][nc].sharkId == s.id) {
                            s.r = nr;
                            s.c = nc;
                            s.dir = d;
                            break;
                        }
                    }
                }
            }

            // 겹치는 상어 처리
            HashMap<String, Integer> sharkMap = new HashMap<>();
            for (int i = 1; i <= M; i++) {
                Shark s = sharks[i];
                if (!s.isAlive)
                    continue;
                String key = s.r + "," + s.c;
                if (sharkMap.containsKey(key)) {
                    if (sharkMap.get(key) > s.id) {
                        sharks[sharkMap.get(key)].isAlive = false;
                        sharkMap.put(key, s.id);
                    } else {
                        s.isAlive = false;
                    }
                } else {
                    sharkMap.put(key, s.id);
                }
            }

            // 냄새 제거
            for (int i = 0; i < N; i++) {
                for (int j = 0; j < N; j++) {
                    if (smells[i][j] == null)
                        continue;
                    if (--smells[i][j].time == 0) {
                        smells[i][j] = null;
                    }
                }
            }

            // 이동 위치에 냄새 뿌리기
            for (int i = 1; i <= M; i++) {
                Shark s = sharks[i];
                if (!s.isAlive)
                    continue;
                smells[s.r][s.c] = new Smell(s.id, k);
            }

            time++;
        }

        System.out.println(time > 1000 ? -1 : time);
    }

    private static boolean check(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }

    private static boolean isFinish() {
        int aliveCnt = 0;
        for (int i = 1; i <= M; i++) {
            if (sharks[i].isAlive)
                aliveCnt++;
        }
        if (aliveCnt <= 1) {
            return true;
        } else {
            return false;
        }
    }

    static class Shark {
        int id, r, c, dir;
        boolean isAlive = true;

        public Shark(int id, int r, int c) {
            this.id = id;
            this.r = r;
            this.c = c;
        }
    }

    static class Smell {
        int sharkId;
        int time;

        public Smell(int sharkId, int time) {
            this.sharkId = sharkId;
            this.time = time;
        }
    }
}