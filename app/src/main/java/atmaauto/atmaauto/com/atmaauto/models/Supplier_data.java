package atmaauto.atmaauto.com.atmaauto.models;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Supplier_data {
    @SerializedName("data")
    @Expose
    private List<Supplier> suppliers = new ArrayList<>();

    public List<Supplier> getData() {
        return suppliers;
    }

    public void setData(List<Supplier> data) {
        this.suppliers = data;
    }
}
