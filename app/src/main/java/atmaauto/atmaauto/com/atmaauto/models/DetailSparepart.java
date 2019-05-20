package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class DetailSparepart {

    @SerializedName("Id_Detail_Sparepart")
    @Expose
    private Integer idDetailSparepart;
    @SerializedName("Id_Transaksi")
    @Expose
    private Integer idTransaksi;
    @SerializedName("Id_Jasa_Montir")
    @Expose
    private Integer idJasaMontir;
    @SerializedName("Nama_Sparepart")
    @Expose
    private String namaSparepart;
    @SerializedName("Kode_Sparepart")
    @Expose
    private String kodeSparepart;
    @SerializedName("Harga_Satuan")
    @Expose
    private Double hargaSatuan;
    @SerializedName("Jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("Subtotal_Detail_Sparepart")
    @Expose
    private Double subtotalDetailSparepart;

    public Integer getIdDetailSparepart() {
        return idDetailSparepart;
    }

    public void setIdDetailSparepart(Integer idDetailSparepart) {
        this.idDetailSparepart = idDetailSparepart;
    }

    public DetailSparepart(Integer idDetailSparepart, Integer idTransaksi, Integer idJasaMontir, String kodeSparepart, Double hargaSatuan, Integer jumlah, Double subtotalDetailSparepart) {
        this.idDetailSparepart = idDetailSparepart;
        this.idTransaksi = idTransaksi;
        this.idJasaMontir = idJasaMontir;
        this.kodeSparepart = kodeSparepart;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
        this.subtotalDetailSparepart = subtotalDetailSparepart;
    }

    public String getNamaSparepart() {
        return namaSparepart;
    }

    public void setNamaSparepart(String namaSparepart) {
        this.namaSparepart = namaSparepart;
    }

    public DetailSparepart(Integer idJasaMontir, String kodeSparepart, Double hargaSatuan, Integer jumlah, Double subtotalDetailSparepart) {
        super();
        this.idJasaMontir = idJasaMontir;
        this.kodeSparepart = kodeSparepart;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
        this.subtotalDetailSparepart = subtotalDetailSparepart;
    }

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Integer getIdJasaMontir() {
        return idJasaMontir;
    }

    public void setIdJasaMontir(Integer idJasaMontir) {
        this.idJasaMontir = idJasaMontir;
    }

    public String getKodeSparepart() {
        return kodeSparepart;
    }

    public void setKodeSparepart(String kodeSparepart) {
        this.kodeSparepart = kodeSparepart;
    }

    public Double getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Double hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Double getSubtotalDetailSparepart() {
        return subtotalDetailSparepart;
    }

    public void setSubtotalDetailSparepart(Double subtotalDetailSparepart) {
        this.subtotalDetailSparepart = subtotalDetailSparepart;
    }
}
