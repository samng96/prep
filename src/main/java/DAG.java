import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

public class DAG {
    public class DAGNode {
        public int id;

        public DAGNode(int id) {
            this.id = id;
        }
    }

    public class DAGEdge {
        public DAGNode source;
        public DAGNode destination;
        public int weight;

        public DAGEdge(DAGNode source, DAGNode destination, int weight) {
            this.source = source;
            this.destination = destination;
            this.weight = weight;
        }
    }

    public ArrayList<DAGNode> nodes;
    public ArrayList<DAGEdge> edges;

    public DAGNode getNodeById(int id) {
        for (DAGNode node : nodes) {
            if (node.id == id) {
                return node;
            }
        }
        return null;
    }

    public DAGEdge getEdgeByNodes(DAGNode source, DAGNode destination) {
        for (DAGEdge edge : edges) {
            if (edge.source == source && edge.destination == destination) {
                return edge;
            }
        }
        return null;
    }

    public List<DAGEdge> getEdgesByNode(DAGNode node) {
        List<DAGEdge> result = new ArrayList<DAGEdge>();

        for (DAGEdge edge : edges) {
            if (edge.source == node) {
                result.add(edge);
            }
        }
        return result;
    }

    public void buildDAG(int nodes, int[][][] edges) {
        for (int i = 0; i < nodes; i++) {
            this.nodes.add(new DAGNode(i));
        }

        for (int i = 0; i < nodes; i++) {
            if (edges[i] == null) { continue; }

            for (int j = 0; j < edges[i].length; j++) {
                assert edges[i][j].length == 2;
                DAGNode source = getNodeById(i);
                DAGNode destination = getNodeById(edges[i][j][0]);

                assert getEdgeByNodes(source, destination) == null;
                this.edges.add(new DAGEdge(source, destination, edges[i][j][1]));
            }
        }
    }

    public void printDAG() {
        out.println("nodes: " + nodes.size() + " edges: " + edges.size());
        for (DAGEdge edge : edges) {
            out.println("e: " + edge.source.id + " " + edge.destination.id + " w:" + edge.weight);
        }
    }

}
