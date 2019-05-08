package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Montir {
    @SerializedName("Id_Jasa_Montir")
    @Expose
    private Integer Id_Jasa_Montir;
    @SerializedName("Id_Pegawai")
    @Expose
    private Integer Id_Pegawai;
    @SerializedName("Id_Motor_Konsumen")
    @Expose
    private Integer Id_Motor_Konsumen;

    public Montir(Integer id_Jasa_Montir, Integer id_Pegawai, Integer id_Motor_Konsumen) {
        Id_Jasa_Montir = id_Jasa_Montir;
        Id_Pegawai = id_Pegawai;
        Id_Motor_Konsumen = id_Motor_Konsumen;
    }

    public Integer getId_Jasa_Montir() {
        return Id_Jasa_Montir;
    }

    public void setId_Jasa_Montir(Integer id_Jasa_Montir) {
        Id_Jasa_Montir = id_Jasa_Montir;
    }

    public Integer getId_Pegawai() {
        return Id_Pegawai;
    }

    public void setId_Pegawai(Integer id_Pegawai) {
        Id_Pegawai = id_Pegawai;
    }

    public Integer getId_Motor_Konsumen() {
        return Id_Motor_Konsumen;
    }

    public void setId_Motor_Konsumen(Integer id_Motor_Konsumen) {
        Id_Motor_Konsumen = id_Motor_Konsumen;
    }
}
