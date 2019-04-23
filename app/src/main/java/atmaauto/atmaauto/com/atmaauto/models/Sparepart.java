package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sparepart {
    @SerializedName("Kode_Sparepart")
    @Expose
    private String kodeSparepart;
    @SerializedName("Nama_Sparepart")
    @Expose
    private String namaSparepart;
    @SerializedName("Merk_Sparepart")
    @Expose
    private String merkSparepart;
    @SerializedName("Tipe_Barang")
    @Expose
    private String tipeBarang;
    @SerializedName("Rak_Sparepart")
    @Expose
    private String rakSparepart;
    @SerializedName("Jumlah_Sparepart")
    @Expose
    private String jumlahSparepart;
    @SerializedName("Stok_Minimum_Sparepart")
    @Expose
    private String stokMinimumSparepart;
    @SerializedName("Harga_Beli")
    @Expose
    private String hargaBeli;
    @SerializedName("Harga_Jual")
    @Expose
    private String hargaJual;
    @SerializedName("Gambar")
    @Expose
    private String gambar;

    public String getKodeSparepart() {
        return kodeSparepart;
    }

    public void setKodeSparepart(String kodeSparepart) {
        this.kodeSparepart = kodeSparepart;
    }

    public String getNamaSparepart() {
        return namaSparepart;
    }

    public void setNamaSparepart(String namaSparepart) {
        this.namaSparepart = namaSparepart;
    }

    public String getMerkSparepart() {
        return merkSparepart;
    }

    public void setMerkSparepart(String merkSparepart) {
        this.merkSparepart = merkSparepart;
    }

    public String getRakSparepart() {
        return rakSparepart;
    }

    public void setRakSparepart(String rakSparepart) {
        this.rakSparepart = rakSparepart;
    }

    public String getTipeBarang() {
        return tipeBarang;
    }

    public void setTipeBarang(String tipeBarang) {
        this.tipeBarang = tipeBarang;
    }

    public String getJumlahSparepart() {
        return jumlahSparepart;
    }

    public void setJumlahSparepart(String jumlahSparepart) {
        this.jumlahSparepart = jumlahSparepart;
    }

    public String getStokMinimumSparepart() {
        return stokMinimumSparepart;
    }

    public void setStokMinimumSparepart(String stokMinimumSparepart) {
        this.stokMinimumSparepart = stokMinimumSparepart;
    }

    public String getHargaBeli() {
        return hargaBeli;
    }

    public void setHargaBeli(String hargaBeli) {
        this.hargaBeli = hargaBeli;
    }

    public String getHargaJual() {
        return hargaJual;
    }

    public void setHargaJual(String hargaJual) {
        this.hargaJual = hargaJual;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
