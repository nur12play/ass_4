# Assignment 4 — Graph Algorithms Project
**Directed Graph Analysis: SCC, DAG Condensation, Topological Sorting, and Path Computation**
##  Goal of the Assignment
Реализовать систему обработки ориентированных графов, включающую:

* поиск **сильно связанных компонент (SCC)**
* построение **конденсационного графа (DAG)**
* **топологическую сортировку**
* вычисление **кратчайших путей** в DAG
* вычисление **критического (длиннейшего) пути** в DAG
* автоматическая обработка нескольких наборов данных

Проект моделирует **умную инфраструктуру города (Smart City)** и её подсистемы.

---

##  Implemented Algorithms

| Function                      | Algorithm                            |
| ----------------------------- | ------------------------------------ |
| Strongly Connected Components | **Tarjan’s Algorithm**               |
| DAG Condensation              | Component graph construction         |
| Topological Sort              | **Kahn’s Algorithm (BFS in-degree)** |
| Shortest Path in DAG          | Dynamic Programming                  |
| Longest Path in DAG           | Dynamic Programming (Critical Path)  |

---

##  Project Structure

```
project/
├── data/            # JSON datasets
├── results/         # Auto-saved program outputs
├── src/
│   ├── graph.scc/   # Tarjan SCC
│   ├── graph.topo/  # Kahn Topological Sort
│   ├── graph.dagsp/ # DAG shortest & longest path
│   └── Main.java    # Main controller
└── README.md
```

---

## Supported JSON Format

```json
{
  "directed": true,
  "nodes": 8,
  "edges": [
    { "u": 0, "v": 1, "w": 3 }
  ],
  "source": 0
}
```

If `source` is not provided → default `0`.

---

##  How to Run

### IDE (IntelliJ IDEA)

```
Run → Main
```

### Command line

```
javac Main.java
java Main
```

---

##  Program Output Example

```
=== Strongly Connected Components ===
Component 0: [3, 2, 1]
Component 1: [0]

=== Topological Order ===
[1, 0]

=== Shortest Distances from Component 1 ===
Node 0: 5.0
Node 1: 0.0

=== Critical Path Summary ===
Start: 1
End: 0
Length: 6.0
```

Реальные результаты сохраняются в папку `/results`.

---

##  Provided Datasets

### Small graphs

| File        | Description              |
| ----------- | ------------------------ |
| small1.json | Basic IoT logic          |
| small2.json | Smart traffic control    |
| small3.json | Security + access system |

### Medium graphs

| File         | Description                |
| ------------ | -------------------------- |
| medium1.json | Smart transit & monitoring |
| medium2.json | Camera + ticket systems    |
| medium3.json | Water & emergency system   |

### Large graphs

| File        | Description                |
| ----------- | -------------------------- |
| large1.json | Full city digital twin     |
| large2.json | Smart airport system       |
| large3.json | National railway & borders |

---

##  Results Folder

Все результаты выполнения сохраняются автоматически:

```
results/
├── small1.out.txt
├── medium1.out.txt
├── large1.out.txt
└── summary.log
```

---

##  Conclusion

В ходе работы:

* реализованы алгоритмы анализа графа
* обработаны **9 разнородных real-world datasets**
* выполнено:

  * выделение SCC
  * построение DAG
  * топологическая сортировка
  * расчёт кратчайших и длиннейших путей
* разработан модуль автоматической записи результатов
* система полностью соответствует требованиям задания

---

##  Author

| Field      | Value                                       |
| ---------- | ------------------------------------------- |
| Student    | Nurdaulet Omar                              |
| Course     | Algorithms / Data Structures / Graph Theory |
| University | Astana IT University                        |
| Year       | 2025                                        |


Ответь `да` — и я сформирую.
