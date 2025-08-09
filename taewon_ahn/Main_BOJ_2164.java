package taewon_ahn;

import java.io.*;
import java.util.ArrayDeque;
import java.util.Queue;

public class Main_BOJ_2164 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        Queue<Integer> que = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            que.add(i);
        }

        int order = 1;
        while (que.size() > 1) {
            if (order % 2 == 0) {
                que.add(que.poll());
            } else {
                que.poll();
            }
            order++;
        }
        System.out.println(que.poll());
    }
}
