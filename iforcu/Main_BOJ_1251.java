package iforcu;

import java.io.*;

/*
 *  문제명 : [단어 나누기]
 *  링크   : [https://www.acmicpc.net/problem/1251]
 *  난이도 : [실버5]
 *  설명   :
 *    - 주어진 단어를 세 부분으로 나누고, 각 부분을 뒤집은 후 합쳐서 사전순으로 가장 앞서는 단어 찾기
 *    - 각 부분은 최소 길이 1 이상이어야 함
 *    - 단어 길이는 3 이상 50 이하
 *  풀이   :
 *    - 브루트포스로 모든 가능한 분할점 조합을 시도
 *    - 각 분할에 대해 세 부분을 뒤집고 합쳐서 후보 문자열 생성
 *    - 사전순으로 가장 작은 문자열을 찾아 출력
 */

public class Main_BOJ_1251 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        // 입력 단어 읽기 및 초기화
        String word = br.readLine();
        int n = word.length();
        String result = null;

        // 모든 가능한 분할점 조합을 시도하여 최소 사전순 문자열 찾기
        for (int i = 1; i < n - 1; i++) {        // 첫 번째 분할
            for (int j = i + 1; j < n; j++) {    // 두 번째 분할
                // 세 부분으로 나누고 각각을 뒤집기
                String part1 = new StringBuilder(word.substring(0, i)).reverse().toString();
                String part2 = new StringBuilder(word.substring(i, j)).reverse().toString();
                String part3 = new StringBuilder(word.substring(j)).reverse().toString();

                String candidate = part1 + part2 + part3;

                // 사전순으로 더 작은 문자열이면 갱신
                if (result == null || candidate.compareTo(result) < 0) {
                    result = candidate;
                }
            }
        }

        // 결과 출력
        System.out.println(result);
    }
}
