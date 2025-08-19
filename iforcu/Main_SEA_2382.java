package iforcu;

import java.util.*;
import java.io.*;

/*
 *  문제명: [미생물 격리]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AV597vbqAH0DFAVl]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 2차원 배열 시뮬레이션 문제
 *  - 미생물 (r, c, value, d) 정보 필요
 *  - Queue 미생물 관리
 *  - Map을 통해 key와 key를 통한 배열 관리
 *  풀이:
 *  - lifeCells이라는 Queue를 현재 살아있는 미생물이라 정의
 *  - newLifeCells은 Map을 한번 이동 후 남아 있는 미생물이라 정의
 *  1. M만큼 시간을 흐르는 for문
 *      1. lifeCells에서 모든 미생물 이동 및 newLifeCells에 저장
 *          1. 만약 가장자리면 방향 변경 및 value / 2
 *          2. newLifeCells에 미생물 정보 저장 key가 없으면 new ArrayList 생성
 *      2. newLifeCells에 있는 미생물을 다시 lifeCells로 저장
 *          1. group은 key -> (r,c) 를 기준으로 모여있는 미생물 그룹을 이용해 미생물 통합
 *          2. 이후 다시 통합된 미생물을 lifeCells에 저장
 *  2. 현재 lifeCells에 남아있는 value값의 합을 출력
 */ 

public class Main_SEA_2382 {
    static int N, M, K;
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            M = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            PriorityQueue<int[]> lifeCells = new PriorityQueue<>(
                (a,b) -> Integer.compare(b[2], a[2])
            );

            for (int i = 0; i < K; i++) {
                int[] data = Arrays.stream(br.readLine().split(" "))
                                    .mapToInt(Integer::parseInt)
                                    .toArray();
                data[3] -= 1;
                lifeCells.add(data);
            }

            // 1. M 만큼의 시간을 흐르는 로직
            for (int time = 1; time <= M; time++) {
                Map<String, List<int[]>> newLifeCells = new HashMap<>();
                
                // 1. 현재 남아있는 미생물을 모두 이동 시키고 newLifeCells에 저장
                int qSize = lifeCells.size();
                while(qSize-- > 0) {
                    int[] data = lifeCells.poll();
                    int nr = data[0] + dr[data[3]];
                    int nc = data[1] + dc[data[3]];
                    int value = data[2];
                    int d = data[3];
                    String key = nr + "," + nc;
                    // 1. 만약 가장자리면 방향을 바꾸고 value/2 저장
                    if(nr == 0 || nr == N-1 || nc == 0 || nc == N-1) {
                        d = (d % 2 == 0) ? d + 1 : d - 1;
                        value = value/2;
                    }
                    // 2. computeIfAbsent사용 key가 없다면 새로운 List 만듬, 이후에 int[] 값 저장
                    newLifeCells.computeIfAbsent(key, k ->  new ArrayList<>())
                                .add(new int[]{nr, nc, value, d});
                }

                // 2. newLifeCells에 있는 미생물을 다시 lifeCells 넣기
                for (Map.Entry<String, List<int[]>> entry : newLifeCells.entrySet()){
                    String key = entry.getKey();
                    List<int[]> group = entry.getValue(); // group은 특정 (r,c)에 몰려있는 미생물 정보들

                    int totalValue = 0;
                    int maxValue = Integer.MIN_VALUE;
                    int selectedD = 0;
                    // 1. group 안에 있는 값을 정리하여 우선 순위 및 통합
                    for(int[] cell : group) {
                        int value = cell[2];
                        int d = cell[3];
                        totalValue += value;

                        if(value > maxValue) {
                            maxValue = value;
                            selectedD = d;
                        }
                    }

                    // 2. 다시 lifeCells에 현재 남아있는 미생물 저장
                    String[] keys = key.split(",");
                    int r = Integer.parseInt(keys[0]);
                    int c = Integer.parseInt(keys[1]);
                    lifeCells.add(new int[] {r, c, totalValue, selectedD});
                }
            }

            // 2. 현재 lifeCells에 남아있는 value 값의 합을 출력
            int ans = 0;
            for(int[] cell : lifeCells) {
                ans += cell[2];
            }
            System.out.println("#"+test_case+" "+ans);
        }
    }
}
