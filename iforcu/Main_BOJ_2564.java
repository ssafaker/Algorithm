package iforcu;

import java.io.*;

public class Main_BOJ_2564 {
    static int N;
	static int w, h;
	static int d, r;
	static int sum = 0;
	static int[] num;

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String[] s = br.readLine().split(" ");
		w = Integer.parseInt(s[0]);
		h = Integer.parseInt(s[1]);
		N = Integer.parseInt(br.readLine());
		num = new int[N+1];
		for (int i = 0; i < N+1; i++) {
			s = br.readLine().split(" ");
			d = Integer.parseInt(s[0]);
			r = Integer.parseInt(s[1]);
			
			if(d == 1) num[i] = r;
			else if (d == 2) num[i] = 2*w + h - r;
			else if (d == 3) num[i] = 2 * (w + h) - r;
			else num[i] = w + r;
		}
		
		for (int i = 0; i < N; i++) {
			sum += Math.min(Math.abs(num[i] - num[N]), (2 * (w+h)) - Math.abs(num[i] - num[N]));
		}
		
		System.out.println(sum);
	}
}
