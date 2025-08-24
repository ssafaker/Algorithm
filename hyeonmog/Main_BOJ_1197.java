package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

//강사님 풀이
public class Main_BOJ_1197 {
    static int result;
    static int V;
    static int E;
    static long min;
    static int[] r;
    static int[] p;
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        V = Integer.parseInt(st.nextToken());
        E = Integer.parseInt(st.nextToken());
        PriorityQueue<Edge> points = new PriorityQueue<>((a, b) -> a.w - b.w);

        for(int i = 0 ; i < E ; i++) {
            st = new StringTokenizer(br.readLine());
            int s = Integer.parseInt(st.nextToken());
            int e = Integer.parseInt(st.nextToken());
            int w = Integer.parseInt(st.nextToken());
            points.offer(new Edge(s, e, w));
        }

        //makeset
        p = new int[V+1];
        r = new int[V+1];
        for(int i = 1 ; i < V+1 ; i++) {
            p[i] = i;
            r[i] = 1;
        }
        min = 0;
        int cnt = 0;
        while(cnt != V-1) {
            Edge edge = points.poll();
            if(union(edge.s, edge.e)) {
                cnt++;
                min += edge.w;
            }
        }

        System.out.println(min);
    }

    static public boolean union(int x, int y) {
        x = find(x);
        y = find(y);
        
        if(x == y) return false;    //이미 연결된 케이스
        if(r[x] < r[y]) {
            r[y] += r[x];
            p[x] = p[y];
        } else {
            r[x] += r[y];
            p[y] = p[x];
        }
        return true;
    }
    static public int find(int x) {
        if(p[x] == x) {
            return p[x];
        } else {
            return p[x]=find(p[x]);
        }
    }
    
}
class Edge{
    int s;
    int e;
    int w;
    public Edge(int s, int e, int w) {
        this.s = s;
        this.e = e;
        this.w = w;
    }

    // @Override
    // public int compareTo(Edge o) {
    //     return Integer.compare(this.w, o.w);
    // }
}