#include <fstream>
 
using namespace std;
ifstream cin("stack2.in");
ofstream cout("stack2.out");
 
struct Node {
    int value;
    Node* next;
};
 
Node* head;
 
void push(int x)
{
    Node* tmp = new Node;
    tmp->value = x;
    tmp->next = head;
    head = tmp;
}
 
int pop()
{
    Node* tmp = head;
    head = head->next;
    int x = tmp->value;
    delete tmp;
    return x;
}
 
int main()
{
    char ch;
    int m;
    int k;
    cin >> m;
    for (int i = 0; i < m; i++)
    {
        cin >> ch;
        if (ch == '+')
        {
            cin >> k;
            push(k);
        }
        if (ch == '-')
            cout << pop() << endl;
    }
    return 0;
}