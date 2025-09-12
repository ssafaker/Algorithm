package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.StringTokenizer;

class Main_BOJ_11501 {
    static int n;
    static int[] arr;
    static long result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());

        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            n = Integer.parseInt(br.readLine());
            st = new StringTokenizer(br.readLine());
            arr = new int[n];
            result = 0;
            for(int i = 0  ; i < n ; i++) {
                arr[i] = Integer.parseInt(st.nextToken());
            }

            int highCost = 0;
            Deque<Integer> stock = new ArrayDeque<>();
            
            for(int i = n-1 ; i >= 0 ; i--) {
                if(arr[i] > highCost) {
                    while(!stock.isEmpty()) {
                        int cur = stock.poll();
                        result += highCost - cur;
                    }
                    highCost = arr[i];
                }
                else {
                    stock.offer(arr[i]);
                }
            }

            while(!stock.isEmpty()) {
                int cur = stock.poll();
                result += highCost - cur;
            }


            System.out.println(result);

        }

    }
}