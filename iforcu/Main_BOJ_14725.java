package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [개미굴]
 *  링크 : [https://www.acmicpc.net/problem/14725]
 *  난이도 : [골드 3]
 *  설명: 
 *   - 개미굴의 각 경로가 주어질 때, 전체 개미굴 구조를 계층적으로 출력하는 문제.
 *   - 각 경로는 루트에서 특정 방까지의 먹이 정보로 구성됨.
 *   - 같은 깊이에서는 사전순으로 정렬하여 출력해야 함.
 *  풀이:
 *   - Trie 자료구조를 사용하여 개미굴의 계층 구조 표현.
 *   - TreeMap을 사용하여 자동으로 사전순 정렬 보장.
 *   - DFS로 트리를 순회하며 깊이에 따라 "--" 출력.
 */

public class Main_BOJ_14725 {
    static int N;
    
    // Trie 노드 클래스
    static class TrieNode {
        String value;
        Map<String, TrieNode> children; // TreeMap으로 자동 사전순 정렬

        public TrieNode(String value) {
            this.value =  value;
            this.children =  new TreeMap<>();
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        TrieNode root = new TrieNode(""); 
        
        // Trie에 각 경로 삽입
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int K = Integer.parseInt(st.nextToken());
            
            TrieNode cur = root;
            for (int j = 0; j < K; j++) {
                String food = st.nextToken();
                cur.children.putIfAbsent(food, new TrieNode(food));
                cur = cur.children.get(food);
            }
        }
        
        printTrie(root, 0); // 개미굴 구조 출력
    }

    // DFS로 Trie 순회하며 개미굴 구조 출력
    static void printTrie(TrieNode node, int depth) {
        if(!node.value.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            // 깊이에 따라 "--" 추가
            for (int i = 0; i < depth - 1; i++) {
                sb.append("--");
            }
            System.out.println(sb.toString() + node.value);
        }
        // 자식 노드들을 사전순으로 순회 (TreeMap 사용)
        for (TrieNode child : node.children.values()) {
            printTrie(child, depth + 1);
        }
    }
}
