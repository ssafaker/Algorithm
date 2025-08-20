package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_15651 {
    static int N, M;
    static int[] select;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        select = new int[M];
        dfs(0);
        System.out.println(sb);
    }

    static void dfs(int depth) {
        if(depth == M) {
            for (int i : select) sb.append(i+" ");
            sb.append("\n");
            return;
        }
        
        for (int i = 1; i <= N; i++) {
            select[depth] = i;
            dfs(depth + 1);
        }
    }
}
