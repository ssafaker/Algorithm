package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_BOJ_1260 {
    static int N, M, V;
    static ArrayList<Integer>[] A;
    static boolean[] visited;
    static StringBuilder sb;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        V = Integer.parseInt(st.nextToken());
        A = new ArrayList[N + 1];

        for (int i = 0; i <= N; i++) {
            A[i] = new ArrayList<>();
        }
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int start = Integer.parseInt(st.nextToken());
            int end = Integer.parseInt(st.nextToken());
            A[start].add(end);
            A[end].add(start);
        }
        for (int i = 0; i <= N; i++) {
            Collections.sort(A[i]);
        }

        sb = new StringBuilder();
        visited = new boolean[N + 1];
        dfs(V);
        System.out.println(sb);

        sb = new StringBuilder();
        visited = new boolean[N + 1];
        bfs(V);
        System.out.println(sb);
    }

    private static void bfs(int node) {
        Queue<Integer> queue = new ArrayDeque<>();
        queue.offer(node);
        visited[node] = true;

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            sb.append(cur).append(" ");

            for (int next : A[cur]) {
                if (!visited[next]) {
                    visited[next] = true;
                    queue.offer(next);
                }
            }
        }
    }

    private static void dfs(int node) {
        visited[node] = true;
        sb.append(node).append(" ");

        for (int next : A[node]) {
            if (!visited[next]) {
                dfs(next);
            }
        }
    }
}
