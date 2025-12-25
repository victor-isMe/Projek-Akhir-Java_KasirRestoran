import java.util.Scanner; //import kelas scanner dari paket java.util
import java.util.ArrayList; //import kelas ArrayList
import java.util.Arrays; //import kelas Arrays
import java.time.LocalDateTime; //import LocalDateTime dari paket java.time
import java.time.format.DateTimeFormatter; //Import format date time untuk merapikan format waktu pesanan

class Pesanan {//Membuat class untuk menyimpan setiap informasi dari pesanan customer
    //Mendeklarasikan variabel
    int idPesanan;
    String namaPemesan;
    LocalDateTime waktuPesanan;

    //Membuat ArrayList untuk menyimpan nama menu yang dipesan
    ArrayList<String> pesanan = new ArrayList<>();
    //Untuk menyimpan harga dari menu yang dipilih sebelumnya
    ArrayList<Integer> hargaPesanan = new ArrayList<>();
    //Untuk menyimpan jumlah item per menu yang dipesan
    ArrayList<Integer> quantity = new ArrayList<>();
    //Menginisiasi statusPesanan
    String statusPesanan = "Belum Dibayar";

    public Pesanan(int idPesanan, String namaPemesan) { //Constructor Pesanan untuk mengatur ID pesanan dan nama pemesan
        this.idPesanan = idPesanan;
        this.namaPemesan = namaPemesan;
        this.waktuPesanan = LocalDateTime.now(); //Mencatat waktu sekarang secara real time dengan format yang telah ditentukan
    }

    public String getWaktuPesananFormatted() { //Untuk merapikan format waktu pesanan
        DateTimeFormatter formatWaktu = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return this.waktuPesanan.format(formatWaktu);
    }

    public int getTotalHarga() { //Method untuk menghitung total harga dari semua pesanan
        int total = 0;
        for (int i = 0;i<hargaPesanan.size();i++) {
            total += hargaPesanan.get(i) * quantity.get(i);
        }
        return total;
    }
}

public class KasirRestoran {

    //Method untuk mencatat pesanan
    public static void catatPesanan(Scanner input, ArrayList<String> menu, ArrayList<Integer> hargaMenu, ArrayList<Pesanan> listPesanan, int setNextId[]) {
        System.out.print("\nNama pemesan : ");
        String namaPemesan = input.nextLine();
        int id = setNextId[0]; //Untuk memberikan id pada pesanan

        //Menginisiasi objek Pesanan dari class Pesanan
        Pesanan newPesanan = new Pesanan(id, namaPemesan);

        //Mendeklarasikan variabel
        int pilih;
        char choice;

        //Perulangan do while untuk bisa menginputkan lebih dari satu menu pada pesanan
        do {
            tampilkanMenuRestoran(menu, hargaMenu); //Memanggil method untuk menampilkan daftar menu

            System.out.print("\nMasukkan pesanan customer(Input berupa angka) : ");
            pilih = input.nextInt();input.nextLine();

            if (pilih >= 1 && pilih <= menu.size()) {
                System.out.print("Jumlah item : ");
                int jumlah = input.nextInt();input.nextLine();

                if (jumlah != 0) {
                    newPesanan.pesanan.add(menu.get(pilih-1));
                    newPesanan.hargaPesanan.add(hargaMenu.get(pilih-1));
                    newPesanan.quantity.add(jumlah);
                }
            } else {
                System.out.println("Mohon masukkan angka yang valid!!");
            }

            System.out.print("Ingin pesan lagi? (y/n): ");
            choice = input.next().charAt(0);input.nextLine();
        } while(choice == 'y' || choice == 'Y');

        if (newPesanan.pesanan.size() != 0){//Mengecek jika terdapat pesanan yang tercatat
            listPesanan.add(newPesanan);

            System.out.println("\nPesanan berhasil dicatat!");

            //Menampilkan ringkasan pesanan customer
            System.out.println("\nRingkasan Pesanan\n");
            System.out.println("ID Pesanan : " + newPesanan.idPesanan);
            System.out.println("Nama Pemesan : " + newPesanan.namaPemesan + "\n");

            for (int i = 0;i<newPesanan.pesanan.size();i++) {
                System.out.println((i+1) + ". " + newPesanan.pesanan.get(i) + "\nJumlah item : " + newPesanan.quantity.get(i));
            }

            //Melakukan increment sebesar 1 untuk id pesanan berikutnya
            setNextId[0]++;
        } else{//Jika tidak ada pesanan yang tercatat
            System.out.println("Tidak ada pesanan yang dicatat.");
        }
    }

    //Method untuk menampilkan daftar menu
    public static void tampilkanMenuRestoran(ArrayList<String> daftarMenu, ArrayList<Integer> daftarHarga) {
        System.out.println("\nDaftar Menu Makanan&Minuman Restoran 'Dapur Nusantara'");

        for (int i=1;i<=daftarMenu.size();i++) {
            System.out.println(i + ". " + daftarMenu.get(i-1) + " : Rp" + daftarHarga.get(i-1));
        }
    }

    //Method untuk menampilkan daftar listPesanan
    public static void tampilkanListPesanan(ArrayList<Pesanan> listPesanan) {
        System.out.println("\nDaftar Pesanan:");
        for (Pesanan p : listPesanan) {
            System.out.println("- (ID : "+p.idPesanan+") - "+p.namaPemesan);
        }
    }

    //Method ubah/edit Menu Makanan/Minuman
    public static void editMenu(Scanner input, ArrayList<String> menu, ArrayList<Integer> hargaMenu) {
        System.out.println("\nEdit Menu Makanan&Minuman");

        if(menu.isEmpty()) {
            System.out.println("Tidak ada item pada Menu Makanan&Minuman");
            return;
        }

        tampilkanMenuRestoran(menu, hargaMenu);

        System.out.print("\nPilih menu yang ingin diubah(dalam angka) : ");
        int nomorMenu = input.nextInt();input.nextLine();

        if (nomorMenu < 1 || nomorMenu > menu.size()) {
            System.out.println("Nomor menu tidak valid!");
            return;
        }

        System.out.println("\nMenu yang dipilih : "+menu.get(nomorMenu-1)+" - Rp"+hargaMenu.get(nomorMenu-1));
        System.out.println("\n1. Hapus Menu");
        System.out.println("2. Ubah Nama");
        System.out.println("3. Ubah Harga");
        System.out.print("Pilih(1-3) : ");
        int pilihEdit = input.nextInt();input.nextLine();

        switch(pilihEdit) {
            case 1:
                System.out.println("Menu '"+menu.get(nomorMenu-1)+"' berhasil dihapus.");
                menu.remove(nomorMenu-1);
                hargaMenu.remove(nomorMenu-1);
                break;
            
            case 2:
                System.out.print("Masukkan nama baru : ");
                String namaBaru = input.nextLine();
                menu.set(nomorMenu-1, namaBaru);
                System.out.println("\nNama Menu berhasil diubah!");
                break;

            case 3:
                System.out.print("Masukkan harga baru : ");
                int hargaBaru = input.nextInt();input.nextLine();
                hargaMenu.set(nomorMenu-1, hargaBaru);
                System.out.println("\nHarga Menu berhasil diubah!");
                break;

            default:
                System.out.println("\nNomor tidak valid!!");
        }
    }

    //Method tampilkan riwayat pesanan
    public static void tampilkanRiwayatPesanan(ArrayList<Pesanan> listPesanan) {

        System.out.println("\nRiwayat Pesanan");

        if (listPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan yang tercatat.");
            return;
        }

        for (Pesanan p : listPesanan) {
            int total = p.getTotalHarga();

            System.out.println("\nID Pesanan : "+p.idPesanan);
            System.out.println("Nama Pemesan : "+p.namaPemesan);
            System.out.println("Waktu Pemesanan : "+p.getWaktuPesananFormatted());
            System.out.println("Rincian Pesanan : ");
            for (int i=0;i<p.pesanan.size();i++) {
                int subTotal = p.hargaPesanan.get(i)*p.quantity.get(i);
                
                System.out.println((i+1) + ". "+p.pesanan.get(i)+" x"+p.quantity.get(i)+" | Harga per item Rp" + p.hargaPesanan.get(i) +" | SubTotal : Rp"+subTotal);
            }
            System.out.println("Total Harga : Rp"+total);

            System.out.println("Status Pesanan : "+p.statusPesanan);
            System.out.println("-------------------------------------------------------------------");
        }
    }

    //Method untuk tambah menu baru
    public static void tambahkanMenuBaru(Scanner input, ArrayList<String> menu, ArrayList<Integer> hargaMenu) {

        System.out.println("\nTambah Menu Makanan&Minuman");

        System.out.print("Masukkan nama menu baru : ");
        String newMenu = input.nextLine();
        menu.add(newMenu);

        System.out.print("Masukkan harga menu : ");
        int newHargaMenu = input.nextInt();input.nextLine();
        hargaMenu.add(newHargaMenu);
                    
        System.out.println(newMenu + " telah berhasil ditambahkan ke dalam Menu Makanan.");
    }

    //Method untuk menampilkan pesanan berdasarkan status
    public static void tampilkanStatusPesanan(Scanner input, ArrayList<Pesanan> listPesanan) {
        if (listPesanan.isEmpty()) { //Jika belum ada pesanan yang tercatat
            System.out.println("Belum ada pesanan yang masuk.");
            return;
        } else {
            
            tampilkanListPesanan(listPesanan);

            System.out.print("\nMasukkan ID Pesanan yang ingin di cek : ");
            int cariId = input.nextInt();input.nextLine();

            Pesanan ditemukan = null; //Mendeklarasikan variabel 'ditemukan' yang dipakai untuk menyimpan objek Pesanan yang dicari
            for (Pesanan p : listPesanan) { //Melakukan perulangan for untuk mengecek setiap pesanan dan dimasukkan pada variabel 'p'
                if (p.idPesanan == cariId) { //Jika ditemukan pesanan dengan ID yang dicari
                    ditemukan = p; //Menyimpan objek Pesanan ke variabel 'ditemukan'
                    break; //Menghentikan perulangan jika data yang sesuai sudah ditemukan
                }
            }

            if (ditemukan == null) { //Jika data pesanan tidak ditemukan
                System.out.println("\nID Pesanan tidak ditemukan!"); 
            } else { //Jika data pesanan ditemukan
                System.out.println("\nID Pesanan : "+ditemukan.idPesanan);
                System.out.println("Nama Pemesan : "+ditemukan.namaPemesan);
                System.out.println("Status Pesanan : "+ditemukan.statusPesanan+"\n");
            }
        }
    }

    //Method untuk menghitung dan menampilkan rincian harga pesanan
    public static void tampilkanRincianHarga(Scanner input, ArrayList<Pesanan> listPesanan) {
        if (listPesanan.isEmpty()) {
            System.out.println("Belum ada pesanan yang masuk.");
        } else {
            tampilkanListPesanan(listPesanan);

            System.out.print("\nMasukkan ID Pesanan : ");
            int cariId = input.nextInt();input.nextLine();

            Pesanan ditemukan = null;
            for (Pesanan p : listPesanan) {
                if (p.idPesanan == cariId) {
                    ditemukan = p;
                    break;
                }
            }

            if (ditemukan == null) { //Jika data pesanan tidak ditemukan
                System.out.println("\nID Pesanan tidak ditemukan!");
            } else { //Jika data pesanan ditemukan
                int total = ditemukan.getTotalHarga(); //Memanggil method 'getTotalHarga()' dari class Pesanan untuk menghitung total harga

                System.out.println("\nID Pesanan : "+ditemukan.idPesanan);
                System.out.println("Nama Pemesan : "+ditemukan.namaPemesan);
                System.out.println("\nDaftar Pesanan : ");
                for (int i=0;i<ditemukan.pesanan.size();i++) {
                    //Menghitung subtotal untuk per item pesanan
                    int subTotal = ditemukan.hargaPesanan.get(i)*ditemukan.quantity.get(i);
                    System.out.println((i+1)+". "+ditemukan.pesanan.get(i)+" x"+ditemukan.quantity.get(i)+" | Harga per item: Rp"+ditemukan.hargaPesanan.get(i)+" | SubTotal: Rp"+subTotal);
                }
                System.out.println("Total Harga : Rp"+total);

                System.out.print("\nApakah sudah dibayar? (y/n): ");
                char bayar = input.next().charAt(0);
                if (bayar == 'y' || bayar == 'Y') {
                    ditemukan.statusPesanan = "Sudah Dibayar"; //Mengubah status pesanan
                }
            }
        }
    }

    public static void main(String[] args) {

        //Digunakan agar ID pesanan dapat berubah di dalam fungsi catatPesanan()
        int[] setNextId = {1};

        //Untuk menyimpan semua pesanan yang sudah tercatat
        ArrayList<Pesanan> listPesanan = new ArrayList<>();

        Scanner input = new Scanner(System.in); //Membuat objek scanner baru

        //Mendeklarasikan variabel
        int pilihan;
        boolean kondisi;

        //Menyimpan data untuk nama makanan, minuman dan harganya pada List
        String[] arrMenu = {"Mie Aceh", "Rendang", "Pempek", "Soto Betawi", "Rawon", "Coto Makasar", "Ayam Betutu", "Gudeg", "Karedok", "Sate Lilit", "Es Cendol Dawet", "Es Teller", "Es Pisang Ijo", "Es Kuwut", "Es Teh", "Es Jeruk", "Air Putih", "Es Degan", "Kopi Klotok", "Kopi Toraja"};
        Integer[] arrHargaMenu = {25000, 35000, 20000, 22000, 32000, 30000, 32000, 22000, 18000, 30000, 8000, 15000, 15000, 10000, 8000, 10000, 8000, 15000, 10000, 15000};

        //Membuat ArrayList dari List yang telah dibuat sebelumnya
        ArrayList<String> menu = new ArrayList<>(Arrays.asList(arrMenu));
        ArrayList<Integer> hargaMenu = new ArrayList<>(Arrays.asList(arrHargaMenu));

        //Menginisiasi variabel kondisi dengan nilai true
        kondisi = true;

        //Perulangan while akan terus dikerjakan saat kondisi bernilai true
        while (kondisi == true){

            //Tampilan print yang akan memberi tahu user apa saja fitur yang ada pada program
            System.out.println("\nSelamat datang di Restoran 'Dapur Nusantara'");
            System.out.println("1. Tambahkan Menu Makanan&Minuman");
            System.out.println("2. Tampilkan Menu Makanan&Minuman");
            System.out.println("3. Ubah Menu Makanan&Minuman");
            System.out.println("4. Catat Pesanan");
            System.out.println("5. Cek Status Pesanan");
            System.out.println("6. Hitung Total Pesanan");
            System.out.println("7. Riwayat Pesanan");
            System.out.println("8. Keluar Program");

            //Mengambil input dari user
            System.out.print("\nMasukkan pilihan (1-8): ");
            pilihan = input.nextInt();input.nextLine();

            switch(pilihan) {
                case 1: //Fitur menambahkan menu

                    //Memanggil method tambahkanMenuBaru()
                    tambahkanMenuBaru(input, menu, hargaMenu);
                    break;

                case 2: //Fitur untuk menampilkan semua menu beserta harganya
                
                    //Memanggil method tampilkanMenuRestoran()
                    tampilkanMenuRestoran(menu, hargaMenu);
                    break;

                case 3: //Fitur untuk edit/ubah menu makanan dan minuman

                    //Memanggil method editMenu()
                    editMenu(input, menu, hargaMenu);
                    break;

                case 4: //Fitur mencatat pesanan customer

                    //Memanggil method catatPesanan()
                    catatPesanan(input, menu, hargaMenu, listPesanan, setNextId);
                    break;

                case 5: //Fitur untuk menampilkan status pesanan berdasarkan ID Pesanan

                    //Memanggil method tampilkanStatusPesanan()
                    tampilkanStatusPesanan(input, listPesanan);
                    break;

                case 6: //Fitur menampilkan rincian harga pesanan dan total harganya

                    //Memanggil method tampilkanRincianHarga()
                    tampilkanRincianHarga(input, listPesanan);
                    break;

                case 7://Fitur menampilkan riwayat pesanan

                    //Memanggil method tampilkanRiwayatPesanan()
                    tampilkanRiwayatPesanan(listPesanan);
                    break;

                case 8: //Untuk menghentikan perulangan while dan keluar dari program
                    kondisi = false;
                    System.out.println("Terima Kasih Telah Menggunakan Program Ini :)");
                    break;

                default: //Jika input pengguna tidak valid
                    System.out.println("Angka yang anda masukkan tidak valid! Mohon coba lagi!");
        }

    }
}
}
