import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by samng on 2/5/18.
 */
public class Graph {
    ArrayList<GraphNode> nodes = new ArrayList<GraphNode>();
    ArrayList<GraphEdge> edges = new ArrayList<GraphEdge>();

    public class GraphNode {
        int id;

        public GraphNode(int id) {
            this.id = id;
        }
    }

    private class GraphEdge {
        GraphNode v1;
        GraphNode v2;

        public GraphEdge(GraphNode v1, GraphNode v2) {
            this.v1 = v1;
            this.v2 = v2;
        }
    }

    public GraphNode getNodeById(int id) {
        for (GraphNode node : nodes) {
            if (node.id == id) {
                return node;
            }
        }
        return null;
    }

    public GraphEdge getEdgeByIds(int v1, int v2) {
        for (GraphEdge edge : edges) {
            if ((edge.v1.id == v1 && edge.v2.id == v2) ||
                    (edge.v2.id == v1 && edge.v1.id == v2)) {
                return edge;
            }
        }
        return null;
    }

    public List<GraphEdge> getEdgesByNode(GraphNode node) {
        ArrayList<GraphEdge> result = new ArrayList<GraphEdge>();
        for (GraphEdge edge : edges) {
            if (edge.v1 == node || edge.v2 == node) {
                result.add(edge);
            }
        }
        return result;
    }

    // Note this doesn't find the shortest path! We can update if needed, but for our purposes,
    // this is currently only used for tree questions.
    public List<GraphNode> getPath(GraphNode n1, GraphNode n2) {
        ArrayList<GraphNode> path = new ArrayList<GraphNode>();

        path.add(n1);
        if (getPath(n1, n2, path)) {
            return path;
        }
        return null;
    }

    private boolean getPath(GraphNode n1, GraphNode n2, ArrayList<GraphNode> path) {
        if (getEdgeByIds(n1.id, n2.id) != null) {
            path.add(n2);
            return true;
        } else {
            // If we don't have an edge from n1 to n2, we put n2 on the path and we traverse all our children.
            for (GraphEdge edge : getEdgesByNode(n1)) {
                GraphNode candidateNode = edge.v1 == n1 ? edge.v2 : edge.v1;
                if (path.contains(candidateNode)) {
                    continue;
                }
                path.add(candidateNode);
                if (getPath(candidateNode, n2, path)) {
                    return true;
                }

                // If we didn't lead to a complete path, remove that child from the path.
                path.remove(candidateNode);
            }
        }
        return false;
    }

    public void BuildGraph(int nodes, int[][] edges) {
        // First build the nodes
        for (int i = 0; i < nodes; i++) {
            GraphNode node = new GraphNode(i);
            this.nodes.add(node);
        }

        // Now add the edges
        for (int i = 0; i < nodes; i++){
            if (edges[i] == null) {
                continue;
            }

            GraphNode node = getNodeById(i);
            for (int j = 0; j < edges[i].length; j++) {
                if (getEdgeByIds(i, edges[i][j]) == null) {
                    this.edges.add(new GraphEdge(node, getNodeById(edges[i][j])));
                }
            }
        }
    }

    public void PrintGraph() {
        out.println("nodes: " + nodes.size() + " edges: " + edges.size());
        for (GraphEdge edge : edges) {
            out.println("e: " + edge.v1.id + " " + edge.v2.id);
        }
    }

    // Count the number of gardens - a garden is cycle of 4 in the graph.
    public int CountGardens(int nodes, int[][] edges) {
        this.BuildGraph(nodes, edges);
        this.PrintGraph();

        int result = 0;
        // The naive approach is to keep a path of where we've come, and check each new edge we're considering
        // against the node list, and ensure that we can draw the edges back home.
        for (GraphNode node : this.nodes) {
            result += countGardensFromNode(node, new int[] { node.id, -1, -1, -1 }, 0);
        }

        // We divide the result by 8 because each node will count the same garden, plus we go in both directions.
        return result / 8;
    }

    private int countGardensFromNode(GraphNode node, int[] path, int level) {
        int result = 0;
        // For each edge from this node, walk it if we haven't visited it before.
        for (GraphEdge edge : getEdgesByNode(node)) {
            GraphNode candidateNode = edge.v1 == node ? edge.v2 : edge.v1;
            // If we're at the last edge, check to make sure we've got an edge to the first node.
            if (level == 3) {
                if (candidateNode.id == path[0]) {
                    return 1;
                }
                continue;
            } else {

                boolean seenNode = false;
                for (int i = 0; i < level; i++) {
                    if (path[i] == candidateNode.id) {
                        seenNode = true;
                        break;
                    }
                }
                if (seenNode) {
                    continue;
                }
                // We haven't seen this node yet. Add it to our path and traverse.
                path[level + 1] = candidateNode.id;
                result += countGardensFromNode(candidateNode, path, level + 1);
            }
        }

        return result;
    }

    // Buggy robot - once during execution, robot can take random path, and can backtrack.
    // Print how many possible end locations, followed by the list of end locations.
    public void BuggyRobot(int nodes, int[][] edges, int[][] instructions) {
        BuildGraph(nodes, edges);
        PrintGraph();

        int result = 0;
        List<GraphNode> results = new ArrayList<GraphNode>();

        runBuggyRobot(getNodeById(0), 0, instructions, false, results);

        out.println("number of resuls: " + results.size());
        for (GraphNode node : results) {
            out.print(node.id + " ");
        }
        out.println();
    }

    private void runBuggyRobot(GraphNode currentNode, int instructionIndex, int[][] instructions, boolean hasDeviated, List<GraphNode> results) {
        runBuggyRobotIfNotDeviated(currentNode, instructionIndex, instructions, hasDeviated, results);

        if (instructionIndex < instructions.length) {
            // Now run an instruction if applicable.
            if (instructions[instructionIndex][0] == currentNode.id) {
                // This instruction is applicable, so move it.
                currentNode = getNodeById(instructions[instructionIndex][1]);
            }
            runBuggyRobot(currentNode, instructionIndex + 1, instructions, hasDeviated, results);
        }

        if (instructionIndex == instructions.length - 1) {
            // If we just ran the final instruction, deviate if necessary, then count up our end position.
            runBuggyRobotIfNotDeviated(currentNode, instructionIndex, instructions, hasDeviated, results);

            if (!results.contains(currentNode)) {
                results.add(currentNode);
            }
        }
    }

    private void runBuggyRobotIfNotDeviated(GraphNode currentNode, int instructionIndex, int[][] instructions, boolean hasDeviated, List<GraphNode> results) {
        if (!hasDeviated) {
            // If we haven't deviated yet, deviate before running our current instruction set.
            for (GraphEdge edge : getEdgesByNode(currentNode)) {
                GraphNode candidateNode = edge.v1 == currentNode ? edge.v2 : edge.v1;
                runBuggyRobot(candidateNode, instructionIndex, instructions, true, results);
            }
        }
    }

    // N Div Tree - in the given tree, find the number of paths (u,v) such that no edges (a,b)
    // in path (u,v) have the quality that a divides b or b divides a.
    public int CountNDivTree(int nodes, int[][] edges) {
        this.BuildGraph(nodes, edges);
        this.PrintGraph();

        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < nodes; i++) {
            for (int j = i + 1; j < nodes; j++) {
                runNDivTree(getNodeById(i), getNodeById(j), results);
            }
        }

        for (String s : results) {
            out.println(s);
        }
        return results.size();
    }

    private int runNDivTree(GraphNode n1, GraphNode n2, List<String> results) {
        List<GraphNode> path = getPath(n1, n2);
        if (path == null) {
            return 0;
        }

        GraphNode prev = n1;
        // Indicies start at 0, so add one to everything.
        for (int i = 1; i < path.size(); i++) {
            if ((prev.id + 1) % (path.get(i).id + 1) == 0 || (path.get(i).id + 1) % (prev.id + 1) == 0) {
                return 0;
            }
            prev = path.get(i);
        }
        results.add(n1.id + " " + n2.id);
        return 1;
    }
}
