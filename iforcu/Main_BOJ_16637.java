package iforcu;

import java.io.*;

/*
 *  문제명: [괄호 추가하기]
 *  링크 : [https://www.acmicpc.net/problem/16637]
 *  난이도 : [Gold 3]
 *  설명:
 *  - 주어진 수식에 괄호를 적절히 추가하여 만들 수 있는 최댓값을 구하는 문제.
 *  - 괄호는 중첩 불가, 연산자는 +, -, * 만 존재.
 *  - 수식 길이 N(<=19).
 *
 *  풀이:
 *  - DFS(재귀)로 현재 인덱스에서 두 가지 선택:
 *    1) 괄호를 추가하지 않고 연산 수행.
 *    2) 다음 숫자와 괄호를 묶어서 먼저 계산 후 연산 수행.
 *  - 백트래킹으로 모든 가능한 괄호 배치를 탐색하여 최대값(ans)을 갱신.
 */

public class Main_BOJ_16637 {
    static int N, ans;
    static char[] arr;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());   // 수식 길이
        String s = br.readLine();
        arr = s.toCharArray();                 // 수식을 문자 배열로 변환

        ans = Integer.MIN_VALUE;
        dfs(0, 0);

        System.out.println(ans);
    }

    // DFS 탐색: idx는 현재 인덱스, result는 현재까지 계산값
    static void dfs(int idx, int result) {
        // 수식 끝까지 왔다면 최대값 갱신
        if (idx >= N) {
            ans = Math.max(ans, result);
            return;
        }

        char cur = arr[idx];

        // 맨 처음 숫자는 그대로 시작값으로 사용
        if (idx == 0) {
            dfs(idx + 2, cur - '0');
            return;
        }

        // 이전 연산자와 현재 숫자를 이용해 계산
        char op = arr[idx - 1];
        int num = cur - '0';
        dfs(idx + 2, calculate(result, op, num));

        // 괄호를 추가할 수 있다면 (현재 숫자와 다음 숫자 묶기)
        if (idx + 2 < N) {
            int a = cur - '0';
            char nextOp = arr[idx + 1];
            int b = arr[idx + 2] - '0';

            // (a nextOp b) 를 먼저 계산
            int bracketResult = calculate(a, nextOp, b);

            // result op (괄호 결과) 계산
            dfs(idx + 4, calculate(result, op, bracketResult));
        }
    }

    // 사칙연산 처리
    static int calculate(int a, char op, int b) {
        switch (op) {
            case '+': return a + b;
            case '-': return a - b;
            case '*': return a * b;
        }
        return 0;
    }
}
