package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

//창용 마을 무리의 개수
class Main_SEA_7465 {
    static ArrayList<Integer>[] arrList;
    static boolean[] visited;
    static int n;
    static int m;
    static int result;
    static public void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            arrList = new ArrayList[n+1];
            visited = new boolean[n+1];
            result = 0;
            for(int i = 0 ; i < n+1 ; i++) {
                arrList[i] = new ArrayList<>();
            }
            for(int i = 0 ; i < m ; i++) {
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                arrList[a].add(b);
                arrList[b].add(a);
            }

            for(int i = 1 ; i < n+1 ; i++) {
                if(!visited[i]) {
                    bfs(i);
                    result++;
                }
            }

            System.out.println("#" + testCase + " " + result);

        }

    } 

    static public void bfs(int idx) {
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        dq.offer(idx);
        visited[idx] = true;
        while(!dq.isEmpty()) {
            idx = dq.poll();
            
            for(int i : arrList[idx]) {
                if(!visited[i]) {
                    dq.offer(i);
                    visited[i] = true;
                }
            }   
        }
    }

}