#ifndef GRAPH_H
#define GRAPH_H

#include <vector>
#include <memory>
#include <fstream>
#include <sstream>
#include <algorithm>
#include <string>
#include <stack>
#include <functional>

template<typename NodePayLoad, typename EdgePayLoad>
class Edge;

template<typename NodePayLoad, typename EdgePayLoad>
class Graph;

template<typename NodePayLoad, typename EdgePayLoad>
class Node {
    friend class Edge<NodePayLoad, EdgePayLoad>;

    friend class Graph<NodePayLoad, EdgePayLoad>;

private:
    size_t id;
    std::shared_ptr<std::vector<std::shared_ptr<Edge<NodePayLoad, EdgePayLoad>>>> adj;
    std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> previous;
    NodePayLoad data;
public:

    // constructor
    Node(size_t, NodePayLoad);

    // returns id of this node
    inline size_t getId() const {
        return id;
    }

    // returns vector of edges with start point in this node
    inline std::shared_ptr<std::shared_ptr<std::vector<Edge<NodePayLoad, EdgePayLoad>>>> getAdj() const {
        return adj;
    }

    // returns previous node
    inline std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> getPrevious() const {
        return previous;
    }

    // returns data in node
    inline NodePayLoad &getData() {
        return data;
    }

    // distructor
    virtual ~Node() {
    }
};

template<typename NodePayLoad, typename EdgePayLoad>
class Edge {
    friend class Graph<NodePayLoad, EdgePayLoad>;

private:
    std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> from;
    std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> to;
    EdgePayLoad weight;
public:
    //constructor
    Edge(std::shared_ptr<Node<NodePayLoad, EdgePayLoad>>, std::shared_ptr<Node<NodePayLoad, EdgePayLoad>>, EdgePayLoad);

    // distructor
    virtual ~Edge() {
    }

    // returns start point of edge
    inline std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> getFrom() const {
        return from;
    }

    // returns end point of edge
    inline std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> getTo() const {
        return to;
    }

    // return weight of edge
    inline EdgePayLoad getWeight() const {
        return weight;
    }

    // checks connectivity of two nodes by this edge
    inline bool connects(std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> node1, std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> node2) const {
        return ((node1 == this->from && node2 == this->to) || (node1 == this->to && node2 == this->from));
    }
};

template<typename NodePayLoad, typename EdgePayLoad>
class Graph {
    friend class Node<NodePayLoad, EdgePayLoad>;

    friend class Edge<NodePayLoad, EdgePayLoad>;

public:
    typedef std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> NodeHandle;
    typedef std::shared_ptr<Edge<NodePayLoad, EdgePayLoad>> EdgeHandle;
    typedef std::function<void(NodeHandle const &)> NodeVisitor;
    typedef std::function<void(EdgeHandle const &)> EdgeVisitor;
private:
    std::vector<NodeHandle> vertex;
    std::vector<EdgeHandle> edges;
public:
    Graph() {
    }

    ~Graph() {
    }

    /*
        loads graph from file in format:
            nodes_count edges_count
            node_data_1 node_data_2 ... node_data_nodes_count
            node_index_1 node_index_2 edge_weight
            ... // edges_count - 1 strings of description of edges
    */
    void loadFromFile(std::string const &filename);

    // returns NodeHandle by id in vector of nodes
    inline NodeHandle getNodeHandleById(size_t id) const {
        return vertex[id];
    }

    // saves graph to file in format like in loading function
    void saveToFile(std::string const &filename);

    // adds node to graph
    NodeHandle addNode(NodePayLoad const &);

    // adds edge to graph with ends in a and b
    void addEdge(NodeHandle const &a, NodeHandle const &b, EdgePayLoad const &);

    // does visitor for each node in graph
    void forEachNode(std::function<void(NodeHandle const &)> const &visitor) const;

    // returns count of nodes in graph
    size_t getNodesCount() const;

    // does visitor for each edge than contains source node
    void forEachEdge(NodeHandle const &source, EdgeVisitor const &visitor);

    // returns the second end of edge
    NodeHandle move(NodeHandle const &origin, EdgeHandle const &edge);

    // returns data in node
    NodePayLoad &operator[](NodeHandle const &node);

    // run dfs
    void dfs(NodeVisitor const &startNode, NodeVisitor const &endNode, NodeVisitor const &discoverNode);
};

#endif // GRAPH_H
