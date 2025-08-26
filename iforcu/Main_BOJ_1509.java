package iforcu;

import java.io.*;

/**
 * 문제: [팰린드롬 분할]
 * 난이도: [Gold 1]
 * 링크: [https://www.acmicpc.net/problem/1509]
 * 설명:
 *  - 문자열 s를 팰린드롬 부분 문자열로 분할할 때,
 *    분할 개수의 최솟값을 구하는 문제.
 * 풀이:
 * 1. 팰린드롬 판별 전처리
 *  - isPalindrome[i][j] = s[i..j]가 팰린드롬인지 여부
 *  - 길이가 1 또는 2인 경우 바로 처리
 *  - 길이가 3 이상이면 양 끝 문자가 같고, 내부 문자열이 팰린드롬인지 확인
 * 2. DP 계산
 *  - dp[i] = s[0..i] 구간을 팰린드롬 분할했을 때 최소 분할 개수
 *  - 점화식:
 *      dp[i] = min(dp[i], dp[j-1] + 1) (단, s[j..i]가 팰린드롬일 때)
 *      j == 0이면 dp[i] = 1 (전체가 팰린드롬인 경우)
 * 3. 정답 = dp[N-1]
 */
public class Main_BOJ_1509 {
    static boolean[][] isPalindrome; // 팰린드롬 여부 저장
    static int[] dp; // 최소 분할 수 DP 배열

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        int N = s.length();
        isPalindrome = new boolean[N+1][N+1];
        dp = new int[N];

        // 1. 팰린드롬 전처리
        for (int len = 1; len <= N; len++) {
            for (int start = 0; start + len - 1 < N; start++) {
                int end = start + len - 1;
                if (s.charAt(start) == s.charAt(end)) {
                    if (len <= 2) isPalindrome[start][end] = true;
                    else isPalindrome[start][end] = isPalindrome[start + 1][end - 1];
                }
            }
        }

        // 2. DP로 최소 분할 개수 구하기
        for (int i = 0; i < N; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j <= i; j++) {
                if (isPalindrome[j][i]) {
                    if (j == 0) dp[i] = 1; // 전체가 팰린드롬
                    else dp[i] = Math.min(dp[i], dp[j-1] + 1);
                }
            }
        }

        // 3. 결과 출력
        System.out.println(dp[N - 1]);
    }
}
