package taewon_ahn;

import java.io.*;
import java.util.PriorityQueue;

public class Main_BOJ_11286 {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        Integer N = Integer.parseInt(br.readLine());
        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> {
            if (Math.abs(a) == Math.abs(b)) {
                return a < b ? -1 : 1;
            } else {
                return Math.abs(a) - Math.abs(b);
            }
        });
        for (int i = 0; i < N; i++) {
            int cur = Integer.parseInt(br.readLine());
            if (cur != 0) {
                pq.add(cur);
            } else {
                if (pq.isEmpty()) {
                    sb.append(0);
                } else {
                    sb.append(pq.poll());
                }
                sb.append("\n");
            }
        }
        System.out.print(sb);
    }
}
