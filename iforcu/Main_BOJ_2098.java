package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [외판원 순회 - BOJ 2098]
 * 난이도: [Gold 1]
 * 링크: [https://www.acmicpc.net/problem/2098]
 * 설명:
 *  - N개의 도시가 주어지고, 모든 도시를 한 번씩 방문 후 출발 도시(0번)로 돌아오는 최소 비용을 구하는 문제.
 *  - 이동 불가인 경우 비용은 0으로 주어짐.
 * 풀이:
 * 1. 비트마스킹 DP
 *    - dp[cur][mask] = 현재 `cur` 도시에 있고, 방문 집합이 mask일 때의 최소 비용
 *    - mask: 방문한 도시들을 비트로 표현 (1이면 방문)
 * 2. 점화식:
 *    dp[cur][mask] = min( dp[prev][mask ^ (1<<cur)] + W[prev][cur] )
 *    (단, prev → cur 경로가 존재해야 함)
 * 3. 초기화:
 *    - 시작은 0번 도시에서 출발, 즉 dp[0][1<<0] = 0
 * 4. 정답:
 *    - 모든 도시 방문(mask == FULL_MASK) 후, 다시 0번으로 돌아오는 최소 비용
 *    - ans = min( dp[i][FULL_MASK] + W[i][0] )
 */
public class Main_BOJ_2098 {
    static int N, ans;
    static int INF = 16 * 1_000_000 + 1; // 충분히 큰 값 (최대 비용보다 큼)
    static int[][] W;   // 비용 행렬
    static int[][] dp;  // dp[cur][mask]

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        W =  new int[N][N];
        final int FULL_MASK = (1 << N) - 1; // 모든 도시 방문 상태
        dp = new int[N][1 << N];

        // dp 배열 초기화
        for (int i = 0; i < N; i++) {
            Arrays.fill(dp[i], INF);
        }

        // 비용 행렬 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                W[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        // 시작점(도시 0) 초기화
        dp[0][1] = 0;

        // 모든 방문 상태(mask)에 대해 DP 갱신
        for (int mask = 0; mask <= FULL_MASK; mask++) {
            for (int cur = 0; cur < N; cur++) {
                if((mask & (1 << cur)) != 0) { // 현재 도시(cur)가 방문된 상태라면
                    for (int prev = 0; prev < N; prev++) {
                        if(cur != prev && (mask & (1 << prev)) != 0) {
                            if(W[prev][cur] != 0) { // 경로 존재해야 함
                                int newMask = mask ^ (1 << cur); // cur을 방문하기 전 상태
                                if(dp[prev][newMask] != INF) {
                                    dp[cur][mask] = Math.min(
                                        dp[cur][mask],
                                        dp[prev][newMask] + W[prev][cur]
                                    );
                                }
                            }
                        }
                    }
                }
            }
        }

        // 모든 도시 방문 후, 다시 0번 도시로 돌아오는 최소 비용 계산
        ans = INF;
        for (int i = 0; i < N; i++) {
            if(W[i][0] != 0) { // i → 0 경로 존재해야 함
                ans = Math.min(ans, dp[i][FULL_MASK] + W[i][0]);
            }
        }

        System.out.println(ans);
    }
}
