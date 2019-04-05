package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pegawai {

    @SerializedName("Id_Role")
    @Expose
    private Integer idRole;
    @SerializedName("Id_Cabang")
    @Expose
    private Integer idCabang;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("Nama_Pegawai")
    @Expose
    private String namaPegawai;
    @SerializedName("Alamat")
    @Expose
    private String alamat;
    @SerializedName("Telepon_Pegawai")
    @Expose
    private String teleponPegawai;
    @SerializedName("Gaji_Pegawai")
    @Expose
    private Integer gajiPegawai;
    @SerializedName("Username")
    @Expose
    private String username;
    @SerializedName("Password")
    @Expose
    private String password;

    public Integer getIdRole() {
        return idRole;
    }

    public void setIdRole(Integer idRole) {
        this.idRole = idRole;
    }

    public Integer getIdCabang() {
        return idCabang;
    }

    public void setIdCabang(Integer idCabang) {
        this.idCabang = idCabang;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNamaPegawai() {
        return namaPegawai;
    }

    public void setNamaPegawai(String namaPegawai) {
        this.namaPegawai = namaPegawai;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getTeleponPegawai() {
        return teleponPegawai;
    }

    public void setTeleponPegawai(String teleponPegawai) {
        this.teleponPegawai = teleponPegawai;
    }

    public Integer getGajiPegawai() {
        return gajiPegawai;
    }

    public void setGajiPegawai(Integer gajiPegawai) {
        this.gajiPegawai = gajiPegawai;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
