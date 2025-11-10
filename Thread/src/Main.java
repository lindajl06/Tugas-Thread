public class Main {

    public static void main(String[] args) {
        
        DatabaseManager.initializeDatabase();
        DatabaseManager.populateSampleData();
        BookDisplayThread displayThread = new BookDisplayThread();

        System.out.println("============ GOODREADS ============");
        System.out.println("--------- READING ACTIVITY --------");
        
        displayThread.start();

        try {
            displayThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
