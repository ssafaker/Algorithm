package iforcu;

/*
 *  문제명 : 붕대 감기
 *  링크   : https://school.programmers.co.kr/learn/courses/30/lessons/250137
 *  난이도 : Level 1
 *  설명   :
 *    - [t초] 동안 1초마다 x만큼 체력 회복, t초 연속 성공 시 y만큼 추가 회복
 *    - 공격받으면 기술 취소 및 체력 감소, 체력이 0 이하가 되면 -1 반환
 *    - 모든 공격 후 남은 체력 반환, 최대 체력 초과 불가
 *  풀이   :
 *    - 공격 사이 구간마다 회복량, 추가 회복량 계산
 *    - 공격 시 체력 감소 및 사망 체크
 *    - 반복문으로 시간 흐름과 상태 갱신
 */

public class Main_PSG_붕대감기 {
    public int solution(int[] bandage, int health, int[][] attacks) {
        int bonusTime = bandage[0];
        int heal = bandage[1];
        int bonusHeal = bandage[2];
        int curHealth = health;
        int maxHealth = health;
        int curTime = 0;
        
        for(int[] attack : attacks) {
            int time = attack[0];
            int damage = attack[1];
            int duration = time - curTime - 1;
            curTime = time;
            // 공격 전까지 회복 및 추가 회복 계산
            curHealth += (duration / bonusTime * bonusHeal) + (duration * heal);
            if(curHealth > maxHealth) curHealth = maxHealth;
            // 공격 피해 적용
            curHealth -= damage;
            if(curHealth <= 0) return -1;
        }
        // 모든 공격 후 남은 체력 반환
        return curHealth;
    }
}
