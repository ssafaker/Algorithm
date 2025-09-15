/*
 *  문제명 : [구간 곱 구하기]
 *  링크   : [https://www.acmicpc.net/problem/11505]
 *  난이도 : [골드 1]
 *  설명   :
 *    - N개의 수에 대해 값의 변경과 구간 곱을 반복적으로 구하는 문제.
 *    - 단순 곱셈 누적합으로는 시간 초과가 발생하므로, 효율적인 자료구조가 필요하다.
 *    - 세그먼트 트리를 사용해 O(logN)으로 변경과 곱 계산을 처리한다.
 *  풀이   :
 *    - 입력받은 수열을 세그먼트 트리에 저장하여 구간 곱과 값 변경을 빠르게 처리한다.
 *    - 변경 연산(1)은 해당 인덱스의 값을 갱신하고, 곱 연산(2)은 구간의 곱을 출력한다.
 *    - init, update, query 함수로 트리 초기화, 갱신, 구간 곱을 각각 구현한다.
 */
package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_11505 {
    static final int MOD = 1_000_000_007;

    static class SegmentTree {
        private long[] tree;
        private long[] arr;
        private int N;

    // 세그먼트 트리 생성자: 트리 배열 및 원본 배열 초기화
    // 입력 배열을 받아 트리 크기 설정 및 초기화 함수 호출
    // 트리의 각 노드에 구간 곱을 저장
    public SegmentTree(long[] originalArr) {
            this.arr = originalArr;
            this.N = originalArr.length;
            this.tree = new long[N * 4];
            init(0, N-1, 1);
        }

    // idx번째 값을 newV로 변경하는 함수
    // 트리와 원본 배열 모두 값을 갱신
    // 변경된 값에 따라 트리의 구간 곱을 재계산
    public void update(int idx, long newV) {
            arr[idx] = newV;
            update(0, N-1, 1, idx, newV);
        }

    // left~right 구간의 곱을 구하는 함수
    // 트리의 쿼리 함수를 호출하여 구간 곱 반환
    // 결과는 MOD로 나눈 나머지로 출력
    public long query(int left, int right) {
            return query(0, N-1, 1, left, right);
        }

    // 세그먼트 트리 쿼리: 구간 곱 계산
    // 쿼리 범위가 노드 범위와 겹치면 곱 반환, 아니면 재귀적으로 탐색
    // 구간 곱 결과를 MOD로 나눠 반환
    private long query(int start, int end, int node, int left, int right) {
            if(left > end || right < start) return 1;
            if(left <= start && end <= right) return tree[node];
            int mid = (start + end) / 2;
            long leftProduct = query(start, mid, node * 2, left, right);
            long rightProduct = query(mid + 1, end, node * 2 + 1, left, right);
            return (leftProduct * rightProduct) % MOD;
        }

    // 세그먼트 트리 갱신: idx 위치 값 변경
    // 리프 노드까지 내려가 값을 변경 후, 부모 노드로 곱을 갱신
    // 변경된 값에 따라 상위 노드의 곱도 재계산
    private void update(int start, int end, int node, int idx, long newV) {
            if(idx < start || idx > end) return;
            if(start == end) {
                tree[node] = newV;
                return;
            }
            int mid = (start + end) / 2;
            if(idx <= mid) {
                update(start, mid, node * 2, idx, newV);
            } else {
                update(mid + 1, end, node * 2 + 1, idx, newV);
            }

            tree[node] = (tree[node * 2] * tree[node * 2 + 1]) % MOD;
        }

    // 세그먼트 트리 초기화: 리프부터 곱 계산
    // 리프 노드에 배열 값 저장, 부모 노드는 자식 곱으로 계산
    // 모든 노드에 구간 곱을 저장하여 트리 완성
    private long init(int start, int end, int node) {
            if(start == end) return tree[node] = arr[start];
            int mid = (start + end) / 2;
            long leftProduct = init(start, mid, node * 2);
            long rightProduct = init(mid + 1, end, node * 2 + 1);
            return tree[node] = (leftProduct * rightProduct) % MOD;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());
        int K = Integer.parseInt(st.nextToken());

        // 입력 처리 및 연산 분기 (값 변경/구간 곱)
        long[] array = new long[N];
        for (int i = 0; i < N; i++) {
            array[i] = Integer.parseInt(br.readLine());
        }

        SegmentTree sTree = new SegmentTree(array);
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            // a==1: 값 변경, a==2: 구간 곱 출력
            if(a == 1) {
                sTree.update(b-1, c);
            } else {
                sb.append(sTree.query(b-1, (int)(c-1))).append("\n");
            }
        }

        System.out.println(sb.toString());
    }
}
