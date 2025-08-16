package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [무선 충전]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRDL1aeugDFAUo]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 사용자들은 이동 위치가 정해져있음
 *  - 충전소는 D = |XA – XB| + |YA – YB| 공식으로 해당 위치에서 충전가능
 *  - 사용자들이 여러명이 접속하면 균등한 양을 충전 받음
 *  - 시간에 따른 충전량의 최대 값을 구하는 문제
 *  - 각 사용자의 시간마다 최고의 충전량을 받는 방법을 찾아야함
 *  풀이:
 *  - user의 위치 정보를 배열로 저장
 *  - 모든 충전소의 정보를 2차원 배열로 저장
 *  - 1. user의 move 구현
 *  - 2. user가 이동한 후 위치를 기준으로 충전할 수 있는 리스트 생성
 *  - 3. 충전할 수 있는 리스트를 기준으로 최대 충전 값 구하기
 */

public class Main_SEA_5644 {
    static int[] dr = {0, -1, 0, 1, 0};
    static int[] dc = {0, 0, 1, 0, -1};
    static int M, A;
    static int[][] charges;
    static int[] moveA, moveB;
    static int[] user1, user2;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            moveA = new int[M + 1];
            moveB = new int[M + 1];

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++) {
                moveA[i] = Integer.parseInt(st.nextToken());
            }

            st = new StringTokenizer(br.readLine());
            for (int i = 1; i <= M; i++) {
                moveB[i] = Integer.parseInt(st.nextToken());
            }

            charges = new int[A][4];  // BC 정보: x, y, C, P
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                int y = Integer.parseInt(st.nextToken());
                int x = Integer.parseInt(st.nextToken());
                int C = Integer.parseInt(st.nextToken());
                int P = Integer.parseInt(st.nextToken());
                charges[i] = new int[]{x, y, C, P};
            }

            user1 = new int[2];
            user2 = new int[2];
            user1[0] = 1; 
            user1[1] = 1;
            user2[0] = 10; 
            user2[1] = 10;

            int totalEnergy = 0;
            for (int time = 0; time <= M; time++) {
                // 1. 사용자 위치 이동
                if (time > 0) {
                    move(user1, moveA[time]);
                    move(user2, moveB[time]);
                }

                // 2. 각 사용자에 대해 사용 가능한 충전 리스트
                List<Integer> chargeList1 = getAvailableChargeList(user1);
                List<Integer> chargeList2 = getAvailableChargeList(user2);

                // 3. 최대 충전 조합 계산
                int max = getMaxEnergyFromChargeList(chargeList1, chargeList2);
                totalEnergy += max;
            }

            System.out.println("#" + test_case + " " + totalEnergy);
        }
    }

    static int getMaxEnergyFromChargeList(List<Integer> chargeList1, List<Integer> chargeList2) {
        int max = 0;
        // 둘다 없으면 리턴
        if(chargeList1.isEmpty() && chargeList2.isEmpty()) return 0;
        // user2만 있으면
        if (chargeList1.isEmpty()) {
            for (int b : chargeList2){
                max = Math.max(max, charges[b][3]);
            }
            return max;
        }
        // user1만 있으면
        if (chargeList2.isEmpty()) {
            for (int a : chargeList1){
                max = Math.max(max, charges[a][3]);
            }
            return max;
        }
        // 둘다 있으면, 각각의 선택을 for문으로 
        for(int a : chargeList1) {
            for(int b : chargeList2) {
                // 둘다 같은 위치를 골랐을 때, 아니면 각자의 위치에서 더 했을 때
                if(a == b) {
                    max = Math.max(max, charges[a][3]);
                } else {
                    max = Math.max(max, charges[a][3] + charges[b][3]);
                }
            }
        }
        return max;
    }

    static List<Integer> getAvailableChargeList(int[] user) {
        List<Integer> list = new ArrayList<>();
        int r = user[0];
        int c = user[1];

        for (int i = 0; i < A; i++) {
            // 위치에 따른 각 충전에서 거리 D 공식 이용
            int dist = Math.abs(charges[i][0] - r) + Math.abs(charges[i][1] - c);
            if (dist <= charges[i][2]) {
                list.add(i);
            }
        }
        return list;
    }

    static void move(int[] user, int dir) {
        user[0] += dr[dir];
        user[1] += dc[dir];
    }
}
