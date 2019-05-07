package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TransaksiPengadaan {


    @SerializedName("Id_Pengadaan")
    @Expose
    private Integer idPengadaan;
    @SerializedName("Id_Supplier")
    @Expose
    private Integer idSupplier;
    @SerializedName("Nama_Supplier")
    @Expose
    private String namaSupplier;
    @SerializedName("Nama_Sales")
    @Expose
    private String namaSales;
    @SerializedName("Tanggal_Pengadaan")
    @Expose
    private String tanggalPengadaan;
    @SerializedName("Total_Harga")
    @Expose
    private Integer totalHarga;
    @SerializedName("Status_Pengadaan")
    @Expose
    private Integer statusPengadaan;
    @SerializedName("detail_pengadaan")
    @Expose
    private DetailPengadaan detailPengadaan;

    public Integer getIdPengadaan() {
        return idPengadaan;
    }

    public void setIdPengadaan(Integer idPengadaan) {
        this.idPengadaan = idPengadaan;
    }

    public Integer getIdSupplier() {
        return idSupplier;
    }

    public void setIdSupplier(Integer idSupplier) {
        this.idSupplier = idSupplier;
    }

    public String getNamaSupplier() {
        return namaSupplier;
    }

    public void setNamaSupplier(String namaSupplier) {
        this.namaSupplier = namaSupplier;
    }

    public String getNamaSales() {
        return namaSales;
    }

    public void setNamaSales(String namaSales) {
        this.namaSales = namaSales;
    }

    public String getTanggalPengadaan() {
        return tanggalPengadaan;
    }

    public void setTanggalPengadaan(String tanggalPengadaan) {
        this.tanggalPengadaan = tanggalPengadaan;
    }

    public Integer getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(Integer totalHarga) {
        this.totalHarga = totalHarga;
    }

    public Integer getStatusPengadaan() {
        return statusPengadaan;
    }

    public void setStatusPengadaan(Integer statusPengadaan) {
        this.statusPengadaan = statusPengadaan;
    }

    public DetailPengadaan getDetailPengadaan() {
        return detailPengadaan;
    }

    public void setDetailPengadaan(DetailPengadaan detailPengadaan) {
        this.detailPengadaan = detailPengadaan;
    }

}
