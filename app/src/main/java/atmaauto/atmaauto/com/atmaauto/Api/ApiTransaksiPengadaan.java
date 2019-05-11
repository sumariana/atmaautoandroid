package atmaauto.atmaauto.com.atmaauto.Api;

import java.util.Date;

import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan_data;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPengadaan_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiTransaksiPengadaan {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/transaksi_pengadaans")
    Call<TransaksiPengadaan_data> tampiltransaksipengadaan();

    @GET("api/detail_pengadaans/showByIdPengadaan/{id}")
    Call<DetailPengadaan_data> tampildetilpengadaan(@Path("id") Integer id);

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

    @DELETE("api/detail_pengadaans/delete/{id}")
    Call<ResponseBody>deletedetailpengadaan(@Path("id") Integer id);

    @DELETE("api/transaksi_pengadaans/delete/{id}")
    Call<ResponseBody>deletetransaksipengadaan(@Path("id") Integer id);

    @PATCH("api/transaksi_pengadaans/updatemobile/{id}")
    @FormUrlEncoded
    Call<ResponseBody> updatetransaksipengadaan(@Path("id") Integer id,
                                       @Field("Tanggal_Pengadaan") String Tanggal_Pengadaan,
                                       @Field("Total_Harga") Double Total_Harga);
}
