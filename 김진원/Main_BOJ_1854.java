package ssafy.study.P0822;

/**
 * 알고리즘: 다익스트라 변형
 * 시간복잡도: O(MKlog(NK))
 */
import java.io.*;
import java.util.*;

public class Main_BOJ_1854 {
    // 인접 리스트 그래프
    static ArrayList<Edge>[] A;

    // 각 정점까지의 경로 길이들을 담는 PQ 배열 (정점별 최대 힙, 크기 K)
    // distancePQ[v]에는 "정점 v까지의 경로 길이"들을 넣되, 항상 K개 이하로 유지
    static PriorityQueue<Integer>[] distancePQ;

    // K번째 최단 경로를 구할 때의 K
    static int K;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        // N: 정점 수, M: 간선 수, K: K번째 최단
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        // 그래프 초기화
        A = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = new ArrayList<>();
        }

        // 각 정점별로 "경로 길이들을 담는 PQ(최대 힙)" 준비
        // 최대 힙을 쓰는 이유: K개를 초과하는 순간, 가장 큰(=K개 중 가장 나쁜) 값을 바로 제거하기 위함
        distancePQ = new PriorityQueue[N + 1];
        Comparator<Integer> cp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                // 내림차순 -> 최대 힙
                return Integer.compare(o2, o1);
            }
        };
        for (int i = 0; i <= N; i++) {
            distancePQ[i] = new PriorityQueue<>(K, cp);
        }

        // 간선 입력 (단방향)
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int u = Integer.parseInt(st.nextToken()); // 출발
            int v = Integer.parseInt(st.nextToken()); // 도착
            int w = Integer.parseInt(st.nextToken()); // 가중치(거리)
            A[u].add(new Edge(v, w));
        }

        // 시작 정점은 문제 조건상 1 (요구사항에 따라 바꿀 수 있음)
        dijkstra(1);

        // 각 정점 i의 K번째 최단 경로가 존재하면 distancePQ[i].peek()이 바로 그 값
        // (최대 힙이므로 K개 중 가장 큰 값 = K번째로 짧은 경로)
        for (int i = 1; i <= N; i++) {
            if (distancePQ[i].size() == K) {
                bw.write(distancePQ[i].peek() + "\n");
            } else {
                bw.write("-1\n"); // K개가 안 되면 존재하지 않음
            }
        }

        bw.flush();
        bw.close();
        br.close();
    }

    /**
     * 다익스트라 변형:
     * - 전역 우선순위큐 pq: (누적거리 오름차순) 최소 힙 -> 가장 짧은 경로 후보부터 확장
     * - 각 정점별 distancePQ[v]: 최대 힙, 크기 ≤ K -> v까지의 경로 상위 K개만 유지
     */
    public static void dijkstra(int s) {
        // 전역 PQ: (정점, 누적거리)를 누적거리 오름차순으로 꺼내는 최소 힙
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        pq.add(new Edge(s, 0));

        // 시작 정점 s까지의 거리 0을 등록
        distancePQ[s].offer(0);

        while (!pq.isEmpty()) {
            Edge now = pq.poll();
            int v = now.v;  // 현재 정점
            int w = now.w;  // s -> v 까지의 누적 거리

            // v에서 나가는 모든 간선에 대해 relax 시도
            for (Edge e : A[v]) {
                int nv = e.v;      // 다음 정점
                int nw = e.w;      // 간선 가중치
                int newDist = w + nw; // s -> nv 까지의 새 경로 길이

                // 1) 아직 nv까지 모아둔 경로가 K개 미만이면 무조건 추가
                if (distancePQ[nv].size() < K) {
                    distancePQ[nv].offer(newDist);
                    pq.add(new Edge(nv, newDist));
                }
                // 2) 이미 K개가 꽉 찼는데, 새 경로가 "K개 중 최악(=peek)"보다 더 짧으면 교체
                else if (distancePQ[nv].peek() > newDist) {
                    distancePQ[nv].poll();          // 최악인 경로 제거
                    distancePQ[nv].offer(newDist);  // 더 나은 새 경로 추가
                    pq.add(new Edge(nv, newDist));  // 이 경로를 기반으로 다시 확장
                }
                // 3) 그 외(더 나쁘면) 버림
            }
        }
    }

    /**
     * Edge: (다음 정점 v, 지금까지의 누적 거리 w)
     * 전역 PQ에서 누적 거리 오름차순으로 정렬되도록 Comparable 구현
     */
    static class Edge implements Comparable<Edge> {
        int v;
        int w;

        Edge(int v, int w) {
            this.v = v;
            this.w = w;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.w, o.w); // 누적 거리 오름차순
        }
    }
}