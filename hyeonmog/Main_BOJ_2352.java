package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_BOJ_2352 {
    static int n;
    static int[] arr;
    static int[] increaseDp, decreaseDp;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        arr = new int[n+1];
        increaseDp = new int[n+1];
        decreaseDp = new int[n+1];
        StringTokenizer st = new StringTokenizer(br.readLine());
        for(int i = 1 ; i < n+1 ; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        result = 1;

        for(int k = 1 ; k < n+1 ; k++) {
            increaseDp[k] = 1;
            decreaseDp[k] = 1;
            for(int i = 1 ; i < k ; i++) {
                if(arr[k] < arr[i] && k < i) {
                    increaseDp[k] = Math.max(increaseDp[k], increaseDp[i]+1);
                }
                if(arr[k] > arr[i] && k > i) {
                    decreaseDp[k] = Math.max(decreaseDp[k], decreaseDp[i]+1);
                }
            }
            result = Math.max(result, increaseDp[k]);
            result = Math.max(result, decreaseDp[k]);
        }

        System.out.println(result);

    }
}