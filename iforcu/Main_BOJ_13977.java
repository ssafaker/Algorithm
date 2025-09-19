package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [이항 계수와 쿼리]
 *  링크   : [https://www.acmicpc.net/problem/13977]
 *  난이도 : [플래티넘5]
 *  설명   :
 *    - M개의 쿼리마다 N, K가 주어질 때, 이항 계수 (N C K)를 1,000,000,007로 나눈 나머지를 출력
 *    - 1 ≤ M ≤ 100,000, 1 ≤ N ≤ 4,000,000, 0 ≤ K ≤ N
 *    - 각 쿼리마다 (N C K) % 1,000,000,007을 빠르게 계산해야 함
 *  풀이   :
 *    - 페르마의 소정리를 이용한 모듈러 역원 활용
 *    - 팩토리얼을 미리 계산해두고, (N! / (K! * (N-K)!)) % MOD를 역원으로 처리
 *    - 쿼리마다 O(1)로 결과 계산
 */

public class Main_BOJ_13977 {
    static final int MOD = 1_000_000_007;
    static List<int[]> list;
    static int max;
    static long[] fact;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // 입력 처리 및 쿼리 정보 저장
        int M = Integer.parseInt(br.readLine());
        max = 0;
        list = new ArrayList<>();
        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());
            max = Math.max(max, N);
            list.add(new int[]{N, K});
        }

        // 팩토리얼 미리 계산 (1 ~ max)
        fact = new long[max + 1];
        fact[0] = 1;
        for (int i = 1; i <= max; i++) {
            fact[i] = (fact[i - 1] * i) % MOD;
        }

        // 각 쿼리에 대해 이항 계수 계산 및 결과 저장
        for (int[] arr : list) {
            sb.append(binomialCoefficient(arr[0], arr[1])).append("\n");
        }

        // 결과 출력
        System.out.println(sb.toString());
    }
    // 거듭제곱을 이용한 모듈러 역원 계산 (페르마의 소정리)
    static long power(long a, int b) {
        long res = 1L;
        a %= MOD;
        while (b > 0) {
            if(b % 2 == 1) res = (res * a) % MOD;
            a = (a * a) % MOD;
            b /= 2;
        }
        return res;
    }

    // 이항 계수 계산: n! / (k! * (n-k)!) mod MOD
    static long binomialCoefficient(int N, int K) {
        long numerator = fact[N];
        long denominator = (fact[K] * fact[N-K]) % MOD;

        // 모듈러 역원을 이용해 분모를 나눔
        long inverseDenominator = power(denominator, MOD - 2);

        long ans = (numerator * inverseDenominator) % MOD;
        return ans;
    }
}