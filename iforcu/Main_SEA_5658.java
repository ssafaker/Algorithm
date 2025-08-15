package iforcu;

import java.util.*;
import java.io.*;

/*
 *  문제명: [보물상자 비밀번호]
 *  링크 : [https://swexpertacademy.com/main/code/problem/problemDetail.do?contestProbId=AWXRUN9KfZ8DFAUo]
 *  난이도 : [모의 SW 역량테스트]
 *  설명:
 *  - 문자열을 한칸씩 이동 하고 각각을 4개로 나눔
 *  - 나눈 값을 list 저장하고 K번째로 큰 값을 찾는 문제
 *  - 값은 중복이 되면 안됨.
 *  풀이:
 *  - 문자열을 Queue에 문자로 넣음
 *  - Queue 값을 4개의 문자열로 나누고 각각의 값을 10진수로하여 list 저장
 *  - Queue를 이용해 한칸 이동
 *  - list를 정렬 후 K번째 큰 값을 출력
 */

public class Main_SEA_5658 {
    static int N, K, R;
    static Queue<Character> pw;
    static List<Integer> list; 

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine().trim());

        for (int test_case = 1; test_case <= T; test_case++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            R = N/4;
            pw = new LinkedList<>();
            list = new ArrayList<>();
            String s = br.readLine();
            char[] c = s.toCharArray();
            for (int i = 0; i < N; i++) {
                pw.offer(c[i]);
            }
            
            // A 로직 반복 N/4 - 1
            for (int i = 0; i < R; i++) {
                // 1. queue의 값을 N/4 의 값만큼 각각을 나누어 16진수 -> 10진수 변경후 List에 저장
                roundPW(pw);

                // 2. queue의 값을 한칸 이동
                pw.offer(pw.poll());
            }
            // B list 정렬 후 k번 째 큰 값
            list.sort((a,b) -> Integer.compare(b, a));

            System.out.println("#"+test_case+" "+list.get(K-1));
        }
    }

    static void roundPW(Queue<Character> PW) {
        String[] s = new String[4];
        for (int i = 0; i < 4; i++) s[i] = "";

        List<Character> tempList = new ArrayList<>(PW);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < R; j++) {
                s[i] += tempList.get(i * R + j);
            }
        }
        
        for (int i = 0; i < 4; i++) {
            int decimal = Integer.parseInt(s[i], 16);
            // 중복 방지
            if (!list.contains(decimal)) {
                list.add(decimal);
            }
        }
    }
}
