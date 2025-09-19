package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [제곱 ㄴㄴ 수]
 *  링크   : [https://www.acmicpc.net/problem/1016]
 *  난이도 : [플래티넘5]
 *  설명   :
 *    - min 이상 max 이하의 정수 중 1보다 큰 제곱수로 나누어 떨어지지 않는 수(제곱ㄴㄴ수)의 개수를 구하는 문제
 *    - 1 ≤ min ≤ 1,000,000,000,000, min ≤ max ≤ min + 1,000,000
 *    - 매우 큰 범위의 수에 대해 효율적으로 판별해야 함
 *  풀이   :
 *    - 에라토스테네스의 체와 유사하게, 각 제곱수의 배수를 방문 처리
 *    - min~max 구간을 boolean 배열로 관리하여 제곱수의 배수만 체크
 *    - 방문되지 않은 수의 개수를 세어 정답 출력
 */

public class Main_BOJ_1016 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 입력 처리 및 구간 정보 저장
        long min = Long.parseLong(st.nextToken());
        long max = Long.parseLong(st.nextToken());

        int ans = (int) (max - min + 1);
        boolean[] visited = new boolean[ans];

        // 제곱수의 배수를 방문 처리하여 제곱ㄴㄴ수가 아닌 수를 체크
        for (long i = 2; i * i <= max; i++) {
            long pow = i * i;
            long start = min / pow;
            if(min % pow != 0) start++;
            for (long j = start; j  * pow <= max; j++) {
                visited[(int)(pow * j - min)] = true; 
            }
        }

        // 방문되지 않은(제곱ㄴㄴ수) 개수 세기
        int count = 0;
        for (int i = 0; i < ans; i++) {
            if(visited[i]) count++;
        }

        // 결과 출력
        System.out.println(ans - count);
    }
}