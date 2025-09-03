package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [전깃줄 - 2]
 *  링크 : [https://www.acmicpc.net/problem/2568]
 *  난이도 : [플래티넘 5]
 *  설명: 
 *   - 전깃줄이 서로 교차하지 않게 하기 위해 제거해야 할 전깃줄의 최소 개수와 번호.
 *   - A 전봇대와 B 전봇대 사이에 N개의 전깃줄이 연결되어 있음.
 *   - 전깃줄이 교차하지 않으려면 A 기준 정렬했을 때 B도 증가 순서여야 함.
 *  풀이:
 *   - A 기준으로 정렬 후 B에서 LIS(최장 증가 부분 수열) 찾기.
 *   - 이진 탐색을 이용한 O(N log N) LIS 알고리즘 사용.
 *   - LIS에 포함되지 않는 전깃줄들을 역추적하여 제거 대상 찾기.
 */

public class Main_BOJ_2568 {
    static int N;
    static List<int[]> wires;
    static int[] index; // 각 전깃줄이 LIS에서 차지하는 위치
    
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());

        wires = new ArrayList<>();

        for (int i = 0; i < N; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            wires.add(new int[] {a, b});
        }

        // A 전봇대 기준으로 정렬
        wires.sort((a,b) -> a[0] - b[0]);
        
        // B 전봇대에서 LIS 찾기
        List<Integer> dp =  new ArrayList<>();
        
        index = new int[N];
        for (int i = 0; i < N; i++) {
            int value = wires.get(i)[1];
            int id = Collections.binarySearch(dp, value);

            if(id < 0) id = -(id + 1);

            if(id == dp.size()) dp.add(value);
            else dp.set(id, value);

            index[i] = id; // 현재 전깃줄이 LIS에서 차지하는 위치 저장
        }
        StringBuilder sb = new StringBuilder();
        int size = dp.size(); // LIS의 길이
        int curIndex = size - 1;
        sb.append(N-size).append("\n"); // 제거할 전깃줄의 개수
        
        // LIS에 포함되지 않는 전깃줄들을 역추적하여 찾기
        Stack<Integer> st = new Stack<>();
        for (int i = N - 1; i >= 0; i--) {
            if(index[i] == curIndex) curIndex--; // LIS에 포함되는 전깃줄
            else st.push(wires.get(i)[0]); // 제거할 전깃줄의 A 번호
        }

        // 제거할 전깃줄들을 오름차순으로 출력
        while (!st.isEmpty()) {
            sb.append(st.pop()).append("\n");
        }

        System.out.println(sb.toString());
    }
}
