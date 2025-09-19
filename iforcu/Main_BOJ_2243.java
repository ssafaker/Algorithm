package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [사탕상자]
 *  링크   : [https://www.acmicpc.net/problem/2243]
 *  난이도 : [플래티넘5]
 *  설명   :
 *    - 사탕의 맛(1~1,000,000)별로 사탕을 넣거나 꺼내는 쿼리를 빠르게 처리하는 문제
 *    - A=1: b번째로 맛있는 사탕을 꺼내고, 해당 맛의 사탕 개수 감소
 *    - A=2: b맛의 사탕을 c개 넣거나 빼기
 *    - 사탕의 총 개수는 2,000,000,000 이하, 쿼리 수는 최대 100,000
 *  풀이   :
 *    - 맛별 사탕 개수를 세그먼트 트리로 관리
 *    - k번째로 맛있는 사탕을 찾는 쿼리는 세그먼트 트리에서 k번째 원소 탐색
 *    - 사탕 추가/삭제는 update로 처리, 쿼리마다 O(logN)로 동작
 */

public class Main_BOJ_2243 {

    static class SegmentTree {
        private static final int MAX_TASTE = 1_000_000;
        private long[] tree;

        SegmentTree() {
            tree = new long[MAX_TASTE * 4];
        }

        // 맛 index에 사탕 개수 diff만큼 추가/삭제
        public void update(int taste, int count) {
            update(1, MAX_TASTE, 1, taste, count);
        }   

        private void update(int start, int end, int node, int index, int diff) {
            if(index < start || index > end) return;

            tree[node] += diff;
            if(start != end) {
                int mid = (start + end) / 2;
                update(start, mid, node * 2, index, diff);
                update(mid+1, end, node * 2 + 1, index, diff);
            }
        }

        // k번째로 맛있는 사탕의 맛 index 반환
        public int query(int order) {
            return query(1, MAX_TASTE, 1, order);
        }

        private int query(int start, int end, int node, int rank) {
            if(start == end) return start;

            int mid = (start + end) / 2;
            long leftChildCount = tree[node * 2];
            if(rank <= leftChildCount) {
                return query(start, mid, node * 2, rank);
            } else {
                return query(mid + 1, end, node * 2 + 1, rank - (int)leftChildCount);
            }
        }
    }

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        // 쿼리 개수 입력 및 세그먼트 트리 초기화
        int N = Integer.parseInt(br.readLine());
        SegmentTree sTree = new SegmentTree();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            if(a == 1) {
                // b번째로 맛있는 사탕 꺼내기 및 개수 감소
                int b = Integer.parseInt(st.nextToken());
                int taste = sTree.query(b);
                sb.append(taste).append("\n");
                sTree.update(taste, -1);
            } else {
                // b맛의 사탕 c개 추가/삭제
                int b = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                sTree.update(b, c);
            }
        }

        // 결과 출력
        System.out.println(sb.toString());
    }
}