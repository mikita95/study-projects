#include "gtest/gtest.h"
#include "graph.cpp"

namespace {
    const int NODE_COUNT = 500;
    const int INF = -1000;
    std::vector<int> num;
    Graph<int, int>::NodeHandle currentNode;
    Graph<int, int> graph;
    int currentColor;
    std::vector<int> colors;
    bool flag;

    int myrand() {
        int val = rand() - RAND_MAX / 2;
        if (val != 0)
            return val;
        else
            return 1;
    }

    void edgeVisitor(Graph<int, int>::EdgeHandle const &edge) {
        num.push_back(graph[edge->getFrom() == currentNode ? edge->getTo() : edge->getFrom()]);
    }

    bool myCheck() {
        if (num.size() != NODE_COUNT - 1) {
            return false;
        }
        for (int i = 0; i < NODE_COUNT; i++) {
            int cnt = 0;
            for (int k = 0; k < (int) num.size(); k++) {
                if (num[k] == i)
                    cnt++;
            }
            if (currentNode != graph.getNodeHandleById(i) && cnt == 0) {
                return false;
            }
        }
        return true;
    }

    void fullGraphVisitor(Graph<int, int>::NodeHandle const &source) {
        num.clear();
        currentNode = source;
        graph.forEachEdge(source, edgeVisitor);
        EXPECT_TRUE(myCheck());
    }

    void makeFullGraph() {
        graph = Graph<int, int>();
        for (int i = 0; i < NODE_COUNT; i++) {
            graph.addNode(i);
        }
        for (int i = 0; i < NODE_COUNT; i++) {
            for (int j = 0; j < NODE_COUNT; j++) {
                graph.addEdge(graph.getNodeHandleById(i), graph.getNodeHandleById(j), 0);
            }
        }
    }

    void edgeVisitor1(Graph<int, int>::EdgeHandle const &edge) {
        EXPECT_EQ(edge->getFrom(), graph.move(edge->getTo(), edge));
        EXPECT_EQ(edge->getTo(), graph.move(edge->getFrom(), edge));
    }

    void nodeVisitor(Graph<int, int>::NodeHandle const &source) {
        graph.forEachEdge(source, edgeVisitor1);
    }

    void startNode(Graph<int, int>::NodeHandle const &source) {
        colors[source->getId()] == INF ? (colors[source->getId()] = currentColor) : (currentColor = colors[source->getId()]);
    }

    void discoverNode(Graph<int, int>::NodeHandle const &source) {
        if (colors[source->getId()] == INF) {
            colors[source->getId()] = (currentColor + 1) % 2;
        }
        else if (colors[source->getId()] == currentColor) {
            flag = false;
        }
    }

    void endNode(Graph<int, int>::NodeHandle const &source) {
        currentColor = INF;
    }

    bool isSuccess() {
        currentColor = 0;
        for (size_t i = 0; i < graph.getNodesCount(); i++) {
            colors.push_back(INF);
        }
        flag = true;
        graph.dfs(startNode, endNode, discoverNode);
        return flag;
    }
}

TEST(correctness, check_count) {
    int cnt = 100000;
    graph = Graph<int, int>();
    for (int i = 0; i < cnt; i++)
        graph.addNode(myrand());
    EXPECT_EQ(cnt, (int) graph.getNodesCount());
}

TEST(correctness, make_full_graph) {
    makeFullGraph();
    EXPECT_EQ(NODE_COUNT, (int) graph.getNodesCount());
    graph.forEachNode(fullGraphVisitor);
}

TEST(correctness, work_with_files) {
    makeFullGraph();
    graph.saveToFile("graph_test.txt");
    graph = Graph<int, int>();
    graph.loadFromFile("graph_test.txt");
    EXPECT_EQ(NODE_COUNT, (int) graph.getNodesCount());
    graph.forEachNode(fullGraphVisitor);
}

TEST(correctness, check_moving) {
    makeFullGraph();
    graph.forEachNode(nodeVisitor);
}

TEST(correctness, dfs_test_1) {
    graph = Graph<int, int>();
    Graph<int, int>::NodeHandle current;
    Graph<int, int>::NodeHandle prev;
    prev = graph.addNode(0);
    for (int i = 0; i < NODE_COUNT - 1; i++) {
        current = graph.addNode(0);
        graph.addEdge(current, prev, 0);
        prev = current;
    }
    EXPECT_EQ(true, isSuccess());
}

TEST(correctness, dfs_test_2) {
    graph = Graph<int, int>();
    Graph<int, int>::NodeHandle init;
    Graph<int, int>::NodeHandle current;
    Graph<int, int>::NodeHandle prev;
    init = graph.addNode(0);
    prev = init;
    for (int i = 0; i < NODE_COUNT - 1; i++) {
        current = graph.addNode(0);
        graph.addEdge(current, prev, 0);
        prev = current;
    }
    graph.addEdge(prev, init, 0);
    EXPECT_EQ(true, isSuccess());
}

TEST(correctness, dfs_test_3) {
    graph = Graph<int, int>();
    Graph<int, int>::NodeHandle init;
    Graph<int, int>::NodeHandle current;
    Graph<int, int>::NodeHandle prev;
    init = graph.addNode(0);
    prev = init;
    for (int i = 0; i < NODE_COUNT; i++) {
        current = graph.addNode(0);
        graph.addEdge(current, prev, 0);
        prev = current;
    }
    graph.addEdge(prev, init, 0);
    EXPECT_EQ(true, !isSuccess());
}



