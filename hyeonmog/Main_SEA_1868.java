package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

class Main_SEA_1868 {
    static final int[] dx = {-1, -1, 0, 1, 1, 1, 0, -1};
    static final int[] dy = {0, 1, 1, 1, 0, -1, -1, -1};
    static int n;
    static char[][] arr;
    static boolean[][] visited;
    static int[][] record;
    static int result;
    static public void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            n = Integer.parseInt(br.readLine());
            arr = new char[n][n];
            visited = new boolean[n][n];
            record = new int[n][n];
            result = 0;

            for(int i = 0 ; i < n ; i++) {
                String str = br.readLine();
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = str.charAt(j);
                }
            }


            createRecord();
            //0먼저 클릭
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    if(record[i][j] == 0 && !visited[i][j] && arr[i][j] == '.') {
                        bfs(i, j);
                        result++;
                    }
                }
            }

            // //나머지들 클릭
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    if(!visited[i][j] && arr[i][j] == '.') {
                        visited[i][j] = true;
                        result++;
                    }
                }
            }


            System.out.println("#" + testCase + " " + result);
        }
    }

    static public void bfs(int x, int y) {
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        visited[x][y] = true;
        dq.offer(new int[]{x, y});

        while(!dq.isEmpty()) {
            int[] cur = dq.poll();
            x = cur[0];
            y = cur[1];

            for(int dir = 0 ; dir < 8 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if(visited[nx][ny]) continue;
                if(arr[nx][ny] == '*') continue;
                if(record[nx][ny] == 0) {
                    dq.offer(new int[]{nx, ny});
                    visited[nx][ny] = true;
                }
                else {
                    visited[nx][ny] = true;
                }
            }
        }
    }

    static public void createRecord() {
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(arr[i][j] == '*') continue;
                int cnt = 0;
                for(int dir = 0 ; dir < 8 ; dir++) {
                    int nx = i + dx[dir];
                    int ny = j + dy[dir];
                    if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                    if(arr[nx][ny] == '*') cnt++;
                }
                record[i][j] = cnt;
            }
        }
    }

}