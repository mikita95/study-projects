/*
struct Node {
    long long hash;
    int vertCnt;
    string s;
    Node * l;
    Node * r;
    Node() : l(NULL), r(NULL) {}
    Node(string s, Node * l, Node * r) : s(s), l(l), r(r) {
        vertCnt = 1;
        int lCnt = 0, rCnt = 0;
        if (l) {
            lCnt  = l->vertCnt;
            vertCnt += lCnt;
        }
        if (l) {
            rCnt  = r->vertCnt;
            vertCnt += rCnt;
        }
        hash = 0;
        if (l) hash += l->hash;
        hash *= prime[1];
        hash += s[0];
        if (r) {
            hash *= prime[rCnt];
            hash += r->hash;
        }

    }
    ~Node() {
        if (l) delete l;
        if (r) delete r;
    }
};
 */

public class Node {

    long hash;
    int count;
    String s;
    Node left;
    Node right;

    Node() {
        left = null;
        right = null;
    }

    Node(String s, Node left, Node right) {
        this.left = left;
        this.right = right;
        this.s = s;
        count = 1;
        int leftCount, rightCount = 0;
        if (left != null) {
            leftCount = left.count;
            count += leftCount;
        }
        if (left != null) {
            rightCount = right.count;
            count += rightCount;
        }
        hash = 0;
        if (left != null) hash += left.hash;
        hash *= HelpUtils.hashNode.getHash(1);
        hash += s.charAt(0);
        if (right != null) {
            hash *= HelpUtils.hashNode.getHash(rightCount);
            hash += right.hash;
        }

    }
}
