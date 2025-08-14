package ssafy.study.P0801;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main_ETC_돌던지기파장 {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(System.in);
		int T = sc.nextInt();
		for (int tc = 1; tc <= T; tc++) {
			int N = sc.nextInt();
			int[][] A = new int[N + 2][N + 2];
			for (int i = 0; i < N + 2; i++) {
				for (int j = 0; j < N + 2; j++) {
					if (i == 0 || j == 0 || i == N + 1 || j == N + 1) {
						A[i][j] = -1;
					}
				}
			}
			int rockCount = sc.nextInt();
			int[] rockPower = new int[rockCount];
			for (int i = 0; i < rockCount; i++) {
				rockPower[i] = sc.nextInt();
			}
			int[][] rockMap = new int[rockCount][2];
			for (int i = 0; i < rockCount; i++) {
				rockMap[i][0] = sc.nextInt();
				rockMap[i][1] = sc.nextInt();
			}
			int max = 0;
			for (int i = 0; i < rockCount; i++) {
				int x = rockMap[i][0];
				int y = rockMap[i][1];
				A[x][y]++;
				max = Math.max(max, A[x][y]);
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x - j][y - j] != -1) {
						A[x - j][y - j]++;
						max = Math.max(max, A[x - j][y - j]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x - j][y] != -1) {
						A[x - j][y]++;
						max = Math.max(max, A[x - j][y]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x - j][y + j] != -1) {
						A[x - j][y + j]++;
						max = Math.max(max, A[x - j][y + j]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x][y + j] != -1) {
						A[x][y + j]++;
						max = Math.max(max, A[x][y + j]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x + j][y + j] != -1) {
						A[x + j][y + j]++;
						max = Math.max(max, A[x + j][y + j]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x + j][y] != -1) {
						A[x + j][y]++;
						max = Math.max(max, A[x + j][y]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x + j][y - j] != -1) {
						A[x + j][y - j]++;
						max = Math.max(max, A[x + j][y - j]);
					} else
						break;
				}
				for (int j = 1; j <= rockPower[i]; j++) {
					if (A[x][y - j] != -1) {
						A[x][y - j]++;
						max = Math.max(max, A[x][y - j]);
					} else
						break;
				}
			}
			System.out.println("#" + tc + " " + max);
		}
		sc.close();
	}

}
