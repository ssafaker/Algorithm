package iforcu;

import java.util.*;
import java.io.*;

/*
 * 문제명: [줄기세포배양]
 * 링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRJ8EKe48DFAUo]
 * 난이도 : [모의 SW 역량테스트]
 * 설명:
 * - 줄기 세포는 life를 갖고 time에 따라서 활성화후 time*2가 되면 죽음
 * - 번식은 상하좌우로 진행되며, life에 따라 공간을 점유함
 * - 세포의 활성화, 비활성화의 갯수를 구하는 문제
 * - 배양 용기의 크기는 무한하다고 가정해야함. (2차원 배열 사용 x)
 * 풀이:
 * - Queue를 이용해 살아있는 세포를 관리 
 * - int[] 에 세포의 정보를 저장 
 *  - 배열 인덱스: 0=r, 1=c, 2=life, 3=birthTime
 * - Set를 이용해 점유 공간을 저장함.
 * - newMaps에서 세포 우선권을 만듬.
 * - newMap에 세포를 기준으로 새로운 배양줄기 map, set을 구성
 */

public class Main_SEA_5653 {
    static int[] dr = {-1, 1, 0, 0};
    static int[] dc = {0, 0, -1, 1};

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            // 살아있는 세포들을 관리하는 큐. 각 세포는 int[] 배열로 표현.
            // 배열 인덱스: 0=r, 1=c, 2=life, 3=birthTime
            Queue<int[]> lifeCells = new LinkedList<>();
            
            // 모든 세포가 차지한 공간을 "r,c" 형태의 문자열 키로 기록
            Set<String> map = new HashSet<>();

            // 1. 초기 세포 설정
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    int life = Integer.parseInt(st.nextToken());
                    if (life > 0) {
                        lifeCells.add(new int[]{i, j, life, 0}); // {r, c, 생명력, 태어난 시간=0}
                        map.add(i + "," + j); // 좌표를 문자열 키로 변환하여 저장
                    }
                }
            }

            // 2. K시간 동안 시뮬레이션 진행
            for (int t = 1; t <= K; t++) {
                // 동시 번식 충돌 해결을 위한 임시 저장소. Key: "r,c", Value: 생명력
                Map<String, Integer> newMaps = new HashMap<>();

                int qSize = lifeCells.size();
                // qSize 값이 0보다 크면 실행 이후 자동으로 -1;
                // lifeCells를 전부 확인
                while (qSize-- > 0) {
                    int[] cell = lifeCells.poll();
                    int r = cell[0];
                    int c = cell[1];
                    int life = cell[2];
                    int birthTime = cell[3];
                    
                    int age = t - birthTime;

                    // 활성화 및 번식: 나이가 생명력 + 1과 같아지는 시점
                    if (age == life + 1) {
                        for (int d = 0; d < 4; d++) {
                            int nextR = r + dr[d];
                            int nextC = c + dc[d];
                            String key = nextR + "," + nextC;
                            
                            // 현재 map에 점유되지 않는 공간이면 세포 배양
                            if (!map.contains(key)) {
                                // 세포의 우선권 처리를 위한 로직 - life 현재 세포, newBorns : 0 or 점유된 life 값
                                if (life > newMaps.getOrDefault(key, 0)) {
                                    newMaps.put(key, life);
                                }
                            }
                        }
                    }

                    // 나이가 (생명력 * 2)가 될 때까지 살아있음.
                    if (age <= life * 2) {
                        lifeCells.add(cell);
                    }
                }

                // 3. 새로 태어난 세포들을 큐와 점유 공간에 추가
                for (Map.Entry<String, Integer> entry : newMaps.entrySet()) {
                    String key = entry.getKey();
                    int newLife = entry.getValue();

                    String[] coords = key.split(","); // "r,c" 문자열을 파싱하여 좌표 복원
                    int newR = Integer.parseInt(coords[0]);
                    int newC = Integer.parseInt(coords[1]);

                    lifeCells.add(new int[]{newR, newC, newLife, t}); // 새 세포의 birthTime은 현재 시간 t
                    map.add(key);
                }
            }
            System.out.println("#"+test_case+" "+lifeCells.size());
        }
    }
}
