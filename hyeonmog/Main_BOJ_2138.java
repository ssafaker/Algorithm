package hyeonmog;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

//1시간
class Main_BOJ_2138 {
    static int n;
    static int[] firstArr;
    static int[] secondArr;
    static int[] targetArr;
    static int firstResult;
    static int secondResult;
    static public void main(String[] args) throws IOException{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        n = Integer.parseInt(br.readLine());
        firstArr = new int[n];
        secondArr = new int[n];
        targetArr = new int[n];
        firstResult = 0;
        secondResult = 0;

        String str1 = br.readLine();
        String str2 = br.readLine();
        for(int i = 0 ; i < n ; i++) {
            firstArr[i] = Character.getNumericValue(str1.charAt(i));
            secondArr[i] = Character.getNumericValue(str1.charAt(i));
            targetArr[i] = Character.getNumericValue(str2.charAt(i));
        }

        clickSwitch(firstArr, 0);
        firstResult++;
        for(int i = 1 ; i < n ; i++) {
            if(firstArr[i-1] != targetArr[i-1]) {
                clickSwitch(firstArr, i);
                firstResult++;
            }
        }

        for(int i = 1 ; i < n ; i++) {
            if(secondArr[i-1] != targetArr[i-1]) {
                clickSwitch(secondArr, i);
                secondResult++;
            }
        }
        int result = 0;

        if(isValid(firstArr) && isValid(secondArr)) {
            result = Math.min(firstResult, secondResult);
        } else if (!isValid(firstArr) && !isValid(secondArr)) {
            result = -1;
        } else {
            if(isValid(firstArr)) result = firstResult;
            else result = secondResult;
        }

        System.out.println(result);
    }

    static public boolean isValid(int[] arr) {
        for (int i = 0; i < n; i++) {
            if(arr[i] != targetArr[i]) {
                return false;
            }   
        }
        return true;
    }

    static public void clickSwitch(int[] arr, int idx) {

        if(idx-1 >= 0) arr[idx-1] = arr[idx-1] ^ 1;
        arr[idx] = arr[idx] ^ 1;
        if(idx+1 < arr.length) arr[idx+1] = arr[idx+1] ^ 1;
    }
}