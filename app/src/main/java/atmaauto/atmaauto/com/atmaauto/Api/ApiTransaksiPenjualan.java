package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiTransaksiPenjualan {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/transaksi_penjualans")
    Call<TransaksiPenjualan_data> tampilTransaksiPenjualan();

    @POST("api/transaksi_penjualans/store")
    @FormUrlEncoded
    Call<ResponseBody> addPenjualansparepart(@Field("Id_Konsumen") Integer Id_Konsumen,
                                    @Field("Tanggal_Transaksi") String Tanggal_Transaksi,
                                    @Field("Jenis_Transaksi") String Jenis_Transaksi,
                                    @Field("Subtotal") Double Subtotal,
                                    @Field("Diskon") Integer Diskon,
                                    @Field("Total") Double Total,
                                    @Field("Status") Integer Status);

    @POST("api/transaksi_penjualans/storeDetail")
    @FormUrlEncoded
    Call<ResponseBody> adddetailpenjualansparepart(@Field("Id_Transaksi") Integer Id_Transaksi,
                                          @Field("Id_Jasa_Montir") Integer Id_Jasa_Montir,
                                          @Field("Kode_Sparepart") String Kode_Sparepart,
                                          @Field("Harga_Satuan") Double Harga_Satuan,
                                          @Field("Jumlah") Integer Jumlah,
                                          @Field("Subtotal_Detail_Sparepart") Double Subtotal_Detail_Sparepart);

    @POST("api/montirs/store")
    @FormUrlEncoded
    Call<ResponseBody> addmontir(@Field("Id_Pegawai") Integer Id_Pegawai,
                                                   @Field("Id_Motor_Konsumen") Integer Id_Motor_Konsumen);

}
