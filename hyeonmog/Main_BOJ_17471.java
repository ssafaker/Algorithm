package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Queue;
import java.util.StringTokenizer;

class Main_BOJ_17471 {
    static int n;
    static ArrayList<Integer>[] arr;
    static int[] population;
    static boolean visited[];
    static boolean subVisited[];
    static int result;
    static int sum;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());

        arr = new ArrayList[n+1];
        population = new int[n+1];
        visited = new boolean[n+1];
        subVisited = new boolean[n+1];
        result = 10000;
        sum = 0;

        st = new StringTokenizer(br.readLine());
        for(int i = 1 ; i < n+1 ; i++) {
            population[i] = Integer.parseInt(st.nextToken());
            sum += population[i];
        }

        for(int i = 0 ; i < n+1 ; i++) arr[i] = new ArrayList<>();

        for(int rep = 0 ; rep < n ; rep++) {
            st = new StringTokenizer(br.readLine());
            int m = Integer.parseInt(st.nextToken());
            for(int i = 0 ; i < m ; i++) {
                arr[rep+1].add(Integer.parseInt(st.nextToken()));
            }
        }

        // for(int i = 0 ; i < n+1 ; i++) {
        //     System.out.println("인접리스트" + arr[i].toString());
        // }

        //subset
        for(int cnt = 1 ; cnt < n+1 ; cnt++) {
            Arrays.fill(subVisited, false);
            subset(1, 0, cnt, new ArrayList<>());
        }


        System.out.println(result == 10000 ? -1 : result);

    }
    public static void subset(int start, int depth, int cnt, ArrayList<Integer> firstGroup) {
        if(depth == cnt) {
            bfs(firstGroup);
            return;
        }

        for(int i = start ; i < n+1 ; i++) {

            if(!subVisited[i]) {
                firstGroup.add(i);
                subVisited[i] = true;
                subset(start+1, depth+1, cnt, firstGroup);
                subVisited[i] = false;
                firstGroup.remove(firstGroup.size()-1);
            }
        }
    }

    public static void bfs(ArrayList<Integer> firstGroup) {
        visited = new boolean[n+1];
        Queue<Integer> q = new ArrayDeque<>();
        q.offer(firstGroup.get(0));
        int firstGroupSum = population[firstGroup.get(0)];
        visited[firstGroup.get(0)] = true;
        while(!q.isEmpty()) {
            int cur = q.poll();
            for(int i = 0 ; i < arr[cur].size() ; i++) {
                int nextV = arr[cur].get(i);
                if(visited[nextV]) continue;
                if(!firstGroup.contains(nextV)) continue;
                q.offer(nextV);
                visited[nextV] = true;
                firstGroupSum += population[nextV];
            }
        }
        //첫 번째 그룹에 속했는데 방문 못하면 종료
        for(int i = 1 ; i < n+1 ; i++) {
            if(!visited[i]) {
                if(firstGroup.contains(i)) {
                    return;
                }
            }
        }
        int secondGourpCnt = 0;
        //다른 그룹 탐색 시작
        for(int idx = 1 ; idx < n+1 ; idx++) {
            //탐색 시작
            if(!visited[idx]) {
                //다른 그룹이 2개 이상으로 나뉘면 종료
                if(++secondGourpCnt == 2) return;
                q.clear();
                q.offer(idx);
                visited[idx] = true;

                while(!q.isEmpty()) {
                    int cur = q.poll();
                    for(int i = 0 ; i < arr[cur].size() ; i++) {
                        int nextV = arr[cur].get(i);
                        if(visited[nextV]) continue;
                        q.offer(nextV);
                        visited[nextV] = true;
                    }
                }

            }
        }
        result = Math.min(result, Math.abs((sum - firstGroupSum) - firstGroupSum));



    }
}