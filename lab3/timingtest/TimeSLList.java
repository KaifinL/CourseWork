package timingtest;
import edu.princeton.cs.algs4.Stopwatch;
import org.checkerframework.checker.units.qual.A;

/**
 * Created by hug.
 */
public class TimeSLList {
    private static void printTimingTable(AList<Integer> Ns, AList<Double> times, AList<Integer> opCounts) {
        System.out.printf("%12s %12s %12s %12s\n", "N", "time (s)", "# ops", "microsec/op");
        System.out.printf("------------------------------------------------------------\n");
        for (int i = 0; i < Ns.size(); i += 1) {
            int N = Ns.get(i);
            double time = times.get(i);
            int opCount = opCounts.get(i);
            double timePerOp = time / opCount * 1e6;
            System.out.printf("%12d %12.2f %12d %12.2f\n", N, time, opCount, timePerOp);
        }
    }

    public static void main(String[] args) {
        timeGetLast();
    }

    public static void timeGetLast() {
        // TODO: YOUR CODE HERE
        AList<Integer> Ns = new AList<>();
        Ns.addLast(1000);
        Ns.addLast(2000);
        Ns.addLast(4000);
        Ns.addLast(8000);
        Ns.addLast(16000);
        Ns.addLast(32000);
        Ns.addLast(64000);
        Ns.addLast(128000);
        AList<Integer> Ops = new AList<>();
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        Ops.addLast(10000);
        AList<Double> times = new AList<>();
        int i=0;int j=0;int x=0;
        for (i=0;i<8;i++){
            int a = Ns.get(i);
            SLList item = new SLList();
            for (j=0;j<a;j++){
                item.addLast(j);
            }
            Stopwatch sw = new Stopwatch();
            helper(item,10000);
            double timeInSeconds = sw.elapsedTime();
            times.addLast(timeInSeconds);
        }
        printTimingTable(Ns,times,Ops);
        }
        public static void helper(SLList L,int M){
        int x;
        for (x=0;x<8;x++){
            L.getLast();
        }
        }
    }

