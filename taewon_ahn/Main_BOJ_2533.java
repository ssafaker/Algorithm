import java.io.*;
import java.util.*;

public class Main_BOJ_2533 {
	static int N;
	static List<Integer>[] graph;
	static int[][] dp;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		graph = new List[N + 1];
		for (int i = 1; i <= N; i++) {
			graph[i] = new ArrayList<>();
		}

		dp = new int[N + 1][2];

		for (int i = 0; i < N - 1; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int u = Integer.parseInt(st.nextToken());
			int v = Integer.parseInt(st.nextToken());
			graph[u].add(v);
			graph[v].add(u);
		}

		dfs(1, -1);

		System.out.println(Math.min(dp[1][0], dp[1][1]));
	}

	private static void dfs(int node, int parent) {
		dp[node][0] = 0; // 얼리어덥터 아님
		dp[node][1] = 1; // 얼리어덥터임

		for (int child : graph[node]) {
			if (child != parent) {
				dfs(child, node);

				// 해당 노드가 얼리어덥터가 아니면 모든 자식이 얼리어덥터
				dp[node][0] += dp[child][1];

				// 해당 노드가 얼리어덥터면, 자식은 얼리어덥터거나 아니거나 상과없음
				dp[node][1] += Math.min(dp[child][1], dp[child][0]);
			}

		}
	}
}