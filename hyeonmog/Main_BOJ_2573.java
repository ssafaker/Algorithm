package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Main_BOJ_2573 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int n, m;
    static int[][] arr;
    static boolean[][] visited;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        visited = new boolean[n][m];
        result = 0;


        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        int iceCnt = cntIce();

        while(iceCnt == 1) {
            result++;
            visited = new boolean[n][m];
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < m ; j++) {
                    if(!visited[i][j] && arr[i][j] > 0) {
                        melt(i, j);
                    }
                }
            }
            visited = new boolean[n][m];

            iceCnt = cntIce();
        }

        if(iceCnt == 0) {
            System.out.println(0);
        } else {
            System.out.println(result);
        }


    }

    public static void melt(int x, int y) {
        int[][] newArr = new int[n][m];
        for(int i = 0 ; i < n ; i++) {
            newArr[i] = arr[i].clone();
        }

        Deque<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x, y});
        visited[x][y] = true;

        while(!q.isEmpty()) {
            int[] cur = q.poll();
            x = cur[0];
            y = cur[1];

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(arr[nx][ny] == 0) {
                    if(newArr[x][y] > 0) newArr[x][y]--;
                }
                else if(!visited[nx][ny]){
                    visited[nx][ny] = true;
                    q.offer(new int[]{nx, ny});
                }
            }

        }

        for(int i = 0 ; i < n ; i++) {
            arr[i] = newArr[i].clone();
        }

    }

    /*
     * 1 - 진행 result++
     * 2 - 종료 result++
     * 0 - 불가 result = 0
     */
    public static int cntIce() {
        int cnt = 0;
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(arr[i][j] > 0 && !visited[i][j]) {
                    if(++cnt >= 2) {
                        return cnt;
                    }
                    visited[i][j] = true;
                    Deque<int[]> q = new ArrayDeque<>();
                    q.offer(new int[]{i, j});

                    while(!q.isEmpty()) {
                        int[] cur = q.poll();
                        int x = cur[0];
                        int y = cur[1];

                        for(int dir = 0 ; dir < 4 ; dir++) {
                            int nx = x + dx[dir];
                            int ny = y + dy[dir];

                            if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                            if(visited[nx][ny]) continue;
                            if(arr[nx][ny] == 0) continue;

                            visited[nx][ny] = true;
                            q.offer(new int[]{nx, ny});
                        }
                    }
                }
            }
        }

        return cnt;
    }
}