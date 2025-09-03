package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [사회망 서비스(SNS)]
 *  링크 : [https://www.acmicpc.net/problem/2533]
 *  난이도 : [골드 3]
 *  설명: 
 *   - 친구 관계가 트리 형태로 주어질 때, 얼리 어답터의 최소 수를 구하는 문제.
 *   - 얼리 어답터가 아닌 사람은 모든 친구가 얼리 어답터여야 새 아이디어 수용.
 *   - 모든 사람이 새 아이디어를 수용하도록 하는 얼리 어답터의 최소 개수.
 *  풀이:
 *   - 트리 DP를 사용하여 각 노드가 얼리 어답터인 경우와 아닌 경우를 계산.
 *   - dp[i][0]: i번 노드가 얼리 어답터가 아닐 때 최소 개수.
 *   - dp[i][1]: i번 노드가 얼리 어답터일 때 최소 개수.
 */

public class Main_BOJ_2533 {
    static int N;
    static List<Integer>[] tree;
    static int[][] dp; // dp[i][0]: i가 얼리어답터 X, dp[i][1]: i가 얼리어답터 O
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        tree = new ArrayList[N+1];
        for (int i = 0; i <= N; i++) tree[i] = new ArrayList<>();
        
        // 트리 구성
        for (int i = 0; i < N-1; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken());
            int u = Integer.parseInt(st.nextToken());
            tree[v].add(u);
            tree[u].add(v);
        }

        dp = new int[N+1][2];
        visited = new boolean[N+1];

        dfs(1); // 1번 노드를 루트로 DFS 시작
        
        System.out.println(Math.min(dp[1][0], dp[1][1]));
    }

    // 트리 DP로 각 노드에서의 최소 얼리 어답터 수 계산
    static void dfs(int u) {
        visited[u] = true;
        dp[u][0] = 0; // u가 얼리 어답터가 아닌 경우
        dp[u][1] = 1; // u가 얼리 어답터인 경우

        for (int v : tree[u]) {
            if(!visited[v]) {
                dfs(v);

                // u가 얼리 어답터가 아니면 모든 자식이 얼리 어답터여야 함
                dp[u][0] += dp[v][1];
                // u가 얼리 어답터면 자식은 어떤 상태든 상관없음
                dp[u][1] += Math.min(dp[v][0], dp[v][1]);
            }
        }
    }
}
