package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

//0 0 0 0 0 0
class Main_BOJ_1459 {
    static int destX, destY, w, s;
    static long result;
    public static void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        result = 0;
        destX = Integer.parseInt(st.nextToken());
        destY = Integer.parseInt(st.nextToken());
        w = Integer.parseInt(st.nextToken());
        s = Integer.parseInt(st.nextToken());

        //대각선 이동하는 게 그냥 2번 이동하는 거 보다 비효율적이면
        if(2*w < s) {
            result += (long)Math.min(destX, destY) * (2*w);
        }
        else {
            result += (long)Math.min(destX, destY) * s;
        }
        int move = Math.abs(destX-destY);
        if(move >= 2) {
            if(2*w < 2*s) {
                if(move % 2 == 1) {
                    result += (long)(move-1) * w;
                }
                else {
                    result += (long)move * w;
                }
            }
            else {
                if(move % 2 == 1) {
                    result += (long)(move-1) * s;
                }
                else {
                    result += (long)move * s;
                }
            }
            
            move %= 2;
        }
        if(move == 1) {
            result += w;
        }
        
        System.out.println(result);

    }

}