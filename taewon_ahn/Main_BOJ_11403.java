import java.io.*;
import java.util.*;

public class Main_BOJ_11403 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		int N = Integer.parseInt(br.readLine());
		
		List<Integer>[] graph = new List[N];
		for (int i = 0; i < N; i++) {
			graph[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				int n =  Integer.parseInt(st.nextToken());
				if (n > 0) {
					graph[i].add(j);
				}
			}
		}
		
		for (int i = 0; i < N; i++) {
			boolean[] visited = new boolean[N];
			Queue<Integer> q = new ArrayDeque<Integer>();
			q.offer(i);
			
			while (!q.isEmpty()) {
				int cur = q.poll();
				
				for (int next: graph[cur]) {
					if (!visited[next]) {
						visited[next] = true;
						q.offer(next);
					}
				}
			}
			
			for (int j = 0; j < N; j++) {
				if (visited[j]) {
					sb.append(1);
				} else {
					sb.append(0);
				}
				sb.append(" ");
			}
			sb.append("\n");
		}
		System.out.println(sb);
	}
}
