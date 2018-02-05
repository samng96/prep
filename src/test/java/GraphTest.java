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
}
