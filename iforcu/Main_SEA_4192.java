package iforcu;

import java.io.*;
import java.util.*;
 
public class Main_SEA_4192 {
    static int N;
    static int[][] map;
    static boolean[][] visited;
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static StringTokenizer st;
    static int startR, startC;
    static int targetR, targetC;
     
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
         
        for(int test_case = 1; test_case <= T; test_case++)
        {
            N = Integer.parseInt(br.readLine());
             
            map = new int[N][N];
            visited = new boolean[N][N];
             
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    map[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            st = new StringTokenizer(br.readLine());
            startR = Integer.parseInt(st.nextToken());
            startC = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            targetR = Integer.parseInt(st.nextToken());
            targetC = Integer.parseInt(st.nextToken());
             
            System.out.printf("#%d %d\n", test_case, bfs());
        }
    }
 
    static int bfs() {
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {startR, startC, 0});
        visited[startR][startC] = true;
         
        while(!queue.isEmpty()) {
            int[] temp = queue.poll();
            int r = temp[0];
            int c = temp[1];
            int cost = temp[2];
             
            if(r == targetR && c == targetC) {
                return cost;
            }
             
            for (int i = 0; i < 4; i++) {
                int nr = r + dr[i];
                int nc = c + dc[i];
                 
                if(!isValid(nr,nc)) continue;
                 
                if(!visited[nr][nc] && map[nr][nc] == 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[] {nr, nc, cost+1});
                }
            }
        }
         
        return -1;
    }
     
    static boolean isValid(int r, int c) {
        return r >= 0 && r < N && c >= 0 && c < N;
    }
}