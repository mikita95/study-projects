#include <queue>
#include <vector>
#include <fstream>
#include <sstream>
#include <string.h>
#include <stdlib.h>
#include <iostream>
 
using namespace std;
ifstream fin("quack.in");
ofstream fout("quack.out");
vector<string> mas;
queue<unsigned short> q;
vector< pair<string, int> > labels;
 
unsigned short reg[27];
int main()
{
    string s;
    int cf = 0;
    while (fin >> s)
    {
        if (!s.empty())
        {
            mas.push_back(s);
            if (s[0] == ':')
            {
                labels.push_back(make_pair(s, cf));
            }
            cf++;
        }
    }
 
    cf = 0;
    while (cf < mas.size())
    {
        if (mas[cf][0] == '+')
        {
            unsigned short x = q.front();
            q.pop();
            unsigned short y = q.front();
            q.pop();
            q.push(x + y);
        } else
        if (mas[cf][0] == '-')
        {
            unsigned short x = q.front();
            q.pop();
            unsigned short y = q.front();
            q.pop();
            q.push(x - y);
        } else
        if (mas[cf][0] == '*')
        {
            unsigned short x = q.front();
            q.pop();
            unsigned short y = q.front();
            q.pop();
            q.push(x * y);
        } else
        if (mas[cf][0] == '/')
        {
            unsigned short x = q.front();
            q.pop();
            unsigned short y = q.front();
            q.pop();
            if (x == 0 && y == 0)
                q.push(0);
            else
                q.push(x / y);
        } else
        if (mas[cf][0] == '%')
        {
            unsigned short x = q.front();
            q.pop();
            unsigned short y = q.front();
            q.pop();
            if (x == 0 && y == 0)
                q.push(0);
            else
                q.push(x % y);
        } else
        if (mas[cf][0] == '>')
        {
            unsigned short x = q.front();
            q.pop();
            reg[mas[cf][1] - 'a'] = x;
        } else
        if (mas[cf][0] == '<')
        {
            q.push(reg[mas[cf][1] - 'a']);
        } else
        if (mas[cf][0] == 'P' && mas[cf].length() == 2)
        {
            fout << reg[mas[cf][1] - 'a'] << endl;
        } else
        if (mas[cf][0] == 'P')
        {
            unsigned short x = q.front();
            q.pop();
            fout << x << endl;
        } else
        if (mas[cf][0] == 'C' && mas[cf].length() == 2)
        {
            char x = reg[mas[cf][1] - 'a'] % 256;
            fout << x;
        } else
        if (mas[cf][0] == 'C' )
        {
            char x = q.front() % 256;
            q.pop();
            fout << x;
        } else
        if (mas[cf][0] == ':')
        {
            cf++;
            continue;
        } else
        if (mas[cf][0] == 'J')
        {
            int k;
            string st;
            st = mas[cf].substr(1, mas[cf].length() - 1);
            for (int i = 0; i < labels.size(); i++)
                if (":" + st == labels[i].first )
                {
                    k = labels[i].second;
                    break;
                }
            cf = k;
            continue;
        } else
        if (mas[cf][0] == 'Z')
        {
            if (reg[mas[cf][1] - 'a'] == 0)
            {
                int k;
                string st;
                st = mas[cf].substr(2, mas[cf].length() - 2);
                for (int i = 0; i < labels.size(); i++)
                    if (':' + st == labels[i].first )
                    {
                        k = labels[i].second;
                        break;
                    }
                cf = k;
                continue;
            }
        } else
        if (mas[cf][0] == 'E')
        {
            if (reg[mas[cf][1] - 'a'] == reg[mas[cf][2] - 'a'])
            {
                int k;
                string st;
                st = mas[cf].substr(3, mas[cf].length() - 3);
                for (int i = 0; i < labels.size(); i++)
                    if (':' + st == labels[i].first )
                    {
                        k = labels[i].second;
                        break;
                    }
                cf = k;
                continue;
            }
        } else
        if (mas[cf][0] == 'G')
        {
            if (reg[mas[cf][1] - 'a'] > reg[mas[cf][2] - 'a'])
            {
                int k;
                string st;
                st = mas[cf].substr(3, mas[cf].length() - 3);
                for (int i = 0; i < labels.size(); i++)
                    if (':' + st == labels[i].first )
                    {
                        k = labels[i].second;
                        break;
                    }
                cf = k;
                continue;
            }
        } else
        if (mas[cf][0] == 'Q')
        {
            break;
        } else
        {
            unsigned short x = 0;
            int k;
            if (mas[cf][0] != '-')
            {
                for (int i = mas[cf].length() - 1; i >= 0; i--)
                {
                    unsigned short t = mas[cf][i] - '0';
 
                    for (k = 0; k < mas[cf].length() - i - 1; k++)
                        t = t * 10;
                    x += t;
                }
            }
            else
            {
            for (int i = mas[cf].length() - 1; i > 0; i--)
                {
                    unsigned short t = mas[cf][i] - '0';
                    for (k = 0; k < mas[cf].length() - i - 1; k++)
                        t = t * 10;
                    x += t;
                }
            x = -x;
            }
            q.push(x);
 
        }
        cf++;
    }
 
    return 0;
}