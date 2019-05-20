package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class Jasa {

    @SerializedName("Id_Jasa")
    @Expose
    private Integer idJasa;
    @SerializedName("Nama_Jasa")
    @Expose
    private String namaJasa;
    @SerializedName("Harga_Jasa")
    @Expose
    private String hargaJasa;

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

    public String getHargaJasa() {
        return hargaJasa;
    }

    public void setHargaJasa(String hargaJasa) {
        this.hargaJasa = hargaJasa;
    }
}
