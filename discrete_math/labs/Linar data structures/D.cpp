#include <fstream>
 
using namespace std;
ifstream cin("queue2.in");
ofstream cout("queue2.out");
 
struct Node {
    int value;
    Node* next;
};
 
Node* head = NULL;
Node* tail = NULL;
 
void push(int x)
{
   Node* tmt = new Node;
   tmt->value = x;
   tmt->next = NULL;
   if (head == NULL)
   {
       head = tmt;
       tail = head;
   }
   else
   {
       tail->next = tmt;
       tail = tmt;
   }
}
 
int pop()
{
    int x = head->value;
    Node* tmt = head->next;
    delete head;
    if (!tmt)
    {
        head = tail = NULL;
    }
    else
        head = tmt;
    return x;
}
 
int main()
{
    char ch;
    int m, k;
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