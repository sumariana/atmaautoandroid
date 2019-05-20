package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiPenjualan {

    @SerializedName("Id_Transaksi")
    @Expose
    private Integer idTransaksi;
    @SerializedName("Id_Konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("Tanggal_Transaksi")
    @Expose
    private String tanggalTransaksi;
    @SerializedName("Nama_Konsumen")
    @Expose
    private String namaKonsumen;
    @SerializedName("Jenis_Transaksi")
    @Expose
    private String jenisTransaksi;
    @SerializedName("Subtotal")
    @Expose
    private Integer subtotal;
    @SerializedName("Diskon")
    @Expose
    private Integer diskon;
    @SerializedName("Total")
    @Expose
    private Integer total;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("detail_jasa")
    @Expose
    private DetailJasa detailJasa;
    @SerializedName("detail_sparepart")
    @Expose
    private DetailSparepart detailSparepart;

    public Integer getIdTransaksi() {
        return idTransaksi;
    }

    public void setIdTransaksi(Integer idTransaksi) {
        this.idTransaksi = idTransaksi;
    }

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getTanggalTransaksi() {
        return tanggalTransaksi;
    }

    public void setTanggalTransaksi(String tanggalTransaksi) {
        this.tanggalTransaksi = tanggalTransaksi;
    }

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public void setNamaKonsumen(String namaKonsumen) {
        this.namaKonsumen = namaKonsumen;
    }

    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public void setJenisTransaksi(String jenisTransaksi) {
        this.jenisTransaksi = jenisTransaksi;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }

    public Integer getDiskon() {
        return diskon;
    }

    public void setDiskon(Integer diskon) {
        this.diskon = diskon;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DetailJasa getDetailJasa() {
        return detailJasa;
    }

    public void setDetailJasa(DetailJasa detailJasa) {
        this.detailJasa = detailJasa;
    }

    public DetailSparepart getDetailSparepart() {
        return detailSparepart;
    }

    public void setDetailSparepart(DetailSparepart detailSparepart) {
        this.detailSparepart = detailSparepart;
    }

}
