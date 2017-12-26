#include <iostream>
#include "list.h"
#include "iterator.h"
#include "reverse_iterator.h"

void testContructor1() {
   list l;
}

void testContructor2() {
   list l;
   list l1(l),
           l2 = l;
}

void selfAssigment() {
   list l;
   l = l;
   l.push_back(1);
   l = l;
}

void testPushBack() {
   list l;
   for (int i = 1; i <= 10; i++)
       l.push_back(i);
}

void testPushPop() {
   list l;
   for (int i = 1; i <= 10; i++)
       l.push_back(i);
   for (int i = 1; i <= 10; i++)
       l.pop_back();
   if (l.begin() != l.end())
       std::cout << "error\n";
}

void testPushFront() {
   list l;
   for (int i = 1; i <= 10; i++)
       l.push_front(i);
}

void testPushPopFront() {
   list l;
   for (int i = 1; i <= 10; i++)
       l.push_front(i);
   for (int i = 1; i <= 10; i++)
       l.pop_front();
   if (l.begin() != l.end())
       std::cout << "error\n";
}

void testEmpty() {
   list l;
   if (!l.empty())
       std::cout << "error\n";
   //l.pop_back();
   if (!l.empty())
       std::cout << "error\n";
   for (int i = 1; i <= 10; i++)
       l.push_back(i);
   if (l.empty())
       std::cout << "error\n";
   for (int i = 1; i <= 10; i++)
       l.pop_back();
   if (!l.empty())
       std::cout << "error\n";

   if (!l.empty())
       std::cout << "error\n";

  // l.pop_front();
   if (!l.empty())
       std::cout << "error\n";
   for (int i = 1; i <= 10; i++)
       l.push_front(i);
   if (l.empty())
       std::cout << "error\n";
   for (int i = 1; i <= 10; i++)
       l.pop_front();
   if (!l.empty())
       std::cout << "error\n";
   //l.pop_front();
   if (!l.empty())
       std::cout << "error\n";
}

void testBack() {
   list l;
   const int N = 10;
   int array[] = {123, 41, 81, 85, 41, 52, 62, 98, 13, 78};
   for (int i = 0; i < N; i++)
       l.push_back(array[i]);
   for (int i = N - 1; i >= 0; i--) {
       if (l.back() != array[i])
           std::cout << "error\n";
       l.pop_back();
   }
}

void testFront() {
   list l;
   const int N = 10;
   int array[] = {123, 41, 81, 85, 41, 52, 62, 98, 13, 78};
   for (int i = 0; i < N; i++)
       l.push_front(array[i]);
   for (int i = N - 1; i >= 0; i--) {
       if (l.front() != array[i])
           std::cout << "error\n";
       l.pop_front();
   }
}

void testIterator() {
   list l;
   const int N = 10;
   for (int i = 1; i <= N; i++)
       l.push_back(i);
   std::cout << "\tlinear order:\n\t";
   for (iterator it = l.begin(); it != l.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
   for (int i = 1; i <= N; i++)
       l.pop_front();

   for (int i = 1; i <= N; i++)
       l.push_front(i);
   std::cout << "\treverse order:\n\t";
   for (iterator it = l.begin(); it != l.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
}

void testReverseIterator() {
   list l;
   const int N = 10;
   for (int i = 1; i <= N; i++)
       l.push_back(i);
   std::cout << "\treverse order:\n\t";
   for (reverse_iterator it = l.rbegin(); it != l.rend(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
   for (int i = 1; i <= N; i++)
       l.pop_front();

   for (int i = 1; i <= N; i++)
       l.push_front(i);
   std::cout << "\tlinear order:\n\t";
   for (reverse_iterator it = l.rbegin(); it != l.rend(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
}

void testInsert() {
   list l;
   const int N = 10, M = 10;
   int i;
   for (i = 1; i <= N; i++)
       l.push_back((i + 1) * M);
   i = 1;
   for (iterator it = l.begin(); it != l.end(); it++, i++) {
       l.insert(it, i * M + M / 2);
   }
   std::cout << "\tlinear order:\n\t";
   for (iterator it = l.begin(); it != l.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
}

void spliceTest() {
   const int N = 10, A = 3, B = 7;
   list l1, l2;
   for (int i = 1 ; i <= N; i++) {
       if (i < A || i >= B)
           l1.push_back(i);
   }
   for (int i = 1 ; i <= N; i++) {
       l2.push_back(i);
   }

   iterator itA = l2.begin(), itB = l2.begin(), pos = l1.begin();
   for (; *itA != A && itA != l2.end(); itA++);
   for (; *itB != B && itB != l2.end(); itB++);
   for (; *pos != B && pos != l1.end(); pos++);
   l1.splice(++pos, l2, itA, itB);

   std::cout << "\tlinear order:\n\t";
   for (iterator it = l1.begin(); it != l1.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
   std::cout << "\tlinear order:\n\t";
   for (iterator it = l2.begin(); it != l2.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
}

void testErase() {
   list l;
   const int N = 10;
   for (int i = 1; i <= N; i++)
       l.push_back(i);
   iterator it = l.begin();
   for (int i = 1; i <= N; i++)
   {
       iterator tmp = it;
       it++;
       if (i & 1)
           l.erase(tmp);

   }
   std::cout << "\tonly even:\n\t";
   for (iterator it = l.begin(); it != l.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
   for (int i = 1; i <= N / 2; i++)
       l.pop_back();

   for (int i = 1; i <= N; i++)
       l.push_back(i);
   it = l.begin();
   for (int i = 1; i <= N; i++) {
       iterator tmp = it;
       it++;
       if (!(i & 1))
           l.erase(tmp);
   }
   std::cout << "\tonly odds:\n\t";
   for (iterator it = l.begin(); it != l.end(); it++)
       std::cout << *it << " ";
   std::cout << "\n";
}

void copyTest() {
   list l1, l2;
   l1.push_back(1);
   l2.push_back(2);
   l1 = l2;

   if (l1.back() != l2.back())
       std::cout << "error\n";

   l2.push_back(4);
   if (l2.back() != 4)
       std::cout << "error\n";
   if (l1.back() != 2)
       std::cout << "error\n";

   l1.push_back(5);
   if (l1.back() != 5)
       std::cout << "error\n";
   if (l2.back() != 4)
       std::cout << "error\n";
}

int main()
{
   std::cout << "Testing empty constructor...\n";
   testContructor1();
   std::cout << "Testing copy constructor...\n";
   testContructor2();
   std::cout << "Testing push_back...\n";
   testPushBack();
   std::cout << "Testing push/pop back...\n";
   testPushPop();
   std::cout << "Testing push_front...\n";
   testPushFront();
   std::cout << "Testing push/pop front...\n";
   testPushPopFront();
   std::cout << "Testing self-assigment...\n";
   selfAssigment();
   std::cout << "Testing coping...\n";
   copyTest();
   std::cout << "Testign empty()...\n";
   testEmpty();
   std::cout << "Testing back()...\n";
   testBack();
   std::cout << "Testing front()...\n";
   testFront();
   std::cout << "Testing iterator...\n";
   testIterator();
   std::cout << "Testing reverse iterator...\n";
   testReverseIterator();
   std::cout << "Testing insert()...\n";
   testInsert();
   std::cout << "Testing erase()...\n";
   testErase();
   std::cout << "Testing splice()...\n";
   spliceTest();

   return 0;
}
