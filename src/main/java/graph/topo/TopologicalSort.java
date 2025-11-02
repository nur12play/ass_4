package graph.topo;
import java.util.*;

public class TopologicalSort {

    public static List<Integer> kahn(Map<Integer, List<int[]>> dag) {

        if (dag == null || dag.isEmpty()) {
            System.out.println("[WARN] DAG is empty. Returning empty topological order.");
            return new ArrayList<>();
        }

        Map<Integer, Integer> indeg = new HashMap<>();
        for (int v : dag.keySet()) indeg.put(v, 0);

        for (int u : dag.keySet()) {
            List<int[]> edges = dag.get(u);
            if (edges == null) continue;
            for (int[] e : edges) {
                int v = e[0];
                indeg.put(v, indeg.getOrDefault(v, 0) + 1);
            }
        }

        Queue<Integer> q = new ArrayDeque<>();
        for (var entry : indeg.entrySet()) {
            if (entry.getValue() == 0) q.add(entry.getKey());
        }

        if (q.isEmpty()) {
            System.out.println("[WARN] No zero-in-degree vertices. DAG may be incorrect or misconstructed.");
        }

        List<Integer> order = new ArrayList<>();

        while (!q.isEmpty()) {
            int u = q.poll();
            order.add(u);

            List<int[]> edges = dag.getOrDefault(u, Collections.emptyList());
            for (int[] e : edges) {
                int v = e[0];
                indeg.put(v, indeg.get(v) - 1);
                if (indeg.get(v) == 0) q.add(v);
            }
        }

        if (order.size() != dag.size()) {
            System.out.println("[WARN] TopoSort: Graph might not be a DAG or condensation produced duplicates.");
            System.out.println("       Vertices in DAG: " + dag.size() + ", in topo order: " + order.size());
            System.out.println("       Attempting to continue with partial order...");
        } else {
            System.out.println("[INFO] Topological sort completed: " + order);
        }

        return order;
    }
}
