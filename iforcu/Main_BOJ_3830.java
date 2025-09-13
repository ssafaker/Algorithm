package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [교수님은 기다리지 않는다]
 *  링크   : [https://www.acmicpc.net/problem/3830]
 *  난이도 : [플래티넘 3]
 *  설명   :
 *    - 샘플들의 무게 차이를 측정하고, 쿼리로 두 샘플의 무게 차이를 빠르게 구하는 문제.
 *    - 측정 결과를 바탕으로 두 샘플의 무게 차이를 알 수 없으면 "UNKNOWN"을 출력.
 *  풀이   :
 *    - Union-Find(Disjoint Set) 자료구조를 활용해 각 샘플의 상대적 무게 차이를 관리.
 *    - 경로 압축과 가중치 누적을 통해 쿼리마다 빠르게 무게 차이를 계산.
 */

public class Main_BOJ_3830 {
    static int N, M;
    static int[] parents;
    static long[] weightDiff;

    static int find(int x) {
    // x의 루트 노드를 찾으면서 경로 압축 및 가중치 누적
        if(parents[x] == x) return x;
        int root = find(parents[x]);
        weightDiff[x] += weightDiff[parents[x]];
        parents[x] = root;
        return root;
    }

    static void union(int a, int b, int w) {
    // a와 b를 같은 집합으로 합치고, 무게 차이 관계를 갱신
    // weightDiff[rootB] = weightDiff[a] + w - weightDiff[b]로 상대적 무게 누적
        int rootA = find(a);
        int rootB = find(b);
        if(rootA == rootB) return;
        weightDiff[rootB] = weightDiff[a] + w - weightDiff[b];
        parents[rootB] = rootA;
    }

    static boolean isConnected(int a, int b) {
    // a와 b가 같은 집합(연결된 상태)인지 확인
        return find(a) == find(b);
    }
    
    public static void main(String[] args) throws Exception {
    // 입력 및 초기화
    // 각 샘플의 부모와 무게 차이 배열 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        while (true) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            if(N == 0 && M == 0) break;
            weightDiff = new long[N + 1];
            parents = new int[N + 1];
            for (int i = 0; i <= N; i++) parents[i] = i;

            for (int i = 0; i < M; i++) {
                // 각 명령 처리: !는 무게 측정, ?는 쿼리
                st = new StringTokenizer(br.readLine());
                char com = st.nextToken().charAt(0);
                if(com == '!') {
                    // ! a b w: b가 a보다 w그램 무겁다는 정보 입력, union으로 관계 갱신
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());
                    int w = Integer.parseInt(st.nextToken());
                    union(a, b, w);
                } else {
                    // ? a b: a와 b가 연결되어 있으면 무게 차이 출력, 아니면 UNKNOWN 출력
                    int a = Integer.parseInt(st.nextToken());
                    int b = Integer.parseInt(st.nextToken());

                    if(!isConnected(a, b)) sb.append("UNKNOWN\n");
                    else sb.append(weightDiff[b] - weightDiff[a]).append("\n");
                }
            }
        }
        System.out.println(sb.toString());
    }
}