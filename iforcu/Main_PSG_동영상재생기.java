package iforcu;

/*
 *  문제명 : 동영상재생기
 *  링크   : https://school.programmers.co.kr/learn/courses/30/lessons/250135
 *  난이도 : Level 1
 *  설명   :
 *    - 동영상의 전체 길이, 현재 위치, 구간 반복 시작/끝, 명령어 목록이 주어짐
 *    - 명령어(prev/next)로 10초 단위 이동, 구간 반복 중이면 끝으로 점프
 *    - 영상 범위 밖 이동 시 0초 또는 영상 끝으로 보정
 *    - 최종 위치를 mm:ss 문자열로 반환
 *  풀이   :
 *    - 시간 문자열을 초 단위로 변환해 연산
 *    - 명령어 반복 처리, 구간 반복 조건마다 위치 보정
 *    - 최종 위치를 mm:ss로 변환해 반환
 */

public class Main_PSG_동영상재생기 {
    public String solution(String video_len, String pos, String op_start, String op_end, String[] commands) {
        // 시간 문자열을 초 단위로 변환
        int videoLenSec = toSeconds(video_len);
        int posSec = toSeconds(pos);
        int opStartSec = toSeconds(op_start);
        int opEndSec = toSeconds(op_end);

        // 시작 위치가 구간 반복 범위 내에 있으면 끝으로 점프
        if (posSec >= opStartSec && posSec <= opEndSec) {
            posSec = opEndSec;
        }

        // 명령어 순차 처리
        for (String cmd : commands) {
            if (cmd.equals("prev")) {
                posSec -= 10;
                // 0초 미만 이동 시 0초로 보정
                if (posSec < 0) {
                    posSec = 0;
                }
            } else if (cmd.equals("next")) {
                posSec += 10;
                // 영상 길이 초과 시 끝으로 보정
                if (posSec > videoLenSec) {
                    posSec = videoLenSec;
                }
            }
            // 이동 후 구간 반복 범위 진입 시 끝으로 점프
            if (posSec >= opStartSec && posSec <= opEndSec) {
                posSec = opEndSec;
            }
        }

        // 최종 위치를 mm:ss 문자열로 변환해 반환
        return toMmss(posSec);
    }

    private int toSeconds(String mmss) {
        String[] parts = mmss.split(":");
        int minutes = Integer.parseInt(parts[0]);
        int seconds = Integer.parseInt(parts[1]);
        return minutes * 60 + seconds;
    }

    private String toMmss(int seconds) {
        int mm = seconds / 60;
        int ss = seconds % 60;
        return String.format("%02d:%02d", mm, ss);
    }
}
