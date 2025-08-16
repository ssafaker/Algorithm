import java.io.*;
import java.util.*;

public class Main_SEA_5653 {
    static int[] dr = { -1, 1, 0, 0 };
    static int[] dc = { 0, 0, -1, 1 };

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int N = Integer.parseInt(st.nextToken());
            int M = Integer.parseInt(st.nextToken());
            int K = Integer.parseInt(st.nextToken());

            // 세포정보 key: (r, c), value: [생명력, 생성시간]
            Map<String, int[]> cells = new HashMap<>();
            Queue<int[]> queue = new ArrayDeque<>();

            // 초기 세포 입력
            for (int i = 0; i < N; i++) {
                st = new StringTokenizer(br.readLine());
                for (int j = 0; j < M; j++) {
                    int life = Integer.parseInt(st.nextToken());
                    if (life > 0) {
                        cells.put(i + "," + j, new int[] { life, 0 });
                        queue.add(new int[] { i, j, life, 0 });
                    }
                }
            }

            // bfs 시뮬
            for (int time = 1; time <= K; time++) {
                int size = queue.size();
                Map<String, Integer> candidates = new HashMap<>();

                for (int s = 0; s < size; s++) {
                    int[] cur = queue.poll();
                    int r = cur[0], c = cur[1], life = cur[2], birth = cur[3];

                    // 활성 상태 체크
                    if (time == birth + life + 1) {
                        for (int d = 0; d < 4; d++) {
                            int nr = r + dr[d];
                            int nc = c + dc[d];
                            String key = nr + "," + nc;

                            if (!cells.containsKey(key)) {
                                candidates.merge(key, life, Math::max);
                            }
                        }
                    }

                    // 아직 죽지 않은 세포 다시 큐에 넣기
                    if (time < birth + life * 2) {
                        queue.add(cur);
                    }
                }

                // 새 세포들 추가
                for (Map.Entry<String, Integer> e : candidates.entrySet()) {
                    String[] pos = e.getKey().split(",");
                    int r = Integer.parseInt(pos[0]);
                    int c = Integer.parseInt(pos[1]);
                    int life = e.getValue();
                    cells.put(e.getKey(), new int[] { life, time });
                    queue.add(new int[] { r, c, life, time });
                }
            }

            System.out.println("#" + tc + " " + queue.size());
        }
    }
}