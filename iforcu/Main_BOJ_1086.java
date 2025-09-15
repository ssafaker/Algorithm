package iforcu;

import java.io.*;
/*
 *  문제명 : [박성원]
 *  링크   : [https://www.acmicpc.net/problem/1086]
 *  난이도 : [플래티넘 5]
 *  설명   :
 *    - N개의 자연수로 이루어진 집합의 모든 순열을 이어붙여 K로 나누어떨어지는 경우의 확률을 구하는 문제.
 *    - 각 수의 길이가 최대 50, N은 최대 15, K는 최대 100.
 *    - 정답을 기약분수 형태로 출력해야 한다.
 *  풀이   :
 *    - 모든 순열을 탐색하면 시간 초과이므로, DP와 비트마스크를 활용해 경우의 수를 효율적으로 계산.
 *    - 각 수를 이어붙일 때 나머지 정보를 관리하며, 순열별로 K로 나누어떨어지는 경우의 수를 구함.
 *    - 전체 경우의 수(분모)와 K로 나누어떨어지는 경우의 수(분자)를 구해 기약분수로 출력.
 */

public class Main_BOJ_1086 {
    static int N, K;
    static String[] arr; 
    static int[] rems;    
    static int[] lens;   
    static int[] tens_rem; 
    static long[][] dp;
    public static void main(String[] args) throws Exception {
    // 입력 처리
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new String[N];
        rems = new int[N];
        lens = new int[N];

        // 각 수의 문자열과 길이 저장
        // 각 수를 K로 나눈 나머지 계산
        for (int i = 0; i < N; i++) {
            arr[i] = br.readLine();
            lens[i] = arr[i].length();
        }

        K = Integer.parseInt(br.readLine());

        for (int i = 0; i < N; i++) {
            rems[i] = stringMod(arr[i], K);
        }

        tens_rem =  new int[51];
        // 10^i % K 값 미리 계산하여 저장
        tens_rem[0] = 1;
        for (int i = 1; i <= 50; i++) {
            tens_rem[i] = (tens_rem[i-1] * 10) % K;
        }

        dp = new long[1 << N][K];
        // dp[mask][rem]: mask에 해당하는 수들을 사용해 나머지가 rem인 경우의 수
        dp[0][0] = 1;

        for (int mask = 0; mask < (1 << N); mask++) {
            // 모든 부분집합(mask)과 나머지(rem)에 대해 DP 갱신
            for (int rem = 0; rem < K; rem++) {
                // 현재 상태에서 사용하지 않은 수 i를 추가해 다음 상태로 전이
                if(dp[mask][rem] > 0) {
                    for (int i = 0; i < N; i++) {
                        if((mask & (1 << i)) == 0) {
                            int next_mask = mask | (1 << i);
                            int next_rem = (rem * tens_rem[lens[i]] + rems[i]) % K;
                            dp[next_mask][next_rem] += dp[mask][rem];
                        }
                    }
                }
            }
        }

        long numerator = dp[(1<<N) - 1][0];
        // 모든 수를 사용한 순열 중 K로 나누어떨어지는 경우의 수(분자)
        long denominator = 1;
        // 전체 순열의 개수(분모): N!
        for (int i = 2; i <= N; i++) {
            denominator *= i;
        }

        if (numerator == 0) System.out.println("0/1");
        // 확률이 0인 경우
        else if (numerator == denominator) System.out.println("1/1");
        // 확률이 1인 경우
        else {
            // 확률을 기약분수로 출력
            long divison = gcd(numerator, denominator);
            System.out.println((numerator / divison) + "/" + (denominator / divison));
        }
    }

    // 문자열 s를 k로 나눈 나머지 계산
    static int stringMod(String s, int k) {
        int result = 0;
        for (int i = 0; i < s.length(); i++) {
            result = (result * 10 + (s.charAt(i) - '0')) % k;
        }
        return result;
    }

    // 최대공약수(GCD) 계산
    private static long gcd(long a, long b) {
        if(b == 0) return a;
        return gcd(b, a%b);
    }
}