package iforcu;

import java.io.*;
import java.util.*;

/*
 * 문제명: [다리 만들기 2]
 * 링크 : https://www.acmicpc.net/problem/17472
 * 난이도 : 골드 1
 * 설명:
 * - N x M 지도에 여러 개의 섬(1로 표시)이 존재.
 * - 다리는 상하좌우 직선으로만 연결할 수 있고, 다른 섬을 가로지르지 못함.
 * - 다리 길이는 2 이상이어야 함.
 * - 모든 섬을 연결하는 다리들의 길이 합의 최솟값을 구하는 문제.
 * - 모든 섬을 연결할 수 없다면 -1 출력.
 * 풀이:
 * 1. BFS로 각 섬에 고유한 번호(색깔)를 부여 → 구분 가능하게 함.
 * 2. 각 섬에서 다른 섬으로 뻗어나가며 다리 후보(간선) 탐색.
 *    - 다리 길이가 2 이상일 때만 유효.
 *    - 간선 리스트에 (from, to, cost) 형태로 저장.
 * 3. 크루스칼(Kruskal) MST 알고리즘 사용:
 *    - 모든 다리를 비용 기준으로 정렬.
 *    - Union-Find(Disjoint Set)으로 사이클 방지하며 연결.
 * 4. 모든 섬이 연결되면 다리 길이 합 출력, 아니면 -1 출력.
 */

public class Main_BOJ_17472 {
    static int N, M;
    static int[][] grid;
    static boolean[][] visited;
    static int[] dr = {-1, 0, 1, 0};  // 상, 우, 하, 좌
    static int[] dc = {0, 1, 0, -1};

    static ArrayList<Edge> edges;
    static int[] parent;
    static int islandCount;

    // 다리를 표현하는 간선 클래스
    static class Edge implements Comparable<Edge> {
        int from, to, cost;
        
        public Edge(int from, int to, int cost) {
            this.from = from;
            this.to = to;
            this.cost = cost;
        }

        public int compareTo(Edge other) {
            return Integer.compare(this.cost, other.cost);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        grid = new int[N][M];
        visited = new boolean[N][M];

        // 지도 입력 받기
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < M; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 1. BFS로 섬 라벨링 (2번부터 시작해서 섬 번호 부여)
        int color = 2;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(grid[i][j] == 1) bfs(i, j, color++);
            }
        }
        islandCount = color - 2; // 실제 섬의 개수

        // 2. 다리 후보 간선 탐색
        edges = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < M; j++) {
                if(grid[i][j] != 0) {
                    findBridges(i, j, grid[i][j]);
                }
            }
        }

        // 3. 크루스칼을 위한 Union-Find 준비
        parent = new int[islandCount + 2];
        for (int i = 2; i <= islandCount+1; i++) {
            parent[i] = i;
        }

        // 간선 정렬
        Collections.sort(edges);

        // 4. MST 구성
        int totalBridgesLength = 0;
        int connectedIslands = 0;

        for (Edge edge : edges) {
            if(find(edge.from) != find(edge.to)) {
                union(edge.from, edge.to);
                totalBridgesLength += edge.cost;
                connectedIslands++;
            }
        }

        // 모든 섬이 연결되었는지 확인
        if(connectedIslands == islandCount - 1) System.out.println(totalBridgesLength);
        else System.out.println(-1);
    }

    // Union-Find - Find
    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union-Find - Union
    static void union(int x, int y) {
        int rootX = find(x);
        int rootY = find(y);
        if(rootX != rootY) parent[rootY] = rootX;
    }

    // 특정 섬에서 다른 섬으로 다리 후보 탐색
    static void findBridges(int r, int c, int num) {
        for (int i = 0; i < 4; i++) {
            int curR = r;
            int curC = c;
            int bridgeLength = 0;

            while(true) {
                curR += dr[i];
                curC += dc[i];

                if(!isValid(curR, curC)) break;         // 범위 밖
                if(grid[curR][curC] == num) break;      // 같은 섬
                if(grid[curR][curC] == 0){              // 바다 → 다리 연장
                    bridgeLength++;
                } else { // 다른 섬 도착
                    if(bridgeLength >= 2) edges.add(new Edge(num, grid[curR][curC], bridgeLength));
                    break;
                }
            }
        }
    }

    // BFS로 섬 라벨링
    static void bfs(int r, int c, int color) {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {r,c});
        grid[r][c] = color;
        visited[r][c] = true;

        while (!queue.isEmpty()) {
            int[] temp =  queue.poll();
            int curR = temp[0];
            int curC = temp[1];
            for (int i = 0; i < 4; i++) {
                int nr = curR + dr[i];
                int nc = curC + dc[i];
                
                if(!isValid(nr, nc)) continue;
                if(grid[nr][nc] == 1 && !visited[nr][nc]) {
                    grid[nr][nc] = color;
                    visited[nr][nc] = true;
                    queue.offer(new int[] {nr, nc});
                }
            }
        }
    }

    // 범위 체크
    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < M;
    }
}
