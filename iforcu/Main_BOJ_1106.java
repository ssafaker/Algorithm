package iforcu;

import java.io.*;
import java.util.*;

/**
 * 문제: [호텔]
 * 난이도: [Gold 4]
 * 링크: [https://www.acmicpc.net/problem/1106]
 * 설명:
 *  각 도시에서 일정 비용으로 특정 인원을 모집할 수 있을 때,
 *  적어도 C명 이상의 고객을 모집하는 최소 비용을 구하는 문제.
 * 풀이:
 *  - dp[i] = i명의 고객을 모집하는 데 필요한 최소 비용.
 *  - 각 도시의 (비용, 인원)을 이용해 dp를 갱신.
 *  - 인원을 더 모집할 수 있으므로, dp를 unbounded 방식으로 채움.
 *  - 최소 C명 이상일 때의 dp[i] 중 최솟값을 정답으로 선택.
 */
public class Main_BOJ_1106 {
    static int N, C, ans;
    static int[] dp;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        C = Integer.parseInt(st.nextToken());
        N = Integer.parseInt(st.nextToken());

        dp = new int[C + 101]; // 안전 여유 공간 확보
        Arrays.fill(dp, Integer.MAX_VALUE);
        dp[0] = 0;

        // 각 도시 입력 처리
        for (int i = 0; i < N; i++) {
            st = new StringTokenizer(br.readLine());
            int cost = Integer.parseInt(st.nextToken());
            int people = Integer.parseInt(st.nextToken());

            // 무한 배낭 방식: 동일 도시를 여러 번 선택 가능
            for (int j = people; j < C + 101; j++) {
                if (dp[j - people] != Integer.MAX_VALUE) {
                    dp[j] = Math.min(dp[j], dp[j - people] + cost);
                }
            }
        }

        ans = Integer.MAX_VALUE;
        for (int i = C; i < C + 101; i++) {
            ans = Math.min(ans, dp[i]);
        }
        System.out.println(ans);
    }
}