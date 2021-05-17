import org.knowm.xchart.style.Theme;

public class Thread1 implements Runnable {

    private boolean stop = false;
    @Override
    public void run() {
        while (!stop) {
            System.out.println("KeFin is running");
        }
    }

    public synchronized boolean isStopped() {
        return this.stop;
    }

    public synchronized void requestToStop() {
        this.stop = true;
    }

    public static void main(String[] args) {
        Thread1 kefin = new Thread1();
        Thread trie = new Thread(kefin);
        trie.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("request to stop!!!");
        kefin.requestToStop();
        Runnable test2 = () -> {
            System.out.println("KeFin is so so so so handsome");
        };
        Thread trie2 = new Thread(test2);
        trie2.start();
    }
    public static class myThread extends Thread {
        @Override
        public void run() {
            System.out.println("keFin is so handsome");
        }
    }
}
