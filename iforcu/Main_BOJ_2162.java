package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [선분 그룹]
 *  링크 : [https://www.acmicpc.net/problem/2162]
 *  난이도 : [플래티넘 5]
 *  설명: 
 *   - N개의 선분이 주어질 때, 서로 교차하는 선분들을 같은 그룹으로 묶는 문제.
 *   - 직접 교차하지 않아도 다른 선분을 통해 연결되면 같은 그룹.
 *   - 그룹의 개수와 가장 큰 그룹의 선분 개수를 구해야 함.
 *  풀이:
 *   - Union-Find를 사용하여 교차하는 선분들을 같은 집합으로 관리.
 *   - CCW 알고리즘으로 선분 교차 여부 판단.
 *   - 최종적으로 루트 노드의 개수와 가장 큰 집합의 크기를 출력.
 */

public class Main_BOJ_2162 {
    static int N, size;
    static List<int[]> lines;
    static int[] parent;
    static HashMap<Integer, Integer> map;

    // Union-Find의 Find 연산 (경로 압축)
    static int find(int x) {
        if(parent[x] == x) return x;
        return parent[x] = find(parent[x]);
    }

    // Union-Find의 Union 연산
    static void union(int a, int b) {
        int rootA = find(a);
        int rootB = find(b);
        parent[rootB] = rootA;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        lines = new ArrayList<>();

        parent = new int[N];
        for (int i = 0; i < N; i++) parent[i] = i;

        // 선분 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int x1 = Integer.parseInt(st.nextToken());
            int y1 = Integer.parseInt(st.nextToken());
            int x2 = Integer.parseInt(st.nextToken());
            int y2 = Integer.parseInt(st.nextToken());
            lines.add(new int[] {x1, y1, x2, y2});
        }

        // 모든 선분 쌍에 대해 교차 여부 확인 후 Union
        for (int i = 0; i < N; i++) {
            int[] cur = lines.get(i);
            for (int j = 0; j < N; j++) {
                if (find(i) == find(j)) continue;
                int[] target = lines.get(j);

                if(isIntersect(cur[0], cur[1], cur[2], cur[3], target[0], target[1], target[2], target[3])) {
                    union(i, j);
                }
            }
        }

        // 각 그룹별 선분 개수 계산
        map = new HashMap<>();
        for (int i = 0; i < N; i++) {
            int root = find(i);
            map.put(root, map.getOrDefault(root, 0) + 1);
        }

        // 가장 큰 그룹의 크기 찾기
        size = 0;
        if (!map.isEmpty()) {
            size = Collections.max(map.values());
        }

        System.out.println(map.size()); // 그룹의 개수
        System.out.println(size); // 가장 큰 그룹의 크기
    }

    // 두 선분의 교차 여부 판단 (이전 문제와 동일)
    static boolean isIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int c1 = ccw(x1, y1, x2, y2, x3, y3);
        int c2 = ccw(x1, y1, x2, y2, x4, y4);
        int c3 = ccw(x3, y3, x4, y4, x1, y1);
        int c4 = ccw(x3, y3, x4, y4, x2, y2);

        if( c1 * c2 < 0 && c3 * c4 < 0) return true;

        if( c1 == 0 && isBetween(x1, y1, x2, y2, x3, y3)) return true;
        if( c2 == 0 && isBetween(x1, y1, x2, y2, x4, y4)) return true;
        if( c3 == 0 && isBetween(x3, y3, x4, y4, x1, y1)) return true;
        if( c4 == 0 && isBetween(x3, y3, x4, y4, x2, y2)) return true;
        return false;
    }

    static boolean isBetween(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.min(x1, x2) <= x3 && x3 <= Math.max(x1, x2) && Math.min(y1,y2) <= y3 && y3 <= Math.max(y1, y2);
    }

    static int ccw(int x1, int y1, int x2, int y2, int x3, int y3) {
        long result = (long)(x2 - x1) * (y3 - y1) - (long)(y2 - y1) * (x3 - x1);
        return Long.compare(result, 0);
    }
}
