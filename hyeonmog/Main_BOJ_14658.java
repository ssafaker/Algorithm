package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_BOJ_14658 {
    static public void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int result = 0;
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        Star[] stars = new Star[k];
        for(int i = 0 ; i < k ; i++) {
            st = new StringTokenizer(br.readLine());
            stars[i] = new Star(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()));
        }
        for(int i = 0 ; i < k ; i++) {
            for (int j = 0 ; j < k ; j++) {
                // Star starX = stars[i];
                // Star starY = stars[j];
                int tramX = stars[i].x;
                int tramY = stars[j].y;
                int breakStarCnt = 0;
                //
                //  tramX, tramY              star.x, star.y+l
                //
                //
                //  star.x+l, star.y            tramX+l, tramY+l
                //
                for(int s = 0 ; s < k ; s++) {
                    if(stars[s].x >= tramX && stars[s].x <= tramX+l && stars[s].y >= tramY && stars[s].y <= tramY+l) {
                        breakStarCnt++;
                    }
                }
                result = Math.max(breakStarCnt, result);
            }
        }


        System.out.println((k-result));

    }
}

class Star {
    int x;
    int y;

    public Star(int x, int y) {
        this.x = x;
        this.y = y;
    }
}