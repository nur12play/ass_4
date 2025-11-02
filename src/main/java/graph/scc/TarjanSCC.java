package graph.scc;
import java.util.*;

public class TarjanSCC {
    private final Map<Integer, List<int[]>> graph;
    private int time = 0, compCount = 0;
    private final Map<Integer, Integer> disc = new HashMap<>();
    private final Map<Integer, Integer> low = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Set<Integer> inStack = new HashSet<>();
    private final List<List<Integer>> components = new ArrayList<>();
    private final Map<Integer, Integer> compMap = new HashMap<>();

    public TarjanSCC(Map<Integer, List<int[]>> graph) {
        this.graph = graph;
    }

    public List<List<Integer>> run() {
        for (int v : graph.keySet()) {
            if (!disc.containsKey(v))
                dfs(v);
        }
        return components;
    }

    private void dfs(int u) {
        disc.put(u, time);
        low.put(u, time);
        time++;
        stack.push(u);
        inStack.add(u);

        for (int[] edge : graph.get(u)) {
            int v = edge[0];
            if (!disc.containsKey(v)) {
                dfs(v);
                low.put(u, Math.min(low.get(u), low.get(v)));
            } else if (inStack.contains(v)) {
                low.put(u, Math.min(low.get(u), disc.get(v)));
            }
        }

        if (Objects.equals(low.get(u), disc.get(u))) {
            List<Integer> comp = new ArrayList<>();
            int v;
            do {
                v = stack.pop();
                inStack.remove(v);
                comp.add(v);
                compMap.put(v, compCount);
            } while (v != u);
            components.add(comp);
            compCount++;
        }
    }

    public Map<Integer, Integer> getCompMap() {
        return compMap;
    }

    public static Map<Integer, List<int[]>> buildCondensation(
            Map<Integer, List<int[]>> original,
            List<List<Integer>> components,
            Map<Integer, Integer> compMap) {

        Map<Integer, List<int[]>> dag = new HashMap<>();
        for (int i = 0; i < components.size(); i++) dag.put(i, new ArrayList<>());

        for (var e : original.entrySet()) {
            for (int[] edge : e.getValue()) {
                int uComp = compMap.get(e.getKey());
                int vComp = compMap.get(edge[0]);
                if (uComp != vComp)
                    dag.get(uComp).add(new int[]{vComp, edge[1]});
            }
        }
        return dag;
    }

    public int componentOf(int v) {
        return compMap.get(v);
    }
}
