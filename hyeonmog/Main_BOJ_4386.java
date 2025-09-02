package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main_BOJ_4386 {

    static final double INF = (double) 1e9;
    
    static int n;
    static double[] distanceArr;
    static Pos[] stars;
    static ArrayList<Edge>[] edges;

    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        stars = new Pos[n];
        distanceArr = new double[n];
        Arrays.fill(distanceArr, INF);

        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            stars[i] = new Pos(x, y);
        }

        edges = new ArrayList[n];
        for(int i = 0 ; i < n ; i++) {
            edges[i] = new ArrayList<Edge>();
        }

        for(int i = 0 ; i < n ; i++) {
            for(int j = i+1 ; j < n ; j++) {
                double w = Math.sqrt(Math.pow((stars[i].x - stars[j].x), 2) + Math.pow((stars[i].y - stars[j].y), 2));
                edges[i].add(new Edge(i, j, w));
                edges[j].add(new Edge(j, i, w));
            }
        }
        prim(0);


        double result = 0.0;
        for(int i = 0  ; i < n ; i++) {
            result += distanceArr[i];
        }
        System.out.printf("%.2f", result);

    }

    public static void prim(int startV) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> Double.compare(a.w, b.w));
        distanceArr[startV] = 0;
        for(int i = 0 ; i < edges[startV].size() ; i++) {
            Edge edge = edges[startV].get(i);
            pq.offer(new Edge(edge.s, edge.e, edge.w));
        }

        int cnt = 0;
        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            if(distanceArr[edge.e] != INF) continue;
            cnt++;
            distanceArr[edge.e] = edge.w;
            for(int i = 0 ; i < edges[edge.e].size() ; i++) {
                Edge tempEdge = edges[edge.e].get(i);
                pq.offer(tempEdge);
            }
            if(cnt == n-1) break;
        }


    }
}



class Pos {
    double x;
    double y;
    public Pos(double x, double y) {
        this.x = x;
        this.y = y;
    }
}

class Edge {
    int s;
    int e;
    double w;
    public Edge(int s, int e, double w) {
        this.s = s;
        this.e = e;
        this.w = w;
    }
}