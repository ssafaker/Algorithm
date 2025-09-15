package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class Main_BOJ_12904 {
    static StringBuilder sb;
    static String targetStr;
    static int result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        result = 0;
        sb = new StringBuilder();
        targetStr = br.readLine();
        String str = br.readLine();
        sb.append(str);
        for(int i = sb.length()-1 ; i >= targetStr.length() ; i--) {
            if(sb.charAt(i) == 'A') {
                sb.deleteCharAt(i);
            }
            else {
                sb.deleteCharAt(i);
                sb.reverse();
            }
        }
        if(sb.toString().equals(targetStr)) {
            result = 1;
        }
        System.out.println(result);
    }
}