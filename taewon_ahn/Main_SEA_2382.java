
import java.io.*;
import java.util.*;

public class Main_SEA_2382 {
    static int N, M, K;
    static int[] dr = { 0, -1, 1, 0, 0 };
    static int[] dc = { 0, 0, 0, -1, 1 };
    static List<미생물> arr;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());
            arr = new ArrayList<>();
            for (int i = 0; i < K; i++) {
                st = new StringTokenizer(br.readLine());
                int r = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int cnt = Integer.parseInt(st.nextToken());
                int dir = Integer.parseInt(st.nextToken());
                arr.add(new 미생물(r, c, cnt, dir));
            }

            // 정해진 시간 만큼 시뮬
            for (int i = 0; i < M; i++) {
                미생물이동();

                약물경계처리();

                결합처리();
            }

            // 미생물 수 출력
            int totalCnt = 0;
            for (미생물 el : arr) {
                totalCnt += el.cnt;
            }
            System.out.printf("#%d %d\n", tc, totalCnt);
        }
    }

    private static void 미생물이동() {
        for (미생물 el : arr) {
            el.r = el.r + dr[el.dir];
            el.c = el.c + dc[el.dir];
        }
    }

    private static void 약물경계처리() {
        for (미생물 el : arr) {
            if (el.r <= 0) {
                el.dir = 2;
                el.cnt = el.cnt / 2;
            } else if (el.r >= N - 1) {
                el.dir = 1;
                el.cnt = el.cnt / 2;
            } else if (el.c <= 0) {
                el.dir = 4;
                el.cnt = el.cnt / 2;
            } else if (el.c >= N - 1) {
                el.dir = 3;
                el.cnt = el.cnt / 2;
            }

        }
    }

    private static void 결합처리() {
        // r,c로 그룹화
        HashMap<String, List<미생물>> map = new HashMap<>();
        for (미생물 el : arr) {
            String key = el.r + "," + el.c;
            map.computeIfAbsent(key, k -> new ArrayList<>()).add(el);
        }

        arr.clear();

        for (List<미생물> elements : map.values()) {
            if (map.size() == 1) {
                arr.add(elements.get(0));
            } else {
                int totalCnt = 0;
                int max = 0;
                int maxDir = 0;

                for (미생물 el : elements) {
                    totalCnt += el.cnt;
                    if (el.cnt > max) {
                        max = el.cnt;
                        maxDir = el.dir;
                    }
                }

                arr.add(new 미생물(elements.get(0).r, elements.get(0).c, totalCnt, maxDir));
            }
        }
    }

    static class 미생물 {
        int r, c, cnt, dir;

        public 미생물(int r, int c, int cnt, int dir) {
            this.r = r;
            this.c = c;
            this.cnt = cnt;
            this.dir = dir;
        }
    }
}
