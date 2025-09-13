package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [최솟값]
 *  링크   : [https://www.acmicpc.net/problem/10868]
 *  난이도 : [골드 1]
 *  설명   :
 *    - N개의 정수로 이루어진 배열에서 여러 쿼리(a, b)에 대해 구간 내 최솟값을 빠르게 구하는 문제.
 *    - 쿼리 수가 많으므로 효율적인 자료구조가 필요하다.
 *  풀이   :
 *    - 세그먼트 트리를 사용하여 구간 최솟값을 O(logN) 시간에 구할 수 있도록 구현.
 *    - 트리의 리프 노드는 입력값, 내부 노드는 두 자식 노드의 최솟값을 저장.
 *    - 각 쿼리마다 트리를 탐색하여 해당 구간의 최솟값을 반환.
 */

public class Main_BOJ_10868 {
    static int N, M;

    static class SegmentTree {
    // 세그먼트 트리 자료구조
    // 트리의 각 노드는 해당 구간의 최솟값을 저장
        private int n;
        private int[] arr;
        private long[] tree;

        SegmentTree(int[] originalArr) {
            // 입력 배열을 받아 트리 배열을 생성하고 초기화
            this.n = originalArr.length;
            this.arr = originalArr;
            this.tree = new long[n * 4];
            init(0, n-1, 1);
        }

        long query(int left, int right) {
            return query(0, n-1, 1, left, right);
        }

        private long query(int start, int end, int node, int l, int r){
            // 쿼리 함수
            // 쿼리 구간이 노드 범위와 겹치지 않으면 무한대 반환
            // 구간이 노드 범위에 완전히 포함되면 해당 노드 값 반환
            // 아니면 자식 노드로 분할하여 탐색 후 최솟값 반환
            if(r < start || end < l) return Long.MAX_VALUE;
            if(l <= start && end <= r) return tree[node];

            int mid = (start + end) / 2;
            long leftMin = query(start, mid, node * 2, l, r);
            long rightMin = query(mid + 1, end, node * 2 + 1, l, r);
            return Math.min(leftMin, rightMin);
        }

        private void init(int start, int end, int node) {
            // 트리 초기화 함수
            // 리프 노드면 입력값 저장, 아니면 자식 노드의 최솟값 저장
            if(start == end) {
                tree[node] = arr[start];
                return;
            }
            int mid = (start + end) / 2;
            init(start, mid, node * 2);
            init(mid + 1, end, node * 2 + 1);
            tree[node] = Math.min(tree[node*2], tree[node*2 + 1]);
        }

    }
    public static void main(String[] args) throws Exception {
    /* 입력 및 트리 생성
     * - N, M 입력 후 N개의 정수 배열 입력
     * - 세그먼트 트리 객체 생성
     */
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        int[] arr = new int[N];
        for (int i = 0; i < N; i++) arr[i] = Integer.parseInt(br.readLine());
        SegmentTree sTree = new SegmentTree(arr);

        for (int i = 0; i < M; i++) {
            // M개의 쿼리(a, b) 입력
            // 각 쿼리에 대해 구간 최솟값을 트리에서 탐색하여 결과에 저장
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            sb.append(sTree.query(a-1, b-1)).append("\n");
        }

        System.out.println(sb.toString());
    // 모든 쿼리 결과 출력
    }
}