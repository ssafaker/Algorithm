package iforcu;

import java.io.*;

/**
 * 문제: [계단 수]
 * 난이도: [Gold 1]
 * 링크: [https://www.acmicpc.net/problem/1562]
 *
 * 설명:
 *  - 계단 수: 인접한 자리의 차이가 1인 수
 *  - 길이가 N인 계단 수 중에서 0~9 모든 숫자가 적어도 한 번씩 등장하는 수의 개수를 구하는 문제
 *  - 답은 1,000,000,000 으로 나눈 나머지를 출력
 *
 * 풀이:
 * 1. DP 정의
 *    dp[len][lastDigit][mask] =
 *        길이가 len이고 마지막 숫자가 lastDigit이며,
 *        등장한 숫자들의 상태(mask)를 가진 계단 수의 개수
 *
 *    - mask는 0~9 각 숫자의 등장 여부를 비트로 표현 (bitmasking)
 *    - 예: mask= 0000010101 (2진수) → 0,2,4번 숫자가 등장
 *
 * 2. 초기화
 *    - 길이가 1일 때, 1~9로 시작 가능 (0은 시작 불가)
 *    - dp[1][i][1<<i] = 1 (i=1~9)
 *
 * 3. 점화식
 *    - 이전 숫자에서 이동:
 *      if (lastDigit > 0) → lastDigit-1에서 올 수 있음
 *      if (lastDigit < 9) → lastDigit+1에서 올 수 있음
 *    - 새로운 mask = 기존 mask | (1<<lastDigit)
 *
 * 4. 정답
 *    - 길이가 N이고, mask가 FULL_MASK(=1111111111, 즉 0~9 모두 등장)인 경우의 합
 */
public class Main_BOJ_1562 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());

        long[][][] dp = new long[N+1][10][1<<10]; // dp 배열
        final int MOD = 1000000000;
        final int FULL_MASK = (1<<10) - 1; // 0~9 모두 등장한 상태 (1111111111)

        // 초기화: 한 자리 수 (1~9)
        for (int i = 1; i <= 9; i++) {
            dp[1][i][1<<i] = 1;
        }

        // DP 채우기
        for (int len = 2; len <= N; len++) {
            for (int lastDigit = 0; lastDigit <= 9; lastDigit++) {
                for (int mask = 0; mask < (1<<10); mask++) {
                    int newMask = mask | (1 << lastDigit); // 이번 자리 포함한 mask

                    long count = 0;
                    // 이전 자리에서 lastDigit로 올 수 있는 경우
                    if(lastDigit > 0) count += dp[len - 1][lastDigit - 1][mask];
                    if(lastDigit < 9) count += dp[len - 1][lastDigit + 1][mask];

                    dp[len][lastDigit][newMask] = (dp[len][lastDigit][newMask] + count) % MOD;
                }
            }
        }

        // 정답: 길이 N에서, mask가 FULL_MASK인 경우의 합
        long totalCount = 0;
        for (int lastDigit = 0; lastDigit <= 9; lastDigit++) {
            totalCount = (totalCount + dp[N][lastDigit][FULL_MASK]) % MOD;
        }

        System.out.println(totalCount);
    }
}
