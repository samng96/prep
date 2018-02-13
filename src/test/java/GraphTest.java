import org.junit.Test;

import static java.lang.System.out;

/**
 * Created by samng on 2/5/18.
 */
public class GraphTest {
    @Test
    public void Gardens() {
        Graph graph = new Graph();

        out.println("num gardens: " + graph.CountGardens(8, new int[][] {{1, 3, 4}, {0, 2, 5}, {1, 3, 6}, {0, 2, 7}, {0, 5, 7}, {1, 4, 6}, {2, 5, 7}, {3, 4, 6}}));
    }

    @Test
    public void BuggyRobotTest() {
        Graph graph = new Graph();

        graph.BuggyRobot(5, new int[][] {{1, 3}, {2, 3}, {3}, {}, {}}, new int[][] {{0, 1}, {2, 3}, {1, 2}});
    }

    @Test
    public void NDivTreeTest() {
        Graph graph = new Graph();

        graph.CountNDivTree(6, new int[][] {{1, 3}, {2}, {}, {4, 5}, {}, {}});
    }

    @Test
    public void testDijkstra() {
        Graph graph = new Graph();
        graph.BuildGraphWithWeights(9, new int[][][] {
                {{1, 4}, {7, 8}},
                {{2, 8}, {7, 11}},
                {{3, 7}, {5, 4}, {8, 2}},
                {{4, 9}, {5, 14}},
                {{5, 10}},
                {{6, 2}},
                {{7, 1}, {8, 6}},
                {{8, 7}},
                {}});
        graph.PrintGraph();

        out.println("shortest path from 0 to 5: " + graph.getShortestPath(0, 5));
    }
}
