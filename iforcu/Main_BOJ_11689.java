package iforcu;

import java.io.*;

/*
 *  문제명 : [GCD(n, k) = 1]
 *  링크   : [https://www.acmicpc.net/problem/11689]
 *  난이도 : [골드 1]
 *  설명   :
 *    - 자연수 n에 대해 1 ≤ k ≤ n 중 GCD(n, k) = 1인 k의 개수를 구하는 문제.
 *    - 오일러 피 함수(Euler's Totient Function)를 활용해 효율적으로 계산한다.
 *    - n의 소인수 분해를 통해 각 소인수에 대해 곱셈 공식으로 결과를 누적한다.
 *  풀이   :
 *    - n을 소인수 분해하며 오일러 피 함수 공식(ans = ans / p * (p-1))을 적용한다.
 *    - 소인수마다 n에서 해당 소인수를 제거하며 반복한다.
 *    - 마지막에 남은 소인수에 대해 한 번 더 공식 적용 후 결과 출력.
 */

public class Main_BOJ_11689 {
    public static void main(String[] args) throws Exception {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        long n = Long.parseLong(br.readLine());
        long ans = n;
        // 오일러 피 함수 계산: n의 소인수 분해 반복
        for(long i = 2; i * i <= n; i++) {
            if(n % i == 0) {
                ans = ans / i * (i - 1); // 소인수 공식 적용
                while (n % i == 0) n /= i; // n에서 소인수 제거
            }
        }

        // 마지막 남은 소인수에 대해 공식 적용
        if(n > 1) ans = ans / n * (n-1);
        System.out.println(ans); // 결과 출력
    }
}
