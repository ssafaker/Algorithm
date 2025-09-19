package iforcu;

import java.io.*;
import java.util.*;
/*
 *  문제명 : [오아시스 재결합]
 *  링크   : [https://www.acmicpc.net/problem/3015]
 *  난이도 : [플래티넘 5]
 *  설명   :
 *    - N명이 줄을 서 있을 때, 서로 볼 수 있는 쌍의 수를 구하는 문제.
 *    - 두 사람이 서로 볼 수 있으려면, 그 사이에 두 사람보다 키가 큰 사람이 없어야 한다.
 *    - N은 최대 500,000, 키는 2^31 미만의 자연수.
 *  풀이   :
 *    - 스택을 이용해 각 사람의 키를 관리하며, 볼 수 있는 쌍의 수를 효율적으로 계산.
 *    - 같은 키가 연속될 경우 count로 묶어 처리하여 중복 계산을 방지.
 *    - 스택에서 pop할 때마다 볼 수 있는 쌍을 누적하여 정답을 구함.
 */

public class Main_BOJ_3015 {
    static class Person {
        int h;
        int count;
        Person(int h, int count) {
            this.h = h;
            this.count = count;
        }
    }
    public static void main(String[] args) throws Exception {
        // 입력 처리 및 변수 초기화
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        long ans = 0;
        // 스택: 현재까지 줄에 선 사람들의 키와 같은 키의 연속 개수 관리
        Stack<Person> st = new Stack<>();

        for (int i = 0; i < N; i++) {
            // 각 사람의 키 입력 및 쌍 계산
            int curH = Integer.parseInt(br.readLine());
            int count = 1;

            while (!st.isEmpty() && st.peek().h <= curH) {
                // 스택의 top이 현재 사람보다 키가 작거나 같으면 pop하며 쌍의 수 누적
                // 같은 키가 연속될 경우 count를 합산
                Person top = st.pop();

                ans += top.count;
                if(top.h == curH) {
                    count += top.count;
                }
            }

            // 스택에 남아있는 사람이 있으면 현재 사람과 볼 수 있는 쌍 추가
            if(!st.isEmpty()) ans++;
            // 현재 사람을 스택에 추가 (키, 연속 개수)
            st.push(new Person(curH, count));
        }

        // 전체 볼 수 있는 쌍의 수 출력
        System.out.println(ans);
    }
}
