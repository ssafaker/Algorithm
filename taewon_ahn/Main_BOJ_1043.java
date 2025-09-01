import java.io.*;
import java.util.*;

public class Main_BOJ_1043 {
    static int[] parent;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        List<Integer> truePeople = new ArrayList<>();
        st = new StringTokenizer(br.readLine());
        int T = Integer.parseInt(st.nextToken());
        for (int i = 0; i < T; i++) {
            truePeople.add(Integer.parseInt(st.nextToken()));
        }

        parent = new int[N + 1];
        for (int i = 1; i <= N; i++) {
            parent[i] = i;
        }

        // 파티 등록 & union
        List<Integer>[] party = new List[M];
        for (int i = 0; i < M; i++) {
            party[i] = new ArrayList<>();
            st = new StringTokenizer(br.readLine());
            T = Integer.parseInt(st.nextToken());

            for (int j = 0; j < T; j++) {
                party[i].add(Integer.parseInt(st.nextToken()));
            }

            for (int j = 1; j < party[i].size(); j++) {
                union(party[i].get(0), party[i].get(j));
            }
        }

        // 진실 아는 사람들 대표노드 union
        for (int i = 1; i < truePeople.size(); i++) {
            union(truePeople.get(0), truePeople.get(i));
        }
        int trueRoot = truePeople.size() > 0 ? find(truePeople.get(0)) : -1;


        int answer = 0;
        for (List<Integer> group : party) {
            boolean canLie = true;
            for (int p : group) {
                if (trueRoot > 0 && trueRoot == find(p)) {
                    canLie =false;
                }
            }

            if (canLie) {
                answer++;
            }
        }

        System.out.println(answer);
    }

    private static void union(int a, int b) {
        if (find(a) != find(b))
            parent[find(a)] = find(b);
    }

    private static int find(int a) {
        if (parent[a] == a)
            return a;
        return parent[a] = find(parent[a]);
    }
}