/*
 *  문제명: [구간 합 구하기]
 *  링크 : [https://www.acmicpc.net/problem/2042]
 *  난이도 : [골드 1]
 *  설명:
 *   - N개의 수에 대해 값의 변경과 구간 합을 반복적으로 구하는 문제.
 *   - 단순 누적합으로는 시간 초과가 발생하므로, 효율적인 자료구조가 필요하다.
 *   - 펜윅 트리(구간 합 트리)를 사용해 O(logN)으로 변경과 합 계산을 처리한다.
 *  풀이:
 *   - 입력받은 수열을 펜윅 트리(BIT)에 저장하여 구간 합과 값 변경을 빠르게 처리한다.
 *   - 변경 연산(1)은 해당 인덱스의 값을 갱신하고, 합 연산(2)은 구간의 합을 출력한다.
 *   - update, query 함수로 트리 갱신과 구간 합을 각각 구현한다.
 */
package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_2042 {
    static int N, M, K;
    static long[] tree, arr;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        arr = new long[N];
        tree = new long[N+1];
        // 입력받은 수열을 배열에 저장
        for (int i = 0; i < N; i++) {
            arr[i] = Long.parseLong(br.readLine());
        }

        // 펜윅 트리(BIT) 초기화: 각 값 반영
        for (int i = 0; i < N; i++) {
            update(i+1, arr[i]);
        }
        StringBuilder sb = new StringBuilder();
        // M번의 값 변경, K번의 구간 합 연산 처리
        for (int i = 0; i < M + K; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            long c = Long.parseLong(st.nextToken());

            // a==1: 값 변경, a==2: 구간 합 출력
            if(a == 1) {
                changeValue(b, c);
            } else {
                sb.append(query((int)c) - query(b-1)).append("\n");
            }
        }

        System.out.println(sb.toString());
    }

    // i번째까지의 구간 합을 구하는 펜윅 트리 쿼리 함수
    static long query(int i) {
        long sum = 0;
        while (i > 0) {
            sum += tree[i];
            i -= (i & -i); // 하위 비트만큼 이동하며 누적합 계산
        }
        return sum;
    }

    // i번째 값에 diff만큼 더하는 펜윅 트리 갱신 함수
    static void update(int i, long diff) {
        while (i <= N) {
            tree[i] += diff;
            i += (i & -i); // 하위 비트만큼 이동하며 트리 갱신
        }
    }

    // idx번째 값을 value로 변경하는 함수
    static void changeValue(int idx, long value) {
        long oldV = arr[idx - 1];
        long diff = value - oldV;

        arr[idx - 1] = value;
        update(idx, diff); // 변경된 값만큼 트리 갱신
    }
}
