import graph.scc.TarjanSCC;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

public class SCC_Tests {

    @Test
    public void testSimpleCycleSCC() {
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int i = 1; i <= 4; i++) graph.put(i, new ArrayList<>());
        graph.get(1).add(new int[]{2, 1});
        graph.get(2).add(new int[]{3, 1});
        graph.get(3).add(new int[]{1, 1});

        TarjanSCC scc = new TarjanSCC(graph);
        List<List<Integer>> comps = scc.run();

        assertEquals(2, comps.size());
        assertTrue(comps.stream().anyMatch(c -> c.containsAll(List.of(1,2,3))));
    }
}
