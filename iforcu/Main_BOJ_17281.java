package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_17281 {
    static int N, ans;
    static int[][] player;
    static int[] order;
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        player = new int[N][9];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                player[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        visited = new boolean[9];
        order = new int[9];
        ans = Integer.MIN_VALUE;
        dfs(0);

        System.out.println(ans);
    }

    private static void dfs(int depth) {
        if(depth == 9){
            playBall();
            return;
        }

        if(depth == 3) {
            order[depth] = 0;
            dfs(depth + 1);
        } else {
            for (int i = 1; i < 9; i++) {
                if(!visited[i]) {
                    visited[i] = true;
                    order[depth] = i;
                    dfs(depth + 1);
                    visited[i] = false;
                }
            }
        }
    }

    static void playBall() {
        int score = 0;
        int index = 0;
        for (int i = 0; i < N; i++) {
            int outCount = 0;
            boolean[] bases =  new boolean[3];

            while(outCount < 3) {
                int hitter = order[index];
                int result = player[i][hitter];

                if(result == 0) outCount++;
                else if(result == 1) score += moveRunners(bases, 1);
                else if(result == 2) score += moveRunners(bases, 2);
                else if(result == 3) score += moveRunners(bases, 3);
                else if(result == 4) score += moveRunners(bases, 4);
                index = (index + 1) % 9;
            }
        }
        ans = Integer.max(ans, score);
    }

    static int moveRunners(boolean[] bases, int hit) {
        int score = 0;

        for (int i = 2; i >= 0; i--) {
            if(bases[i]) {
                int nextBase = i + hit;
                if(nextBase >= 3) score++;
                else bases[nextBase] = true;
                bases[i] = false;
            }
        }

        if(hit == 4) score++;
        else bases[hit-1] = true;
        return score;
    }
}
