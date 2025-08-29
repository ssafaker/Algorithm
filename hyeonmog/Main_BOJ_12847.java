package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

class Main_BOJ_12847 {
    static int n, m;
    static int[] arr;
    static long result;
    public static void main(String[] srags) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        arr = new int[n];
        result = 0;
        st = new StringTokenizer(br.readLine());
        for(int i = 0 ; i < n ; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
        }
        
        long sum = 0;
        for(int i = 0 ; i < m ; i++) {
            sum += arr[i];
        }
        result = sum;
        int previousIdx = 0;

        for(int i = m ; i < n ; i++) {
            sum -= arr[previousIdx++];
            sum += arr[i];
            result = Math.max(result, sum);
        }
        System.out.println(result);
    }
}