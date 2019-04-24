package atmaauto.atmaauto.com.atmaauto.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Motor {
    @SerializedName("Id_Motor")
    @Expose
    private Integer idMotor;
    @SerializedName("Merk")
    @Expose
    private String merk;
    @SerializedName("Tipe")
    @Expose
    private String tipe;

    public Motor(){

    }
    public Motor(Integer idMotor,String merk,String tipe)
    {
        super();
        this.idMotor=idMotor;
        this.merk=merk;
        this.tipe=tipe;
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
}
