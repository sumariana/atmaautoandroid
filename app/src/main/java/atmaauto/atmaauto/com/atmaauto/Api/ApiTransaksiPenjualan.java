package atmaauto.atmaauto.com.atmaauto.Api;

import android.content.Intent;

import atmaauto.atmaauto.com.atmaauto.models.DetailJasa_data;
import atmaauto.atmaauto.com.atmaauto.models.Konsumen_data;
import atmaauto.atmaauto.com.atmaauto.models.Pegawai_data;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import atmaauto.atmaauto.com.atmaauto.models.TransaksiPenjualan_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiTransaksiPenjualan {
    String JSONURL = "https://atmauto.jasonfw.com/";

    @GET("api/transaksi_penjualans")
    Call<TransaksiPenjualan_data> tampilTransaksiPenjualan();

    @GET("api/pegawais")
    Call<Pegawai_data> tampilmontir();

    @GET("api/detail_jasas")
    Call<DetailJasa_data> tampilservis();

    @GET("api/transaksi_penjualans/transaksikeluar")
    Call<TransaksiPenjualan_data> transaksikeluar();

    @GET("api/transaksi_penjualans/delete/{id}")
    Call<ResponseBody> deletetransaksi(@Path("id") Integer id);

    @GET("api/transaksi_penjualans/showByIdMotorKonsumen/{Id_Motor}")
    Call<Sparepart_data> tampilsparepartmotor(@Path("Id_Motor") Integer Id_Motor);

    @POST("api/transaksi_penjualans/store")
    @FormUrlEncoded
    Call<ResponseBody> addPenjualansparepart(@Field("Id_Konsumen") Integer Id_Konsumen,
                                    @Field("Tanggal_Transaksi") String Tanggal_Transaksi,
                                    @Field("Jenis_Transaksi") String Jenis_Transaksi,
                                    @Field("Subtotal") Double Subtotal,
                                    @Field("Diskon") Integer Diskon,
                                    @Field("Id_Montir") Integer Id_Montir,
                                    @Field("Id_Motor_Konsumen") Integer Id_Motor_Konsumen,
                                    @Field("Id_Pegawai") Integer Id_Pegawai);

    @POST("api/transaksi_penjualans/storeSparepart")
    @FormUrlEncoded
    Call<ResponseBody> adddetailpenjualansparepart(@Field("Id_Transaksi") Integer Id_Transaksi,
                                          @Field("Id_Jasa_Montir") Integer Id_Jasa_Montir,
                                          @Field("Kode_Sparepart") String Kode_Sparepart,
                                          @Field("Harga_Satuan") Double Harga_Satuan,
                                          @Field("Jumlah") Integer Jumlah,
                                          @Field("Subtotal_Detail_Sparepart") Double Subtotal_Detail_Sparepart);

    @POST("api/transaksi_penjualans/storeJasa")
    @FormUrlEncoded
    Call<ResponseBody> adddetailpenjualanjasa(@Field("Id_Transaksi") Integer Id_Transaksi,
                                                   @Field("Id_Jasa") Integer Id_Jasa,
                                                   @Field("Id_Jasa_Montir") Integer Id_Jasa_Montir,
                                                   @Field("Subtotal_Detail_Jasa") Double Subtotal_Detail_Jasa);

//    @POST("api/montirs/store")
//    @FormUrlEncoded
//    Call<ResponseBody> addmontir(@Field("Id_Pegawai") Integer Id_Pegawai,
//                                                   @Field("Id_Motor_Konsumen") Integer Id_Motor_Konsumen);

}
