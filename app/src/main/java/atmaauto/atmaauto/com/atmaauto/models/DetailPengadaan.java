package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailPengadaan {

    @SerializedName("Id_Detail_Pengadaan")
    @Expose
    private Integer idDetailPengadaan;
    @SerializedName("Id_Pengadaan")
    @Expose
    private Integer idPengadaan;
    @SerializedName("Kode_Sparepart")
    @Expose
    private String kodeSparepart;
    @SerializedName("Harga_Satuan")
    @Expose
    private Double hargaSatuan;
    @SerializedName("Jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("Subtotal_Pengadaan")
    @Expose
    private Double subtotalPengadaan;

    public DetailPengadaan(Integer idDetailPengadaan, Integer idPengadaan, String kodeSparepart, Double hargaSatuan, Integer jumlah, Double subtotalPengadaan) {
        super();
        this.idDetailPengadaan = idDetailPengadaan;
        this.idPengadaan = idPengadaan;
        this.kodeSparepart = kodeSparepart;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
        this.subtotalPengadaan = subtotalPengadaan;
    }

    public DetailPengadaan(String kodeSparepart, Double hargaSatuan, Integer jumlah, Double subtotalPengadaan) {
        super();
        this.kodeSparepart = kodeSparepart;
        this.hargaSatuan = hargaSatuan;
        this.jumlah = jumlah;
        this.subtotalPengadaan = subtotalPengadaan;
    }

    public Integer getIdDetailPengadaan() {
        return idDetailPengadaan;
    }


    public void setIdDetailPengadaan(Integer idDetailPengadaan) {
        this.idDetailPengadaan = idDetailPengadaan;
    }

    public Integer getIdPengadaan() {
        return idPengadaan;
    }

    public void setIdPengadaan(Integer idPengadaan) {
        this.idPengadaan = idPengadaan;
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

    public Double getSubtotalPengadaan() {
        return subtotalPengadaan;
    }

    public void setSubtotalPengadaan(Double subtotalPengadaan) {
        this.subtotalPengadaan = subtotalPengadaan;
    }



}
