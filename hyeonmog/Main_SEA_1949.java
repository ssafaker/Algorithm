package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

//등산로 조성
class Main_SEA_1949 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static int[][] arr;
    static int[][] distance;
    static int n;
    static int k;
    static int maxHeight;
    static int answer;
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        
        for(int testCase = 1 ; testCase < t+1 ; testCase++) {  
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            maxHeight = 0;
            arr = new int[n][n];
            distance = new int[n][n];
            answer = 0;
            for(int i = 0 ; i < n ; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                    maxHeight = Math.max(arr[i][j], maxHeight);
                }
            }


            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    for(int depth = 0 ; depth <= k ; depth++) {
                        //여기 까지가 깎는 경우의 수
                        arr[i][j] -= depth;
                        for(int a = 0 ; a < n ; a++) {
                            for(int b = 0 ; b < n ; b++) {
                                //가장 높은 높이면 find 시작
                                if(arr[a][b] == maxHeight) {
                                    find(a, b);
                                }
                            }
                        }
                        arr[i][j]+=depth;

                    }
                }
            }


            System.out.println("#" + testCase + " " + answer);
        }
    }
    static public void find(int x, int y) {
        ArrayDeque<int[]> dq = new ArrayDeque<>();
        distance = new int[n][n];
        dq.offer(new int[] {x, y});
        distance[x][y] = 1;
        while(!dq.isEmpty()) {
            int[] cur = dq.poll();
            x = cur[0];
            y = cur[1];

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if(nx < 0 || ny < 0 || nx >= n || ny >= n) continue;
                if(distance[nx][ny] > distance[x][y]) continue;
                if(arr[x][y] <= arr[nx][ny]) continue;
                
                distance[nx][ny] = distance[x][y]+1;
                dq.offer(new int[]{nx, ny});
                answer = Math.max(distance[nx][ny], answer);
            }

        }


    }


}