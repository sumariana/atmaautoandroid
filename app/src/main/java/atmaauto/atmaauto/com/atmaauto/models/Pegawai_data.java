package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Pegawai_data {
    @SerializedName("data")
    @Expose
    private List<Pegawai> data = null;

    public List<Pegawai> getData() {
        return data;
    }

    public void setData(List<Pegawai> data) {
        this.data = data;
    }
}
