package iforcu;

import java.io.*;

/*
 *  문제명: [본대 산책2]
 *  링크 : [https://www.acmicpc.net/problem/12850]
 *  난이도 : [플래티넘 5]
 *  설명: 
 *   - 정보과학관에서 출발하여 정확히 D분 후 정보과학관으로 돌아오는 경우의 수.
 *   - 건물 간 이동은 1분이 걸리며, 연결된 건물로만 이동 가능.
 *   - 8개 건물: 정보, 전산, 미래, 신양, 한경, 진리, 형남, 학생회관.
 *  풀이:
 *   - 인접 행렬로 건물 간 연결 관계 표현.
 *   - 행렬의 거듭제곱을 이용하여 D분 후의 경우의 수 계산.
 *   - 분할 정복을 이용한 행렬 거듭제곱으로 시간 복잡도 최적화.
 */

public class Main_BOJ_12850 {
    static long D;
    static int N = 8;
    final static int MOD = 1_000_000_007;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        D = Long.parseLong(br.readLine());
        long[][] adj = new long[N][N];

        // 건물 간 연결 관계 설정 (인접 행렬)
        adj[0][1] = 1;
        adj[0][2] = 1;
        adj[1][0] = 1;
        adj[1][2] = 1;
        adj[1][3] = 1;
        adj[2][0] = 1;
        adj[2][1] = 1;
        adj[2][3] = 1;
        adj[2][4] = 1;
        adj[3][1] = 1;
        adj[3][2] = 1;
        adj[3][4] = 1;
        adj[3][5] = 1;
        adj[4][2] = 1;
        adj[4][3] = 1;
        adj[4][5] = 1;
        adj[4][6] = 1;
        adj[5][3] = 1;
        adj[5][4] = 1;
        adj[5][7] = 1;
        adj[6][4] = 1;
        adj[6][7] = 1;
        adj[7][5] = 1;
        adj[7][6] = 1;

        if(D == 0) {
            System.out.println(1);
        } else {
            // 인접 행렬의 D제곱을 구하여 D분 후 경우의 수 계산
            long[][] result = power(adj, D);
            System.out.println(result[0][0]); // 정보과학관(0)에서 출발해서 정보과학관(0)으로 돌아오는 경우
        }
    }
    
    // 분할 정복을 이용한 행렬 거듭제곱
    static long[][] power(long[][] adj, long exp) {
        if(exp == 1L) return adj;

        long[][] half = power(adj, exp / 2);
        long[][] result = mul(half, half);

        if(exp % 2 == 1) result = mul(result, adj);
        return result;
    }

    // 두 행렬의 곱셈
    static long[][] mul(long[][] m1, long[][] m2) {
        long[][] result = new long[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for (int k = 0; k < N; k++) {
                    result[i][j] += m1[i][k] * m2[k][j];
                    result[i][j] %= MOD;
                }
            }
        }
        return result;
    }
}
