package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [배열 정렬]
 *  링크 : [https://www.acmicpc.net/problem/28707]
 *  난이도 : [골드 1]
 *  설명:
 *   - 초기 배열이 주어지고, 두 원소를 교환할 수 있는 연산과 그 비용이 주어짐.
 *   - 연산을 반복해서 배열을 오름차순 정렬하는 데 필요한 최소 비용을 구하는 문제.
 *  풀이:
 *   - 상태를 배열로 두고, 각 상태를 문자열로 변환해 방문/비용을 관리.
 *   - 우선순위 큐(Dijkstra/BFS 변형)를 사용하여 최소 비용 탐색.
 *   - 배열을 교환하며 새로운 상태로 이동, 이미 더 작은 비용으로 방문한 상태는 스킵.
 *   - 목표 배열과 일치할 때 탐색 종료 후 비용 반환.
 */

public class Main_BOJ_28707 {
    static int N, M, ans;
    static int[] arr, targetArr;
    static String targetStr;
    static int[][] operate;
    static HashMap<String, Integer> cost;
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        arr = new int[N];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for (int i = 0; i < N; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        // 목표 상태: 정렬된 배열
        targetArr = Arrays.copyOf(arr, N);
        Arrays.sort(targetArr);
        targetStr = Arrays.toString(targetArr);
        
        // 연산 입력
        M = Integer.parseInt(br.readLine());
        operate = new int[M][3];
        cost = new HashMap<>();
        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            operate[i][0] = Integer.parseInt(st.nextToken()) - 1;
            operate[i][1] = Integer.parseInt(st.nextToken()) - 1;
            operate[i][2] = Integer.parseInt(st.nextToken());
        }

        ans = -1;
        bfs(arr);
        System.out.println(ans);
    }

    // 다익스트라 변형 (상태 공간 탐색)
    static void bfs(int[] arr) {
        PriorityQueue<Object[]> pq = new PriorityQueue<>(Comparator.comparingInt(o -> (int) o[0]));
        pq.add(new Object[] {0, arr});
        cost.put(Arrays.toString(arr), 0);

        while (!pq.isEmpty()) {
            Object[] curObj = pq.poll();
            int curCost = (int) curObj[0];
            int[] curArr = (int[]) curObj[1];
            String curStr = Arrays.toString(curArr);

            // 이미 더 작은 비용으로 방문한 경우 스킵
            if(curCost > cost.getOrDefault(curStr, Integer.MAX_VALUE)) continue;

            // 목표 배열 도달
            if(curStr.equals(targetStr)) {
                ans = curCost;
                return;
            }

            // 가능한 모든 교환 연산 수행
            for (int[] op : operate) {
                int l = op[0];
                int r = op[1];
                int c = op[2];

                int[] nextArr =  Arrays.copyOf(curArr, N);
                int temp = nextArr[l];
                nextArr[l] = nextArr[r];
                nextArr[r] = temp;

                String nextStr = Arrays.toString(nextArr);
                int newCost = curCost + c;

                if(newCost < cost.getOrDefault(nextStr, Integer.MAX_VALUE)) {
                    cost.put(nextStr, newCost);
                    pq.offer(new Object[] {newCost, nextArr});
                }
            }
        }
    }
}
