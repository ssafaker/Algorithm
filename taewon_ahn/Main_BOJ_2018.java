
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main_BOJ_2018 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine());

        int answer = 1; // 자기 자신 포함
        int start = 1;
        int end = 1;
        int sum = 1;

        while (end < n) {
            if (sum < n) {
                sum += ++end;
            } else if (sum > n) {
                sum -= start++;
            } else {
                answer++;
                sum += ++end;
            }
        }
        System.out.println(answer);
    }
}
