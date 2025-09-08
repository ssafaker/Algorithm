import java.io.*;
import java.util.*;

public class Main_BOJ_1389 {
	static int N, M;
	static List<Integer>[] graph;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());

		graph = new List[N + 1];
		for (int i = 0; i < N + 1; i++) {
			graph[i] = new ArrayList<>();
		}

		for (int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			graph[s].add(e);
			graph[e].add(s);
		}

		int minDepth = Integer.MAX_VALUE;
		int minUser = -1;

		for (int i = 1; i <= N; i++) {
			boolean[] visited = new boolean[N + 1];
			Queue<int[]> q = new ArrayDeque<>();

			q.offer(new int[] { i, 0 }); // 현재 노드, depth
			visited[i] = true;

			int totalDepth = 0;

			while (!q.isEmpty()) {
				int[] cur = q.poll();
				int curDepth = cur[1];

				totalDepth += curDepth;

				for (int next : graph[cur[0]]) {
					if (visited[next])
						continue;
					visited[next] = true;
					q.offer(new int[] {next, curDepth + 1});
				}
			}
			
			if (totalDepth < minDepth) {
				minDepth = totalDepth;
				minUser = i;
			}
		}
		
		System.out.println(minUser);
	}
}
