package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_BOJ_10986 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        long[] S = new long[N + 1];
        long[] C = new long[M];
        st = new StringTokenizer(br.readLine());
        for (int i = 1; i <= N; i++) {
            S[i] = S[i - 1] + Long.parseLong(st.nextToken());
        }
        long answer = 0;
        for (int i = 1; i <= N; i++) {
            int remainder = (int) (S[i] % M);
            if (remainder == 0) answer++;
            if (remainder >= 0) C[remainder]++;
        }
        for (int i = 0; i < M; i++) {
            if (C[i] > 1) {
                answer += (C[i] * (C[i] - 1)) / 2;
            }
        }
        System.out.println(answer);
    }
}
