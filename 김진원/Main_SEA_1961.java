
/**
 * 알고리즘: 구현, 시뮬레이션
 * 시간복잡도: O(n^2)
 */
import java.io.*;
import java.util.*;

public class Main_SEA_1961 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		StringTokenizer st;

		int T = Integer.parseInt(br.readLine()); // 테스트 케이스 개수 입력

		// 테스트 케이스 반복
		for (int t = 1; t <= T; t++) {

			int N = Integer.parseInt(br.readLine()); // 배열 크기 입력
			int[][] A = new int[N][N]; // N x N 배열 선언

			// 배열 값 입력
			for (int i = 0; i < N; i++) {
				st = new StringTokenizer(br.readLine());
				for (int j = 0; j < N; j++) {
					A[i][j] = Integer.parseInt(st.nextToken());
				}
			}

			// 각 행마다 90도, 180도, 270도 회전한 결과를 출력
			bw.write("#" + t + "\n");
			for (int i = 0; i < N; i++) {

				// 90도 회전
				for (int j = N - 1; j >= 0; j--) {
					bw.write(A[j][i] + "");
				}
				bw.write(" ");

				// 180도 회전
				for (int j = N - 1; j >= 0; j--) {
					bw.write(A[N - 1 - i][j] + "");
				}
				bw.write(" ");

				// 270도 회전
				for (int j = 0; j < N; j++) {
					bw.write(A[j][N - 1 - i] + "");
				}
				bw.write("\n");

			}
		}

		bw.flush();
		bw.close();
		br.close();
	}
}