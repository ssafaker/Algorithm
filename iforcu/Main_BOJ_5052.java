package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [전화번호 목록]
 *  링크 : [https://www.acmicpc.net/problem/5052]
 *  난이도 : [골드 4]
 *  설명: 
 *   - 전화번호 목록이 주어질 때, 한 번호가 다른 번호의 접두어인지 확인하는 문제.
 *   - 접두어 관계가 있으면 "NO", 없으면 "YES" 출력.
 *   - 예: "911"과 "91125426"이 있으면 "911"이 "91125426"의 접두어.
 *  풀이:
 *   - Trie 자료구조를 사용하여 접두어 관계 효율적으로 검사.
 *   - 삽입 과정에서 접두어 관계 발견 시 즉시 false 반환.
 *   - 문자열 끝에 도달했는데 자식이 있으면 접두어 관계 존재.
 */

public class Main_BOJ_5052 {
    // Trie 노드 클래스
    static class TrieNode {
        Map<Character, TrieNode> child;
        boolean isEnd; // 전화번호의 끝을 나타내는 플래그
        
        public TrieNode() {
            this.child = new TreeMap<>();
            this.isEnd = false;
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < T; i++) {
            int n = Integer.parseInt(br.readLine());
            String[] phones = new String[n];

            for (int j = 0; j < n; j++) phones[j] = br.readLine();

            Arrays.sort(phones); // 정렬하여 접두어 관계 효율적 검사
            boolean consistent = checkPhoneNumbers(phones);
            sb.append(consistent ? "YES\n" : "NO\n");
        }
        System.out.println(sb.toString());
    }
    
    // 전화번호들의 일관성 검사
    static boolean checkPhoneNumbers(String[] phones) {
        TrieNode root = new TrieNode();
        for (String phone : phones) if(!insertNumbers(root, phone)) return false;
        return true;
    }

    // Trie에 전화번호 삽입하며 접두어 관계 검사
    static boolean insertNumbers(TrieNode root, String phone) {
        TrieNode cur = root;
        for (int i = 0; i < phone.length(); i++) {
            char c = phone.charAt(i);
            // 현재 위치가 다른 번호의 끝이면 접두어 관계 존재
            if(cur.isEnd) return false;
            cur = cur.child.computeIfAbsent(c, key -> new TrieNode());
            if(i == phone.length() - 1) {
                cur.isEnd = true;
                // 현재 번호가 끝났는데 자식이 있으면 다른 번호의 접두어
                if(cur.child.size() > 0) return false;
            }
        }
        return true;
    }
}
