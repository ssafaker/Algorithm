package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.StringTokenizer;

class Main_BOJ_14502 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static int n, m;
    static int[][] arr;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n][m];
        result = 0;
        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        makeWall(0, 0);

        System.out.println(result);
    }

    //백트래킹으로 벽 생성
    public static void makeWall(int depth, int idx) {
        if(depth == 3) {
            spreadVirus();
            return;
        }

        for(int num = idx ; num < n*m ; num++) {
            int i = num / m;
            int j = num % m;

            if(arr[i][j] == 0) {
                arr[i][j] = 1;
                makeWall(depth+1, i+1);
                arr[i][j] = 0;
            }

        }

    }

    //bfs로 바이러스 퍼뜨리기
    public static void spreadVirus() {
        int[][] temp = new int[n][m];
        for(int i = 0 ; i < n ; i++) {
            temp[i] = arr[i].clone();
        }
        
        boolean[][] visited = new boolean[n][m];
        for(int r = 0 ; r < n ; r++) {
            for(int c = 0 ; c < m ; c++) {
                
                if(temp[r][c] == 2 && !visited[r][c]) {
                    Queue<int[]> q = new ArrayDeque<>();
                    q.offer(new int[]{r, c});
                    visited[r][c] = true;

                    while(!q.isEmpty()) {
                        int[] cur = q.poll();
                        int x = cur[0];
                        int y = cur[1];

                        for(int dir = 0 ; dir < 4 ; dir++) {
                            int nx = x + dx[dir];
                            int ny = y + dy[dir];

                            if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                            if(visited[nx][ny]) continue;

                            if(temp[nx][ny] == 0) {
                                q.offer(new int[]{nx, ny});
                                visited[nx][ny] = true;
                                temp[nx][ny] = 2;
                            }

                        }

                    }

                }

            }
        }

        getSafeArea(temp);

    }

    //바이러스 퍼지고 난 뒤 안전 영역 계산
    public static void getSafeArea(int[][] temp) {
        int cnt = 0;
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(temp[i][j] == 0) {
                    cnt++;
                }
            }
        }

        result = Math.max(result, cnt);

    }
}