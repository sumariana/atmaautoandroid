package atmaauto.atmaauto.com.atmaauto.Api;


import android.content.Intent;

import atmaauto.atmaauto.com.atmaauto.models.Sparepart_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.Part;

public interface ApiSparepart {
    String JSONURL = "https://atmauto.jasonfw.com/";
    //https://atmauto.jasonfw.com/

    @GET("api/spareparts")
    Call<Sparepart_data>tampilkatalog();

    @GET("api/spareparts/sorthargaasc")
    Call<Sparepart_data>tampilkatalogtermurah();

    @GET("api/spareparts/sorthargadesc")
    Call<Sparepart_data>tampilkatalogtermahal();

    @GET("api/spareparts/sortjumlahasc")
    Call<Sparepart_data>tampilkatalogtersedikit();

    @GET("api/spareparts/sortjumlahdesc")
    Call<Sparepart_data>tampilkatalogterbanyak();

    @GET("api/spareparts/showBelowMinimumStock")
    Call<Sparepart_data>tampilstokmin();

    @GET("api/spareparts/sorthargaascjumlahasc")
    Call<Sparepart_data>sorthargaascjumlahasc();
    @GET("api/spareparts/sorthargaascjumlahdesc")
    Call<Sparepart_data>sorthargaascjumlahdesc();
    @GET("api/spareparts/sorthargadescjumlahasc")
    Call<Sparepart_data>sorthargadescjumlahasc();
    @GET("api/spareparts/sorthargadescjumlahdesc")
    Call<Sparepart_data>sorthargadescjumlahdesc();

    @Multipart
    @POST("api/spareparts/storemobile")
    Call<ResponseBody> addSparepart(
            @Part MultipartBody.Part Gambar,
            @Part("Kode_Sparepart") RequestBody Kode_Sparepart,
            @Part("Nama_Sparepart") RequestBody Nama_Sparepart,
            @Part("Merk_Sparepart") RequestBody Merk_Sparepart,
            @Part("Tipe_Barang") RequestBody Tipe_Barang,
            @Part("Jumlah_Sparepart") RequestBody Jumlah_Sparepart,
            @Part("Stok_Minimum_Sparepart") RequestBody Stok_Minimum_Sparepart,
            @Part("Harga_Beli") RequestBody Harga_Beli,
            @Part("Harga_Jual") RequestBody Harga_Jual,
            @Part("Rak_Sparepart") RequestBody Rak_Sparepart
    );

    @Multipart
    @POST("api/spareparts/updatepicmobile")
    Call<ResponseBody> patchpicSparepart(
            @Part MultipartBody.Part Gambar,
            @Part("Kode_Sparepart") RequestBody Kode_Sparepart
    );

    @PATCH("api/spareparts/updatemobile/{Kode_Sparepart}")
    @FormUrlEncoded
    Call<ResponseBody> updatesparepart(@Path("Kode_Sparepart") String Kode_Sparepart,
                                       @Field("Nama_Sparepart") String Nama_Sparepart,
                                       @Field("Merk_Sparepart") String Merk_Sparepart,
                                       @Field("Tipe_Barang") String Tipe_Barang,
                                       @Field("Jumlah_Sparepart") Integer Jumlah_Sparepart,
                                       @Field("Stok_Minimum_Sparepart") Integer Stok_Minimum_Sparepart,
                                       @Field("Harga_Beli") Integer Harga_Beli,
                                       @Field("Harga_Jual") Integer Harga_Jual,
                                       @Field("Rak_Sparepart") String Rak_Sparepart);

    @DELETE("api/spareparts/delete/{id}")
    Call<ResponseBody>deleteSparepart(@Path("id") String id);
}
