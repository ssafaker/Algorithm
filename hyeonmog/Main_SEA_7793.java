package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Main_SEA_7793 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static char[][] arr;
    static boolean[][] visited;
    static int n, m;
    static int result;

    static Info destinationInfo;
    static Info startInfo;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            
            
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            arr = new char[n][m];
            visited = new boolean[n][m];
            result = -1;
            destinationInfo = new Info(0, 0, 0);
            startInfo = new Info(0, 0, 0);
            for(int i = 0 ; i < n ; i++) {
                String str = br.readLine();
                for(int j = 0 ; j < m ; j++) {
                    arr[i][j] = str.charAt(j);
                    if(arr[i][j] == 'S') {
                        startInfo.x = i;
                        startInfo.y = j;
                        arr[i][j] = '.';
                        
                    } else if(arr[i][j] == 'D') {
                        destinationInfo.x = i;
                        destinationInfo.y = j;
                    }
                }
            }
            
            bfs();
            
            System.out.println("#" + testCase + " " + (result == -1 ? "GAME OVER" : result));
        }
    }
    
    public static void spreadEvil() {
        char[][] dummyArr = new char[n][m];
        
        for(int i = 0 ; i < n ; i++) {
            dummyArr[i] = arr[i].clone();
        }

        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(dummyArr[i][j]=='*') {
                    for(int dir = 0 ; dir < 4 ; dir++) {
                        int nx = i + dx[dir];
                        int ny = j + dy[dir];
                        if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                        if(arr[nx][ny] == '.') {
                            arr[nx][ny] = '*';
                        }
                    }
                }
            }
        }
    }

    public static void bfs() {
        Deque<Info> q = new ArrayDeque<>();
        q.offer(startInfo);
        visited[startInfo.x][startInfo.y] = true;
        int pastTime = 0;

        while(!q.isEmpty()) {
            Info info = q.poll();
            int x = info.x;
            int y = info.y;
            int time = info.time;
            if(arr[x][y] == '*') continue;
            if(time != pastTime) {
                pastTime++;
                spreadEvil();
            }

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(visited[nx][ny]) continue;

                if(nx == destinationInfo.x && ny == destinationInfo.y) {
                    result = time+1;
                    return;
                }

                if(arr[nx][ny] == '.') {
                    visited[nx][ny] = true;
                    q.offer(new Info(nx, ny, time+1));
                }
            }


        }
        
    }
}

class Info {
    int x;
    int y;
    int time;
    public Info(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }
}