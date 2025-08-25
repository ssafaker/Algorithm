package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [스타트와 링크]
 *  링크 : [https://www.acmicpc.net/problem/14889]
 *  난이도 : [실버 1]
 *  설명:
 *  - N명의 사람을 두 팀으로 나누어 팀 능력치 차이의 최소값을 구하는 문제.
 *  - 능력치는 ability[i][j]로 주어지며, i와 j가 같은 팀일 경우 합산된다.
 *  - N은 짝수이며, 두 팀은 각각 N/2명으로 구성되어야 한다.
 *
 *  풀이:
 *  - DFS + 백트래킹으로 팀 조합을 생성한다.
 *  - team[] 배열을 사용해 어떤 사람이 스타트팀에 속했는지 표시.
 *  - 한쪽 팀이 완성되면 나머지는 자동으로 다른 팀이 되므로 따로 처리할 필요 없음.
 *  - 두 팀의 능력치를 계산해 최소 차이를 갱신한다.
 *  - 대칭 조합(예: 팀 A와 팀 B를 서로 바꾼 경우)을 줄이기 위해 0번 사람을 무조건 스타트팀에 고정.
 */

public class Main_BOJ_14889 {
    static int N, ans;
    static int[][] ability; // 능력치 저장 배열
    static boolean[] team;  // 스타트팀 여부 표시

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        ability = new int[N][N];
        team = new boolean[N];

        // 능력치 입력
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                ability[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        ans = Integer.MAX_VALUE;

        // 0번 사람은 스타트팀에 고정 (중복 조합 방지)
        team[0] = true;
        dfs(1, 1);

        System.out.println(ans);
    }

    // DFS: depth는 탐색할 사람 번호, teamCount는 현재 스타트팀 인원 수
    static void dfs(int depth, int teamCount) {
        // 스타트팀이 N/2명이 되면 두 팀의 차이를 계산
        if(teamCount == N/2){
            ans = Math.min(ans, calculate());
            return;
        }

        // depth부터 시작해 스타트팀 구성
        for (int i = depth; i < N; i++) {
            if(team[i]) continue;   // 이미 선택된 경우 skip
            team[i] = true;         // 스타트팀에 포함
            dfs(i + 1, teamCount + 1);
            team[i] = false;        // 백트래킹
        }
    }

    // 두 팀의 능력치 차이를 계산
    static int calculate() {
        int startTeamAbility = 0;
        int linkTeamAbility = 0;
        for (int i = 0; i < N-1; i++) {
            for (int j = i+1; j < N; j++) {
                if(team[i] && team[j]) { // 스타트팀
                    startTeamAbility += ability[i][j];
                    startTeamAbility += ability[j][i];
                } else if(!team[i] && !team[j]){ // 링크팀
                    linkTeamAbility += ability[i][j];
                    linkTeamAbility += ability[j][i];
                }
            }
        }
        return Math.abs(startTeamAbility - linkTeamAbility);
    }
}
