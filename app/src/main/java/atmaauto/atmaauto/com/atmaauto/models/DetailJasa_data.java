package atmaauto.atmaauto.com.atmaauto.models;


import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DetailJasa_data {


    @SerializedName("data")
    @Expose
    private List<DetailJasa> data = null;

    public List<DetailJasa> getData() {
        return data;
    }

    public void setData(List<DetailJasa> data) {
        this.data = data;
    }
}
