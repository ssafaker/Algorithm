package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : 개똥벌레 (BOJ 3020)
 *  링크   : https://www.acmicpc.net/problem/3020
 *  난이도 : 골드 5
 *  설명   :
 *    - N미터 길이, H미터 높이의 동굴에 석순/종유석 장애물이 번갈아 등장
 *    - 개똥벌레가 지나갈 때 파괴해야 하는 장애물의 최솟값과 그 구간의 개수 구하기
 *  풀이   :
 *    - 석순/종유석 각각 누적합 배열로 구간별 장애물 개수 계산
 *    - 각 높이별로 파괴해야 하는 장애물 수를 빠르게 구함
 *    - 최솟값과 해당 구간 개수 카운트
 */

public class Main_BOJ_3020 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int H = Integer.parseInt(st.nextToken());
        int[] arr = new int[H];
        int[] prefix = new int[H];
        for(int i = 0; i < N; i++) {
            int len = Integer.parseInt(br.readLine());
            if(i % 2 == 0) {
                arr[len - 1] += 1;
            } else {
                arr[H-1] += 1;
                arr[(H-len)-1] -= 1;
            }
        }
        prefix[H-1] = arr[H-1];
        for(int i = H-2; i >= 0; i--) {
            prefix[i] += prefix[i+1] + arr[i];
        }
        int least = 500001;
        int count = 0;
        for(int i = 0; i < H; i++) {
            if(least > prefix[i]) {
                least = prefix[i];
                count = 1;
            } else if(least == prefix[i]) {
                count++;
            }
        }
        System.out.println(least+" "+count);
    }
}