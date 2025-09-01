package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [열쇠]
 *  링크 : [https://www.acmicpc.net/problem/9328]
 *  난이도 : [골드 1]
 *  설명: 
 *   - 건물 내부에 있는 문서('$')를 가능한 한 많이 훔쳐야 하는 문제.
 *   - 문을 열기 위해서는 대응되는 열쇠가 필요하며, 열쇠는 건물 내부에서 획득 가능.
 *   - 건물의 바깥쪽에서도 진입 가능하므로, 건물 테두리를 '.'로 감싸 접근을 쉽게 처리.
 *  풀이:
 *   - BFS 탐색을 사용해 건물 내부를 탐색.
 *   - 열쇠 획득 시 관련 문들을 다시 방문 가능하도록 큐에 삽입.
 *   - 도달 가능한 모든 문서를 세어 결과 출력.
 */

public class Main_BOJ_9328 {
    static int h, w, ans;
    static char[][] map;
    static int[] dr = {1, -1, 0, 0};
    static int[] dc = {0, 0, 1, -1};
    static boolean[][] visited;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine()); // 테스트 케이스 수
        StringBuilder sb = new StringBuilder();

        for (int test_case = 1; test_case <= T; test_case++) {
            // 건물 크기 입력
            StringTokenizer st = new StringTokenizer(br.readLine());
            h = Integer.parseInt(st.nextToken());
            w = Integer.parseInt(st.nextToken());

            map = new char[h+2][w+2];
            visited = new boolean[h+2][w+2];

            // 건물 전체를 '.'로 초기화 (바깥 영역 포함)
            for (int i = 0; i < h + 2; i++) {
                Arrays.fill(map[i], '.');
            }

            // 건물 내부 지도 입력
            for (int i = 1; i <= h; i++) {
                String s = br.readLine();
                char[] c = s.toCharArray();
                for (int j = 1; j <= w; j++) {
                    map[i][j] = c[j-1];
                }
            }

            // 초기 보유 열쇠 입력
            String s = br.readLine();
            char[] c = s.toCharArray();
            int startKey = 0;
            if(c[0] != '0') {
                for (int i = 0; i < c.length; i++) {
                    startKey = startKey | 1 << (c[i] - 'a');
                }
            }

            // BFS 탐색 후 얻은 문서 수 저장
            sb.append(bfs(startKey)).append("\n");
        }
        System.out.println(sb);
    }

    // BFS 탐색
    static int bfs(int startKey) {
        int count = 0; // 획득한 문서 수
        Queue<int[]> queue = new LinkedList<>();

        // 알파벳 A~Z 문(door) 대기 리스트
        List<int[]>[] doors = new ArrayList[26];
        for (int i = 0; i < 26; i++) {
            doors[i] =  new ArrayList<>();
        }

        // 시작 위치 (건물 밖) 추가
        queue.offer(new int[] {0, 0});
        visited[0][0] = true;
        int curKey = startKey;

        while (!queue.isEmpty()) {
            int temp[] = queue.poll();
            int r = temp[0];
            int c = temp[1];

            // 4방향 탐색
            for (int d = 0; d < 4; d++) {
                int nr = r + dr[d];
                int nc = c + dc[d];

                // 범위 밖, 벽, 이미 방문한 곳은 스킵
                if(!isValid(nr, nc) || map[nr][nc] == '*' || visited[nr][nc]) continue;

                char cell = map[nr][nc];
                visited[nr][nc] = true;

                if(Character.isLowerCase(cell)) { 
                    // 열쇠 획득
                    int keyType = cell - 'a';
                    curKey |= (1 << keyType);
                    queue.offer(new int[] {nr, nc});

                    // 열쇠에 해당하는 문들을 다시 큐에 삽입
                    if(!doors[keyType].isEmpty()) {
                        for (int[] point : doors[keyType]) {
                            queue.offer(new int[] {point[0], point[1]});
                        }
                        doors[keyType].clear();
                    }

                } else if(Character.isUpperCase(cell)) {
                    // 문 만남
                    int doorType = cell - 'A';
                    if((curKey & (1 << doorType)) != 0) {
                        // 열쇠 있으면 바로 통과
                        queue.offer(new int[] {nr, nc});
                    } else {
                        // 열쇠 없으면 대기 리스트에 저장
                        doors[doorType].add(new int[] {nr, nc});
                    }
                } else if(cell == '$') {
                    // 문서 획득
                    count++;
                    queue.offer(new int[] {nr, nc});
                } else {
                    // 빈 공간 이동
                    queue.offer(new int[] {nr, nc});
                }
            }
        }
        return count;
    }

    // 유효 범위 체크
    static boolean isValid(int r, int c) {
        return r >= 0 && r <= h+1 && c >= 0 && c <= w+1; 
    }
}