import java.io.*;

public class Main_BOJ_10844 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int N = Integer.parseInt(br.readLine());
        long MOD = 1000000000L;

        long[][] dp = new long[10][N + 1];

        for (int i = 1; i <= 9; i++) {
            dp[i][1] = 1L;
        }

        for (int i = 2; i <= N; i++) {
            for (int j = 0; j <= 9; j++) {
                if (j == 0) {
                    dp[j][i] = dp[1][i - 1] % MOD;
                } else if (j == 9) {
                    dp[j][i] = dp[8][i - 1] % MOD;
                } else {
                    dp[j][i] = (dp[j - 1][i - 1] + dp[j + 1][i - 1]) % MOD;
                }
            }
        }

        // N자리 계단 수의 총 개수 구하기
        long sum = 0;
        for (int i = 0; i <= 9; i++) {
            sum = (sum + dp[i][N]) % MOD;
        }

        System.out.println(sum);
    }
}