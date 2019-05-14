package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailJasa {
    @SerializedName("Id_Detail_Jasa")
    @Expose
    private Integer idDetailJasa;
    @SerializedName("Id_Transaksi")
    @Expose
    private Integer idTransaksi;
    @SerializedName("Id_Jasa")
    @Expose
    private Integer idJasa;
    @SerializedName("Nama_Jasa")
    @Expose
    private String namaJasa;
    @SerializedName("Harga_Jasa")
    @Expose
    private Double hargaJasa;
    @SerializedName("Id_Jasa_Montir")
    @Expose
    private Integer idJasaMontir;
    @SerializedName("Subtotal_Detail_Jasa")
    @Expose
    private Double subtotalDetailJasa;

    public DetailJasa(Integer idJasa, String namaJasa, Integer idJasaMontir, Double subtotalDetailJasa) {
        this.idJasa = idJasa;
        this.namaJasa = namaJasa;
        this.idJasaMontir = idJasaMontir;
        this.subtotalDetailJasa = subtotalDetailJasa;
    }

    public Integer getIdDetailJasa() {
        return idDetailJasa;
    }

    public void setIdDetailJasa(Integer idDetailJasa) {
        this.idDetailJasa = idDetailJasa;
    }

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Integer getIdJasa() {
        return idJasa;
    }

    public void setIdJasa(Integer idJasa) {
        this.idJasa = idJasa;
    }

    public String getNamaJasa() {
        return namaJasa;
    }

    public void setNamaJasa(String namaJasa) {
        this.namaJasa = namaJasa;
    }

    public Double getHargaJasa() {
        return hargaJasa;
    }

    public void setHargaJasa(Double hargaJasa) {
        this.hargaJasa = hargaJasa;
    }

    public Integer getIdJasaMontir() {
        return idJasaMontir;
    }

    public void setIdJasaMontir(Integer idJasaMontir) {
        this.idJasaMontir = idJasaMontir;
    }

    public Double getSubtotalDetailJasa() {
        return subtotalDetailJasa;
    }

    public void setSubtotalDetailJasa(Double subtotalDetailJasa) {
        this.subtotalDetailJasa = subtotalDetailJasa;
    }
}
