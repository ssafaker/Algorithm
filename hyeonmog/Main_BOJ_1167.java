package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class Main_BOJ_1167 {
    static int n;
    static int[] dist;
    static boolean[] visited;
    static ArrayList<Node>[] arrList;
    static int result;
    static int firstLastIdx;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        n = Integer.parseInt(br.readLine());
        dist = new int[n+1];
        visited = new boolean[n+1];
        arrList = new ArrayList[n+1];
        result = 0;
        firstLastIdx = 0;
        for(int i = 0 ; i < n+1 ; i++) {
            arrList[i] = new ArrayList<>();
        }

        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            int nodeNum = Integer.parseInt(st.nextToken());
            while (true) { 
                int a = Integer.parseInt(st.nextToken());
                if(a == -1) break;
                int b = Integer.parseInt(st.nextToken());

                arrList[nodeNum].add(new Node(a, b));
            }
        }

        //
        //1번에서 가장 먼 노드 탐색
        visited[1] = true;
        dfs(1);
        //이전 dfs에서 찾은 가장 먼 노드에서 시작해서 가장 먼 노드까지의 거리
        visited = new boolean[n+1];
        dist = new int[n+1];
        visited[firstLastIdx] = true;
        dfs(firstLastIdx);

        System.out.println(result);

    }

    public static void dfs(int startNode) {
        for(Node node : arrList[startNode]) {
            if(dist[node.e] == 0 && !visited[node.e]) {
                dist[node.e] = dist[startNode] + node.w;
                if(result < dist[node.e]) {
                    result = dist[node.e];
                    firstLastIdx = node.e;
                }
                dfs(node.e);
            }
        }
    }
}

class Node {
    int e;
    int w;
    public Node(int e, int w) {
        this.e = e;
        this.w = w;
    }
}