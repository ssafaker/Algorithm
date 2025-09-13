
/*
 *  문제명: [책 페이지]
 *  링크 : [https://www.acmicpc.net/problem/1019]
 *  난이도 : [플래티넘 5]
 *  설명:
 *   - 1부터 N까지의 모든 페이지 번호에서 0~9가 각각 몇 번 등장하는지 구하는 문제.
 *   - 단순히 1씩 증가시키며 세면 시간 초과가 발생하므로, 자리수별로 묶어서 빠르게 계산해야 한다.
 *   - 각 자리수(1의 자리, 10의 자리 등)별로 등장 횟수를 누적하여 전체 카운팅을 효율적으로 수행한다.
 *  풀이:
 *   - 시작과 끝 범위를 1의 자리, 10의 자리 등으로 나누어 각 블록별로 등장 횟수를 계산한다.
 *   - 끝 수가 9가 될 때까지, 시작 수가 0이 될 때까지 각각 처리하여 경계값을 맞춘다.
 *   - 블록 단위로 각 숫자가 등장하는 횟수를 빠르게 누적하고, 마지막에 모든 자리수의 합을 출력한다.
 */
package iforcu;

import java.io.*;

public class Main_BOJ_1019 {
    static long[] number;
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        number = new long[10];

        int start = 1;
        int end = N;
        int point = 1;
        // 각 자리수별로 경계값을 맞추며 블록 단위로 카운팅
        while (start <= end) {
            // 끝 수가 9가 될 때까지 하나씩 감소시키며 카운팅
            while(end % 10 != 9 && start <= end) {
                countNumbers(end, point);
                end--;
            }
            if(start > end) break;

            // 시작 수가 0이 될 때까지 하나씩 증가시키며 카운팅
            while (start % 10 != 0 && start <= end) {
                countNumbers(start, point);
                start++;
            }
            // 블록 단위로 각 숫자가 등장하는 횟수 누적
            start /= 10;
            end /= 10;

            long blockCount = (end - start + 1);
            for (int i = 0; i < 10; i++) {
                number[i] += blockCount * point;
            }
            point *= 10;
        }

        for (long i : number) {
            System.out.print(i+" "); // 각 숫자별 등장 횟수 출력
        }
    }

    // 한 숫자에 대해 각 자리수별로 등장 횟수 누적
    public static void countNumbers(int n, int point) {
        while (n > 0) {
            number[n % 10] += point;
            n /= 10;
        }
    }
}