package ssafy.study.P0801;

import java.io.*;
import java.util.*;

public class Main_BOJ_22866 {
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
		int N = Integer.parseInt(br.readLine());
		StringTokenizer st = new StringTokenizer(br.readLine());
		int[] H = new int[N];
		Deque<Integer> stack = new ArrayDeque<>();
		int[] count = new int[N];
		int[] near = new int[N];
		// 초기화 밑 왼쪽 탐색
		for (int i = 0; i < N; i++) {
			H[i] = Integer.parseInt(st.nextToken()); // 높이 초기화
			near[i] = -100000;
			while (!stack.isEmpty() && H[stack.peek()] <= H[i])
				stack.pop(); // 왼쪽 나보다 작은거 다빼
			count[i] = stack.size(); // 왼쪽 보이는 건물 세기
			if (stack.size() > 0)
				near[i] = stack.peek(); // 왼쪽 가장 가까운 건물 번호
			stack.push(i);
		}
		stack.clear();
		// 오른쪽 탐색
		for (int i = N - 1; i >= 0; i--) {
			while (!stack.isEmpty() && H[stack.peek()] <= H[i])
				stack.pop(); // 오른쪽 나보다 작은거 다빼
			count[i] += stack.size(); // 오른쪽 보이는 건물 세기
			if (stack.size() > 0 && stack.peek() - i < i - near[i])
				near[i] = stack.peek(); // 오른쪽 가장 가까운 건물 번호 넣는데 왼쪽 가까운 건물이랑 거리가 같으면 왼쪽이 우선
			stack.push(i);
		}
		for (int i = 0; i < N; i++) {
			bw.write(count[i] + "");
			if (count[i] > 0) {
				bw.write(" " + (near[i] + 1) + "\n");
			} else {
				bw.write("\n");
			}
		}
		bw.flush();
		bw.close();
		br.close();
	}
}