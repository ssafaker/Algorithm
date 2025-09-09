package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main_BOJ_2631 {
    static int n;
    static int[] arr;
    static int[] dp;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        dp = new int[n];
        result = 1;
        for(int i = 0 ; i < n ; i++) {
            arr[i] = Integer.parseInt(br.readLine());
        }

        for(int k = 0 ; k < n ; k++) {
            dp[k] = 1;
            for(int i = 0 ; i < k ; i++) {
                if(arr[k] > arr[i]) {
                    dp[k] = Math.max(dp[k], dp[i]+1);
                }
            }
            result = Math.max(result, dp[k]);
        }

        System.out.println(n-result);

    }
}