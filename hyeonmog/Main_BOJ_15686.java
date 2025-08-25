package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

class Main_BOJ_15686 {
    static int n, m;
    static boolean[] visited;
    static int[][] arr;
    static List<Position> houseList;
    static List<Position> chickenList;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        n = Integer.parseInt(st.nextToken());
        m = Integer.parseInt(st.nextToken());
        result = (int) 1e9;

        arr = new int[n][n];
        
        houseList = new ArrayList<>();
        chickenList = new ArrayList<>();
        
        
        for(int i = 0 ; i < n ; i++) {
            st = new StringTokenizer(br.readLine());
            for(int j = 0 ; j < n ; j++) {
                arr[i][j] = Integer.parseInt(st.nextToken());
                if(arr[i][j] == 1) {
                    houseList.add(new Position(i, j));
                }
                else if(arr[i][j] == 2) {
                    chickenList.add(new Position(i, j));
                }
            }
        }
        
        visited = new boolean[chickenList.size()];

        chickenComb(new ArrayList<Position>(), 0);

        System.out.println(result);

    }

    public static int getChickenScore(int x1, int y1, int x2, int y2) {
        return (Math.abs(x1 - x2) + Math.abs(y1 - y2));
    }


    public static void chickenComb(List<Position> selectedChickens, int idx) {
        if(selectedChickens.size() == m) {
            result = Math.min(result, getStreetScore(selectedChickens));
        }  


        for(int i = idx ; i < chickenList.size() ; i++) {
            //선택안함
            chickenComb(selectedChickens, i+1);
            if(!visited[i]) {
                //선택함
                selectedChickens.add(chickenList.get(i));
                visited[i] = true;
                chickenComb(selectedChickens, i+1);
                visited[i] = false;
                selectedChickens.remove(selectedChickens.size()-1);
            }

        }


    }

    public static int getStreetScore(List<Position> selectedChickens) {

        int streetSum = 0;

        for(Position housePos : houseList) {
            int minScore = (int)1e9;
            for(Position chickenPos : selectedChickens) {
                minScore = Math.min(minScore, getChickenScore(housePos.x, housePos.y, chickenPos.x, chickenPos.y));
            }
            streetSum += minScore;
        }
        return streetSum;
    }
}

class Position {
    int x;
    int y;
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }
}