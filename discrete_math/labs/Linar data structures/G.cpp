
#include <fstream>
#include <deque>
using namespace std;
ifstream cin("queuemin2.in");
ofstream cout("queuemin2.out");
const int size_start = 1;
 
deque<int> q;
 
int get_min()
{
    return q.front();
}
 
void add_to_queue(int x)
{
    while (!q.empty() && q.back() > x)
        q.pop_back();
    q.push_back (x);
}
 
void get_from_queue(int x)
{
    if (!q.empty() && q.front() == x)
        q.pop_front();
}
 
int main()
{
    int s[1001];
    int n, m, k, a, b, c, t;
    long long sum = 0;
    cin >> n >> m >> k >> a >> b >> c;
    pair<int, int> pred_i;
    pair<int, int> pred_m;
    pred_m.second = 0;
    pred_m.first = 0;
    pred_i.second = 0;
    pred_i.first = 0;
 
    for (int i = 0; i < n; i++)
    {
        if (i >= m)
        {
            sum += get_min();
            if (i - m < k)
            {
                t = s[i - m];
                get_from_queue(t);
                pred_m.first = pred_m.second;
                pred_m.second = t;
            }
            else
            {
                t = (int)(a * pred_m.first + b * pred_m.second + c);
                get_from_queue(t);
                pred_m.first = pred_m.second;
                pred_m.second = t;
            }
        }
        if (i >= k)
        {
            t = (int)(a * pred_i.first + b * pred_i.second + c);
            add_to_queue(t);
            pred_i.first = pred_i.second;
            pred_i.second = t;
        }
        else
        {
            cin >> t;
            s[i] = t;
            add_to_queue(t);
            pred_i.first = pred_i.second;
            pred_i.second = t;
        }
    }
    sum += get_min();
 
    cout << sum;
 
    return 0;
}