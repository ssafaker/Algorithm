package taewon_ahn;

import java.io.*;
import java.util.*;

public class Main_SEA_1961 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int tc = 1; tc <= T; tc++) {
            st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());

            int[][] arr = new int[N][N];
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < N; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }

            StringBuilder sb = new StringBuilder();
            int[][] arr90 = rotate90(arr);
            int[][] arr180 = rotate90(arr90);
            int[][] arr270 = rotate90(arr180);

            sb.append("#").append(tc).append("\n");
            for (int i = 0; i < N; i++) {
                for (int row : arr90[i]) {
                    sb.append(row);
                }
                sb.append(" ");
                for (int row : arr180[i]) {
                    sb.append(row);
                }
                sb.append(" ");
                for (int row : arr270[i]) {
                    sb.append(row);
                }
                sb.append("\n");
            }
            System.out.print(sb);
        }
    }

    static int[][] rotate90(int[][] arr) {
        int[][] temp = new int[arr.length][arr[0].length];
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[0].length; j++) {
                temp[j][arr.length - i - 1] = arr[i][j];
            }
        }
        return temp;
    }
}
