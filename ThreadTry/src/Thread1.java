public class Thread1 implements Runnable {

    @Override
    public void run() {
        System.out.println("KeFin is running");
    }

    public static void main(String[] args) {
        Thread1 kefin = new Thread1();
        Thread trie = new Thread(kefin);
        trie.start();
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
    public static class myThread extends Thread {
        @Override
        public void run() {
            System.out.println("keFin is so handsome");
        }
    }
}
