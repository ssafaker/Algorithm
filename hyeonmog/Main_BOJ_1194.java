package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;


/*
 * . 46
 * # 35
 * 0 48
 * 1 49
 * a 97
 * A 65
 * f e d c b a
 */


public class Main_BOJ_1194 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static final int aNumber = (int)'a';
    static final int ANumber = (int)'A';

    static int n, m;
    static char[][] arr;
    static int bitKey;
    static int bitDoor;
    static int startX, startY;
    //거리 저장, 방문처리 배열
    //[x, y, 어떤 키를 가지고 있는지]
    static int[][][] distanceArr;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new char[n][m];

        int maxCharNum = 0;
        for(int i = 0 ; i < n ; i++) {
            String str = br.readLine();
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = str.charAt(j);
                maxCharNum = Math.max(maxCharNum, (int)arr[i][j]);
                if(arr[i][j] == '0') {
                    startX = i;
                    startY = j;
                }
            }
        }
        //문 세팅
        for(int i = 0 ; i < maxCharNum-aNumber+1 ; i++) {
            bitDoor += (int)Math.pow(2, i);
        }

        distanceArr = new int[n][m][bitDoor+1];

        System.out.println(bfs(startX, startY));
        

    }

    public static int bfs(int x, int y) {
        Deque<Info> q = new ArrayDeque<>();
        q.offer(new Info(x, y, 0));

        while(!q.isEmpty()) {
            Info info = q.poll();
            x = info.x;
            y = info.y;
            int key = info.key; 
            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                if(arr[nx][ny] == '#') continue;
                if(distanceArr[nx][ny][key] != 0) continue;

                //열쇠 줍
                if(arr[nx][ny] >= (int)'a' && arr[nx][ny] <= (int)'f') {
                    int nowKey = key | (int)Math.pow(2, ((int)arr[nx][ny]) - aNumber);
                    distanceArr[nx][ny][nowKey] = distanceArr[x][y][key] + 1;
                    q.offer(new Info(nx, ny, nowKey));
                }
                //문 만남
                else if(arr[nx][ny] >= (int)'A' && arr[nx][ny] <= (int)'F') {
                    //문에 해당하는 키를 가짐
                    if((key | (int)Math.pow(2, ((int)arr[nx][ny]) - ANumber)) == key) {
                        distanceArr[nx][ny][key] = distanceArr[x][y][key] + 1; 
                        q.offer(new Info(nx, ny, key));
                    }
                    else {
                        continue;
                    }
                }
                //나가는 케이스
                else if(arr[nx][ny] == '1') {
                    return distanceArr[x][y][key] + 1;
                }
                //그냥 지날 수 있음
                else if(arr[nx][ny] == '.' || arr[nx][ny] == '0') {
                    distanceArr[nx][ny][key] = distanceArr[x][y][key] + 1;
                    q.offer(new Info(nx, ny, key));
                }
                

            }
        }

        return -1;
    }

    
}

class Info {
    int x;
    int y;
    int key;
    public Info(int x, int y, int key) {
        this.x = x;
        this.y = y;
        this.key = key;
    }
}