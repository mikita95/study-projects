public class BinarySearch {
    // pre: l < r && для любого i от l до r - 1 a[i + 1] >= a[i] && существует такое i от l до r, что a[i] >= x
    // post: result == i: a[i] >= x && i - min
    public static int BinarySearch(int[] a, int l, int r, int x) {
        int k = l  + (r - l) / 2;
        if (a.length == 0)
            return -1;
        if (l < r) {
            if (x <= a[k])
                k = BinarySearch(a, l, r - 1, x);
            else
                k = BinarySearch(a, k + 1, r, x);
        }
        return k;
    }
    // pre: на вход подается неубывающая последовательность целых чисел в строковой форме через пробел 
    // post result == i: args[i + 1] >= args[0] && i - min, где i от 0 до args.length - 2
    public static void main(String[] args) {
        int x, l;
        int[] a;
        a = new int[args.length - 1];
        x = Integer.parseInt(args[0]);
        // inv: i < args.length && a[i - 1] - целое число
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        if (a.length == 0)
             System.out.println(-1);
        else
        if (a[0] > x)
            System.out.println(-1);
        else
          if (a[a.length - 1] < x)
              System.out.println(-a.length - 1);
          else {
              l = BinarySearch(a, 0, a.length - 1, x);
              if (a[l] == x)
                 System.out.println(l);
              else
                 System.out.println(-l - 1);
          }
            
    }
}