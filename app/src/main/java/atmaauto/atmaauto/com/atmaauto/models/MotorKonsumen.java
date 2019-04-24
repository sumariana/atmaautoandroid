package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MotorKonsumen {

    @SerializedName("Id_Motor_Konsumen")
    @Expose
    private Integer idMotorKonsumen;
    @SerializedName("Id_Konsumen")
    @Expose
    private Integer idKonsumen;
    @SerializedName("Nama_Konsumen")
    @Expose
    private String namaKonsumen;
    @SerializedName("Id_Motor")
    @Expose
    private Integer idMotor;
    @SerializedName("Merk")
    @Expose
    private String merk;
    @SerializedName("Tipe")
    @Expose
    private String tipe;
    @SerializedName("Plat_Kendaraan")
    @Expose
    private String platKendaraan;

    public Integer getIdMotorKonsumen() {
        return idMotorKonsumen;
    }

    public void setIdMotorKonsumen(Integer idMotorKonsumen) {
        this.idMotorKonsumen = idMotorKonsumen;
    }

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

    public Integer getIdMotor() {
        return idMotor;
    }

    public void setIdMotor(Integer idMotor) {
        this.idMotor = idMotor;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getTipe() {
        return tipe;
    }

    public void setTipe(String tipe) {
        this.tipe = tipe;
    }

    public String getPlatKendaraan() {
        return platKendaraan;
    }

    public void setPlatKendaraan(String platKendaraan) {
        this.platKendaraan = platKendaraan;
    }
}
