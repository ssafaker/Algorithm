import java.io.*;
import java.util.*;

public class Main_BOJ_1074 {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		int N = Integer.parseInt(st.nextToken());
		int r = Integer.parseInt(st.nextToken());
		int c = Integer.parseInt(st.nextToken());

		System.out.println(Z(N, r, c));
	}

	private static int Z(int n, int r, int c) {
		if (n == 0) {
			return 0;
		}

		int half = (int) Math.pow(2, n - 1);
		int size = half * half;

		if (r < half && c < half) {
			// 1사분면
			return Z(n - 1, r, c);
		} else if (r < half && c >= half) {
			// 2사분면
			return size + Z(n - 1, r, c - half);
		} else if (r >= half && c < half) {
			// 3사분면
			return 2 * size + Z(n - 1, r - half, c);
		} else {
			// 4사분면
			return 3 * size + Z(n - 1, r - half, c - half);
		}
	}

}
