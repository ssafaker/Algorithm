import java.util.*;

/*
 *  문제명 : 더 맵게
 *  링크   : https://school.programmers.co.kr/learn/courses/30/lessons/42626
 *  난이도 : Level 2
 *  설명   :
 *    - 모든 음식의 스코빌 지수를 K 이상으로 만들기 위해 가장 맵지 않은 두 음식 섞기 반복
 *    - 섞은 음식의 스코빌 지수 = 가장 맵지 않은 음식 + (두 번째로 맵지 않은 음식 * 2)
 *    - 모든 음식이 K 이상이 되지 못하면 -1 반환
 *  풀이   :
 *    - 우선순위 큐(최소 힙)로 가장 맵지 않은 두 음식 선택
 *    - 섞기 연산 반복, 섞은 횟수 카운트
 *    - 음식이 하나 남았는데 K 미만이면 -1 반환
 */

public class Main_PSG_더맵게 {
    public int solution(int[] scoville, int K) {
        // 모든 음식의 스코빌 지수를 저장할 최소 힙
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        for(int hot : scoville) pq.add(hot);
        int count = 0;
        // 가장 맵지 않은 음식이 K 미만일 때 반복
        while(pq.peek() < K) {
            int a = pq.poll();
            // 음식이 하나 남았는데 K 미만이면 -1 반환
            if(pq.isEmpty()) return -1;
            int b = pq.poll();
            // 두 음식 섞어서 새로운 음식 추가
            pq.add(a + (b * 2));
            count++;
        }
        // 섞은 횟수 반환
        return count;
    }
}
