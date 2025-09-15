package iforcu;


import java.io.*;
import java.util.*;

/*
 *  문제명 : [거의 최단 경로]
 *  링크   : [https://www.acmicpc.net/problem/5719]
 *  난이도 : [플래티넘 5]
 *  설명   :
 *    - 출발점에서 도착점까지의 최단 경로에 포함되지 않는 도로만 이용한 경로 중 가장 짧은 경로(거의 최단 경로)를 구하는 문제.
 *    - 다익스트라로 최단 경로를 찾고, 역추적(backtrack)으로 최단 경로에 포함된 간선을 표시한 뒤, 다시 다익스트라로 거의 최단 경로를 구한다.
 *    - 최단 경로가 없는 경우 -1을 출력한다.
 *  풀이   :
 *    - 첫 번째 다익스트라로 최단 경로 거리와 경로를 구한다.
 *    - 역추적(backtrack)으로 최단 경로에 포함된 간선을 표시한다.
 *    - 두 번째 다익스트라에서 표시된 간선을 제외하고 다시 최단 경로를 구해 출력한다.
 */

public class Main_BOJ_5719 {
    static int N, M;
    static int start, end;
    static List<Edge>[] edges;
    static List<Edge>[] reverseEdges;
    static int[] dist;
    static boolean[][] isShorten;

    static class Edge implements Comparable<Edge> {
        int u, w;
        Edge(int u, int w) {
            this.u = u;
            this.w = w;
        }
        public int compareTo(Edge o) {
            return this.w - o.w;
        }
    }

    // 입력 처리 및 연산 분기 (최단/거의 최단 경로)
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while(true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            if(N == 0 && M == 0) break;

            st = new StringTokenizer(br.readLine());
            start = Integer.parseInt(st.nextToken());
            end = Integer.parseInt(st.nextToken());

            edges = new ArrayList[N];
            reverseEdges = new ArrayList[N];
            for (int i = 0; i < N; i++) {
                edges[i] = new ArrayList<>(); 
                reverseEdges[i] = new ArrayList<>();
            }
            
            isShorten = new boolean[N][N];
            dist = new int[N];

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int v = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                edges[v].add(new Edge(u, w));
                reverseEdges[u].add(new Edge(v, w));
            }

            dijkstra(start, end, false);

            backtrack(start, end);

            dijkstra(start, end, true);

            if(dist[end] == Integer.MAX_VALUE) sb.append(-1).append("\n");
            else sb.append(dist[end]).append("\n");
        }
        System.out.println(sb.toString());
    }

    // 최단 경로에 포함된 간선을 역추적하여 표시하는 함수
    static void backtrack(int start, int end) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(end);

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            if(cur == start) continue;

            for (Edge prev : reverseEdges[cur]) {
                if(dist[prev.u] + prev.w == dist[cur]) {
                    if(!isShorten[prev.u][cur]) {
                        isShorten[prev.u][cur] = true;
                        queue.add(prev.u);
                    }
                }
            }
        }
    }

    // 다익스트라 알고리즘: 최단 경로 및 거의 최단 경로 계산
    // isSecond=true면 최단 경로에 포함된 간선을 제외하고 경로 탐색
    static void dijkstra(int start, int end, boolean isSecond) {
        Arrays.fill(dist, Integer.MAX_VALUE);
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        dist[start] = 0;
        pq.offer(new Edge(start, 0));

        while (!pq.isEmpty()) {
            Edge cur = pq.poll();

            if(cur.w > dist[cur.u]) continue;

            for (Edge next : edges[cur.u]) {
                if(isSecond && isShorten[cur.u][next.u]) continue;
                
                if(dist[next.u] > cur.w + next.w) {
                    dist[next.u] = cur.w + next.w;
                    pq.offer(new Edge(next.u, dist[next.u]));
                }
            }
        }
    }
}