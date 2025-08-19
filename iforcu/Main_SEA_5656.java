package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [벽돌 깨기]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRQm6qfL0DFAUo&categoryId=AWXRQm6qfL0DFAUo&categoryType=CODE&problemTitle=%EB%B2%BD%EB%8F%8C+%EA%B9%A8%EA%B8%B0&orderBy=FIRST_REG_DATETIME&selectCodeLang=ALL&select-1=&pageSize=10&pageIndex=1]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 2차원 배열 사용 int[][] 구현
 *  - W 와 H 에 벽돌 값 존재
 *  - 구슬로 맨 위에 값 제거 
 *  - dfs를 이용한 모든 순열 시뮬레이션
 *  - 깨진 벽돌 로직은 BFS 진행? 
 *  - 원본맵과 부서진 벽돌 맵 구현을 통해 모든 벽돌 부서짐 진행
 *  - 제일 위의 값을 부심
 *  풀이:
 *  - 핀볼이 떨어질 수 있는 모든 경우의 수(중복 순열)를 DFS로 구현
 *  - 만약 핀볼이 떨어져도 부서지는 블록이 없으면 백트래킹
 *  - 핀볼이 떨어지면 부서지는 블록이 있으면 BFS- queue를 이용해 부서지는 로직 구현
 *  - 핀볼이 부서진 후 재정렬하는 로직 구현
 */

public class Main_SEA_5656 {
    static int N, W, H;
    static int[][] grid;
    static int[] dr = {1, 0, -1, 0};
    static int[] dc = {0, 1, 0, -1};
    static int count, ans;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            W = Integer.parseInt(st.nextToken());
            H = Integer.parseInt(st.nextToken());

            grid = new int[H][W];
            ans = Integer.MAX_VALUE;
            count = 0;
            for (int i = 0; i < H; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < W; j++) {
                    grid[i][j] = Integer.parseInt(st.nextToken());
                    if(grid[i][j] != 0) count++;
                }
            }

            dfs(0);
            
            System.out.println("#"+test_case+" "+ans);
        }        
    }

    static void dfs(int depth) {
        // N번 전부 사용 시
        if(depth == N) {
            int tempCount = 0;
            for (int[] arr : grid) {
                for (int i : arr) {
                    if(i != 0) tempCount++;
                }
            }
            ans = Math.min(ans, tempCount);
            return;
        }
        
        // 1부터 W까지 N번 선택하는 모든 중복 순열 탐색
        int[][] temp = new int[H][W]; // 원본 grid를 저장할 temp 2차원 배열
        for (int i = 0; i < W; i++) {
            for (int r = 0; r < H; r++) {
                temp[r] = Arrays.copyOf(grid[r], W);
            }
            crush(i);
            dfs(depth+1);
            for (int r = 0; r < H; r++) {
                grid[r] = Arrays.copyOf(temp[r], W);
            }
        }

        
    }

    // 해당 위치에 핀볼 크러쉬
    static void crush(int curC) {
        int curR = -1;
        for (int i = 0; i < H; i++) {
            if(grid[i][curC] != 0){
                curR = i;
                break;
            }
        }
        // 만약 처리되는 블록이 없다면 리턴
        if(curR == -1) return;
        
        // 핀볼 처리 BFS
        Queue<int[]> queue = new LinkedList<>();
        queue.offer(new int[] {curR,curC});
        while(!queue.isEmpty()) {
            int[] temp = queue.poll();
            int r = temp[0];
            int c = temp[1];
            int value = grid[r][c];
            grid[r][c] = 0;
            // 4방향
            for (int i = 0; i < 4; i++) {
                int nr = r;
                int nc = c;
                // value 값만큼 방향 탐색
                for (int v = 1; v < value; v++) {
                    nr += dr[i];
                    nc += dc[i];

                    if(!isValid(nr, nc)) continue; 

                    if(grid[nr][nc] != 0) {
                        // block 1이면 그냥 0으로 변경 아니면 queue 넣기
                        if(grid[nr][nc] == 1) {
                            grid[nr][nc] = 0;
                        } else {
                            queue.offer(new int[] {nr, nc});
                        }
                    }
                }
            }
        }

        // grid 아래 방향으로 재정렬
        for (int c = 0; c < W; c++) {
            int write = H-1;
            for (int r = H-1; r >= 0; r--) {
                if(grid[r][c] != 0) {
                    grid[write][c] = grid[r][c];
                    if(write!= r) grid[r][c] = 0;
                    write--;
                }
            }
            
            for (int r = write; r >= 0; r--) {
                grid[r][c] = 0;
            }
        }
    }

    static boolean isValid(int r, int c) {
        return r >= 0 && r < H && c >= 0 && c < W;
    }
}
