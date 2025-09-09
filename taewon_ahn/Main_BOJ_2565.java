import java.io.*;
import java.util.*;

public class Main_BOJ_2565 {
	static int N;
	static int[] wires;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());

		List<int[]> wires = new ArrayList<>();

		for (int i = 0; i < N; i++) {
			StringTokenizer st = new StringTokenizer(br.readLine());
			int s = Integer.parseInt(st.nextToken());
			int e = Integer.parseInt(st.nextToken());
			wires.add(new int[] {s, e});
		}

		Collections.sort(wires, ((a, b) -> a[0] - b[0] ));

		List<Integer> lis = new ArrayList<>();

		for (int[] w : wires) {
			int e = w[1];
			int idx = Collections.binarySearch(lis, e);

			if (idx < 0) idx = -(idx + 1); // 삽입 위치로 변환

			if (idx == lis.size()) lis.add(e);
			else lis.set(idx, e);
		}

		System.out.println(N - lis.size());
	}
}
