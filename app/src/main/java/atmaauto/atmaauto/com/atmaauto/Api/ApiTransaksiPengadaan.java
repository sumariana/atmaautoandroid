package atmaauto.atmaauto.com.atmaauto.Api;

import java.util.Date;

import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiTransaksiPengadaan {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/transaksi_pengadaans")
    Call<TransaksiPengadaan_data> tampiltransaksipengadaan();

    @POST("api/transaksi_pengadaans/store")
    @FormUrlEncoded
    Call<ResponseBody> addpengadaan(@Field("Id_Supplier") Integer Id_Supplier,
                                    @Field("Tanggal_Pengadaan") String Tanggal_Pengadaan,
                                    @Field("Total_Harga") Double Total_Harga,
                                    @Field("Status_Pengadaan") Integer Status_Pengadaan);

    @POST("api/transaksi_pengadaans/storeDetail")
    @FormUrlEncoded
    Call<ResponseBody> adddetailpengadaan(@Field("Id_Pengadaan") Integer Id_Pengadaan,
                                    @Field("Kode_Sparepart") String Kode_Sparepart,
                                    @Field("Harga_Satuan") Double Harga_Satuan,
                                    @Field("Jumlah") Integer Jumlah,
                                    @Field("Subtotal_Pengadaan") Double Subtotal_Pengadaan);
}
