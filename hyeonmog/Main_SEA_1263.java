package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main_SEA_1263 {
    static int n;
    static ArrayList<Integer>[] arrList;
    static boolean[] visited;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            arrList = new ArrayList[n];
            result = (int) 1e9;
            for(int i = 0 ; i < n ; i++) {
                arrList[i] = new ArrayList<>();
            }
            for(int i = 0 ; i < n ; i++) {
                for(int j = 0 ; j < n ; j++) {
                    int a = Integer.parseInt(st.nextToken());
                    if(a == 1) {
                        arrList[i].add(j);
                    }
                }
            }

            for(int i = 0 ; i < n ; i++) {
                visited = new boolean[n];
                bfs(i);
            }

            System.out.println("#" + testCase + " " + result);
        }

    }

    public static void bfs(int startIdx) {
        ArrayDeque<Info> q = new ArrayDeque<>();
        q.offer(new Info(startIdx, 0));
        visited[startIdx] = true;
        int sum = 0;
        while(!q.isEmpty()) {
            Info info = q.poll();
            sum += info.dist;

            for(int node : arrList[info.idx]) {
                if(visited[node]) continue;
                visited[node] = true;
                q.offer(new Info(node, info.dist+1));
            }
        }
        result = Math.min(result, sum);

    }
}

class Info {
    int idx;
    int dist;
    public Info(int idx, int dist) {
        this.idx = idx;
        this.dist = dist;
    }
}