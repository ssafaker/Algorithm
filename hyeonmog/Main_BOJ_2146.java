package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Main_BOJ_2146 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static boolean[][] visited;
    static int n;
    static int[][] arr;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());

        arr = new int[n][n];
        visited = new boolean[n][n];
        result = n*n;
        for(int i = 0 ;  i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < n ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        int islandNumber = 1;
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(arr[i][j] == 1 && !visited[i][j]) {
                    registIsland(i, j, islandNumber);
                    islandNumber++;
                } 
            }
        }


        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(arr[i][j] != 0) {
                    createBridge(i, j, arr[i][j]);
                }
            }
        }
        System.out.println(result);


    }

    public static void registIsland(int x, int y, int islandIndex) {
        Queue<Info> q = new ArrayDeque<>();
        visited[x][y] = true;
        arr[x][y] = islandIndex;

        q.offer(new Info(x, y, 0));

        while(!q.isEmpty()) {
            Info info = q.poll();
            x = info.x;
            y = info.y;
            int distance = info.distance;

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if(visited[nx][ny]) continue;
                if(arr[nx][ny] == 0) continue;
                
                arr[nx][ny] = islandIndex;
                visited[nx][ny] = true;
                q.offer(new Info(nx, ny, distance+1));
                
            }

        }
    }

    public static void createBridge(int x, int y, int startIndex) {
        Queue<Info> q = new ArrayDeque<>();
        visited = new boolean[n][n];
        q.offer(new Info(x, y, 0));
        visited[x][y] = true;
        while(!q.isEmpty()) {
            Info info = q.poll();
            x = info.x;
            y = info.y;
            int distance = info.distance;

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if(visited[nx][ny]) continue;
                if(arr[nx][ny] == startIndex) continue;
                
                visited[nx][ny] = true;
                q.offer(new Info(nx, ny, distance+1));

                if(arr[nx][ny] != startIndex && arr[nx][ny] != 0) {
                    result = Math.min(result, distance);
                    return;
                }

            }
        }
    }

}

class Info {
    int x;
    int y;
    int distance;

    public Info(int x, int y, int distance) {
        this.x = x;
        this.y = y;
        this.distance = distance;
    }
}