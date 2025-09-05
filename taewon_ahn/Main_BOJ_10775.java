import java.io.*;
import java.util.*;

public class Main_BOJ_10775 {
	static int[] parent;

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int G = Integer.parseInt(br.readLine());
		int P = Integer.parseInt(br.readLine());

		parent= new int[G + 1];
		for (int i = 1; i <= G; i++) {
			parent[i] = i;
		}
		
		int cnt = 0;
		for (int i = 0; i < P; i++) {
			int plane = Integer.parseInt(br.readLine());
			int findVal = find(plane);
			if (findVal == 0) {
				System.out.println(cnt);
				return;
			} else {
				union(findVal, findVal -1);
				cnt++;
			}
		}
		System.out.println(cnt);
	}

	private static void union(int a, int b) {
		parent[find(a)] = find(b);
	}

	private static int find(int a) {
		if (a == parent[a]) return a;
		return parent[a] = find(parent[a]);
	}

}
