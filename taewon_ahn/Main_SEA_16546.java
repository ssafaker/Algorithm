
import java.io.*;
import java.util.Arrays;

public class Main_SEA_16546 {
    static int[] result = new int[6];
    static int[] arr = new int[6];
    static boolean[] used;
    static boolean isBabyGin;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        for (int i = 1; i <= N; i++) {
            String line = br.readLine();
            for (int j = 0; j < line.length(); j++) {
                arr[j] = Character.getNumericValue(line.charAt(j));
            }
            used = new boolean[6];
            isBabyGin = false;
            perm(0);
            System.out.printf("#%d %b\n", i, isBabyGin);
        }
    }

    private static void perm(int idx) {
        if (isBabyGin)
            return;

        if (idx >= 6) {
            checkIsBabyGin();
            return;
        }

        for (int i = 0; i < 6; i++) {
            if (!used[i]) {
                used[i] = true;
                result[idx] = arr[i];
                perm(idx + 1);
                used[i] = false;
            }
        }
    }

    private static void checkIsBabyGin() {
        int[] left = new int[] { result[0], result[1], result[2] };
        int[] right = new int[] { result[3], result[4], result[5] };
        boolean leftCheck = false;
        boolean rightCheck = false;
        // 왼쪽 절반
        if (result[0] == result[1] && result[1] == result[2])
            leftCheck = true;
        else if (isRun(left))
            leftCheck = true;
        // 오른쪽 절반
        if (result[3] == result[4] && result[4] == result[5])
            rightCheck = true;
        else if (isRun(right))
            rightCheck = true;

        isBabyGin = leftCheck && rightCheck;
    }

    private static boolean isRun(int[] partial) {
        Arrays.sort(partial);
        if (partial[0] + 1 == partial[1] && partial[1] + 1 == partial[2])
            return true;
        else
            return false;
    }

}
