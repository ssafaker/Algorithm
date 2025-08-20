package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [키 순서]
 *  링크 : [https://www.acmicpc.net/problem/2458]
 *  난이도 : [골드 4]
 *  설명:
 *  - N명의 학생들이 있고, 학생들 키는 모두 다르다.
 *  - 자신보다 큰 학생들과 작은 학생들의 수 합이 N-1이 되어야 한다.
 *  - 즉, 그 학생이 전체에서 몇 번째인지 정확히 위치가 확정됨.
 *  풀이:
 *  - 방향 그래프 형태로 "작다/크다" 관계를 저장.
 *  - BFS로 탐색하여 해당 학생 기준:
 *  - 둘의 합이 N-1이면 순서를 확정할 수 있음.
 *  - 모든 학생에 대해 BFS를 돌려 정답 개수를 센다.
 */

public class Main_BOJ_2458 {
    static int N, M, ans;
    static List<List<Integer>> students_front; // 자신보다 큰 학생 그래프
    static List<List<Integer>> students_back;  // 자신보다 작은 학생 그래프
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        students_front = new ArrayList<>();
        students_back = new ArrayList<>();

        // 학생 번호는 1부터 N까지 사용
        for (int i = 0; i <= N; i++) {
            students_front.add(new ArrayList<>());
            students_back.add(new ArrayList<>());
        }

        // 키 비교 관계 입력
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int v = Integer.parseInt(st.nextToken()); // v < u
            int u = Integer.parseInt(st.nextToken()); // u > v
            students_front.get(v).add(u); // v에서 u로 간선 (앞 방향)
            students_back.get(u).add(v);  // u에서 v로 간선 (뒤 방향)
        }

        ans = 0;
        // 각 학생마다 BFS 탐색 수행
        for (int i = 1; i <= N; i++) {
            if(bfs(i)) ans++;
        }
        System.out.println(ans);
    }

    // 학생 i의 순서를 알 수 있는지 체크
    static boolean bfs(int i) {
        int front = 0; // 자신보다 큰 학생 수
        int back = 0;  // 자신보다 작은 학생 수

        // 1. 자신보다 큰 학생 찾기
        Queue<Integer> queue = new LinkedList<>();
        visited = new boolean[N+1];
        queue.offer(i);
        visited[i] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Integer next : students_front.get(cur)) {
                if(visited[next]) continue;
                visited[next] = true;
                queue.offer(next);
                front++;
            }
        }

        // 2. 자신보다 작은 학생 찾기
        visited = new boolean[N+1];
        queue.offer(i);
        visited[i] = true;
        while (!queue.isEmpty()) {
            int cur = queue.poll();
            for (Integer next : students_back.get(cur)) {
                if(visited[next]) continue;
                visited[next] = true;
                queue.offer(next);
                back++;
            }
        }

        // 자신보다 큰 학생 수 + 작은 학생 수 == N-1 → 순서 확정 가능
        return (front + back) == N-1;
    }
}
