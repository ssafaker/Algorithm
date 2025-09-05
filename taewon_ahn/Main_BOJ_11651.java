import java.io.*;
import java.util.*;

public class Main_BOJ_11651 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int N = Integer.parseInt(br.readLine());

		List<Node> arr = new ArrayList<>();
		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int x = Integer.parseInt(st.nextToken());
			int y = Integer.parseInt(st.nextToken());
			arr.add(new Node(x, y));
		}
		
		Collections.sort(arr);
		
		StringBuilder sb = new StringBuilder();
		for (Node n: arr) {
			sb.append(n.x).append(" ").append(n.y).append("\n");
		}
		System.out.print(sb);
	}

	static class Node implements Comparable<Node> {
		int x;
		int y;

		public Node(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int compareTo(Node n) {
			if (n.y != this.y) {
				return this.y - n.y;
			} else {
				return this.x - n.x;
			}
		}
	}
}
