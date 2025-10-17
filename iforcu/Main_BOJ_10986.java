package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : 나머지 합 (BOJ 10986)
 *  링크   : https://www.acmicpc.net/problem/10986
 *  난이도 : 골드 3
 *  설명   :
 *    - N개의 수로 이루어진 배열에서 연속된 부분 구간의 합이 M으로 나누어 떨어지는 구간의 개수 구하기
 *    - 부분합이 M으로 나누어 떨어지는 (i, j) 쌍의 개수
 *  풀이   :
 *    - 누적합을 구하면서 각 나머지별 등장 횟수 카운트
 *    - 같은 나머지끼리 2개 선택하면 구간합이 M으로 나누어 떨어짐
 *    - 조합 공식으로 정답 계산
 */

public class Main_BOJ_10986 {
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        long sum = 0;
        long[] remainderCounts = new long[M];
        remainderCounts[0] = 1;
        st = new StringTokenizer(br.readLine());
        for(int i = 0; i < N; i++) {
            sum += Integer.parseInt(st.nextToken());

            int R = (int) (sum % M);

            remainderCounts[R]++;
        }

        long answer = 0;

        for(int i = 0; i < M; i++) {
            long count = remainderCounts[i];
            if(count > 1) {
                answer += count *(count - 1) / 2;
            }
        }

        System.out.println(answer);
    }
}