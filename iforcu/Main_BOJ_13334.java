package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [철로]
 *  링크 : [https://www.acmicpc.net/problem/13334]
 *  난이도 : [골드 2]
 *  설명: 
 *   - N명의 사람이 집과 사무실 사이를 통근하며, 길이 D인 철로로 최대 몇 명을 커버할 수 있는지 구하는 문제.
 *   - 각 사람의 집-사무실 구간이 철로에 완전히 포함되어야 해당 사람이 철로를 이용 가능.
 *   - 철로의 위치를 최적으로 정해서 최대 인원수를 구해야 함.
 *  풀이:
 *   - 스위핑 + 우선순위 큐를 사용한 그리디 알고리즘.
 *   - 구간 길이가 D 이하인 것만 유효하므로 필터링.
 *   - 끝점 기준으로 정렬하고, 각 끝점을 철로 오른쪽 끝으로 가정하여 최대 개수 계산.
 */

public class Main_BOJ_13334 {
    static int N, D;

    // 집-사무실 구간을 나타내는 클래스
    static class Pair {
        int start, end;
        Pair(int a, int b) {
            this.start = Math.min(a, b); // 더 작은 값이 시작점
            this.end = Math.max(a, b);   // 더 큰 값이 끝점
        }
    }
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        List<Pair> list = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            list.add(new Pair(a, b));
        }

        D = Integer.parseInt(br.readLine());

        // 구간 길이가 D 이하인 것만 유효
        List<Pair> valid = new ArrayList<>();
        for (Pair p : list) {
            if(p.end - p.start <= D) valid.add(p);
        }

        // 끝점 기준으로 정렬
        valid.sort(Comparator.comparingInt(p -> p.end));

        PriorityQueue<Integer> pq = new PriorityQueue<>(); // 시작점들을 저장
        int ans = 0;

        // 스위핑: 각 끝점을 철로의 오른쪽 끝으로 가정
        for (Pair p : valid) {
            int curEnd = p.end;
            int limit = curEnd - D; // 철로의 왼쪽 끝

            pq.add(p.start);
            // 철로 범위를 벗어나는 시작점들 제거
            while (!pq.isEmpty() && pq.peek() < limit) pq.poll();
            ans = Math.max(ans, pq.size());
        }

        System.out.println(ans);
    }
}
