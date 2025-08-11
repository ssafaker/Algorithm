package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main_BOJ_10974 {
    static int n;
    static boolean[] visited;
    static int[] arr;
    
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        visited = new boolean[n];
        arr = new int[n];
        perm(0);
    }

    static public void perm(int depth) {
        if(depth == n) {
            for(int i = 0 ; i < n ; i++) {
                System.out.print(arr[i] + " ");
            }
            System.out.println();
            return;
        }
        for(int i = 0 ; i < n ; i++) {
            if(!visited[i]) {
                visited[i] = true;
                arr[depth] = i+1;
                perm(depth+1);
                visited[i] = false;
            }
        }

    }
}