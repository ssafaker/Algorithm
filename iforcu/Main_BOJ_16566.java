package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [카드 게임] 
 *  링크 : [https://www.acmicpc.net/problem/16566]
 *  난이도 : [골드 1]
 *  설명:
 *   - 철수가 낼 수 있는 카드 후보들이 주어지고, 민수가 순서대로 카드를 낼 때
 *     철수는 반드시 민수보다 큰 카드를 내야 한다.
 *   - 이미 사용한 카드는 다시 사용할 수 없다.
 *   - 철수가 낼 수 있는 카드 중 가장 작은 카드(민수보다 크면서 아직 사용 안 한 카드)를 출력해야 한다.
 *  풀이:
 *   - Union-Find(Disjoint Set Union, DSU)를 활용.
 *   - 카드를 오름차순으로 생각하면서 사용된 카드는 다음 가능한 카드와 union.
 *   - find 연산으로 현재 쓸 수 있는 "가장 작은 큰 카드"를 찾는다.
 *   - 매번 사용한 카드는 다음 카드와 union해서 갱신.
 */

public class Main_BOJ_16566 {
    static int N, M, K;
    static int[] parent;

    // Find: 현재 숫자의 대표(다음 가능한 카드)를 찾음
    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union: 두 집합을 합침 (사용된 카드는 다음 카드로 연결)
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        if(rootA != rootB) {
            parent[rootA] = rootB;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();

        N = Integer.parseInt(st.nextToken()); // 전체 카드 수
        M = Integer.parseInt(st.nextToken()); // 철수가 가진 카드 수
        K = Integer.parseInt(st.nextToken()); // 민수가 낼 카드 수

        boolean[] hasCard = new boolean[N + 2];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < M; i++) {
            hasCard[Integer.parseInt(st.nextToken())] = true; // 철수 카드 표시
        }
        
        // DSU 초기화
        parent = new int[N + 2];
        for (int i = 1; i <= N + 1; i++) {
            parent[i] = i;
        }

        // 철수가 가진 카드가 아닌 위치들은 다음 칸과 union (사용 불가 표시)
        for (int i = 1; i <= N; i++) {
            if (!hasCard[i]) union(i, i + 1);
        }
        
        // 민수가 낼 카드 처리
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < K; i++) {
            int card = Integer.parseInt(st.nextToken());
            int cheolsuCard = find(card + 1); // 민수 카드보다 큰 카드 중 가장 작은 것

            sb.append(cheolsuCard).append("\n");
            union(cheolsuCard, cheolsuCard + 1); // 사용 후 다음 카드로 연결
        }
        System.out.print(sb);
    }
}
