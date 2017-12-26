public class BinarySearch {
    // pre: l < r && ��� ������ i �� l �� r - 1 a[i + 1] >= a[i] && ���������� ����� i �� l �� r, ��� a[i] >= x
    // post: result == i: a[i] >= x && i - min
    public static int BinarySearch(int[] a, int l, int r, int x) {
        int k = l + (r - l) / 2;
        if (a.length == 0)
            return -1;
        else
            if (a[l] > x)
                return (-l - 1);
            else
                if (a[r - 1] < x)
                    return (-r - 1);
	// inv: a[l] < x <= a[r]
        while (l < r) {
            if (x <= a[k])
                r = k;
            else
                l = k + 1;
            k = l + (r - l) / 2; 
        } 
       if (a[r] == x)
            return r;
       else
            return (-r - 1);
    }
    // pre: �� ���� �������� ����������� ������������������ ����� ����� � ��������� ����� ����� ������ 
    // post result == i: args[i + 1] >= args[0] && i - min, ��� i �� 0 �� args.length - 2
    public static void main(String[] args) {
        int x;
        int[] a;
        a = new int[args.length - 1];
        x = Integer.parseInt(args[0]);
        // inv: i < args.length
        for (int i = 1; i < args.length; i++) {
            a[i - 1] = Integer.parseInt(args[i]);
        }
        System.out.println(BinarySearch(a, 0, a.length, x));
    }
}