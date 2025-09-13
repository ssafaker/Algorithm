package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [최솟값과 최댓값]
 *  링크 : [https://www.acmicpc.net/problem/2357]
 *  난이도 : [골드 1]
 *  설명: 
 *   - N개의 정수가 주어질 때, M번의 구간 쿼리에 대해 각 구간의 최솟값과 최댓값을 구하는 문제.
 *   - 각 쿼리는 구간 [a, b]에서 최솟값과 최댓값을 동시에 구해야 함.
 *   - N과 M은 최대 100,000이므로 효율적인 자료구조가 필요.
 *  풀이:
 *   - 세그먼트 트리를 사용하여 구간 최솟값/최댓값 쿼리를 O(log N)에 처리.
 *   - 최솟값용 트리와 최댓값용 트리를 각각 구성.
 *   - 트리 초기화 후 각 쿼리를 처리하여 결과 출력.
 */

public class Main_BOJ_2357 {
    static int n, min, max;
	static int INF = Integer.MAX_VALUE;
	static int[] elements, minTree, maxTree; // 원소 배열, 최솟값 트리, 최댓값 트리
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringBuilder sb = new StringBuilder();
		StringTokenizer st = new StringTokenizer(br.readLine());
		
		n = Integer.parseInt(st.nextToken());
		int m = Integer.parseInt(st.nextToken());
		
		elements = new int[n+1];
		for(int i=1; i<n+1; i++) {
			elements[i] = Integer.parseInt(br.readLine());
		}
		
		// 세그먼트 트리 크기 계산 및 초기화
		int size = getTreeSize();
		minTree = new int[size];
		maxTree = new int[size];
		init(0, minTree, 1, n, 1); // 최솟값 트리 초기화
		init(1, maxTree, 1, n, 1); // 최댓값 트리 초기화
		
		// M개의 쿼리 처리
		for(int i=0; i<m; i++) {
			st = new StringTokenizer(br.readLine());
			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());
			min = INF; 
            max = -1;
			query(0, minTree, 1, n, 1, a, b);
			query(1, maxTree, 1, n, 1, a, b);
			sb.append(min+" "+max+"\n");
		}
		System.out.println(sb.toString());
	}

	// 세그먼트 트리에 필요한 크기 계산
	static int getTreeSize() {
		int h = (int)Math.ceil(Math.log(n)/Math.log(2)) + 1;
		return (int)Math.pow(2, h);
	}
	
	// 세그먼트 트리 초기화 (type: 0=최솟값, 1=최댓값)
	static void init(int type, int[] tree, int start, int end, int node) {
		if(start == end) {
			tree[node] = elements[start];
		} else {
			int mid = (start + end) / 2;
			init(type, tree, start, mid, node * 2);
			init(type, tree, mid + 1, end, node * 2 + 1);
			
			if(type == 0) { // 최솟값
				tree[node] = Math.min(tree[node * 2], tree[node * 2 + 1]);
			} else { // 최댓값
				tree[node] = Math.max(tree[node * 2], tree[node * 2 + 1]);
			}
		}
	}
	
	// 구간 쿼리 처리 (type: 0=최솟값, 1=최댓값)
	static void query(int type, int[] tree, int start, int end, int node, int l, int r) {
		if(l > end || r < start) return; // 구간이 겹치지 않음
		if(l <= start && end <= r) { // 완전히 포함되는 경우
			if(type == 0) {
				min = Math.min(min, tree[node]);
			} else {
				max = Math.max(max, tree[node]);
			}
			return;
		}

		// 부분적으로 겹치는 경우 자식 노드들을 탐색
		int mid = (start + end) / 2;
		query(type, tree, start, mid, node * 2, l, r);
		query(type, tree, mid + 1, end, node * 2 + 1, l, r);
	}
}