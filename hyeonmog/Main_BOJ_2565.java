package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

class Main_BOJ_2565 {
    static int n;
    static int[] increaseDp, decreaseDp;
    static Edge[] wires;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        n = Integer.parseInt(br.readLine());
        increaseDp = new int[n];
        decreaseDp = new int[n];
        wires = new Edge[n];
        result = 1;
        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            wires[i] = new Edge(a, b);
        }

        Arrays.sort(wires, (a, b) -> Integer.compare(a.s, b.s));

        for(int k = 0 ; k < n ; k++) {
            increaseDp[k] = 1;
            decreaseDp[k] = 1;
            for(int i = 0 ; i < k ; i++) {
                if(wires[k].e >= wires[i].e && wires[k].s >= wires[i].s) {
                    increaseDp[k] = Math.max(increaseDp[k], increaseDp[i]+1);
                }
                
                if(wires[k].e <= wires[i].e && wires[k].s <= wires[i].s) {
                    decreaseDp[k] = Math.max(decreaseDp[k], decreaseDp[i]+1);
                }
            }
            result = Math.max(result, increaseDp[k]);
            result = Math.max(result, decreaseDp[k]);
        }

        System.out.println(n-result);



    }
}
class Edge {
    int s;
    int e;
    public Edge(int s, int e) {
        this.s = s;
        this.e = e;
    }
}