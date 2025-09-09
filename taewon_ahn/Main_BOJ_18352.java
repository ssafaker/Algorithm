import java.io.*;
import java.util.*;

public class Main_BOJ_18352 {
	static int N, M, K, X;
	static List<Integer>[] graph;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());
		X = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e= Integer.parseInt(st.nextToken());
			graph[s].add(e);
		}

		boolean[] visited = new boolean[N + 1];
		List<Integer> answer = new ArrayList<>();

		Queue<int[]> q = new ArrayDeque<>();
		q.offer(new int [] {X, 0});
		visited[X] = true;

		while (!q.isEmpty()) {
			int[] cur = q.poll();

			int c = cur[0];
			int dist = cur[1] + 1;

			for (int next: graph[c]) {
				if (visited[next]) continue;
				visited[next] = true;
				q.offer(new int[] {next, dist});
				if (dist == K) {
					answer.add(next);
				}
			}
		}

		Collections.sort(answer);

		if (answer.size() == 0) {
			System.out.println(-1);
			return;
		}

		for (int a : answer) {
			System.out.println(a);
		}
	}

}
