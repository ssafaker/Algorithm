package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main_BOJ_13023 {
    static int n, m;
    static ArrayList<Integer>[] arrList;
    static boolean[] visited;
    static int cnt;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        cnt = 0;
        result = 0;
        arrList = new ArrayList[n];
        for(int i = 0 ; i < n ; i++) {
            arrList[i] = new ArrayList<>();
        }
        visited = new boolean[n];

        for(int i = 0 ; i < m ; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            arrList[a].add(b);
            arrList[b].add(a);
        }

        for(int i = 0 ; i < n ; i++) {
            if(!visited[i] && result != 1) {
                visited[i] = true;
                dfs(i, 0);
                visited[i] = false;
            }
        }

        System.out.println(result);
    }

    public static void dfs(int startNode, int depth) {
        if(depth >= 4) {
            result = 1;
            return;
        }
        for(int next : arrList[startNode]) {
            if(result == 1) return;
            if(visited[next]) continue;
            visited[next] = true;
            dfs(next, depth + 1);
            visited[next] = false;
        }
    }
}