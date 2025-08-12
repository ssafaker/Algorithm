package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

//30분
class Main_SEA_1249 {
    static int[][] arr;
    static int[][] results;
    static final int INF = (int) 1e9;
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1};
    // static boolean[][] visited;

    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for(int test_case = 1 ; test_case < t+1 ; test_case++) {
            int n = Integer.parseInt(br.readLine());
            arr = new int[n][n];
            results = new int[n][n];
            for(int i = 0 ; i < n ; i++) {
                Arrays.fill(results[i], INF);
            }
            for(int i = 0 ; i < n ; i++) {
                String str = br.readLine();
                for(int j = 0 ; j < n ; j++) {
                    arr[i][j] = Character.getNumericValue(str.charAt(j));
                }
            }

            dijkstra();
            System.out.println("#" + test_case + " " + results[n-1][n-1]);


        }

    }

    static public void dijkstra() {
        PriorityQueue<Car> pq = new PriorityQueue<>((a, b) -> a.value - b.value);
        pq.offer(new Car(0, 0, 0));
        results[0][0] = 0;
        while(!pq.isEmpty()) {
            Car car = pq.poll();

            for(int dir = 0 ; dir < 4 ; dir++) {
                int nx = car.x + dx[dir];
                int ny = car.y + dy[dir];

                if(!isValidPlace(nx, ny)) continue;
                if(results[car.x][car.y] + arr[nx][ny] >= results[nx][ny]) continue;

                results[nx][ny] = results[car.x][car.y] + arr[nx][ny];
                if(nx == arr.length-1 && ny == arr[0].length-1) {
                    return;
                }
                pq.offer(new Car(nx, ny, results[nx][ny]));

            }

        }

    }

    static public boolean isValidPlace(int x, int y) {
        if(x < 0 || y < 0 || x >= arr.length || y >= arr[0].length) return false;
        return true;
    }

}

class Car {
    int x;
    int y;
    int value;

    public Car(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
    }
}