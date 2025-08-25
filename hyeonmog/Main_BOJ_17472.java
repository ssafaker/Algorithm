package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

//1시간 30분
//더러움 주의
//프림 사용
// 섬 번호 부여해주기(BFS) -> 섬들 간의 가중치(다리 길이) 구해주고 인접 리스트 생성 -> 프림 알고리즘 사용
class Main_BOJ_17472 {
    static final int[] dx = {-1, 0, 1, 0};
    static final int[] dy = {0, 1, 0, -1}; 
    static final int INF = (int) 1e9;
    
    static ArrayList<Edge>[] distList;
    static int n, m;
    static int[][] arr;
    static int islandCnt;
    static int[] distanceResults;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        
        arr = new int[n][m];
        islandCnt = 0;

        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < m ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
            }
        }


        registIsland();

        //간선 인접리스트 초기화
        distList = new ArrayList[islandCnt+1];
        for(int i = 0 ; i < islandCnt+1 ; i++) {
            distList[i] = new ArrayList<Edge>();
        }
        distanceResults = new int[islandCnt+1];
        Arrays.fill(distanceResults, INF);


        makeEdges();

        prim(1);

        // for(int i = 0 ; i < n ; i++) {
        //     System.out.println(Arrays.toString(arr[i]));
        // }
        // for(int i = 0 ; i < islandCnt+1 ; i++) {
        //     System.out.println();
        //     for(int j = 0 ; j < distList[i].size() ; j++) {
        //         System.out.print(distList[i].get(j).e +  " " + distList[i].get(j).w + "||");
        //     }
        // }
        // System.out.println(Arrays.toString(distanceResults));
        int result = 0;
        for(int i = 1 ; i < islandCnt+1 ; i++) {
            if (distanceResults[i] == INF) {
                result = -1;
                break;
            }
            result += distanceResults[i];
        }
        System.out.println(result);

    }

    //섬 번호 부여하기
    public static void registIsland() {
        boolean[][] visited = new boolean[n][m];
        int islandNum = 0;
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(arr[i][j] == 1 && !visited[i][j]) {
                    islandNum++;
                    islandCnt = islandNum;
                    //탐색 시작
                    ArrayDeque<int[]> q = new ArrayDeque<>();
                    q.offer(new int[]{i, j});
                    visited[i][j] = true;
                    arr[i][j] = islandNum;

                    while(!q.isEmpty()) {
                        int[] cur = q.poll();
                        int x = cur[0];
                        int y = cur[1];

                        for(int dir = 0 ; dir < 4 ; dir++) {
                            int nx = x + dx[dir];
                            int ny = y + dy[dir];
                            if(nx < 0 || ny < 0 || nx >= n || ny >= m) continue;
                            if(visited[nx][ny]) continue;
                            if(arr[nx][ny] == 0) continue;

                            visited[nx][ny] = true;
                            arr[nx][ny] = islandNum;
                            q.offer(new int[]{nx, ny});
                        }
                    }


                }
            }
        }
    }


    //섬끼리 연결하는 최소 간선 구하기
    public static void makeEdges() {
        for(int i = 0 ; i < n ; i++) {
            for(int j = 0 ; j < m ; j++) {
                if(arr[i][j] != 0) {
                    int startIslandNum = arr[i][j];
                    int endIslandNum = 0;
                    for(int dir = 0 ; dir < 4 ; dir++) {
                        int distance = 0;
                        while (true) { 
                            distance++;
                            int nx = i + dx[dir]*distance;
                            int ny = j + dy[dir]*distance;
                            if(nx < 0 || ny < 0 || nx >= n || ny >= m) break;
                            if(arr[nx][ny] == startIslandNum) break;
                            else if(arr[nx][ny] != 0) {
                                distance--;
                                if(distance < 2) break;
                                endIslandNum = arr[nx][ny];
                                boolean flag = false;
                                for(int a = 0 ; a < distList[startIslandNum].size() ; a++) {
                                    if(distList[startIslandNum].get(a).e == endIslandNum) {
                                        distList[startIslandNum].set(a, new Edge(startIslandNum, endIslandNum, Math.min(distList[startIslandNum].get(a).w, distance)));
                                        flag = true;
                                    }
                                }
                                if (!flag) {
                                    distList[startIslandNum].add(new Edge(startIslandNum, endIslandNum, distance));
                                }
                                break;
                            }
                            

                        }
                    }
                }
            }
        }
    }

    //프림 또는 크루스칼 사용하면 됌
    public static void prim(int startEdge) {
        PriorityQueue<Edge> pq = new PriorityQueue<>((a, b) -> a.w - b.w);
        distanceResults[startEdge] = 0;

        for(int i = 0 ; i < distList[startEdge].size() ; i++) {
            pq.offer(new Edge(startEdge, distList[startEdge].get(i).e, distList[startEdge].get(i).w));
        }

        while(!pq.isEmpty()) {
            Edge edge = pq.poll();
            if(distanceResults[edge.e] > edge.w) {
                distanceResults[edge.e] = edge.w;
                for(int i = 0 ; i < distList[edge.e].size() ; i++) {
                    if(distList[edge.e].get(i).e == edge.s) continue;
                    pq.offer(new Edge(edge.e, distList[edge.e].get(i).e, distList[edge.e].get(i).w));
                }
            }
        }
        

    }
}




class Edge {
    int s;
    int e;
    int w;
    public Edge(int s, int e, int w) {
        this.s = s;
        this.e = e;
        this.w = w;
    }

    @Override
    public String toString() {
        return this.s + " " + this.e + " " + this.w; 
    }
}