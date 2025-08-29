package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

class Main_BOJ_12891 {
    static int s, p;
    static Map<Character, Integer> validPwd;
    static Map<Character, Integer> nowPwd;
    static char[] arr;
    static int result;
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        s = Integer.parseInt(st.nextToken());
        p = Integer.parseInt(st.nextToken());
        arr = new char[s];
        validPwd = new HashMap<>();
        nowPwd = new HashMap<>();
        result = 0;

        String str = br.readLine();
        for(int i = 0 ; i < s ; i++) {
            arr[i] = str.charAt(i);
        }
        st = new StringTokenizer(br.readLine());
        int validCnt = 0;
        validCnt = Integer.parseInt(st.nextToken());
        validPwd.put('A', validCnt);
        nowPwd.put('A', 0);
        validCnt = Integer.parseInt(st.nextToken());
        validPwd.put('C', validCnt);
        nowPwd.put('C', 0);
        validCnt = Integer.parseInt(st.nextToken());
        validPwd.put('G', validCnt);
        nowPwd.put('G', 0);
        validCnt = Integer.parseInt(st.nextToken());
        validPwd.put('T', validCnt);
        nowPwd.put('T', 0);


        for(int i = 0 ; i < p ; i++) {
            nowPwd.put(arr[i], nowPwd.getOrDefault(arr[i], 0) + 1);

        }
        boolean flag = false;
        for(Character c : validPwd.keySet()) {
            if(validPwd.get(c) > nowPwd.get(c)) {
                flag = true;
            }
        }
        if(!flag) {
            result++;
        } 
        //시작
        int startIdx = 0;
        for(int i = p ; i < s ; i++) {
            nowPwd.put(arr[i], nowPwd.getOrDefault(arr[i], 0) + 1);
            nowPwd.put(arr[startIdx], nowPwd.get(arr[startIdx]) - 1);
            startIdx++;
            flag = false;
            for(Character c : validPwd.keySet()) {
                if(validPwd.get(c) > nowPwd.get(c)) {
                    flag = true;
                }
            }
            if(!flag) {
                result++;
            } 
        }

        System.out.println(result);

    }
}