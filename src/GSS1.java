import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by sreedish.ps on 27/12/14.
 */
public class GSS1 {
  static int tree[];
  static Integer N;
  static int[] arr;

  public static void main(String[] args) throws IOException {

    Reader in = new Reader();
    PrintWriter out = new PrintWriter(System.out, true);
    N = in.nextInt();

    arr = new int[N];
    for (int i = 0; i < N; i++) {
      arr[i] = in.nextInt();
    }

    SegmentTree root = new SegmentTree(0, N - 1);

    int t = in.nextInt();
    while (t-- > 0) {
      int a = in.nextInt(), b = in.nextInt();
      out.println(root.query(a-1, b-1).max);
    }

  }

  static class SegmentTree {
    SegmentTree leftChild;
    SegmentTree rightChild;
    int sum, leftMax, rightMax, max;
    int start, end;

    public SegmentTree(int start, int end) {
      this.start = start;
      this.end = end;

      if (start != end) {
        int mid = start + ((end - start) >> 2 );
        leftChild = new SegmentTree(start, mid);
        rightChild = new SegmentTree(mid + 1, end);
        join(this, leftChild, rightChild);
      } else {
        sum = leftMax = rightMax = max = arr[start];
      }
    }

    private void join(SegmentTree root, SegmentTree leftChild,
                      SegmentTree rightChild) {
      root.sum = leftChild.sum + rightChild.sum;
      root.leftMax = Math.max(leftChild.leftMax,
          leftChild.sum + rightChild.leftMax);
      root.rightMax = Math.max(rightChild.rightMax,
          rightChild.sum + leftChild.rightMax);
      root.max = Math.max(Math.max(leftChild.max, rightChild.max),
          leftChild.rightMax + rightChild.leftMax);
    }
    public SegmentTree () {}

    public SegmentTree query(int a, int b) {
      if (a == start && end == b) {
        return this;
      }
      int mid = start + ((end - start) >> 2 );
      if (a > mid) return rightChild.query(a, b);
      if (b <= mid) return leftChild.query(a, b);

      SegmentTree ans = new SegmentTree();
      join(ans, leftChild.query(a, mid), rightChild.query(mid + 1, b));
      return ans;

    }
  }

  /**
   * Faster input
   **/
  static class Reader {
    final private int BUFFER_SIZE = 1 << 16;
    private DataInputStream din;
    private byte[] buffer;
    private int bufferPointer, bytesRead;

    public Reader() {
      din = new DataInputStream(System.in);
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }

    public Reader(String file_name) throws IOException {
      din = new DataInputStream(new FileInputStream(file_name));
      buffer = new byte[BUFFER_SIZE];
      bufferPointer = bytesRead = 0;
    }

    public String readLine() throws IOException {
      byte[] buf = new byte[64];
      int cnt = 0, c;
      while ((c = read()) != -1) {
        if (c == '\n') break;
        buf[cnt++] = (byte) c;
      }
      return new String(buf, 0, cnt);
    }

    public int nextInt() throws IOException {
      int ret = 0;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = (c == '-');
      if (neg) c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (neg) return -ret;
      return ret;
    }

    public long nextLong() throws IOException {
      long ret = 0;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = (c == '-');
      if (neg) c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (neg) return -ret;
      return ret;
    }

    public double nextDouble() throws IOException {
      double ret = 0, div = 1;
      byte c = read();
      while (c <= ' ') c = read();
      boolean neg = (c == '-');
      if (neg) c = read();
      do {
        ret = ret * 10 + c - '0';
      } while ((c = read()) >= '0' && c <= '9');
      if (c == '.')
        while ((c = read()) >= '0' && c <= '9') ret += (c - '0') / (div *= 10);
      if (neg) return -ret;
      return ret;
    }

    private void fillBuffer() throws IOException {
      bytesRead = din.read(buffer, bufferPointer = 0, BUFFER_SIZE);
      if (bytesRead == -1) buffer[0] = -1;
    }

    private byte read() throws IOException {
      if (bufferPointer == bytesRead) fillBuffer();
      return buffer[bufferPointer++];
    }

    public void close() throws IOException {
      if (din == null) return;
      din.close();
    }
  }
}
