import java.io.*;
import java.util.*;

public class Main_BOJ_1717 {
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String[] s = br.readLine().split(" ");
        int N = Integer.parseInt(s[0]);
        int M = Integer.parseInt(s[1]);
        // 유니온 배열 초기화
        parent = new int[N + 1];
        for (int i = 0; i <= N; i++) {
            parent[i] = i;
        }

        for (int i = 0; i < M; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int query = Integer.parseInt(st.nextToken());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            if (query == 0) {
                union(a, b);
            } else {
                if (checkSame(a, b)) {
                    System.out.println("YES");
                } else {
                    System.out.println("NO");
                }
            }

        }
    }

    private static void union(int a, int b) {
        // 대표노드를 찾아서 연결
        a = find(a);
        b = find(b);
        if (a != b) {
            parent[b] = a; // 두개 연결
        }
    }

    private static int find(int a) {
        if (parent[a] == a) {
            return a;
        } else {
            return parent[a] = find(parent[a]); // value를 index로 바꿔서 또 찾아보기
        }
    }

    private static boolean checkSame(int a, int b) {
        a = find(a);
        b = find(b);
        if (a == b)
            return true;
        else
            return false;
    }
}