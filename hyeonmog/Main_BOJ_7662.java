package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

class Main_BOJ_7662 {
    static int n;
    static int cnt;
    static PriorityQueue<Integer> maxPq;
    static PriorityQueue<Integer> minPq;
    static Map<Integer, Integer> map;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int t = Integer.parseInt(br.readLine());
        
        for(int testCase = 1 ; testCase < t+1 ; testCase++) {
            map = new HashMap<>();
            maxPq = new PriorityQueue<>((a, b) -> Integer.compare(b, a));
            minPq = new PriorityQueue<>((a, b) -> Integer.compare(a, b));
            n = Integer.parseInt(br.readLine());
            cnt = 0;
            for(int rep = 0 ; rep < n ; rep++) {
                st = new StringTokenizer(br.readLine());
                char command = st.nextToken().charAt(0);
                int value = Integer.parseInt(st.nextToken());
                int v = 0;
                if(command == 'I') {
                    maxPq.offer(value);
                    minPq.offer(value);
                    map.put(value, map.getOrDefault(value, 0) + 1);
                    cnt++;
                }
                else if(command == 'D') {
                    if(cnt == 0) continue;
                    if(value == 1) {
                        while(true) {
                            v = maxPq.poll();
                            if(map.get(v) >= 1) break;
                        }
                        map.put(v, map.get(v)-1);
                        cnt--;
                    }
                    else if(value == -1) {
                        while(true) {
                            v = minPq.poll();
                            if(map.get(v) >= 1) break;
                        }
                        map.put(v, map.get(v)-1);
                        cnt--;
                    }
                }
            }
                if(cnt == 0) {
                    System.out.println("EMPTY");
                }
                else if(cnt == 1) {
                    for(int i : map.keySet()) {
                        if(map.get(i) == 1) {
                            System.out.println(i + " " + i);
                        }
                    }
                }
                else {
                    int max = 0;
                    int min = 0;
                    while(true) {
                        min = minPq.poll();
                        if(map.get(min) >= 1) break;
                    }
                    while(true) {
                        max = maxPq.poll();
                        if(map.get(max) >= 1) break;
                    }
                
                    System.out.println(max + " " + min);
                }
            }
    }
}
    
    