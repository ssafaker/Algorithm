package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [이항 계수 3]
 *  링크   : [https://www.acmicpc.net/problem/11401]
 *  난이도 : [골드 1]
 *  설명   :
 *    - 자연수 N, K가 주어질 때, 이항 계수 (N K)를 1,000,000,007로 나눈 나머지를 구하는 문제.
 *    - N은 최대 4,000,000, K는 0 이상 N 이하.
 *  풀이   :
 *    - 페르마의 소정리를 이용해 모듈러 역원을 구해 이항계수를 효율적으로 계산.
 *    - 팩토리얼을 미리 계산하고, 분모의 역원을 빠르게 구해 정답을 출력.
 */

public class Main_BOJ_11401 {
    static final int MOD = 1_000_000_007;
    public static void main(String[] args) throws Exception {
        // 입력 처리 및 변수 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 팩토리얼 값 미리 계산
        long[] fact = new long[N + 1];
        fact[0] = 1;
        for (int i = 1; i <= N; i++) {
            // fact[i] = i! % MOD
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // 이항계수 계산: 분자/분모
        long numerator = fact[N];
        long denominator = (fact[K] * fact[N-K]) % MOD;
        // 모듈러 역원 계산 (페르마의 소정리)
        long inverseDenominator = power(denominator, MOD - 2);
        // 정답 계산 및 출력
        long ans = (numerator * inverseDenominator) % MOD;
        System.out.println(ans);
    }
    // a^b % MOD를 빠르게 계산하는 함수 (거듭제곱 분할)
    static long power(long a, int b) {
        long res = 1L;
        a %= MOD;
        while (b > 0) {
            // 홀수 거듭제곱일 때 res에 곱함
            if(b % 2 == 1) res = (res * a) % MOD;
            // a를 제곱하여 거듭제곱 분할
            a = (a * a) % MOD;
            b /= 2;
        }
        return res;
    }
}