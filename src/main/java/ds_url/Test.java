package ds_url;

public class Test {

    private Object obj = new Object();

    public synchronized void a() {

        try {

            obj.wait();

            System.out.println("waiting");
        } catch (InterruptedException e) {
            System.out.println("Exception");
        }
    }

    public static void main(String[] args) {
        new Test().a();
    }
}
