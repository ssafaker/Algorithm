package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [휴대폰 자판]
 *  링크   : [https://www.acmicpc.net/problem/5670]
 *  난이도 : [플래티넘 4]
 *  설명   :
 *    - 사전에 있는 단어를 입력할 때, 자동완성 기능을 활용해 버튼 입력 횟수의 평균을 구하는 문제.
 *    - Trie 자료구조를 사용해 각 단어별 입력 횟수를 계산한다.
 *  풀이   :
 *    - Trie에 모든 단어를 삽입한다.
 *    - 각 단어별로 입력해야 하는 키의 개수를 계산해 평균을 구한다.
 */

public class Main_BOJ_5670 {
    
    static class TrieNode {
    // Trie 자료구조: 각 노드가 자식 문자와 단어의 끝 여부를 가짐
        Map<Character, TrieNode> child;
        boolean isEnd;
        
        public TrieNode() {
            this.child = new TreeMap<>();
            this.isEnd = false;
        }

        public void add(String[] words) {
            // 모든 단어를 Trie에 삽입
            for (String word : words) insert(word);
        }

        public double countKeyAverage(String[] words, int n) {
            // 모든 단어에 대해 입력해야 하는 키의 총합을 구해 평균 반환
            int total = 0;
            for (String word : words) total += countKey(word);
            return (double) total / n;
        }

        private void insert(String word) {
            // 단어를 Trie에 삽입
            TrieNode cur = this;
            for (char c : word.toCharArray()) {
                cur.child.putIfAbsent(c, new TrieNode());
                cur = cur.child.get(c);
            }
            cur.isEnd = true;
        }

        private int countKey(String word) {
            // 단어를 입력할 때 실제로 눌러야 하는 키의 개수 계산
            // 첫 글자는 반드시 입력, 이후 분기점/단어 끝/자식이 여러 개일 때 입력 필요
            int count = 1;
            TrieNode cur = this.child.get(word.charAt(0));
            for (int i = 1; i < word.length(); i++) {
                if(i == 0 || cur.child.size() > 1 || cur.isEnd) count++;
                cur = cur.child.get(word.charAt(i));
            }
            return count;
        }
    }

    public static void main(String[] args) throws Exception{
    // 입력 처리 및 Trie 생성, 평균 계산
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null && !line.isEmpty()) {
            int n = Integer.parseInt(line);
            String[] words = new String[n];
            for (int i = 0; i < n; i++) words[i] = br.readLine();
            TrieNode trieNode = new TrieNode();
            trieNode.add(words);
            sb.append(String.format("%.2f", trieNode.countKeyAverage(words, n))).append("\n");
        }
        System.out.println(sb.toString());
    }
}