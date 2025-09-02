package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Main_BOJ_2206 {

    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static int n, m;
    static int[][] arr;
    static int[][][] distanceArr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());

        arr = new int[n][m];
        distanceArr = new int[n][m][2];

        

        for(int i = 0 ; i < n ; i++) {
            String str = br.readLine();
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = Character.getNumericValue(str.charAt(j));
            }
        }

        distanceArr[0][0][0] = 1;
        bfs();


        // for(int i = 0 ; i < n ; i++) {
        //     for(int j = 0 ; j < m ; j++) {
        //         System.out.print(distanceArr[i][j][0] + " ");
        //     }
        //     System.out.println();
        // }

        int result = -1;
        if(distanceArr[n-1][m-1][0] != 0) {
            result = distanceArr[n-1][m-1][0];
        }
        else if(distanceArr[n-1][m-1][1] != 0) {
            result = distanceArr[n-1][m-1][1];
        }
        System.out.println(result);
    
    }

    public static void bfs() {
        Deque<Info> q = new ArrayDeque<>();
        q.offer(new Info(0, 0, true));
        
        while(!q.isEmpty()) {
            Info info = q.poll();
            int x = info.x;
            int y = info.y;
            boolean breakChance = info.breakChance;

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(arr[nx][ny] == 1 && !breakChance) continue;
                //벽 있
                if(arr[nx][ny] == 1) {
                    if(distanceArr[nx][ny][1] != 0) continue;
                    distanceArr[nx][ny][1] = distanceArr[x][y][0]+1;
                    q.offer(new Info(nx, ny, false));
                }
                //벽 없
                else {
                    if(breakChance) {
                        if(distanceArr[nx][ny][0] != 0) continue;
                        distanceArr[nx][ny][0] = distanceArr[x][y][0]+1;
                        q.offer(new Info(nx, ny, breakChance));
                    }
                    else {
                        if(distanceArr[nx][ny][1] != 0) continue;
                        distanceArr[nx][ny][1] = distanceArr[x][y][1]+1;
                        q.offer(new Info(nx, ny, breakChance));
                    }
                }


                
                if(nx == n-1 && ny == m-1) return;

            }

        }

    }

}

class Info {
    int x;
    int y;
    boolean breakChance;

    public Info(int x, int y, boolean breakChance) {
        this.x = x;
        this.y = y;
        this.breakChance = breakChance;
    }
}