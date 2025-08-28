package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

//프림
class Main_BOJ_9372 {
    static final int INF = (int) 1e9;

    static int V, E;
    static int[] distanceArr;
    static ArrayList<Edge>[] edgeList;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        
        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());
            result = 0;
            edgeList = new ArrayList[V+1];
            for(int i = 0 ; i < V+1 ; i++) {
                edgeList[i] = new ArrayList<Edge>();
            }
            distanceArr = new int[V+1];
            for(int i = 0 ; i < V+1 ; i++) {
                distanceArr[i] = INF;
            }
            for(int i = 0 ; i < E ; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                edgeList[s].add(new Edge(s, e, 1));
                edgeList[e].add(new Edge(e, s, 1));
            }

            prim(1);


            System.out.println(result);
        }
    }

    public static void prim(int startV) {
        Queue<Integer> pq = new ArrayDeque<>();
        pq.offer(startV);
        distanceArr[startV] = 0;

        while(!pq.isEmpty()) {
            int cur = pq.poll();
            for(Edge edge : edgeList[cur]) {
                if(distanceArr[edge.e] < distanceArr[edge.s] + edge.w) continue;
                if(distanceArr[edge.e] == INF) result++;
                distanceArr[edge.e] = distanceArr[edge.s] + edge.w;
                pq.offer(edge.e);
            }
        }

    }

}

class Edge {
    int s;
    int e;
    int w;
    public Edge(int s, int e, int w) {
        this.s = s;
        this.e = e;
        this.w = w;
    }
}