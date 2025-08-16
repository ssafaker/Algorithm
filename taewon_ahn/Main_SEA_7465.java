import java.io.*;
import java.util.*;

public class Main_SEA_7465 {
    static int N, M;
    static List<Integer>[] arr;
    static boolean[] visited;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            arr = new ArrayList[N + 1];
            visited = new boolean[N + 1];
            for (int i = 0; i <= N; i++) {
                arr[i] = new ArrayList<>();
            }

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int start = Integer.parseInt(st.nextToken());
                int end = Integer.parseInt(st.nextToken());
                arr[start].add(end);
                arr[end].add(start);
            }

            int groupCnt = 0;
            for (int i = 1; i <= N; i++) {
                if (!visited[i]) {
                    dfs(i);
                    groupCnt++;
                }
            }
            System.out.printf("#%d %d\n", tc, groupCnt);
        }
    }

    private static void dfs(int i) {
        visited[i] = true;

        for (Integer node : arr[i]) {
            if (!visited[node]) {
                dfs(node);
            }
        }
    }
}