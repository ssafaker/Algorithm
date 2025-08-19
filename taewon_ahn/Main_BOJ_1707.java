
import java.io.*;
import java.util.*;

public class Main_BOJ_1707 {
    static ArrayList<Integer>[] A;
    static int[] check;
    static boolean[] visited;
    static boolean isEven;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int testCase = Integer.parseInt(br.readLine());
        for (int t = 0; t < testCase; t++) { // 주어진 테스트 케이스 만큼 돌림
            String[] s = br.readLine().split(" ");
            int V = Integer.parseInt(s[0]);
            int E = Integer.parseInt(s[1]);
            A = new ArrayList[V + 1];
            visited = new boolean[V + 1];
            check = new int[V + 1];
            isEven = true;
            // 노드만큼 공간 할당
            for (int i = 1; i <= V; i++) {
                A[i] = new ArrayList<Integer>();
            }
            // 에지 데이터 저장
            for (int i = 0; i < E; i++) {
                s = br.readLine().split(" ");
                int start = Integer.parseInt(s[0]);
                int end = Integer.parseInt(s[1]);
                A[start].add(end);
                A[end].add(start);
            }
            // 모든 노드에 대하여 DFS 실행
            for (int i = 1; i <= V; i++) {
                if (isEven) {
                    DFS(i);
                } else {
                    break;
                }
            }
            if (isEven)
                System.out.println("YES");
            else
                System.out.println("NO");
        }
    }

    private static void DFS(int start) {
        visited[start] = true;
        for (int i : A[start]) { // 인접리스트 start에 연결된 모든 노드 탐색
            if (!visited[i]) {
                // 분류 작업
                check[i] = (check[start] + 1) % 2;
                DFS(i);
            } else if (check[i] == check[start]) {
                isEven = false;
                return;
            }
        }
    }
}
