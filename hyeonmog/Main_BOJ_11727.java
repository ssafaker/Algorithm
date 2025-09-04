package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_BOJ_11727 {
    static int n;
    static int[] dp;
    public static void main(String[] args) throws  IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        if(n == 1) {
            System.out.println(1);
            return;
        }
        dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 3;
        for(int i = 3 ; i < n+1 ; i++) {
            if(i % 2 == 0) {
                dp[i] = (dp[i-2]*6 - dp[i-1]-2) % 10007;
            }
            else {
                dp[i] = (dp[i-1]*2-1)% 10007;
            }
        }

        System.out.println(dp[n] % 10007);
    }
}
