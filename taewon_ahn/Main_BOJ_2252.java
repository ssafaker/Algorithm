
import java.io.*;
import java.util.*;

public class Main_BOJ_2252 {

    static int N, M;
    static List<Integer>[] A;
    static int[] indgree;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        A = new ArrayList[N + 1];
        for (int i = 1; i <= N; i++) {
            A[i] = new ArrayList<>();
        }
        indgree = new int[N + 1];

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int S = Integer.parseInt(st.nextToken());
            int E = Integer.parseInt(st.nextToken());
            A[S].add(E); // 방향그래프기 때문에 서로 넣어주지 않음
            indgree[E]++; // E로 향하는 진입차수가 1개 존재
        }

        // 위상정렬 실행
        Queue<Integer> queue = new ArrayDeque<>();
        for (int i = 1; i <= N; i++) {
            if (indgree[i] == 0) { // 진입차수 0인 노드부터 큐에 넣어서 탐색 시작
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int cur = queue.poll();
            System.out.print(cur + " ");
            for (int next: A[cur]) {
                indgree[next]--; // 연결된 노드들 진입 차수 -1
                if (indgree[next] == 0) {
                    queue.offer(next);
                }
            }
        }
    }
}
