package ssafy.study.P0801;

import java.io.*;
import java.util.*;

public class Main_BOJ_2477 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int K = Integer.parseInt(br.readLine());
		int[] A = new int[6];
		StringTokenizer st;
		for (int i = 0; i < 6; i++) {
			st = new StringTokenizer(br.readLine());
			st.nextToken();
			A[i] = Integer.parseInt(st.nextToken());
		}
		int mulSum = 0;
		int max = 0;
		for (int i = 0; i < 6; i++) {
			mulSum += A[i] * A[(i + 1) % 6];
			max = Math.max(max, A[i] * A[(i + 1) % 6]);
		}
		int no = 3 * max - mulSum;
		bw.write((max - no) * K + "\n");
		bw.flush();
		bw.close();
		br.close();
	}
}