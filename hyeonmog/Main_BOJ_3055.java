package hyeonmog;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.StringTokenizer;

class Main_BOJ_3055 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static int n, m;
    static int result;
    //시간별로 물이 찼을 때가 세번째 인덱스
    static char[][] arr;
    static boolean[][] visited;
    static Info destinationInfo;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        result = 0;
        arr = new char[n][m];
        visited = new boolean[n][m];
        destinationInfo = new Info(0, 0, 0);
        Info startInfo = new Info(0, 0, 0);
        for(int i = 0 ; i < n ; i++) {
            String str = br.readLine();
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = str.charAt(j);
                if(arr[i][j] == 'S') {
                    arr[i][j] = '.';
                    startInfo.x = i;
                    startInfo.y = j;
                }
                else if(arr[i][j] == 'D') {
                    destinationInfo.x = i;
                    destinationInfo.y = j;
                }
            }
        }

        if(bfs(startInfo.x, startInfo.y)) {
            System.out.println(result);
        } else {
            System.out.println("KAKTUS");
        }
    }

    public static boolean bfs(int x, int y) {
        Deque<Info> q = new ArrayDeque<>();
        visited[x][y] = true;
        q.offer(new Info(x, y, 0));
        int pastTime = 0;
        while(!q.isEmpty()) {
            Info info = q.poll();
            if(info.time != pastTime) {
                pastTime++;
                water();
            }
            x = info.x;
            y = info.y;
            int time = info.time;

            if(arr[x][y] == '*') continue;

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];

                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(arr[nx][ny] == '*' || arr[nx][ny] == 'X') continue;
                if(visited[nx][ny]) continue;
                visited[nx][ny] = true;
                q.offer(new Info(nx, ny, time+1));
                if(nx == destinationInfo.x && ny == destinationInfo.y) {
                    result = time+1;
                    return true;
                }
                

            }

        }

        return false;
    }

    public static void water() {
        char[][] dummyArr = new char[n][m];
        for(int i = 0 ; i < n ; i++) {
            dummyArr[i] = arr[i].clone();
        }
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(arr[i][j] == '*') {
                    for(int dir = 0 ; dir < 4 ; dir++) {
                        int nx = i + dx[dir];
                        int ny = j + dy[dir];
                        
                        if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                        if(arr[nx][ny] == 'D' || arr[nx][ny] == 'X') continue;
                        if(arr[nx][ny] == '.') {
                            dummyArr[nx][ny] = '*';
                        }
                    }
                }
            }
        }
        for(int i = 0 ; i < n ; i++) {
            arr[i] = dummyArr[i].clone();
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