package iforcu;


import java.io.*;
import java.util.*;

/*
 *  문제명 : [히스토그램에서 가장 큰 직사각형]
 *  링크   : [https://www.acmicpc.net/problem/6549]
 *  난이도 : [플래티넘 5]
 *  설명   :
 *    - 높이가 주어진 히스토그램에서 가장 넓이가 큰 직사각형의 넓이를 구하는 문제.
 *    - 스택을 이용해 각 높이별로 최대 넓이를 효율적으로 계산한다.
 *    - 입력이 여러 테스트 케이스로 주어지며, 각 케이스마다 결과를 출력한다.
 *  풀이   :
 *    - 스택에 인덱스를 저장하며, 현재 높이보다 작은 높이가 나오면 넓이를 계산한다.
 *    - 스택이 남아있을 때도 넓이를 계산해 최대값을 갱신한다.
 *    - 각 테스트 케이스마다 최대 넓이를 출력한다.
 */

public class Main_BOJ_6549 {
    static int N;
    static long[] h;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            if(N == 0) break;
            h = new long[N];
            for (int i = 0; i < N; i++) {
                h[i] = Long.parseLong(st.nextToken());
            }
            
            // 스택을 이용해 히스토그램에서 최대 넓이 계산
            Stack<Integer> stack = new Stack<>();
            long maxArea = 0;

            for (int i = 0; i < N; i++) {
                // 현재 높이보다 작은 높이가 나오면 넓이 계산
                while (!stack.isEmpty() && h[stack.peek()] >= h[i]) {
                    long height = h[stack.pop()];
                    long width = stack.isEmpty() ? i : i - stack.peek() - 1;
                    maxArea = Math.max(maxArea, height * width);
                }
                stack.push(i);
            }

            // 스택에 남아있는 높이들도 넓이 계산
            while (!stack.isEmpty()) {
                long height = h[stack.pop()];
                long width = stack.isEmpty() ? N : N - stack.peek() - 1;
                maxArea = Math.max(maxArea, height * width);
            }

            sb.append(maxArea).append("\n");
        }
        System.out.println(sb.toString());
    }
}
