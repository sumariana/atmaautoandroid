package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Konsumen {
    @SerializedName("Id_Konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("Nama_Konsumen")
    @Expose
    private String namaKonsumen;
    @SerializedName("Alamat_Konsumen")
    @Expose
    private String alamatKonsumen;
    @SerializedName("Telepon_Konsumen")
    @Expose
    private String teleponKonsumen;

    public Integer getIdKonsumen() {
        return idKonsumen;
    }

    public void setIdKonsumen(Integer idKonsumen) {
        this.idKonsumen = idKonsumen;
    }

    public String getNamaKonsumen() {
        return namaKonsumen;
    }

    public void setNamaKonsumen(String namaKonsumen) {
        this.namaKonsumen = namaKonsumen;
    }

    public String getAlamatKonsumen() {
        return alamatKonsumen;
    }

    public void setAlamatKonsumen(String alamatKonsumen) {
        this.alamatKonsumen = alamatKonsumen;
    }

    public String getTeleponKonsumen() {
        return teleponKonsumen;
    }

    public void setTeleponKonsumen(String teleponKonsumen) {
        this.teleponKonsumen = teleponKonsumen;
    }
}
