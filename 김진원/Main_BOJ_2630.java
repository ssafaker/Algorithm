package ssafy.study.P0913;

/**
 * 알고리즘: 분할 정복
 * 시간복잡도: O(N^2)
 */
import java.io.*;
import java.util.*;

public class Main_BOJ_2630 {
	static int[][] A; // 색종이 정보를 저장하는 2차원 배열 (0: 흰색, 1: 파란색)
	static int white; // 흰색 색종이 개수 카운트
	static int blue; // 파란색 색종이 개수 카운트

	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

		int N = Integer.parseInt(br.readLine()); // 색종이 한 변의 길이 (N x N)

		StringTokenizer st;
		A = new int[N][N]; // 색종이 정보를 저장할 배열 초기화

		// 색종이 정보 입력
		for (int i = 0; i < N; i++) {
			st = new StringTokenizer(br.readLine());
			for (int j = 0; j < N; j++) {
				A[i][j] = Integer.parseInt(st.nextToken());
			}
		}

		// 흰색과 파란색 색종이 개수 초기화
		white = 0;
		blue = 0;

		// 전체 색종이를 분할 정복 시작
		divide(0, 0, N);

		// 결과 출력 (흰색 → 파란색 순서)
		bw.write(white + "\n");
		bw.write(blue + "\n");
		bw.flush();

		bw.close();
		br.close();
	}

	// (r, c)를 좌상단으로 하는 size × size 영역을 검사 및 분할하는 메서드
	static void divide(int r, int c, int size) {
		// 현재 영역이 모두 같은 색인지 확인
		if (isUniform(r, c, size)) {
			// 모두 같은 색일 경우 해당 색 카운트 증가
			if (A[r][c] == 0)
				white++; // 흰색 색종이 추가
			else
				blue++; // 파란색 색종이 추가
			return; // 더 이상 분할하지 않고 종료
		}

		// 색이 섞여 있으면 4등분하여 재귀적으로 처리
		int half = size / 2;
		divide(r, c, half); // 좌상단
		divide(r, c + half, half); // 우상단
		divide(r + half, c, half); // 좌하단
		divide(r + half, c + half, half); // 우하단
	}

	// 현재 영역이 모두 같은 색인지 검사하는 메서드
	static boolean isUniform(int r, int c, int size) {
		int color = A[r][c]; // 기준 색상
		// 영역 내 모든 칸을 검사
		for (int i = r; i < r + size; i++) {
			for (int j = c; j < c + size; j++) {
				// 다른 색이 하나라도 있으면 false 반환
				if (A[i][j] != color)
					return false;
			}
		}
		// 모든 칸이 같은 색이면 true 반환
		return true;
	}
}