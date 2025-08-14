package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_SEA_2382 {
    static Cell[][] cells;
    static Cell[][] newCells;
    static final int[] dx = {-1, 1, 0, 0};
    static final int[] dy = {0, 0, -1, 1};
    static int n;
    static int m;
    static int k;
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            //1시간 이동하고
            //세포 합치기, 방향 지정
            st = new StringTokenizer(br.readLine());

            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());

            cells = new Cell[n][n];
            
            for(int i = 0 ; i < k ; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int value = Integer.parseInt(st.nextToken());
                int way = Integer.parseInt(st.nextToken())-1;
                cells[x][y] = new Cell(value, 0, way, false);
            }

            for(int time = 0 ; time < m ; time++) {
                newCells = new Cell[n][n];
                move();
                cells = newCells.clone();
            }

            int result = 0;
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    if(cells[i][j]!=null) {
                        result += cells[i][j].value;
                    }
                }
            }
            System.out.println("#" + testCase + " " + result);

        }

    }


    static public void move() {
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(cells[i][j] != null && !cells[i][j].moved) {
                    int nx = i + dx[cells[i][j].way];
                    int ny = j + dy[cells[i][j].way];
                    cells[i][j].moved = true;
                    //이동한 위치가 비었다  
                    if(newCells[nx][ny] == null) {
                        newCells[nx][ny] = new Cell(cells[i][j].value, cells[i][j].tempValue, cells[i][j].way, true);
                        cells[i][j] = null;
                    }
                    //이동한 곳에 세포가 있다.
                    else {
                        //새로 온 세포가 더 값이 크다
                        if(cells[i][j].value > newCells[nx][ny].value) {
                            newCells[nx][ny] = new Cell(cells[i][j].value, newCells[nx][ny].tempValue + newCells[nx][ny].value, cells[i][j].way, true);
                            cells[i][j] = null;
                        }
                        //이미 있는 세포가 더 크다
                        else {
                            newCells[nx][ny].tempValue += cells[i][j].value;
                            cells[i][j] = null;
                        }
                    }
                }
            }
        }
        kill();
        mix();
    }

    static public void mix() {
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(newCells[i][j] != null && newCells[i][j].tempValue != 0) {
                    newCells[i][j].value += newCells[i][j].tempValue;
                    newCells[i][j].tempValue = 0;
                }
            }
        }
    }
    static public void kill() {
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < n ; j++) {
                if(newCells[i][j] != null) {
                    newCells[i][j].moved = false;
                    if(i == 0 || j == 0 || i == n-1 || j == n-1) {
                        newCells[i][j].value /= 2;
                        if(newCells[i][j].way == 0 || newCells[i][j].way == 2) {
                            newCells[i][j].way++;
                        }
                        else {
                            newCells[i][j].way--;
                        }
                    }
                }
            }
        }
    }

}
class Cell {
    int value;
    //내가 잡은 세포 값
    int tempValue;
    int way;
    boolean moved;

    public Cell(int value, int tempValue, int way, boolean moved) {
        this.value = value;
        this.tempValue = tempValue;
        this.way = way;
        this.moved = moved;
    }
}