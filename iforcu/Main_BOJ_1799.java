package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [비숍]
 *  링크 : [https://www.acmicpc.net/problem/1799]
 *  난이도 : [골드 1]
 *  설명: 
 *   - 체스판에서 비숍을 최대한 많이 놓되 서로 공격하지 않도록 배치하는 문제.
 *   - 비숍은 대각선으로만 이동하므로, 같은 대각선 상에 있으면 공격 가능.
 *   - 비숍을 놓을 수 있는 칸(1)과 없는 칸(0)이 주어짐.
 *  풀이:
 *   - 체스판을 흑백으로 나누어 각각 독립적으로 계산 (최적화).
 *   - 백트래킹으로 각 위치에 비숍을 놓거나 놓지 않는 경우를 탐색.
 *   - 대각선 충돌 체크를 위해 우하향, 좌하향 대각선 배열 사용.
 */

public class Main_BOJ_1799 {
    static int N, ans;
    static int[][] map;
    static List<int[]> blackBishops, whiteBishops;
    static boolean[] rightDownDiag, leftDownDiag;
    static int blackMax, whiteMax;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        map = new int[N][N];
        blackBishops = new ArrayList<>();
        whiteBishops = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(st.nextToken());
                if(map[i][j] == 1) {
                    // 체스판을 흑백으로 분리 (i+j가 짝수면 검은칸, 홀수면 흰칸)
                    if((i+j) % 2 == 0) blackBishops.add(new int[] {i, j});
                    else whiteBishops.add(new int[] {i, j});
                }
            }
        }

        rightDownDiag = new boolean[2 * N - 1];
        leftDownDiag = new boolean[2 * N - 1];
        
        // 흑백 칸을 각각 독립적으로 계산
        dfs(0,0, blackBishops, true);
        dfs(0,0, whiteBishops, false);
        
        System.out.println((blackMax + whiteMax));
    }

    // 백트래킹으로 비숍 배치
    static void dfs(int index, int count, List<int[]> bishop, boolean isBlack) {
        if(index == bishop.size()){
            if(isBlack) blackMax = Math.max(blackMax, count);
            else whiteMax = Math.max(whiteMax, count);
            return;
        }

        int[] cur = bishop.get(index);
        int r = cur[0];
        int c = cur[1];

        // 대각선 인덱스 계산 (우하향: r-c+N-1, 좌하향: r+c)
        int rightDiagIndex = r - c + (N-1);
        int leftDiagIndex = r + c;

        // 대각선에 다른 비숍이 없으면 배치
        if(!rightDownDiag[rightDiagIndex] && !leftDownDiag[leftDiagIndex]) {
            rightDownDiag[rightDiagIndex] = true;
            leftDownDiag[leftDiagIndex] = true;

            dfs(index + 1, count + 1, bishop, isBlack);

            rightDownDiag[rightDiagIndex] = false;
            leftDownDiag[leftDiagIndex] = false;
        }

        // 현재 위치에 비숍을 놓지 않는 경우
        dfs(index + 1, count, bishop, isBlack);
    }
}
