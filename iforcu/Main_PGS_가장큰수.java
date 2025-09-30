package iforcu;

import java.util.*;

/*
 *  문제명 : [가장 큰 수]
 *  링크   : [https://school.programmers.co.kr/learn/courses/30/lessons/42746]
 *  난이도 : [Level 2]
 *  설명   :
 *    - 주어진 정수 배열을 이어붙여 만들 수 있는 가장 큰 수를 문자열로 반환
 *    - 단, 모든 수가 0일 경우 "0" 반환
 *  풀이   :
 *    - 각 숫자를 문자열로 변환 후, 두 문자열 a, b에 대해 (b+a)와 (a+b)를 비교해 내림차순 정렬
 *    - 정렬된 순서대로 이어붙여 결과 생성
 *    - 맨 앞이 "0"이면 전체가 0이므로 "0" 반환
 */

public class Main_PGS_가장큰수 {
    // numbers 배열을 이어붙여 만들 수 있는 가장 큰 수를 반환
    public String solution(int[] numbers) {
        String[] arr = new String[numbers.length];
        String answer = "";
        // 각 숫자를 문자열로 변환
        for(int i = 0; i < numbers.length; i++) {
            arr[i] = String.valueOf(numbers[i]);
        }
        // 두 문자열 a, b에 대해 (b+a)와 (a+b)를 비교해 내림차순 정렬
        Arrays.sort(arr,(a, b) -> (b + a).compareTo(a + b));
        // 모든 수가 0일 경우 "0" 반환
        if (arr[0].equals("0")) return "0";
        // 정렬된 순서대로 이어붙여 결과 생성
        for (String s : arr) {
            answer += s;
        }
        return answer;
    }
}
