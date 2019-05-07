package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan_data;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiTransaksiPenjualan {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/transaksi_penjualans")
    Call<TransaksiPenjualan_data> tampilTransaksiPenjualan();
}
