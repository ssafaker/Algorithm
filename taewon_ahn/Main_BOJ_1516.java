import java.io.*;
import java.util.*;

public class Main_BOJ_1516 {
	static List<Integer>[] graph;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		graph = new List[N + 1];
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		int[] times = new int[N + 1];
		int[] indegree = new int[N + 1];

		for (int i = 1; i <= N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int n = Integer.parseInt(st.nextToken());
			times[i] = n;

			n = Integer.parseInt(st.nextToken());
			while (n != -1) {
				graph[n].add(i);
				indegree[i]++;
				n = Integer.parseInt(st.nextToken());
			}
		}

		int[] result = new int[N + 1];
		Queue<Integer> q = new ArrayDeque<>();

		for (int i = 1; i <= N; i++) {
			if (indegree[i] == 0) {
				q.add(i);
				result[i] = times[i];
			}
		}

		while (!q.isEmpty()) {
			int cur = q.poll();

			for (int next : graph[cur]) {
				indegree[next]--;
				result[next] = Math.max(result[next], result[cur] + times[next]);

				if (indegree[next] == 0) {
					q.offer(next);
				}
			}
		}

		for (int i = 1; i <= N; i++) {
			System.out.println(result[i]);
		}
	}

}
