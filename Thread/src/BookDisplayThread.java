import java.util.List;

public class BookDisplayThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.sleep(1000);
            
            // Mengambil data dari database
            List<String> toRead = DatabaseManager.getBooksByStatus("To Read");
            List<String> reading = DatabaseManager.getBooksByStatus("Reading");
            List<String> read = DatabaseManager.getBooksByStatus("Read");

            // Menampilkan output
            System.out.println("--------------------------");
            
            System.out.println("Want to Read: ");
            for (String title : toRead) {
                System.out.println("- " + title);
            }
            System.out.println();

            System.out.println("Currently Reading:");
            for (String title : reading) {
                System.out.println("- " + title);
            }
            System.out.println();

            System.out.println("Read:");
            for (String title : read) {
                System.out.println("- " + title);
            }
            
            System.out.println("----------------------------");

        } catch (InterruptedException e) {
            System.out.println("Thread terinterupsi: " + e.getMessage());
        }
    }
}