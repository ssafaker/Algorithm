
import java.io.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

public class Main_BOJ_11003 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        StringBuilder sb = new StringBuilder();
        int N = Integer.parseInt(st.nextToken());
        int L = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(br.readLine());
        Deque<Node> deque = new ArrayDeque<>();
        for (int i = 0; i < N; i++) {
            Node curNode = new Node(i, Integer.parseInt(st.nextToken()));
            // 이전 모든 노드가 현재 노드보다 크다면 모두 제거
            while (!deque.isEmpty() && deque.getLast().value > curNode.value) {
                deque.removeLast();
            }
            // 현재 노드 추가
            deque.addLast(curNode);
            // 첫번째 노드가 범위 안인지 확인
            if (deque.getFirst().index <= i - L) {
                deque.removeFirst();
            }
            // 첫번째 노드 출력
            sb.append(deque.getFirst().value).append(" ");
        }
        System.out.print(sb);
    }

    static class Node {
        int value, index;

        Node(int index, int value) {
            this.index = index;
            this.value = value;
        }
    }
}