package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_BOJ_2491 {
    static int n;
    static int[] arr;
    static int[] increaseDp;
    static int[] decreaseDp;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n];
        increaseDp = new int[n];
        decreaseDp = new int[n];
        result = 1;
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 0 ; i < n ; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }

        increaseDp[0] = 1;
        for(int k = 1 ; k < n ; k++) {
            increaseDp[k] = 1;
            if(arr[k-1] <= arr[k]) {
                increaseDp[k] += increaseDp[k-1];
            }
            result = Math.max(result, increaseDp[k]);
        }


        decreaseDp[0] = 1;
        for(int k = 1 ; k < n ; k++) {
            decreaseDp[k] = 1;
            if(arr[k-1] >= arr[k]) {
                decreaseDp[k] += decreaseDp[k-1];
            }
            result = Math.max(result, decreaseDp[k]);
        }
        System.out.println(result);


    }
}