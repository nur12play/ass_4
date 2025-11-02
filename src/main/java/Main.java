import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import graph.scc.TarjanSCC;
import graph.topo.TopologicalSort;
import graph.dagsp.DAGPaths;

public class Main {

    private static final Path RESULT_DIR = Paths.get("results");
    private static final Path SUMMARY_LOG = RESULT_DIR.resolve("summary.log");

    private static void writeToFile(Path file, String text, boolean append) throws IOException {
        if (!Files.exists(RESULT_DIR)) Files.createDirectories(RESULT_DIR);
        if (append) Files.writeString(file, text, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        else Files.writeString(file, text, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    public static void main(String[] args) {
        Path dataDir = Paths.get("data");

        if (!Files.exists(dataDir)) {
            System.out.println("Папка data/ не найдена!");
            return;
        }

        try {
            // Очистить summary.log
            writeToFile(SUMMARY_LOG, "=== RUN SUMMARY ===\n\n", false);

            List<Path> jsonFiles = Files.list(dataDir)
                    .filter(path -> path.toString().endsWith(".json"))
                    .toList();

            for (Path file : jsonFiles) {

                StringBuilder log = new StringBuilder();
                log.append("\n==========================================\n");
                log.append(" Running dataset: ").append(file.getFileName()).append("\n");
                log.append("==========================================\n");

                String jsonContent = Files.readString(file);
                JSONObject json = new JSONObject(jsonContent);

                int n = json.getInt("nodes");
                JSONArray edges = json.getJSONArray("edges");

                Map<Integer, List<int[]>> graph = new HashMap<>();
                for (int i = 0; i < n; i++) graph.put(i, new ArrayList<>());

                for (int i = 0; i < edges.length(); i++) {
                    JSONObject e = edges.getJSONObject(i);

                    int u = e.has("u") ? e.getInt("u") : e.getInt("from");
                    int v = e.has("v") ? e.getInt("v") : e.getInt("to");
                    int w = e.has("w") ? e.getInt("w") : e.getInt("weight");

                    graph.get(u).add(new int[]{v, w});
                }

                int src = json.has("source") ? json.getInt("source") : 0;
                log.append("🔍 Source in JSON = ").append(src).append("\n");

                log.append("\n=== Strongly Connected Components ===\n");
                TarjanSCC scc = new TarjanSCC(graph);
                List<List<Integer>> comps = scc.run();

                for (int i = 0; i < comps.size(); i++) {
                    log.append("Component ").append(i).append(": ").append(comps.get(i)).append("\n");
                }

                int srcComp = scc.getCompMap().get(src);
                log.append("✅ Source belongs to SCC component = ").append(srcComp).append("\n");

                log.append("\n=== Topological Order (Condensed DAG) ===\n");
                Map<Integer, List<int[]>> dag = TarjanSCC.buildCondensation(graph, comps, scc.getCompMap());
                List<Integer> order = TopologicalSort.kahn(dag);
                log.append("Topo Order: ").append(order).append("\n");

                log.append("\n=== Shortest & Longest Paths ===\n");
                log.append("[INFO] Using source component: ").append(srcComp).append("\n");

                String pathsOutput = DAGPaths.runToString(dag, order, srcComp);
                log.append(pathsOutput).append("\n");

                Path outFile = RESULT_DIR.resolve(file.getFileName().toString().replace(".json", ".out.txt"));
                writeToFile(outFile, log.toString(), false);

                writeToFile(SUMMARY_LOG, log.toString(), true);
            }

            System.out.println("Все результаты сохранены в папке /results");

        } catch (IOException e) {
            System.err.println(" Ошибка: " + e.getMessage());
        }
    }
}
