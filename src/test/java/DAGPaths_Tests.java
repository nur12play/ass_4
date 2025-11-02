import graph.dagsp.DAGPaths;
import org.junit.jupiter.api.Test;
import java.util.*;

public class DAGPaths_Tests {

    @Test
    public void testPathsInDAG() {
        Map<Integer, List<int[]>> dag = new HashMap<>();
        for (int i = 0; i < 4; i++) dag.put(i, new ArrayList<>());
        dag.get(0).add(new int[]{1, 2});
        dag.get(0).add(new int[]{2, 4});
        dag.get(1).add(new int[]{3, 3});
        dag.get(2).add(new int[]{3, 1});

        List<Integer> topo = List.of(0, 1, 2, 3);

        DAGPaths.run(dag, topo, 0);
    }
}
