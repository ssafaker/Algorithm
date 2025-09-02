package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main_BOJ_1967 {
    static int n;
    static ArrayList<Edge>[] edges;
    static boolean[] visited;
    static int sum;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        visited = new boolean[n+1];
        edges = new ArrayList[n+1];
        sum = 0;
        result = 0;
        for(int i = 0 ; i < n+1 ; i++) {
            edges[i] = new ArrayList<>();
        }
        
        for(int i = 0 ; i < n-1 ; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            edges[s].add(new Edge(e, w));
            edges[e].add(new Edge(s, w));
        }

        for(int i = 1 ; i < n+1 ; i++) {
            visited = new boolean[n+1];
            sum = 0;
            visited[i] = true;
            dfs(i);
        }
        System.out.println(result);

    }
    public static void dfs(int startV) {
        if(sum > result) {
            result = sum;
        }
        for(int i = 0 ; i < edges[startV].size() ; i++) {
            Edge edge = edges[startV].get(i);
            if(visited[edge.e]) continue;
            sum += edge.w;
            visited[edge.e] = true;
            dfs(edge.e);
            visited[edge.e] = false;
            sum -= edge.w;
        }
    }
}
class Edge {
    int e;
    int w;
    public Edge(int e, int w) {
        this.e = e;
        this.w = w;
    }
}
