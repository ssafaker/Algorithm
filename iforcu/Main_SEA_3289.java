package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [서로소 집합]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWBJKA6qr2oDFAWr]
 *  난이도 : [D4]
 *  설명: 
 *  - 주어진 원소들에 대해 합집합(Union)과 같은 집합 판별(Find)을 수행하는 문제.
 *  풀이: 
 *  - Union-Find(Disjoint Set) 알고리즘 활용
 *  - parent 배열로 집합을 관리하고, 경로 압축을 적용해 find 연산을 최적화함.
 */

public class Main_SEA_3289 {
    static int N, M;
    static int[] parent;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 개수
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken()); // 원소 개수
            M = Integer.parseInt(st.nextToken()); // 연산 개수

            parent = new int[N+1]; // 1-based indexing
            for (int i = 0; i <= N; i++) {
                parent[i] = i; // 초기에는 자기 자신이 대표
            }

            StringBuilder sb = new StringBuilder();
            sb.append("#").append(test_case).append(" ");

            for (int i = 0; i < M; i++) {
                st = new StringTokenizer(br.readLine());
                int command = Integer.parseInt(st.nextToken());
                int v = Integer.parseInt(st.nextToken());
                int u = Integer.parseInt(st.nextToken());

                if(command == 0) { 
                    // 합집합(Union)
                    union(v, u);
                } else {
                    // 같은 집합인지 확인
                    int x = find(v);
                    int y = find(u);
                    if(x == y) sb.append(1);
                    else sb.append(0);
                }
            }
            System.out.println(sb);
        }
    }

    // Union 연산: 두 집합을 하나로 합침
    static void union(int v, int u) {
        int x = find(v);
        int y = find(u);
        if(x != y) parent[y] = x;
    }

    // Find 연산: 대표 노드 반환 (경로 압축 적용)
    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }
}
