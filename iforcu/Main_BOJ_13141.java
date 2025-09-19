package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명 : [Ignition]
 *  링크   : [https://www.acmicpc.net/problem/13141]
 *  난이도 : [플래티넘5]
 *  설명   :
 *    - 그래프의 한 정점에서 불을 붙여 모든 간선을 최소 시간 내에 태우는 문제
 *    - 각 간선은 1초당 1만큼의 속도로 불이 번짐
 *    - 간선의 양 끝에서 동시에 불이 붙으면 중앙에서 만남
 *    - 모든 정점은 연결되어 있음, 간선 수 최대 20,000
 *  풀이   :
 *    - 플로이드-워셜로 모든 정점 간 최단거리 계산
 *    - 각 정점에서 불을 붙였을 때 모든 간선이 타는 최대 시간 계산
 *    - 모든 정점에 대해 최소 최대 시간을 구해 정답 출력
 */

public class Main_BOJ_13141 {
    static final int INF = 100_000_000;
    static int N, M;
    static double[][] dist;
    static ArrayList<int[]> edgs = new ArrayList<>();

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        // 정점, 간선 수 입력 및 거리 배열 초기화
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        dist = new double[N + 1][N + 1];

        for (int i = 1; i <= N; i++) {
            Arrays.fill(dist[i], INF);
            dist[i][i] = 0;
        }

        // 간선 정보 입력 및 거리 갱신
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int l = Integer.parseInt(st.nextToken());

            edgs.add(new int[]{s, e, l});

            dist[s][e] = Math.min(dist[s][e], l);
            dist[e][s] = Math.min(dist[e][s], l);
        }

        // 플로이드-워셜로 모든 정점 간 최단거리 계산
        for (int k = 1; k <= N; k++) {
            for (int i = 1; i <= N; i++) {
                for (int j = 1; j <= N; j++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                    }
                }
            }
        }

        double minT = Double.MAX_VALUE;
        // 각 정점에서 불을 붙였을 때 모든 간선이 타는 최대 시간 계산
        for (int sNod = 1; sNod <= N; sNod++) {
            double maxT = 0;

            for (int[] edg : edgs) {
                int u = edg[0];
                int v = edg[1];
                int l = edg[2];

                // 간선의 양 끝에서 불이 붙을 때 중앙에서 만남
                double t = (dist[sNod][u] + dist[sNod][v] + l) / 2.0;
                if (t > maxT) maxT = t;
            }

            if (maxT < minT) minT = maxT;
        }

        // 결과 출력
        System.out.printf("%.1f\n", minT);
    }
}
