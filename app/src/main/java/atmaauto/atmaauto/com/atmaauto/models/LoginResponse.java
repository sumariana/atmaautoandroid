package atmaauto.atmaauto.com.atmaauto.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("data")
    @Expose
    private Pegawai pegawai;

    public Pegawai getData() {
        return pegawai;
    }

    public void setData(Pegawai pegawai) {
        this.pegawai = pegawai;
    }

}
