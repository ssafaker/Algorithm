package iforcu;

import java.io.*;
import java.util.*;

public class Main_BOJ_17471 {
	static int N;
	static int[] people;
	static List<Integer>[] graph;
	static int minDiff = Integer.MAX_VALUE;
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		N = Integer.parseInt(br.readLine());
		people = new int[N];
		graph = new ArrayList[N];
		
		String[] input = br.readLine().split(" ");
		for (int i = 0; i < N; i++) {
			people[i] = Integer.parseInt(input[i]);
			graph[i] = new ArrayList<>();
		}
		
		for (int i = 0; i < N; i++) {
            input = br.readLine().split(" ");
            int size = Integer.parseInt(input[0]); 
            for (int j = 1; j <= size; j++) {
                int neighbor = Integer.parseInt(input[j]) - 1; 
                graph[i].add(neighbor);
            }
        }
		
		for (int i = 0; i < (1 << N) / 2; i++) {
			List<Integer> groupA = new ArrayList<>();
			List<Integer> groupB = new ArrayList<>();
			
			for (int j = 0; j < N; j++) {
				if((i & (1 << j)) != 0) groupA.add(j);
				else groupB.add(j);
			}
			
			if (isConnected(groupA) && isConnected(groupB)) {
				int sumA = getPopulation(groupA);
				int sumB = getPopulation(groupB);
				minDiff = Math.min(minDiff, Math.abs(sumA - sumB));
			}
		}
		
		System.out.println(minDiff == Integer.MAX_VALUE ? -1 : minDiff);
	}

	static int getPopulation(List<Integer> group) {
		int sum = 0;
		for (int idx : group) {
			sum += people[idx];
		}
		return sum;
	}

	static boolean isConnected(List<Integer> group) {
		if (group.isEmpty()) return false;
		
		boolean[] visited = new boolean[N];
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(group.get(0));
		visited[group.get(0)] = true;
		
		int count = 1;
		while(!queue.isEmpty()) {
			int cur = queue.poll();
			for (int next : graph[cur]) {
				if(!visited[next] && group.contains(next)) {
					visited[next] = true;
					queue.offer(next);
					count++;
				}
			}
		}
		
		return count == group.size();
	}
}