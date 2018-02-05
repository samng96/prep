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

    public void BuildGraph(int nodes, int[][] edges) {
        // First build the nodes
        for (int i = 0; i < nodes; i++) {
            GraphNode node = new GraphNode(i);
            this.nodes.add(node);
        }

        // Now add the edges
        for (int i = 0; i < nodes; i++){
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
}