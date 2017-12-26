#include "graph.h"

template<typename NodePayLoad, typename EdgePayLoad>
Node<NodePayLoad, EdgePayLoad>::Node(size_t id, NodePayLoad data) {
    this->id = id;
    this->data = data;
    adj = std::make_shared<std::vector<std::shared_ptr<Edge<NodePayLoad, EdgePayLoad>>>>();
    previous = std::shared_ptr<Node<NodePayLoad, EdgePayLoad>>(std::nullptr_t());
}

template<typename NodePayLoad, typename EdgePayLoad>
Edge<NodePayLoad, EdgePayLoad>::Edge(std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> from, std::shared_ptr<Node<NodePayLoad, EdgePayLoad>> to, EdgePayLoad weight) {
    this->from = from;
    this->to = to;
    this->weight = weight;
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::loadFromFile(std::string const &filename) {
    std::ifstream fin(filename);
    size_t n = 0, m = 0;
    fin >> n >> m;
    NodePayLoad data;

    for (size_t i = 0; i < n; i++) {
        fin >> data;
        this->addNode(data);
    }
    int x = 0, y = 0;
    EdgePayLoad weight;
    for (size_t i = 0; i < m; i++) {
        fin >> x >> y >> weight;
        this->addEdge(vertex[x - 1], vertex[y - 1], weight);
    }
    fin.close();
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::saveToFile(std::string const &filename) {
    std::ofstream fout(filename);
    fout << vertex.size() << " " << edges.size() << std::endl;
    for (auto &node : vertex) {
        fout << node->getData() << " ";
    }
    fout << std::endl;
    for (auto &edge : edges) {
        fout << edge->from->getId() + 1 << " " << edge->to->getId() + 1 << " " << edge->getWeight() << std::endl;
    }
    fout.close();
}

template<typename NodePayLoad, typename EdgePayLoad>
typename Graph<NodePayLoad, EdgePayLoad>::NodeHandle Graph<NodePayLoad, EdgePayLoad>::addNode(NodePayLoad const &data) {
    vertex.push_back(std::make_shared<Node<NodePayLoad, EdgePayLoad>>(vertex.size(), data));
    return vertex[vertex.size() - 1];
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::addEdge(NodeHandle const &a, NodeHandle const &b, EdgePayLoad const &weight) {
    if (a == b)
        return;
    for (auto &edge : (*(a->adj))) {
        if (edge->to == b)
            return;
    }
    std::shared_ptr<Edge<NodePayLoad, EdgePayLoad>> edge1 = std::make_shared<Edge<NodePayLoad, EdgePayLoad>>(a, b, weight);
    std::shared_ptr<Edge<NodePayLoad, EdgePayLoad>> edge2 = std::make_shared<Edge<NodePayLoad, EdgePayLoad>>(b, a, weight);
    edges.push_back(edge1);
    a->adj->push_back(edge1);
    b->adj->push_back(edge2);
}

template<typename NodePayLoad, typename EdgePayLoad>
size_t Graph<NodePayLoad, EdgePayLoad>::getNodesCount() const {
    return vertex.size();
}

template<typename NodePayLoad, typename EdgePayLoad>
NodePayLoad &Graph<NodePayLoad, EdgePayLoad>::operator[](NodeHandle const &node) {
    return node->getData();
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::forEachNode(std::function<void(NodeHandle const &)> const &visitor) const {
    for (auto &node : vertex) {
        visitor(node);
    }
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::forEachEdge(NodeHandle const &source, EdgeVisitor const &visitor) {
    for (auto &edge : (*source->adj)) {
        visitor(edge);
    }
}

template<typename NodePayLoad, typename EdgePayLoad>
typename Graph<NodePayLoad, EdgePayLoad>::NodeHandle Graph<NodePayLoad, EdgePayLoad>::move(NodeHandle const &origin, EdgeHandle const &edge) {
    return edge->from == origin ? edge->to : edge->from;
}

template<typename NodePayLoad, typename EdgePayLoad>
void Graph<NodePayLoad, EdgePayLoad>::dfs(NodeVisitor const &startNode, NodeVisitor const &endNode, NodeVisitor const &discoverNode) {
    std::vector<bool> used(vertex.size());
    for (auto &node : vertex)
        used.push_back(false);
    NodeHandle currentNode;
    for (auto &node : vertex) {
        if (!used[node->getId()]) {
            std::stack<NodeHandle> stack;
            stack.push(node);
            while (!stack.empty()) {
                currentNode = stack.top();
                stack.pop();
                startNode(currentNode);
                for (auto i = vertex[currentNode->getId()]->adj->begin(); i != vertex[currentNode->getId()]->adj->end(); i++) {
                    discoverNode((*i)->to);
                    if (!used[(*i)->to->getId()]) {
                        used[(*i)->to->getId()] = true;
                        stack.push((*i)->to);
                    }
                }
                endNode(currentNode);
            }
        }
    }

}
