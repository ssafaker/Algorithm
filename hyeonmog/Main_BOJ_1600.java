package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Main_BOJ_1600 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static final int[] hdx = {-1, -2, -2, -1, 1, 2, 2, 1};
    static final int[] hdy = {-2, -1, 1, 2, 2, 1, -1, -2};

    static int k, n, m;
    static int[][] arr;
    static int[][][] distanceArr;
    static int result;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        k = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        m = Integer.parseInt(st.nextToken());
        n = Integer.parseInt(st.nextToken());
        result = 0;
        arr = new int[n][m];
        distanceArr = new int[n][m][k+1];

        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        System.out.println(bfs() ? result : -1);
        
    }


    public static boolean bfs() {
        if(n-1 == 0 && m-1 == 0) return true;
        Queue<Info> q = new ArrayDeque<>();
        distanceArr[0][0][0] = 1;
        q.offer(new Info(0, 0, k));

        while(!q.isEmpty()) {
            Info info = q.poll();
            int x = info.x;
            int y = info.y;
            int restHorse = info.restHorse;

            //그냥 이동
            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(!isValidPlace(nx, ny)) continue;
                if(distanceArr[nx][ny][restHorse] != 0) continue;
                if(arr[nx][ny] == 1) continue;

                distanceArr[nx][ny][restHorse] = distanceArr[x][y][restHorse] + 1;

                q.offer(new Info(nx, ny, restHorse));
                if(nx== n-1 && ny == m-1)  {
                    result = distanceArr[nx][ny][restHorse];
                    return true;
                }
            }
            //말 이동
            if(restHorse > 0) {
                for(int dir = 0 ; dir < 8 ; dir++) {
                    int nx = x + hdx[dir];
                    int ny = y + hdy[dir];

                    if(!isValidPlace(nx, ny)) continue;
                    if(distanceArr[nx][ny][restHorse-1] != 0) continue;
                    if(arr[nx][ny] == 1) continue;

                    distanceArr[nx][ny][restHorse-1] = distanceArr[x][y][restHorse] + 1;
                    
                    q.offer(new Info(nx, ny, restHorse-1));
                    if(nx== n-1 && ny == m-1) {
                        result = distanceArr[nx][ny][restHorse-1];
                        return true;
                    }
                }
            }
        }
        return false;

    }


    public static boolean isValidPlace(int x, int y) {
        if(x < 0 || y < 0 || x >= n || y >= m) return false;
        return true;
    }
}

class Info {
    int x;
    int y;
    int restHorse;

    public Info(int x, int y, int restHorse) {
        this.x = x;
        this.y = y;
        this.restHorse = restHorse;
    }
}