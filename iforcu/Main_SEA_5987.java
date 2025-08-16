package iforcu;

import java.util.*;
import java.io.*;
 
class Main_SEA_5987 {
    static int N, M;
    static int[] adj; 
    static long[] dp;
 
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
 
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
 
            adj = new int[N];
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int from = Integer.parseInt(st.nextToken()) - 1; 
                int to = Integer.parseInt(st.nextToken()) - 1;
                adj[to] |= (1 << from); 
            }
 
            dp = new long[1 << N];
            dp[0] = 1;
 
            for (int mask = 1; mask < (1 << N); mask++) {
                for (int i = 0; i < N; i++) {
                    if ((mask & (1 << i)) != 0 && (adj[i] & mask) == adj[i]) {
                        int prevMask = mask ^ (1 << i);
                        dp[mask] += dp[prevMask];
                    }
                }
            }
             
            System.out.println("#" + test_case + " " + dp[(1 << N) - 1]);
        }
    }
}