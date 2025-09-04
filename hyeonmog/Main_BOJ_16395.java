package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_BOJ_16395 {
    static int n, k;
    static int[][] dp;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        k = Integer.parseInt(st.nextToken());
        dp = new int[n+1][n+1];
    
        paskal();

        System.out.println(dp[n][k-1]);
    }

    public static void paskal() {
        for(int i = 1 ; i < n+1 ; i++) {
            dp[i][0] = 1;
            for(int j = 1 ; j < i ; j++) {
                dp[i][j] = dp[i-1][j] + dp[i-1][j-1];
            }
        }
    }
}