package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//N Queen 문제
public class Main_BOJ_9663 {
    static int n;
    static int[][] arr;
    static int cnt;
    static int result;
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        cnt = 0;
        result = 0;
        arr= new int[n][n];

        dfs(0);
        System.out.println(result);
    }
    static public void dfs(int depth) {
        if(depth == n)  {
            if(cnt == n) {
                result++;
            }
            return;
        }
        for(int i = 0 ; i < n ; i++) {
            if(!isValid(depth, i)) continue;
            cnt++;
            arr[depth][i] = 1;
            dfs(depth+1);
            arr[depth][i] = 0;
            cnt--;
        }
    }

    //퀸을 놓을 수 있는 위치인가?
    static public boolean isValid(int x, int y) {
        //수직 위
        for(int i = x ; i >= 0 ; i--) {
            if(arr[i][y] == 1) return false;
        }

        //좌상 대각선
        int dx = -1;
        int dy = -1;
        for(int i = 1 ; i < n ; i++) {
            int nx = x + dx*i;
            int ny = y + dy*i;
            if(nx < 0 || ny < 0) break;
            if(arr[nx][ny] == 1) return false;
        }

        //우상 대각선
        dx = -1;
        dy = 1;
        for(int i = 1 ; i < n ; i++) {
            int nx = x + dx*i;
            int ny = y + dy*i;
            if(nx < 0 || ny >= n) break;
            if(arr[nx][ny] == 1) return false;
        }

        return true;
    }

    
}
