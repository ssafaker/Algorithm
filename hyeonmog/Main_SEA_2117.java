package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_SEA_2117 {
    static int n, m;
    static int[][] arr;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            arr = new int[n][n];
            result = 0;
            for(int i = 0 ; i < n ; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            for(int k = 1 ; k < 100 ; k++) {
                int cost = k * k + (k-1) * (k-1);

                if(m*n*n < cost) break;

                for(int i = 0 ; i < n ; i++) {
                    for(int j = 0 ; j < n ; j++) {
                        calc(i, j, k, cost);
                    }
                }
            }
            System.out.println("#" + testCase + " " + result);
        }

    }
    public static void calc(int x, int y, int k, int cost) {
        int cnt = 0;
        int[][] testArr = new int[n][n];

        for(int i = 0 ; i < k ; i++) {
            for(int j = -k+i+1 ; j <= k-i-1 ; j++) {
                int nx = x + j;
                int ny = y + i;
                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                testArr[nx][ny] = 1;
                if(arr[nx][ny] == 1) {
                    cnt++;
                }
            }
        }


        for(int i = -1 ; i >= -k+1 ; i--) {
            for(int j = -k - i + 1 ; j <= k + i -1 ; j++) {
                int nx = x + j;
                int ny = y + i;
                if(nx < 0 || nx >= n || ny < 0 || ny >= n) continue;
                testArr[nx][ny] = 1;
                if(arr[nx][ny] == 1) {
                    cnt++;
                }
            }
        }

        if(cnt*m - cost >= 0) {
            result = Math.max(cnt, result);
        }
    }
}
