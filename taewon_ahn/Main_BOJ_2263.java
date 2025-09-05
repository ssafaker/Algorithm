import java.io.*;
import java.util.*;

public class Main_BOJ_2263 {
	static int N;
	static int[] inOrder;
	static int[] postOrder;
	static HashMap<Integer, Integer> inMap = new HashMap<>();

	static StringBuilder sb = new StringBuilder();

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		inOrder = new int[N];
		StringTokenizer st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			inOrder[i] = Integer.parseInt(st.nextToken());
			inMap.put(inOrder[i], i);
		}

		postOrder = new int[N];
		st = new StringTokenizer(br.readLine());
		for (int i = 0; i < N; i++) {
			postOrder[i] = Integer.parseInt(st.nextToken());
		}

		solve(0, N - 1, 0, N -1);
		System.out.println(sb);
	}

	private static void solve(int inStart, int inEnd, int postStart, int postEnd) {
	    if (inStart > inEnd || postStart > postEnd) return;

	    int root = postOrder[postEnd];
	    sb.append(root).append(" "); // 루트 먼저 출력

	    int rootIdx = inMap.get(root);
	    int leftSize = rootIdx - inStart;

	    // 왼쪽 서브트리
	    solve(inStart, rootIdx - 1, postStart, postStart + leftSize - 1);

	    // 오른쪽 서브트리
	    solve(rootIdx + 1, inEnd, postStart + leftSize, postEnd - 1);
	}
}
