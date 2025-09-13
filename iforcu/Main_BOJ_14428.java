package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [수열과 쿼리 16]
 *  링크   : [https://www.acmicpc.net/problem/14428]
 *  난이도 : [골드 1]
 *  설명   :
 *    - 길이 N의 수열에 대해 값 변경과 구간 최소값 인덱스 쿼리를 반복적으로 처리하는 문제.
 *    - 세그먼트 트리를 사용해 O(logN)으로 값 변경과 구간 최소값 인덱스 계산을 처리한다.
 *    - 값이 같은 경우 인덱스가 작은 쪽을 반환해야 하므로 비교 함수에 주의한다.
 *  풀이   :
 *    - 세그먼트 트리 각 노드에 구간 최소값의 인덱스를 저장한다.
 *    - 값 변경 시 해당 인덱스만 갱신, 쿼리 시 구간 내 최소값 인덱스를 반환한다.
 *    - compareAndGetIndex 함수로 값이 같을 때 인덱스가 작은 쪽을 선택한다.
 */

public class Main_BOJ_14428 {
    static class SegmentTree {
        private int N;
        private long[] arr;
        private int[] tree;

    // 세그먼트 트리 생성자: 트리 배열 및 원본 배열 초기화
    // 입력 배열을 받아 트리 크기 설정 및 초기화 함수 호출
    // 트리의 각 노드에 구간 최소값 인덱스를 저장
    public SegmentTree(long[] originalArr) {
            this.N = originalArr.length;
            this.arr = originalArr;
            this.tree = new int[N * 4];
            init(0, N-1, 1);
        }

    // left~right 구간의 최소값 인덱스를 구하는 함수
    // 트리의 쿼리 함수를 호출하여 구간 최소값 인덱스 반환
    public int query(int left, int right) {
            return query(0, N-1, 1, left, right);
        }

    // idx번째 값을 newV로 변경하는 함수
    // 트리와 원본 배열 모두 값을 갱신
    // 변경된 값에 따라 트리의 구간 최소값 인덱스를 재계산
    public void update(int idx, long newV) {
            arr[idx] = newV;
            update(0, N-1, 1, idx);
        }

    // 두 인덱스의 값을 비교해 더 작은 값의 인덱스를 반환
    // 값이 같으면 인덱스가 작은 쪽을 반환
    private int compareAndGetIndex(int idx1, int idx2) {
            if(idx1 == -1) return idx2;
            if(idx2 == -1) return idx1;

            if(arr[idx1] < arr[idx2]) return idx1;
            else if(arr[idx1] > arr[idx2]) return idx2;
            else return Math.min(idx1, idx2);
        }

    // 세그먼트 트리 초기화: 리프부터 최소값 인덱스 계산
    // 리프 노드에 배열 값 저장, 부모 노드는 자식 중 더 작은 값의 인덱스 저장
    private int init(int start, int end, int node) {
            if(start == end) return tree[node] = start;
            int mid = (start + end) / 2;
            int leftMinIdx = init(start, mid, node * 2);
            int rightMinIdx = init(mid + 1, end, node * 2 + 1);
            return tree[node] = compareAndGetIndex(leftMinIdx, rightMinIdx);
        }

    // 세그먼트 트리 쿼리: 구간 최소값 인덱스 계산
    // 쿼리 범위가 노드 범위와 겹치면 인덱스 반환, 아니면 재귀적으로 탐색
    private int query(int start, int end, int node, int left, int right) {
            if(left > end || right < start) return -1;
            if(left <= start && end <= right) return tree[node];
            int mid = (start + end) / 2;
            int leftMinIdx = query(start, mid, node * 2, left, right);
            int rightMinIdx = query(mid + 1, end, node * 2 + 1, left, right);
            return compareAndGetIndex(leftMinIdx, rightMinIdx);
        }

    // 세그먼트 트리 갱신: idx 위치 값 변경
    // 리프 노드까지 내려가 값을 변경 후, 부모 노드로 최소값 인덱스를 갱신
    private void update(int start, int end, int node, int idx) {
            if(idx < start || idx > end) return;
            if(start == end) return;

            int mid = (start + end) / 2;
            update(start, mid, node * 2, idx);
            update(mid + 1, end, node * 2 + 1, idx);
            tree[node] = compareAndGetIndex(tree[node * 2], tree[node * 2 + 1]);
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long[] arr = new long[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        SegmentTree sTree = new SegmentTree(arr);
        StringBuilder sb = new StringBuilder();
        int M = Integer.parseInt(br.readLine());
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            if(a == 1) {
                sTree.update(b-1, c);
            } else {
                sb.append(sTree.query(b-1, (int)(c-1)) + 1).append("\n");
            }
        }
        System.out.println(sb.toString());
    }
}