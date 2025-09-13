package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [LCA 2]
 *  링크   : [https://www.acmicpc.net/problem/11438]
 *  난이도 : [플래티넘 4]
 *  설명   :
 *    - N개의 정점으로 이루어진 트리에서 두 노드 쌍의 가장 가까운 공통 조상(LCA)을 빠르게 찾는 문제.
 *    - 루트는 1번이며, 쿼리 수가 많으므로 효율적인 알고리즘이 필요하다.
 *  풀이   :
 *    - 트리의 각 노드에 대해 부모와 깊이를 미리 계산한다.
 *    - 2^k 부모 정보를 저장해 빠르게 올라갈 수 있도록 전처리(Binary Lifting).
 *    - 두 노드의 깊이를 맞춘 뒤, 동시에 부모를 올려가며 LCA를 찾는다.
 */

public class Main_BOJ_11438 {
    static class SegmentTree {
    // 세그먼트 트리 자료구조
    // 각 노드에 구간 합 또는 특정 정보를 저장
        private int tree[];
        private int n;
        SegmentTree(int N) {
            // 트리 배열 생성 및 초기화
            this.n = N;
            tree = new int[n * 4]; 
        }

        public void update(int pos, int diff) {
            // 특정 위치의 값을 diff만큼 갱신
            update(0, n - 1, 1, pos, diff);
        }

        public int query(int pos) {
            // 0 ~ pos 구간의 합 또는 정보 반환
            return query(0, n - 1, 1, 0, pos);
        }

        private void update(int start, int end, int node, int dix, int diff) {
            // 트리의 특정 구간을 재귀적으로 갱신
            // dix가 현재 노드 구간에 포함되지 않으면 return
            // 포함되면 값을 갱신하고, 자식 노드로 분할
            if(dix < start || dix > end) return;

            tree[node] += diff;
            if(start != end) {
                int mid = (start + end) / 2;
                update(start, mid, node * 2, dix, diff);
                update(mid + 1, end, node * 2 + 1, dix, diff);
            }
        }

        private int query(int start, int end, int node, int l, int r) {
            // 쿼리 구간이 노드 범위와 겹치지 않으면 0 반환
            // 구간이 노드 범위에 완전히 포함되면 해당 노드 값 반환
            // 아니면 자식 노드로 분할하여 탐색 후 합 반환
            if(r < start || l > end) return 0;
            if(l <= start && r >= end) return tree[node];
            int mid = (start + end) / 2;
            return query(start, mid, node * 2, l, r) + query(mid + 1, end, node * 2 + 1, l, r);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(br.readLine());
        for (int test = 0; test < T; test++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int n = Integer.parseInt(st.nextToken());
            int m = Integer.parseInt(st.nextToken());

            // 각 테스트 케이스마다 n, m 입력
            // 트리 크기 및 세그먼트 트리 객체 생성
            int size = n + m + 1;
            SegmentTree sTree = new SegmentTree(size);

            // 각 영화의 현재 위치를 저장하는 배열
            int[] pos = new int[n + 1];
            st = new StringTokenizer(br.readLine());
            int[] movies = new int[m];
            for (int i = 0; i < m; i++) {
                // 쿼리로 주어진 영화 번호 입력
                movies[i] = Integer.parseInt(st.nextToken());
            }

            for (int i = 1; i <= n; i++) {
                // 영화 초기 위치 설정 및 트리에 추가
                pos[i] = m + i;
                sTree.update(pos[i], 1);
            }

            // 가장 위에 올 영화의 위치
            int top = m - 1;
            for (int i = 0; i < m; i++) {
                int movie = movies[i];
                int curPos = pos[movie];

                // 각 쿼리마다 영화의 현재 위치를 구하고, 그 왼쪽에 있는 영화 개수 출력
                int count = sTree.query(curPos-1);
                sb.append(count).append(" ");
                // 영화 위치를 트리에서 제거 후, 가장 위로 이동시키고 트리에 다시 추가
                sTree.update(curPos, -1);
                pos[movie] = top;
                sTree.update(pos[movie], 1);
                top--;
            }
            sb.append("\n");
        }
        System.out.println(sb.toString());
    }
}
