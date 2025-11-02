package graph.dagsp;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class DAGPaths {

    public static void run(Map<Integer, List<int[]>> dag, List<Integer> topoOrder, int src) {

        if (!dag.containsKey(src)) {
            System.out.println("[WARN] Source component " + src + " not found in DAG. Using default 0.");
            src = 0;
        }

        System.out.println("[INFO] Using source component: " + src);

        Map<Integer, Double> dist = new HashMap<>();
        Map<Integer, Integer> parent = new HashMap<>();

        for (int v : dag.keySet()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            parent.put(v, null);
        }
        dist.put(src, 0.0);

        for (int u : topoOrder) {
            if (dist.get(u) == Double.POSITIVE_INFINITY) continue;

            List<int[]> edges = dag.getOrDefault(u, Collections.emptyList());
            for (int[] e : edges) {
                int v = e[0];
                double w = e[1];

                if (dist.get(v) > dist.get(u) + w) {
                    dist.put(v, dist.get(u) + w);
                    parent.put(v, u);
                }
            }
        }

        System.out.println("=== Shortest Distances from Component " + src + " ===");
        for (int v : dist.keySet()) {
            double d = dist.get(v);
            System.out.println("Node " + v + ": " + (d == Double.POSITIVE_INFINITY ? "∞" : d));
        }

        Map<Integer, Double> longDist = new HashMap<>();
        Map<Integer, Integer> longParent = new HashMap<>();

        for (int v : dag.keySet()) {
            longDist.put(v, Double.NEGATIVE_INFINITY);
            longParent.put(v, null);
        }
        longDist.put(src, 0.0);

        for (int u : topoOrder) {
            if (longDist.get(u) == Double.NEGATIVE_INFINITY) continue;

            List<int[]> edges = dag.getOrDefault(u, Collections.emptyList());
            for (int[] e : edges) {
                int v = e[0];
                double w = e[1];

                if (longDist.get(v) < longDist.get(u) + w) {
                    longDist.put(v, longDist.get(u) + w);
                    longParent.put(v, u);
                }
            }
        }

        double maxLen = Double.NEGATIVE_INFINITY;
        int end = -1;
        for (var e : longDist.entrySet()) {
            if (e.getValue() > maxLen) {
                maxLen = e.getValue();
                end = e.getKey();
            }
        }

        System.out.println("\n=== Critical Path Summary ===");
        System.out.println("Start: " + src);
        System.out.println("End: " + end);
        System.out.println("Length: " + maxLen);
    }
    public static String runToString(Map<Integer, List<int[]>> dag, List<Integer> topoOrder, int src) {
        StringBuilder sb = new StringBuilder();

        PrintStream oldOut = System.out;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        System.setOut(ps);

        run(dag, topoOrder, src);

        System.out.flush();
        System.setOut(oldOut);

        sb.append(baos.toString());
        return sb.toString();
    }

}
