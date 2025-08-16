package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [창용 마을 무리의 개수]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWngfZVa9XwDFAQU]
 *  난이도 : [D4]
 *  설명:
 *  - graph를 받아서 각각의 연결된 그래프 총 갯수를 구하는 문제
 *  풀이: 
 *  - 입력 받은 v 와 u 값을 각각의 List 형태로 정보 저장
 *  - visited를 활용해 방문 여부를 확인
 *  - bfs를 사용해 연결된 모든 그래프 탐색
 *  - 탐색에 들어갈때마다 새로운 그룹을 찾는걸로 확인해 ans++ 이후 출력
 */

public class Main_SEA_7465 {
    static List<List<Integer>> people;
    static int N, M;
    static boolean[] visited;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());

            visited = new boolean[N+1]; // 1~N이기에 N+1 배열
            people = new ArrayList<>();
            for (int i = 0; i < N+1; i++) { // 1~N이기에 N까지 리스트를 늘림
                people.add(new ArrayList<>());
            }
            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int u = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                people.get(u).add(v);
                people.get(v).add(u);
            }

            int ans = 0;
            for (int i = 1; i <= N; i++) {
                // 새로운 그룹을 발견
                if(!visited[i]) {
                    bfs(i);
                    ans++;
                }
            }

            System.out.println("#"+test_case+" "+ans);
        }
    }
    // BFS 진행
    static void bfs(int i) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(i);
        visited[i] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();

            for (Integer cur : people.get(v)) {
                if(visited[cur])  continue;
                visited[cur] = true;
                queue.add(cur);
            }
        }
    }
}
