package taewon_ahn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main_SEA_5644 {
    static int M, A; // M: 이동시간, A: charger개수
    static int[] pathA, pathB;
    static int[] dx = { 0, 0, 1, 0, -1 };
    static int[] dy = { 0, -1, 0, 1, 0 };
    static BC[] chargers;

    static class BC {
        int x, y, coverage, power;

        public BC(int x, int y, int c, int p) {
            this.x = x;
            this.y = y;
            this.coverage = c;
            this.power = p;
        }

        boolean canCharge(int userX, int userY) {
            return Math.abs(userX - this.x) + Math.abs(userY - this.y) <= this.coverage;
        }
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int T = Integer.parseInt(br.readLine());

        for (int tc = 1; tc <= T; tc++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            M = Integer.parseInt(st.nextToken());
            A = Integer.parseInt(st.nextToken());

            pathA = new int[M + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathA[i + 1] = Integer.parseInt(st.nextToken());
            }

            pathB = new int[M + 1];
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < M; i++) {
                pathB[i + 1] = Integer.parseInt(st.nextToken());
            }

            chargers = new BC[A];
            for (int i = 0; i < A; i++) {
                st = new StringTokenizer(br.readLine());
                int x = Integer.parseInt(st.nextToken());
                int y = Integer.parseInt(st.nextToken());
                int c = Integer.parseInt(st.nextToken());
                int p = Integer.parseInt(st.nextToken());
                chargers[i] = new BC(x, y, c, p);
            }

            System.out.printf("#%d %d\n", tc, solve());
        }
    }

    private static int solve() {
        int totalCharge = 0;
        int ax = 1;
        int ay = 1;
        int bx = 10;
        int by = 10;

        for (int t = 0; t <= M; t++) {
            ax += dx[pathA[t]];
            ay += dy[pathA[t]];
            bx += dx[pathB[t]];
            by += dy[pathB[t]];

            totalCharge += maxCharge(ax, ay, bx, by);
        }
        return totalCharge;
    }

    private static int maxCharge(int ax, int ay, int bx, int by) {
        int maxCharge = 0;
        for (int i = 0; i < A; i++) {
            for (int j = 0; j < A; j++) {
                int charge = 0;
                boolean isACanChargeI = chargers[i].canCharge(ax, ay);
                boolean isBCanChargeJ = chargers[j].canCharge(bx, by);

                if (i == j) {
                    if (isACanChargeI && isBCanChargeJ) {
                        charge = chargers[i].power;
                    } else if (isACanChargeI) {
                        charge = chargers[i].power;
                    } else if (isBCanChargeJ) {
                        charge = chargers[j].power;
                    }
                } else {
                    if (isACanChargeI) {
                        charge += chargers[i].power;
                    }
                    if (isBCanChargeJ) {
                        charge += chargers[j].power;
                    }
                }
                maxCharge = Math.max(charge, maxCharge);
            }
        }
        return maxCharge;
    }
}