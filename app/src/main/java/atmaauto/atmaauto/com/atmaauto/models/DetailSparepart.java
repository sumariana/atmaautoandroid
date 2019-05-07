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
    @SerializedName("Kode_Sparepart")
    @Expose
    private String kodeSparepart;
    @SerializedName("Harga_Satuan")
    @Expose
    private Integer hargaSatuan;
    @SerializedName("Jumlah")
    @Expose
    private Integer jumlah;
    @SerializedName("Subtotal_Detail_Sparepart")
    @Expose
    private Integer subtotalDetailSparepart;

    public Integer getIdDetailSparepart() {
        return idDetailSparepart;
    }

    public void setIdDetailSparepart(Integer idDetailSparepart) {
        this.idDetailSparepart = idDetailSparepart;
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

    public Integer getHargaSatuan() {
        return hargaSatuan;
    }

    public void setHargaSatuan(Integer hargaSatuan) {
        this.hargaSatuan = hargaSatuan;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getSubtotalDetailSparepart() {
        return subtotalDetailSparepart;
    }

    public void setSubtotalDetailSparepart(Integer subtotalDetailSparepart) {
        this.subtotalDetailSparepart = subtotalDetailSparepart;
    }
}
