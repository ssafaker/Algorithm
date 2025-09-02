package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [가장 긴 증가하는 부분 수열 5]
 *  링크 : [https://www.acmicpc.net/problem/14003]
 *  난이도 : [플래티넘 5]
 *  설명:
 *   - 수열 A에서 가장 긴 증가하는 부분 수열(LIS)의 길이와 실제 LIS 수열을 출력하는 문제.
 *   - 단순 O(N^2) DP로는 시간 초과, 이분 탐색을 활용한 O(N log N) 방식 필요.
 *  풀이:
 *   - tails 리스트를 유지하며 이분 탐색으로 LIS를 계산.
 *   - 각 원소의 이전 인덱스를 저장하는 배열 P를 두어 LIS 경로를 복원.
 *   - LIS_indices 배열로 LIS의 마지막 인덱스를 추적하여 스택으로 역추적 후 출력.
 */

public class Main_BOJ_14003 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());

        // 입력 수열
        int[] A = new int[N];
        for (int i = 0; i < N; i++) {
            A[i] = Integer.parseInt(st.nextToken());
        }

        // LIS 계산용 리스트
        List<Integer> tails = new ArrayList<>();

        // P: 이전 원소 인덱스 추적, LIS_indices: 각 길이에서의 원소 인덱스
        int[] P =  new int[N];
        int[] LIS_indices = new int[N];

        // 초기 세팅
        tails.add(A[0]);
        LIS_indices[0] = 0;
        P[0] = -1;
        int length = 1;

        // LIS 탐색
        for (int i = 0; i < N; i++) {
            if(A[i] > tails.get(length - 1)) {
                // 현재 값이 가장 큰 경우 LIS 확장
                P[i] = LIS_indices[length - 1];
                tails.add(A[i]);
                LIS_indices[length] = i;
                length++;
            } else {
                // 이분 탐색으로 적절한 위치 찾기
                int left = 0;
                int right = length - 1;
                while (left < right) {
                    int mid = (left + right) / 2;
                    if (tails.get(mid) >= A[i]) {
                        right = mid;
                    } else {
                        left = mid + 1;
                    }
                }
                // tails 갱신
                tails.set(right, A[i]);
                LIS_indices[right] = i;
                if (right > 0) {
                    P[i] = LIS_indices[right - 1];
                } else {
                    P[i] = -1;
                }
            }
        }

        // LIS 길이 출력
        System.out.println(length);

        // LIS 실제 수열 복원 (역추적)
        Stack<Integer> stack = new Stack<>();
        int index = LIS_indices[length - 1];
        while (index != -1) {
            stack.push(A[index]);
            index = P[index];
        }

        // 결과 출력
        StringBuilder sb = new StringBuilder();
        while (!stack.isEmpty()) {
            sb.append(stack.pop()).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}