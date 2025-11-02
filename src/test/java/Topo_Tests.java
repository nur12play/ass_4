import graph.topo.TopologicalSort;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class Topo_Tests {

    @Test
    public void testTopologicalOrder() {
        Map<Integer, List<int[]>> dag = new HashMap<>();
        for (int i = 0; i < 4; i++) dag.put(i, new ArrayList<>());
        dag.get(0).add(new int[]{1, 1});
        dag.get(0).add(new int[]{2, 1});
        dag.get(1).add(new int[]{3, 1});
        dag.get(2).add(new int[]{3, 1});

        List<Integer> order = TopologicalSort.kahn(dag);

        assertTrue(order.indexOf(0) < order.indexOf(1));
        assertTrue(order.indexOf(0) < order.indexOf(2));
        assertTrue(order.indexOf(1) < order.indexOf(3));
        assertTrue(order.indexOf(2) < order.indexOf(3));
    }
}
