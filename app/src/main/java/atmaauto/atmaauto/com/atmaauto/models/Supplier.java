package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier {
    @SerializedName("Id_Supplier")
    @Expose
    private Integer idSupplier;
    @SerializedName("Nama_Supplier")
    @Expose
    private String namaSupplier;
    @SerializedName("Alamat_Supplier")
    @Expose
    private String alamatSupplier;
    @SerializedName("Telepon_Supplier")
    @Expose
    private String teleponSupplier;
    @SerializedName("Nama_Sales")
    @Expose
    private String namaSales;
    @SerializedName("Telepon_Sales")
    @Expose
    private String teleponSales;

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

    public String getAlamatSupplier() {
        return alamatSupplier;
    }

    public void setAlamatSupplier(String alamatSupplier) {
        this.alamatSupplier = alamatSupplier;
    }

    public String getTeleponSupplier() {
        return teleponSupplier;
    }

    public void setTeleponSupplier(String teleponSupplier) {
        this.teleponSupplier = teleponSupplier;
    }

    public String getNamaSales() {
        return namaSales;
    }

    public void setNamaSales(String namaSales) {
        this.namaSales = namaSales;
    }

    public String getTeleponSales() {
        return teleponSales;
    }

    public void setTeleponSales(String teleponSales) {
        this.teleponSales = teleponSales;
    }
}
