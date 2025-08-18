package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [숫자 만들기]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 주어진 numbers 와 4종류 연산의 갯수가 주어짐
 *  - numbers는 N, 연산의 갯수는 N-1
 *  - 연산자의 사용 순서에 따라 결과가 달라지므로 모든 경우를 탐색
 *  풀이: 
 *  - DFS + 백트래킹 사용
 *  - 현재까지 선택된 숫자와 연산자 개수를 인자로 넘기며 재귀 탐색
 *  - 연산자는 남아 있는 개수만큼 사용 가능 (operates 배열)
 *  - 모든 숫자를 사용했을 때 결과 값을 최댓값, 최솟값과 비교해 갱신
 */

public class Main_SEA_4008 {
    static int N, max_value, min_value;
    static int[] operates;
    static int[] numbers;
    static boolean[] visited;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            N = Integer.parseInt(br.readLine());
            numbers = new int[N];
            operates = new int[4];
            visited = new boolean[N-1];
            
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int i = 0; i < 4; i++) {
                operates[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                numbers[i] = Integer.parseInt(st.nextToken());
            }

            max_value = Integer.MIN_VALUE;
            min_value = Integer.MAX_VALUE;
            // 첫 번째 숫자부터 시작
            dfs(1,numbers[0]);
            System.out.println("#"+test_case+" "+(max_value - min_value));
        }
    }
    static void dfs(int cnt, int num) {
        // 모든 숫자를 사용한 경우 → 최댓값/최솟값 갱신
        if(cnt == N) {
            if(num > max_value) max_value = num;
            if(num < min_value) min_value = num;
            return;
        }

        for (int i = 0; i < 4; i++) {
             // 해당 연산자가 남아 있으면 사용
            if(operates[i] > 0) {
                int temp = num;
                operates[i]--;

                 // 연산 적용
                if(i == 0) num += numbers[cnt];
                else if(i == 1) num -= numbers[cnt];
                else if(i == 2) num *= numbers[cnt]; 
                else num /= numbers[cnt];
                dfs(cnt + 1, num);

                 // 원상복구 (백트래킹)
                operates[i]++;
                num = temp;
            }
        }
    }

}
