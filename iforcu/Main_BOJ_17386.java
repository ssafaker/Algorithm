package iforcu;

import java.io.*;
import java.util.*;

/*
 *  문제명: [선분 교차 1]
 *  링크 : [https://www.acmicpc.net/problem/17386]
 *  난이도 : [골드 3]
 *  설명: 
 *   - 2차원 평면상에서 두 선분이 교차하는지 판단하는 문제.
 *   - 선분의 끝점이 다른 선분 위에 있는 경우도 교차로 인정.
 *   - 좌표는 -1,000,000 ~ 1,000,000 범위로 주어짐.
 *  풀이:
 *   - CCW(Counter Clock Wise) 알고리즘을 사용하여 교차 여부 판단.
 *   - 두 선분이 서로를 기준으로 반대편에 위치하면 교차.
 *   - 한 점이 선분 위에 있는 경우(CCW=0)는 별도로 처리.
 */

public class Main_BOJ_17386 {
    public static void main(String[] args) throws Exception {
        BufferedReader br =  new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());
        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int x3 = Integer.parseInt(st.nextToken());
        int y3 = Integer.parseInt(st.nextToken());
        int x4 = Integer.parseInt(st.nextToken());
        int y4 = Integer.parseInt(st.nextToken());

        if(isIntersect(x1, y1, x2, y2, x3, y3, x4, y4)) System.out.println(1);
        else System.out.println(0);
    }

    // 두 선분의 교차 여부 판단
    static boolean isIntersect(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int c1 = ccw(x1, y1, x2, y2, x3, y3);
        int c2 = ccw(x1, y1, x2, y2, x4, y4);
        int c3 = ccw(x3, y3, x4, y4, x1, y1);
        int c4 = ccw(x3, y3, x4, y4, x2, y2);

        // 두 선분이 서로를 기준으로 반대편에 위치하면 교차
        if( c1 * c2 < 0 && c3 * c4 < 0) return true;

        // 한 점이 다른 선분 위에 있는 경우 체크
        if( c1 == 0 && isBetween(x1, y1, x2, y2, x3, y3)) return true;
        if( c2 == 0 && isBetween(x1, y1, x2, y2, x4, y4)) return true;
        if( c3 == 0 && isBetween(x3, y3, x4, y4, x1, y1)) return true;
        if( c4 == 0 && isBetween(x3, y3, x4, y4, x2, y2)) return true;
        return false;
    }

    // 점이 선분 사이에 있는지 확인
    static boolean isBetween(int x1, int y1, int x2, int y2, int x3, int y3) {
        return Math.min(x1, x2) <= x3 && x3 <= Math.max(x1, x2) && Math.min(y1,y2) <= y3 && y3 <= Math.max(y1, y2);
    }

    // CCW 알고리즘: 세 점의 방향성 판단 (양수: 반시계, 음수: 시계, 0: 일직선)
    static int ccw(int x1, int y1, int x2, int y2, int x3, int y3) {
        long result = (long)(x2 - x1) * (y3 - y1) - (long)(y2 - y1) * (x3 - x1);
        return Long.compare(result, 0);
    }
}
