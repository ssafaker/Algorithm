package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [트리의 순회]
 *  링크 : [https://www.acmicpc.net/problem/2263]
 *  난이도 : [골드 1]
 *  설명:
 *   - 트리의 중위 순회(inOrder)와 후위 순회(postOrder)가 주어질 때,
 *     전위 순회(preOrder)를 구하는 문제.
 *   - 트리의 노드 값은 서로 다르다.
 *  풀이:
 *   - 후위 순회의 마지막 원소는 항상 루트 노드.
 *   - 루트 노드를 중위 순회에서 찾아서 왼쪽 서브트리와 오른쪽 서브트리로 분할.
 *   - 재귀적으로 왼쪽과 오른쪽 서브트리에 대해 같은 과정을 반복.
 *   - 전위 순회는 "루트 → 왼쪽 → 오른쪽" 순서이므로, 루트를 먼저 출력하고 재귀 호출.
 */

public class Main_BOJ_2263 {
    static int N;
    static int[] position, inOrder, postOrder;
    static StringBuilder sb = new StringBuilder();
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        inOrder = new int[N];
        postOrder = new int[N];
        position = new int[N+1];

        // 중위 순회 입력
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            inOrder[i] = Integer.parseInt(st.nextToken());
            position[inOrder[i]] = i; // 값 → 인덱스 매핑
        }

        // 후위 순회 입력
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            postOrder[i] = Integer.parseInt(st.nextToken());
        }

        // 전위 순회 구하기
        getPreOrder(0, N-1, 0, N-1);

        System.out.println(sb);
    }

    // 전위 순회 생성 (inOrder 구간, postOrder 구간)
    static void getPreOrder(int inStart, int inEnd, int postStart, int postEnd) {
        if(inStart > inEnd || postStart > postEnd) return;

        int root = postOrder[postEnd]; // 후위 순회의 마지막 값 = 루트
        sb.append(root).append(" ");

        int rootIndex = position[root]; // 중위 순회에서 루트 위치
        int leftSize = rootIndex  - inStart; // 왼쪽 서브트리 크기

        // 왼쪽 서브트리
        getPreOrder(inStart, rootIndex - 1, postStart, postStart + leftSize - 1);
        // 오른쪽 서브트리
        getPreOrder(rootIndex + 1, inEnd, postStart + leftSize, postEnd - 1);
    }
}
