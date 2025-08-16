
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

//1:20
class Main_SEA_2112 {
    static int n;
    static int m;
    static int k;
    static int[][] arr;
    static int result;
    static public void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;
        int t = Integer.parseInt(br.readLine());
        for(int test_case = 1 ; test_case < t+1 ; test_case++) {

            st = new StringTokenizer(br.readLine());
            n = Integer.parseInt(st.nextToken());
            m = Integer.parseInt(st.nextToken());
            k = Integer.parseInt(st.nextToken());
            result = n;
            arr = new int[n][m];

            for(int i = 0 ; i < n ; i++) {
                st = new StringTokenizer(br.readLine());
                for(int j = 0 ; j < m ; j++) {
                    arr[i][j] = Integer.parseInt(st.nextToken());
                }
            }
            dfs(0, 0);

            System.out.println("#" + test_case + " " + result);
        }

    }

    static public void dfs(int depth, int injectCnt) {
        if(injectCnt > result) {
            return;
        }
        if(depth >= n)  {
            if(isValidFilm()) {
                result = Math.min(result, injectCnt);
            }
            return;
        }
        int[] backup = arr[depth].clone();

        //약품 주입 X
        dfs(depth+1, injectCnt);

        //0 약품 주입
        Arrays.fill(arr[depth], 0);
        dfs(depth+1, injectCnt+1);
        arr[depth] = backup.clone();

        //1 약품 주입
        Arrays.fill(arr[depth], 1);
        dfs(depth+1, injectCnt+1);
        arr[depth] = backup.clone();
    }

    static public boolean isValidFilm() {
        for(int i = 0 ; i < m ; i++) {
            int sameCnt = 0;
            for(int j = 1 ; j < n ; j++) {
                if(arr[j][i] == arr[j-1][i]) {
                    sameCnt++;
                }
                else {
                    if(sameCnt < k-1) sameCnt = 0;
                }
            }
            if(sameCnt < k-1) {
                return false;
            }
        }
        return true;
    }


}