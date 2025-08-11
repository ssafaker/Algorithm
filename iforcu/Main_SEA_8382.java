package iforcu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
 
public class Main_SEA_8382
{
    public static void main(String args[]) throws Exception
    {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());
 
        for(int test_case = 1; test_case <= T; test_case++)
        { 
            String[] s = br.readLine().split(" ");
            int ans = 0;
            int x1 = Integer.parseInt(s[0]);
            int y1 = Integer.parseInt(s[1]);
            int x2 = Integer.parseInt(s[2]);
            int y2 = Integer.parseInt(s[3]);
             
            int a = Math.abs(x1 - x2);
            int b = Math.abs(y1 - y2);
            int temp = Math.abs(a-b);
            int c = temp / 2;
            int d = temp % 2;
             
            ans = (Math.min(a, b) * 2) + (4 * c) + d;
             
            System.out.printf("#%d %d\n",test_case, ans);
        }
    }
}