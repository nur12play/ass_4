Конечно — вот тот же README, **без единого смайлика**, строго академичный формат.

---

## Assignment 4: Graph Analytics — SCC, Topological Sort & DAG Shortest Paths

### Objective

Analyze directed graphs using:

* Strongly Connected Components (Tarjan)
* Condensation DAG construction
* Topological Ordering (Kahn)
* Shortest Path in DAG
* Critical (Longest) Path in DAG

Evaluate behavior on small, medium, and large datasets and analyze structural effects.

---

## Dataset Summary

| Dataset    |  Nodes |  Edges | Weight Model            | Notes                        |
| ---------- | -----: | -----: | ----------------------- | ---------------------------- |
| small*     |   ~5–8 |  ~6–10 | Edge weights (positive) | Mostly linear chains         |
| medium*    | ~12–15 | ~15–20 | Edge weights            | Mixed DAG + small cycles     |
| large*     | ~30–40 | ~40–50 | Edge weights            | Multiple SCC cycles          |
| tasks.json |      8 |      7 | Edge weights            | Provided assignment scenario |

Weight model: positive integer weights per edge.

---

## Methods

| Method                       | Purpose                                               |
| ---------------------------- | ----------------------------------------------------- |
| Tarjan SCC                   | Detect strongly connected components, collapse cycles |
| Condensation Graph           | Convert original graph to a DAG of components         |
| Kahn Topological Sort        | Order SCC components in DAG                           |
| DAG Shortest Path            | Dynamic programming over topological order            |
| Critical Path (Longest Path) | Dependency depth; project planning                    |

---

## Results Summary

### SCC Detection

| Dataset | SCC Count | Largest SCC | Notes                |
| ------- | --------: | ----------- | -------------------- |
| small   |       4–6 | 3–4 nodes   | Minor cycles         |
| medium  |      8–11 | 3–4 nodes   | Multiple cycles      |
| large   |     20–30 | 6 nodes     | Complex connectivity |
| tasks   |         6 | 3 nodes     | Simple task loop     |

---

### Performance Metrics

| Dataset | Nodes | Edges | Time (ms) | Complexity |
| ------- | ----: | ----: | --------: | ---------- |
| small   |    ~8 |   ~10 |     ~1 ms | O(V + E)   |
| medium  |   ~14 |   ~20 |     ~2 ms | O(V + E)   |
| large   |   ~40 |   ~50 |   ~4–6 ms | O(V + E)   |
| tasks   |     8 |     7 |     ~1 ms | O(V + E)   |

All algorithms executed within linear time relative to input size.

---

### Path Results

| Dataset | Source | Shortest Path Value | Critical Path End | Critical Path Length |
| ------- | -----: | ------------------: | ----------------- | -------------------: |
| small   |      0 |                 ~10 | node              |                  ~11 |
| medium  |      0 |                  ~9 | node              |               ~12–17 |
| large   |      0 |              ~22–57 | node              |               ~50–57 |
| tasks   |      4 |    reachable subset | 2                 |                    8 |

---

## Analysis

### Effect of Graph Structure

| Structural Factor | Effect                                            |
| ----------------- | ------------------------------------------------- |
| Large SCCs        | Increases condensation nodes; affects order depth |
| Many SCCs         | Deeper topological chain; more propagation steps  |
| Sparse graphs     | Fewer SCCs, simpler ordering, shorter CP          |
| Dense graphs      | More cycles and SCCs, heavier condensation phase  |
| High weights      | Increases critical path but not topology          |

### Key Insights

* Tarjan SCC reliably isolates cycles and prepares graph for DAG processing.
* Condensed graph ensures acyclic processing and deterministic ordering.
* Topological DP efficiently computes shortest and longest paths on DAGs.
* DAG-based SP outperforms Dijkstra when no cycles are present.
* Graph density strongly influences SCC count and overall depth.

---

## Conclusions

| Task Type              | Recommended Method          | Reason                        |
| ---------------------- | --------------------------- | ----------------------------- |
| Pure DAG               | Topological order + DP      | Fastest possible (O(V+E))     |
| Graph with cycles      | Tarjan SCC → Condensed DAG  | Removes cycles for safe SP    |
| Scheduling / workflows | Critical Path               | Computes max dependency chain |
| Large sparse graphs    | DAG SP                      | Minimal overhead              |
| Dense cyclic graphs    | SCC + Condensed graph first | Required to remove cycles     |

### Final Recommendations

* Always apply SCC detection for general directed graphs.
* Use condensed DAG for fast acyclic processing.
* Topological dynamic programming is preferred for SP when cycles are eliminated.
* Critical path analysis is appropriate for project planning and scheduling.

---

## Usage

Command:

```
java Main
```

Input format:

```json
{
  "nodes": 8,
  "edges": [
    {"u":0,"v":1,"w":3}
  ],
  "source": 0
}
```

---

## References

* Tarjan, R. (1972). Depth-first search and linear graph algorithms.
* Kahn, A. (1962). Topological sorting of large networks.
* Cormen, Leiserson, Rivest, Stein. Introduction to Algorithms (CLRS).
