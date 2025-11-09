public class Main {

    public static void main(String[] args) {
        
        // 1. Inisialisasi database (buat tabel jika belum ada)
        DatabaseManager.initializeDatabase();
        
        // 2. Isi data contoh (hanya jika database kosong)
        DatabaseManager.populateSampleData();

        // 3. Buat objek Thread
        BookDisplayThread displayThread = new BookDisplayThread();

        System.out.println("Memulai thread untuk menampilkan daftar buku...");
        
        // 4. Jalankan Thread
        // Ini akan memanggil method run() di BookDisplayThread secara terpisah
        displayThread.start();

        try {
            // 5. (Opsional) Menunggu thread selesai bekerja sebelum program utama ditutup
            displayThread.join();
            System.out.println("Tugas thread selesai. Program utama berakhir.");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
