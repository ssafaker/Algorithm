package ssafy.study.P0822;

/**
 * 알고리즘: 다익스트라
 * 시간복잡도: O((V+E)logV)
 */
import java.io.*;
import java.util.*;

public class Main_BOJ_1753 {
	static ArrayList<Edge>[] A; // 인접 리스트 간선 배열
	static int[] distance; // 시작 정점부터 각 정점까지의 최단 거리 저장 배열
	static boolean[] visited; // 정점 방문 여부 배열

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int V = Integer.parseInt(st.nextToken()); // 정점 개수
		int E = Integer.parseInt(st.nextToken()); // 간선 개수
		int K = Integer.parseInt(br.readLine()); // 시작 정점

		// 인접 리스트 초기화
		A = new ArrayList[V + 1];
		for (int i = 1; i <= V; i++) {
			A[i] = new ArrayList<>();
		}

		// 최단 거리 배열 초기화
		distance = new int[V + 1];
		for (int i = 0; i <= V; i++) {
			distance[i] = Integer.MAX_VALUE; // 거리 무한대로 초기화
		}
		// 방문 여부 배열 초기화
		visited = new boolean[V + 1];

		// 간선 정보 입력
		for (int i = 0; i < E; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()); // 출발 노드
			int v = Integer.parseInt(st.nextToken()); // 도착 노드
			int w = Integer.parseInt(st.nextToken()); // 가중치
			A[u].add(new Edge(v, w)); // 인접 리스트에 간선 정보 저장
		}

		dijkstra(K); // 다익스트라 알고리즘 수행하여 최단 거리 배열 완성

		// 최단 거리 출력
		for (int i = 1; i <= V; i++) {
			if (visited[i])
				bw.write(distance[i] + "\n");
			else
				bw.write("INF\n"); // 방문할 수 없으면 "INF" 출력
		}

		bw.flush();
		bw.close();
		br.close();
	}

	// 다익스트라 알고리즘
	public static void dijkstra(int K) {
		// 우선순위 큐를 활용해 현재까지 최단 거리가 짧은 정점을 우선으로 꺼냄
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		distance[K] = 0; // 시작 정점의 거리는 0으로 설정
		pq.offer(new Edge(K, 0)); // 시작 정점 우선순위 큐에 넣기

		while (!pq.isEmpty()) {

			// 현재 최단 거리가 가장 짧은 정점 꺼내기
			Edge now = pq.poll();
			int v = now.v;

			// 이미 방문한 정점은 스킵
			if (visited[v])
				continue;

			visited[v] = true; // 방문 처리

			// 현재 정점 v와 연결된 모든 인접 정점 탐색
			for (Edge e : A[v]) {
				int nv = e.v; // 다음 정점
				int nw = e.w; // 현재 정점(v)에서 다음 정점(nv)까지 가중치

				// 시작정점 부터 현재 정점까지 거리가 더 짧으면 갱신
				if (distance[nv] > distance[v] + nw) {
					distance[nv] = distance[v] + nw;
					pq.add(new Edge(nv, distance[nv])); // 갱신된 최단 거리로 우선순위 큐에 추가
				}
			}
		}
	}

	// 간선 정보를 저장하기 위한 클래스
	static class Edge implements Comparable<Edge> {
		int v; // 도착 정점
		int w; // 가중치

		Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}

		// 가중치를 기준으로 오름차순
		@Override
		public int compareTo(Edge o) {
			return Integer.compare(this.w, o.w);
		}
	}
}