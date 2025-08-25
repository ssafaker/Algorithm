package ssafy.study.P0822;

/**
 * 알고리즘: 다익스트라
 * 시간복잡도: O((V+E)logV)
 */
import java.io.*;
import java.util.*;

public class Main_BOJ_1916 {
	// 인접 리스트를 이용해 그래프를 표현 (각 노드에서 갈 수 있는 간선들의 리스트)
	static ArrayList<Edge>[] A;

	// 시작점에서 각 노드까지의 최소 거리 값을 저장할 배열
	static int[] distance;

	// 방문 여부를 기록할 배열 (최단 거리 확정 여부)
	static boolean[] visited;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		// N: 도시(노드) 개수, M: 버스(간선) 개수
		int N = Integer.parseInt(br.readLine());
		int M = Integer.parseInt(br.readLine());

		// 인접 리스트 초기화
		A = new ArrayList[N + 1];
		for (int i = 1; i <= N; i++) {
			A[i] = new ArrayList<>();
		}

		// 거리 배열을 무한대로 초기화
		distance = new int[N + 1];
		Arrays.fill(distance, Integer.MAX_VALUE);

		// 방문 배열 초기화
		visited = new boolean[N + 1];

		StringTokenizer st;
		// M개의 간선 정보 입력
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken()); // 출발 도시
			int v = Integer.parseInt(st.nextToken()); // 도착 도시
			int w = Integer.parseInt(st.nextToken()); // 비용

			// 인접 리스트에 간선 추가
			A[u].add(new Edge(v, w));
		}

		// 출발점(s)과 도착점(e) 입력
		st = new StringTokenizer(br.readLine());
		int s = Integer.parseInt(st.nextToken());
		int e = Integer.parseInt(st.nextToken());

		// 다익스트라 알고리즘 수행 후 결과 출력
		bw.write(dijkstra(s, e) + "\n");

		bw.flush();
		bw.close();
		br.close();
	}

	/**
	 * 다익스트라(Dijkstra) 알고리즘 시작점 s에서 도착점 e까지의 최소 비용을 구하는 함수
	 */
	public static int dijkstra(int s, int e) {
		// 우선순위 큐(PriorityQueue): 현재까지 비용이 가장 적은 경로를 우선 탐색
		PriorityQueue<Edge> pq = new PriorityQueue<>();

		// 시작 노드를 큐에 추가 (시작점까지의 비용은 0)
		pq.offer(new Edge(s, 0));
		distance[s] = 0;

		while (!pq.isEmpty()) {
			// 현재 노드 꺼내기
			int now = pq.poll().v;

			// 이미 방문한 노드라면 스킵
			if (visited[now])
				continue;

			// 현재 노드 방문 처리
			visited[now] = true;

			// 현재 노드와 연결된 인접 노드들 탐색
			for (Edge edge : A[now]) {
				int nv = edge.v; // 인접 노드
				int nw = edge.w; // 가중치(비용)

				// 현재 노드를 거쳐서 nv까지 가는 비용이 더 작으면 갱신
				if (distance[nv] > distance[now] + nw) {
					distance[nv] = distance[now] + nw;
					pq.offer(new Edge(nv, distance[nv])); // 갱신된 거리로 큐에 추가
				}
			}
		}
		// 도착 노드까지의 최소 비용 반환
		return distance[e];
	}

	/**
	 * Edge 클래스: 노드와 해당 노드까지의 비용을 표현 PriorityQueue에서 비용을 기준으로 오름차순 정렬하기 위해
	 * Comparable 구현
	 */
	static class Edge implements Comparable<Edge> {
		int v; // 도착 노드
		int w; // 해당 노드까지의 비용

		Edge(int v, int w) {
			this.v = v;
			this.w = w;
		}

		@Override
		public int compareTo(Edge o) {
			// 비용(w)을 기준으로 오름차순 정렬
			return Integer.compare(this.w, o.w);
		}
	}
}