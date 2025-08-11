package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;
class Node implements Comparable<Node> {
    int value;
    int x;
    int y;

    public Node(int value, int x, int y) {
        this.value = value;
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Node o) {
        return this.value > o.value ? 1 : -1;
    }
}
class Solution {
    static PriorityQueue<Node> pq = new PriorityQueue<>();
    static int[][] arr;
    static int[][] dArr;
    static final int INF = (int) 1e9;
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int n = 0;
        int testCase = 1;
        while((n = Integer.parseInt(br.readLine())) != 0) {
            arr = new int[n][n];
            dArr = new int[n][n];

            for(int i = 0 ; i < n ; i++) Arrays.fill(dArr[i], INF);

            for(int i = 0 ; i < n ; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            //////////////////////////////////////////////////////////////////

            dijkstra();
            System.out.println("Problem " + testCase + ": " +dArr[n-1][n-1]);
            testCase++;
        }

    }

    static public void dijkstra() {
        pq.clear();
        pq.offer(new Node(arr[0][0], 0, 0));
        dArr[0][0] = arr[0][0];

        while(!pq.isEmpty()) {
            Node curNode = pq.poll();
            int x = curNode.x;
            int y = curNode.y;

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = x + dx[dir];
                int ny = y + dy[dir];
                if(!isValidPlace(nx, ny)) continue;
                if(dArr[nx][ny] <= dArr[x][y] + arr[nx][ny]) continue;
                else {
                    dArr[nx][ny] = dArr[x][y] + arr[nx][ny];
                    if(nx == arr.length-1 && ny == arr[0].length-1) return;
                    pq.offer(new Node(dArr[nx][ny], nx, ny));
                }
            }
        }

    }
    static public boolean isValidPlace(int x, int y) {
        if (x < 0 || y < 0 || x >= arr.length || y >= arr[0].length) return false;
        return true;
    }

}