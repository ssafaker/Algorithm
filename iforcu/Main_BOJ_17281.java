package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [⚾]
 *  링크 : [https://www.acmicpc.net/problem/17281]
 *  난이도 : [Gold 4]
 *  설명:
 *  - 9명의 타순을 정하고, 각 이닝마다 선수들의 결과(아웃, 안타, 홈런 등)가 주어진다.
 *  - 단, 1번 선수(인덱스 0)는 반드시 4번 타자(타순 index=3)에 배치해야 한다.
 *  - 모든 가능한 타순을 시뮬레이션하여 최종 점수의 최댓값을 구하는 문제.
 *
 *  풀이:
 *  - 백트래킹(dfs)으로 타순을 완전 탐색.
 *  - 각 타순에 대해 playBall() 함수를 실행해 실제 경기 시뮬레이션.
 *  - 주자 이동은 moveRunners() 함수로 처리.
 *  - 최대 점수를 ans에 저장하여 출력.
 */

public class Main_BOJ_17281 {
    static int N, ans;
    static int[][] player;   // player[i][j] = i번째 이닝, j번 선수의 결과
    static int[] order;      // 타순
    static boolean[] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        // 경기 데이터 입력
        player = new int[N][9];
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                player[i][j] = Integer.parseInt(st.nextToken());
            }
        }
        
        visited = new boolean[9];
        order = new int[9];
        ans = Integer.MIN_VALUE;

        // 타순 결정 시작
        dfs(0);

        System.out.println(ans);
    }

    // 타순 완전탐색 (백트래킹)
    private static void dfs(int depth) {
        if(depth == 9){
            playBall(); // 모든 타순이 정해지면 경기 시뮬레이션
            return;
        }

        // 1번 선수(0번 인덱스)는 반드시 4번 타자 위치에 고정
        if(depth == 3) {
            order[depth] = 0;
            dfs(depth + 1);
        } else {
            for (int i = 1; i < 9; i++) { // 나머지 선수 배치
                if(!visited[i]) {
                    visited[i] = true;
                    order[depth] = i;
                    dfs(depth + 1);
                    visited[i] = false;
                }
            }
        }
    }

    // 실제 경기 진행
    static void playBall() {
        int score = 0;
        int index = 0; // 타순 인덱스

        for (int i = 0; i < N; i++) { // 이닝 수 만큼 반복
            int outCount = 0;
            boolean[] bases =  new boolean[3]; // 1,2,3루 상태

            while(outCount < 3) { // 아웃 3개면 이닝 종료
                int hitter = order[index];      // 현재 타자
                int result = player[i][hitter]; // 타자의 결과

                if(result == 0) outCount++;            // 아웃
                else if(result == 1) score += moveRunners(bases, 1); // 안타
                else if(result == 2) score += moveRunners(bases, 2); // 2루타
                else if(result == 3) score += moveRunners(bases, 3); // 3루타
                else if(result == 4) score += moveRunners(bases, 4); // 홈런

                index = (index + 1) % 9; // 다음 타자
            }
        }
        ans = Math.max(ans, score);
    }

    // 주자 이동 및 득점 처리
    static int moveRunners(boolean[] bases, int hit) {
        int score = 0;

        // 3루부터 역순으로 이동 (덮어쓰기 방지)
        for (int i = 2; i >= 0; i--) {
            if(bases[i]) {
                int nextBase = i + hit;
                if(nextBase >= 3) score++;     // 홈 도착 시 점수
                else bases[nextBase] = true;   // 새로운 루 점유
                bases[i] = false;
            }
        }

        // 타자 자신 처리
        if(hit == 4) score++; // 홈런
        else bases[hit-1] = true;

        return score;
    }
}
