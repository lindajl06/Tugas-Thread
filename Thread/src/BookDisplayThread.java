import java.util.List;

public class BookDisplayThread extends Thread {

    @Override
    public void run() {
        try {
            // Memberi jeda sedikit untuk simulasi tugas yang butuh waktu
            Thread.sleep(1000); 
            
            // Mengambil data dari database
            List<String> toRead = DatabaseManager.getBooksByStatus("To Read");
            List<String> reading = DatabaseManager.getBooksByStatus("Reading");
            List<String> read = DatabaseManager.getBooksByStatus("Read");

            // Menampilkan output sesuai format yang diinginkan
            System.out.println("--------------------------");
            
            System.out.println("Buku yang ingin dibaca: ");
            for (String title : toRead) {
                System.out.println("- " + title);
            }
            System.out.println(); 

            System.out.println("Buku yang sedang dibaca:");
            for (String title : reading) {
                System.out.println("- " + title);
            }
            System.out.println(); 

            System.out.println("Buku yang sudah dibaca:");
            for (String title : read) {
                System.out.println("- " + title);
            }
            
            System.out.println("----------------------------");

        } catch (InterruptedException e) {
            System.out.println("Thread terinterupsi: " + e.getMessage());
        }
    }
}