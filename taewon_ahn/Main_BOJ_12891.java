
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_BOJ_12891 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int S = Integer.parseInt(st.nextToken());
        int P = Integer.parseInt(st.nextToken());
        String str = br.readLine();
        st = new StringTokenizer(br.readLine());
        int[] checks = new int[4];
        for (int i = 0; i < checks.length; i++) {
            checks[i] = Integer.parseInt(st.nextToken());
        }
        int[] arr = new int[4];
        for (int i = 0; i < P; i++) {
            char cur = str.charAt(i);
            if (cur == 'A') {
                arr[0]++;
            } else if (cur == 'C') {
                arr[1]++;
            } else if (cur == 'G') {
                arr[2]++;
            } else {
                arr[3]++;
            }
        }
        int answer = 0;
        if (arr[0] >= checks[0] && arr[1] >= checks[1] && arr[2] >= checks[2] && arr[3] >= checks[3]) {
            answer++;
        }
        for (int i = P; i < S; i++) {
            char left = str.charAt(i - P);
            char right = str.charAt(i);
            if (left == 'A') {
                arr[0]--;
            } else if (left == 'C') {
                arr[1]--;
            } else if (left == 'G') {
                arr[2]--;
            } else {
                arr[3]--;
            }
            if (right == 'A') {
                arr[0]++;
            } else if (right == 'C') {
                arr[1]++;
            } else if (right == 'G') {
                arr[2]++;
            } else {
                arr[3]++;
            }
            if (arr[0] >= checks[0] && arr[1] >= checks[1] && arr[2] >= checks[2] && arr[3] >= checks[3]) {
                answer++;
            }
        }
        System.out.println(answer);
    }
}
