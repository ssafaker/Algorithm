import java.io.*;
import java.util.*;

public class Main_BOJ_1219 {
	static int N, A, B, M;
	static List<Edge> edges;
	static int[] D;
	static int minINF = Integer.MIN_VALUE / 2;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		A = Integer.parseInt(st.nextToken());
		B = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		// 노선 등록
		edges = new ArrayList<>();
		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			int cost = Integer.parseInt(st.nextToken());
			edges.add(new Edge(s, e, cost));
		}

		int[] earns = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			earns[i] = Integer.parseInt(st.nextToken());
		}

		// 노선 가중치 갱신
		for (Edge e : edges) {
			e.w = earns[e.to] - e.cost;
		}

		D = new int[N];
		Arrays.fill(D, minINF);
		D[A] = earns[A];

		// 벨만포드
		for (int i = 0; i < N - 1; i++) {
			for (Edge e : edges) {
				if (D[e.from] != minINF && D[e.from] + e.w > D[e.to]) {
					D[e.to] = D[e.from] + e.w;
				}
			}
		}

		// 양의 사이클 체크
		boolean[] cycle = new boolean[N];
		for (int i = 0; i < N; i++) {
			for (Edge e : edges) {
				if (D[e.from] == minINF)
					continue;
				if (D[e.from] + e.w > D[e.to]) {
					D[e.to] = D[e.from] + e.w;
					cycle[e.to] = true;
				}
				if (cycle[e.from])
					cycle[e.to] = true;
			}
		}

		if (D[B] == minINF) {
			System.out.println("gg");
		} else if (cycle[B]) {
			System.out.println("Gee");
		} else {
			System.out.println(D[B]);
		}
	}

	static class Edge {
		int from;
		int to;
		int cost;
		int w;

		public Edge(int from, int to, int cost) {
			this.from = from;
			this.to = to;
			this.cost = cost;
		}
	}
}