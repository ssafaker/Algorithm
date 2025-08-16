package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.StringTokenizer;

// 25분
class Solution {
    
    static int[][] arr;
    static boolean[][] visited;
    static int n;
    static int startX;
    static int startY;
    static int destX;
    static int destY;
    
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};

    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        
        for(int test_case = 1 ; test_case < t+1 ; test_case++) {
            n = Integer.parseInt(br.readLine());
            arr = new int[n][n];
            visited = new boolean[n][n];

            for(int i = 0 ; i < n ; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            st = new StringTokenizer(br.readLine());
            startX = Integer.parseInt(st.nextToken());
            startY = Integer.parseInt(st.nextToken());
            st = new StringTokenizer(br.readLine());
            destX = Integer.parseInt(st.nextToken());
            destY = Integer.parseInt(st.nextToken());

            int result = bfs();
            System.out.println("#" + test_case + " " + result);
        }


    }

    static public int bfs() {
        ArrayDeque<Person> q = new ArrayDeque<>();
        q.offer(new Person(startX, startY, 0));
        visited[startX][startY] = true;

        while(!q.isEmpty()) {
            Person person = q.poll();
            if(person.x == destX && person.y == destY) {
                return person.time;
            }

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = person.x + dx[dir];
                int ny = person.y + dy[dir];
                if(!isValidPlace(nx, ny)) continue;
                if(visited[nx][ny]) continue;
                if(arr[nx][ny] == 1) continue;
                if(arr[nx][ny] == 2 && ((person.time+1)%3) != 0) {
                    q.offer(new Person(person.x, person.y, person.time+1));
                }
                else if (arr[nx][ny] == 2 && ((person.time+1)%3) == 0) {
                    visited[nx][ny] = true;
                    q.offer(new Person(nx, ny, person.time+1));
                }
                else {
                    visited[nx][ny] = true;
                    q.offer(new Person(nx, ny, person.time+1));
                }

            }
        }
        return -1;
    }

    static public boolean isValidPlace(int x, int y) {
        if(x < 0 || y < 0 || x >= n || y >= n) return false;
        return true;
    }

}
class Person {
    int x;
    int y;
    int time;
    public Person(int x, int y, int time) {
        this.x = x;
        this.y = y;
        this.time = time;
    }   
}