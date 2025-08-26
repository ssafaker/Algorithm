import java.io.*;
import java.util.*;

public class Main_BOJ_15686 {
    static int N, M, answer = Integer.MAX_VALUE;
    static List<int[]> houses = new ArrayList<>();
    static List<int[]> chickens = new ArrayList<>();
    static boolean[] selected;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        // 집, 치킨집 위치 저장
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                int val = Integer.parseInt(st.nextToken());
                if (val == 1) {
                    houses.add(new int[] { i, j });
                } else if (val == 2) {
                    chickens.add(new int[] { i, j });
                }
            }
        }

        selected = new boolean[chickens.size()];
        comb(0, 0);
        System.out.println(answer);
    }

    private static void comb(int start, int count) {
        if (count == M) {
            answer = Math.min(answer, calcDist());
            return;
        }

        for (int i = start; i < chickens.size(); i++) {
            selected[i] = true;
            comb(i + 1, count + 1);
            selected[i] = false;
        }
    }

    private static int calcDist() {
        int totalDist = 0;

        for (int[] house : houses) {
            int minDist = Integer.MAX_VALUE;
            for (int i = 0; i < chickens.size(); i++) {
                if (selected[i]) {
                    int[] chicken = chickens.get(i);
                    int dist = Math.abs(house[0] - chicken[0]) + Math.abs(house[1] - chicken[1]);
                    minDist = Math.min(dist, minDist);
                }
            }
            totalDist += minDist;
        }

        return totalDist;
    }
}