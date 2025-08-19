package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [나무 높이]
 *  링크 : [https://swexpertacademy.com/main/code/userProblem/userProblemDetail.do?contestProbId=AYFofW8qpXYDFAR4]
 *  난이도 : [D2]
 *  설명:
 *  - 각 나무의 높이를 최대 높이에 맞춰서 물을 주는 문제
 *  - 물은 1일차에 1, 2일차 2를 줘서 높게 만들 수 있음
 *  - 각 나무의 크기가 똑같을 때, 가장 최소의 day를 구함
 *  풀이: 
 *  - 현재 나무와 가장 높은 나무에서 최대한 2를 넣을 수 있는 만큼 넣음
 *  - 나머지 1만 남은 나무를 셈
 *  - 1 : count1 과 2 : count2의 값을 비교해 최소값만큼 day에 2 * min
 *  - 1이 더 높을때는 day += 2 * count1 - 1
 *  - 2가 더 높으면  day += count2 / 3 * 4 이후 day에 남은 일수 0 , 1 , 2 중의 값만큼 처리
 */

public class Main_SEA_14510 {
    static int N, highest;
    static int[] trees;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());

            trees = new int[N];
            highest = 0;
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                trees[i] = Integer.parseInt(st.nextToken());
                highest = Math.max(highest, trees[i]); 
            }

            int day = 0;
            int count1 = 0;
            int count2 = 0;
            for (int i = 0; i < N; i++) {
                int diff = highest - trees[i];
                count2 += diff / 2;
                count1 += diff % 2;
            }

            int minCount = Math.min(count1, count2);
            day += minCount * 2;
            count1 -= minCount;
            count2 -= minCount;

            if(count1 > 0) {
                day += (count1 * 2) - 1;
            } else {
                day += count2 / 3 * 4;
                count2 %= 3;
                if(count2 > 0) day += count2 + 1;
            }
            System.out.println("#"+test_case+" "+(day));
        }
    }
}
