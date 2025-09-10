package iforcu;

import java.io.*;

/*
 *  문제명 : [N포커]
 *  링크   : [https://www.acmicpc.net/problem/16565]
 *  난이도 : [골드 2]
 *  설명   :
 *    - 52장의 트럼프 카드에서 N장을 뽑아 포카드(같은 숫자 4장)가 1쌍 이상 포함된 경우의 수를 구하는 문제.
 *    - 조합과 포함-배제 원리를 활용해 여러 쌍의 포카드가 포함된 경우까지 모두 계산한다.
 *    - 조합 테이블을 미리 계산해 효율적으로 경우의 수를 누적한다.
 *  풀이   :
 *    - comb[i][j]로 i개 중 j개를 뽑는 경우의 수를 미리 계산한다.
 *    - i쌍의 포카드가 포함된 경우의 수를 포함-배제 원리로 누적한다.
 *    - MOD로 나눈 나머지를 출력한다.
 */

public class Main_BOJ_16565 {
    static final int MOD = 10_007;
    static final int MAX = 53;
    static int N;
    static long[][] comb;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        if (N < 4) {
            System.out.println(0);
            return;
        }


        // 조합 테이블 미리 계산: comb[i][j] = i개 중 j개를 뽑는 경우의 수
        comb = new long[MAX][MAX];
        for (int i = 0; i < MAX; i++) {
            comb[i][0] = 1;
            for (int j = 1; j <= i; j++) {
                comb[i][j] = (comb[i-1][j-1] + comb[i-1][j]) % MOD;
            }
        }

        // 포함-배제 원리로 포카드가 1쌍 이상 포함된 경우의 수 누적
        long ans = 0;
        for (int i = 1; i * 4 <= N; i++) {
            long rank = comb[13][i]; // 13개 숫자 중 i개 선택
            long rest = comb[52 - 4 * i][N - 4 * i]; // 나머지 카드 선택
            long temp = (rank * rest) % MOD;

            // 홀수번째는 더하고, 짝수번째는 빼는 포함-배제 원리 적용
            if(i % 2 == 1) ans = (ans + temp) % MOD;
            else ans = (ans - temp + MOD) % MOD;
        }

        System.out.println(ans);
    }
}
