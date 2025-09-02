package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main_BOJ_6497 {
    static int V, E;
    static int[] p;
    static int[] r;
    static int totalPrice;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        while (true) { 
            st = new StringTokenizer(br.readLine());
            V = Integer.parseInt(st.nextToken());
            E = Integer.parseInt(st.nextToken());
            if(V == 0 && E == 0) break;
            PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.w - b.w);
            
            p = new int[V];
            r = new int[V];
            totalPrice = 0;
            result = 0;
            
            for(int i = 0 ; i < V ; i++) {
                p[i] = i;
                r[i] = 1;
            }
            
            for(int i = 0 ; i < E ; i++) {
                st = new StringTokenizer(br.readLine());
                int s = Integer.parseInt(st.nextToken());
                int e = Integer.parseInt(st.nextToken());
                int w = Integer.parseInt(st.nextToken());
                totalPrice += w;
                
                pq.offer(new Edge(s, e, w));
            }
            
            while(!pq.isEmpty()) {
                Edge edge = pq.poll();
                if(union(edge.s, edge.e)) {
                    result += edge.w;
                }
            }
            
            System.out.println(totalPrice - result);
            

        }
    }
        
        
        public static boolean union(int x, int y) {
            x = find(x);
            y = find(y);

        //이미 연결된
        if(x==y) return false;

        if(r[x] < r[y]) {
            r[y] += r[x];
            p[x] = p[y];
        } else {
            r[x] += r[y];
            p[y] = p[x];
        }
        return true;

    } 

    public static int find(int x) {
        if(p[x] == x) {
            return x;
        } else {
            return p[x] = find(p[x]);
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