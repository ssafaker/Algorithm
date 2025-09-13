package iforcu;

import java.io.*;
import java.util.*;

/*
 * BOJ 15824 - 너 봄에는 캡사이신이 맛있단다
 * https://www.acmicpc.net/problem/15824
 * 난이도: 골드2
 * 문제: N개의 음식 매운맛 정도가 주어지고, 부분집합들의 (최댓값 - 최솟값)의 합을 구하는 문제
 * 해결방법:
 * 1. 배열을 정렬한 후, 각 원소가 최댓값/최솟값이 되는 부분집합의 개수를 계산
 * 2. i번째 원소가 최댓값이 되는 경우: 2^i개 (자신보다 작거나 같은 원소들로 구성)
 * 3. i번째 원소가 최솟값이 되는 경우: 2^(N-1-i)개 (자신보다 크거나 같은 원소들로 구성)
 * 4. 각 원소의 기여도 = arr[i] * (최댓값 횟수 - 최솟값 횟수)
 */
public class Main_BOJ_15824 {
    static final long MOD = 1_000_000_007L;
    static int N;
    static long[] arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        arr = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 정렬을 통해 각 원소가 최댓값/최솟값이 되는 경우를 쉽게 계산
        Arrays.sort(arr);

        long ans = 0;
        for (int i = 0; i < N; i++) {
            // i번째 원소가 최댓값이 되는 부분집합의 개수: 2^i
            long maxCnt = pow(2, i);          
            // i번째 원소가 최솟값이 되는 부분집합의 개수: 2^(N-1-i)
            long minCnt = pow(2, N - 1 - i);    
            // 해당 원소의 전체 기여도 = (최댓값으로 기여 - 최솟값으로 기여)
            long contrib = (maxCnt - minCnt + MOD) % MOD; 
            ans = (ans + arr[i] * contrib) % MOD;
        }
        System.out.println(ans);
    }

    // 분할정복을 이용한 거듭제곱 (모듈러 연산)
    static long pow(long base, long exp) {
        if (exp == 0) return 1;

        long half = pow(base, exp / 2);
        long result = (half * half) % MOD;
        
        if (exp % 2 == 1) result = (result * base) % MOD;

        return result;
    }
}
