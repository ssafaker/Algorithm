
import java.io.*;
import java.util.StringTokenizer;

public class Main_BOJ_17136 {
    static int[][] board = new int[10][10];
    static int[] paperCnt = { 0, 5, 5, 5, 5, 5 }; // 각 크기별 색종이 개수
    static int minCount = Integer.MAX_VALUE;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int i = 0; i < 10; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 10; j++) {
                board[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        dfs(0, 0, 0);

        System.out.println(minCount == Integer.MAX_VALUE ? -1 : minCount);
    }

    private static void dfs(int r, int c, int cnt) {
        if (r > 9) {
            minCount = Math.min(minCount, cnt);
            return;
        }

        if (cnt >= minCount) { // 가지치기
            return;
        }

        if (c > 9) { // 한 줄 끝에 도달하면 다음 줄로 이동
            dfs(r + 1, 0, cnt);
            return;
        }

        if (board[r][c] == 1) {
            for (int size = 5; size >= 1; size--) {
                if (paperCnt[size] > 0 && canAttach(r, c, size)) {
                    attach(r, c, size, 0); // 색종이 붙이기
                    paperCnt[size]--;
                    dfs(r, c + 1, cnt + 1);
                    attach(r, c, size, 1); // 색종이 떼기
                    paperCnt[size]++;
                }
            }
        } else {
            dfs(r, c + 1, cnt); // 현재 위치가 0이면 다음 칸 이동
        }
    }

    private static void attach(int r, int c, int size, int state) {
        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++) {
                board[i][j] = state;
            }
        }
    }

    private static boolean canAttach(int r, int c, int size) {
        if (r + size > 10 || c + size > 10) {
            return false;
        }

        for (int i = r; i < r + size; i++) {
            for (int j = c; j < c + size; j++) {
                if (board[i][j] != 1)
                    return false;
            }
        }
        return true;
    }
}
