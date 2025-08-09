package iforcu;

import java.io.*;
import java.util.*;

/*
 * 문제명: BOJ 3273 - 두 수의 합
 * 링크 : https://www.acmicpc.net/problem/3273
 * 난이도 : 실버 3
 * 설명: n개의 자연수 중에서 두 수를 골라 합이 x가 되는 경우의 수를 구하는 문제
 * 풀이: 배열을 정렬한 후, 투 포인터 기법을 이용해 두 수의 합을 찾음
 */

public class Main_BOJ_3273 {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        int[] arr = new int[N];
        StringTokenizer st =new StringTokenizer(br.readLine());
        for (int i = 0; i < arr.length; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(arr);

        int x = Integer.parseInt(br.readLine());
        int count = 0;

        int left = 0;
        int right = N - 1;
        while (left < right) {
            int sum = arr[left] + arr[right];
            if (sum == x) {
                count++;
                left++;
                right--;
            } else if (sum < x) {
                left++;
            } else {
                right--;
            }
        }

        System.out.println(count);
    }
}
