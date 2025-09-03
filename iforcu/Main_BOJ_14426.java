package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [접두사 찾기]
 *  링크 : [https://www.acmicpc.net/problem/14426]
 *  난이도 : [실버 1]
 *  설명: 
 *   - N개의 문자열 집합 S가 주어질 때, M개의 문자열이 S에 속한 문자열의 접두사인지 확인.
 *   - 각 문자열에 대해 접두사 관계가 있으면 카운트하여 총 개수 출력.
 *   - 문자열의 길이는 최대 500, N과 M은 최대 10,000.
 *  풀이:
 *   - Trie 자료구조를 사용하여 문자열 집합 S를 저장.
 *   - 각 검사 문자열을 Trie에서 탐색하여 접두사 여부 확인.
 *   - 중간에 경로가 없으면 접두사가 아님을 의미.
 */

public class Main_BOJ_14426 {
    static int N, M;
    static TrieNode root;

    // Trie 노드 클래스 
    static class TrieNode {
        Map<Character, TrieNode> child;
        public TrieNode() {
            this.child = new TreeMap<>();
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        root = new TrieNode();
        // N개의 문자열을 Trie에 삽입
        for (int i = 0; i < N; i++) insertString(br.readLine());
        
        int ans = 0;
        // M개의 문자열이 접두사인지 확인
        for (int i = 0; i < M; i++) if(checkString(br.readLine())) ans++;
        System.out.println(ans);
    }    // 문자열이 Trie에서 접두사로 존재하는지 확인
    private static boolean checkString(String s) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if(!cur.child.containsKey(c)) return false; // 경로가 없으면 접두사 아님
            cur = cur.child.get(c);
        }
        return true; // 끝까지 탐색 성공하면 접두사
    }

    // Trie에 문자열 삽입
    static void insertString(String s) {
        TrieNode cur = root;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            cur = cur.child.computeIfAbsent(c, key -> new TrieNode());
        }
    }
}
