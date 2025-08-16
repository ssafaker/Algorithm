package iforcu;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main_BOJ_9372 {

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int t = 0; t < T; t++) {
            String[] input = br.readLine().split(" ");
            int N = Integer.parseInt(input[0]);
            int M = Integer.parseInt(input[1]);

            for (int i = 0; i < M; i++) {
                String[] edge = br.readLine().split(" ");
                int a = Integer.parseInt(edge[0]);
                int b = Integer.parseInt(edge[1]);
            }

            System.out.println(N - 1);
        }
    }
}
