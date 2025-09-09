package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_BOJ_2839 {
    static int n;
    static int[] dp;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());

        dp = new int[n+5];


        for(int i = 3 ; i <= n+1 ; i += 3) {
            dp[i] = dp[i-3] + 1;
        }

        for(int i = 5 ; i <= n+1 ; i += 5) {
            dp[i] = dp[i-5]+1;
            for(int j = i + 3 ; j <= n+1 ; j+=3) {
                dp[j] = dp[i] + (j-i)/3;
            }
        }

        System.out.println(dp[n] == 0 ? -1 : dp[n]);
    }

}