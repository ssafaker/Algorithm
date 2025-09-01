package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;


class Main_BOJ_17144 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    
    static final int[] firstDx = {0, -1, 0, 1};
    static final int[] firstDy = {1, 0, -1, 0};

    static final int[] secondDx = {0, 1, 0, -1};
    static final int[] secondDy = {1, 0, -1, 0};

    static int r, c, t;
    static int[][] arr;
    static int[][] newArr;

    static Position upAir;
    static Position downAir;

    /*
     * 
     *  1. 미세먼지 퍼뜨리기
     *  2. 바람 방향으로 이동 시키기
     * 
     * 
     */

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        r = Integer.parseInt(st.nextToken());
        c = Integer.parseInt(st.nextToken());
        t = Integer.parseInt(st.nextToken());
        arr = new int[r][c];
        newArr = new int[r][c];

        for(int i = 0 ; i < r ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < c ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if(arr[i][j] == -1) {
                    upAir = new Position(i-1, j);
                    downAir = new Position(i, j);
                }
            }
        }

        for(int time = 0 ; time < t ; time++) {
            spread();
            cleanerWork();
        }
        int result = 0;
        for(int i = 0 ; i < r ; i++) {
            for(int j = 0 ; j < c ; j++) {
                if(arr[i][j] != -1) {
                    result += arr[i][j];
                }
            }
        }

        System.out.println(result);
    }

    //먼지 퍼뜨리는 메서드
    public static void spread() {
        newArr = new int[r][c];
        for(int i = 0 ; i < r ; i++) {
            for(int j = 0 ; j < c ; j++) {
                if(arr[i][j] == -1) {
                    newArr[i][j] = -1;
                    continue;
                }
                if(arr[i][j] == 0) continue;
                
                for(int dir = 0 ; dir < 4 ; dir++) {
                    int nx = i + dx[dir];
                    int ny = j + dy[dir];

                    if(nx < 0 || ny < 0 || nx >= r || ny >= c || arr[nx][ny] == -1 ) {
                        newArr[i][j] += arr[i][j] / 5;
                    }
                    else {
                        newArr[nx][ny] += arr[i][j] / 5;
                    }
                }
                newArr[i][j] += arr[i][j] / 5 + arr[i][j] % 5;
            }
        }
       for(int i = 0 ; i < r ; i ++) {
            arr[i] = newArr[i].clone();
        }
    }

    //공기청정기 작동 메서드
    public static void cleanerWork() {
        for(int i = 0 ; i < r ; i ++) {
            newArr[i] = arr[i].clone();
        }

        //위
        int dir = 0;
        int x = upAir.x;
        int y = upAir.y+1;
        newArr[x][y] = 0;
        while (true) { 
            int nx = x + firstDx[dir];
            int ny = y + firstDy[dir];
            if(nx < 0 || ny < 0 || nx >= r || ny >= c) {
                dir++;
                continue;
            }
            if(arr[nx][ny] == -1) break;
            newArr[nx][ny] = arr[x][y];
            x = nx;
            y = ny;
        }


        //아래
        dir = 0;
        x = downAir.x;
        y = downAir.y+1;
        newArr[x][y] = 0;
        while (true) { 
            int nx = x + secondDx[dir];
            int ny = y + secondDy[dir];
            if(nx < 0 || ny < 0 || nx >= r || ny >= c) {
                dir++;
                continue;
            }
            if(arr[nx][ny] == -1) break;
            newArr[nx][ny] = arr[x][y];
            x = nx;
            y = ny;
        }

        for(int i = 0 ; i < r ; i ++) {
            arr[i] = newArr[i].clone();
        }
    }
    
}


class Position {
    int x;
    int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}