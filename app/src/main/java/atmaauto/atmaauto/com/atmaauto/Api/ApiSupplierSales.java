package atmaauto.atmaauto.com.atmaauto.Api;

import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiSupplierSales {
    @GET("api/suppliers")
    Call<Supplier_data>tampilSupplier();

    @POST("api/suppliers/store")
    @FormUrlEncoded
    Call<Supplier_response>tambahSupplier(@Field("Nama_Supplier") String Nama_Supplier,
                                          @Field("Alamat_Supplier") String Alamat_Supplier,
                                          @Field("Telepon_Supplier") String Telepon_Supplier,
                                          @Field("Nama_Sales") String Nama_Sales,
                                          @Field("Telepon_Sales") String Telepon_Sales);

    @PATCH("api/suppliers/delSales/{id}")
    Call<ResponseBody>hapusSales(@Path("id") Integer id);

    @PATCH("api/suppliers/upSales/{id}")
    @FormUrlEncoded
    Call<ResponseBody>updateSales(@Field("Nama_Sales") String Nama_Sales,
                                  @Field("Telepon_Sales") String Telepon_Sales,
                                  @Path("id") Integer id);
    @PATCH("api/suppliers/update/{id}")
    @FormUrlEncoded
    Call<ResponseBody>updateSupplier(@Field("Nama_Supplier") String Nama_Supplier,
                                     @Field("Alamat_Supplier") String Alamat_Supplier,
                                     @Field("Telepon_Supplier") String Telepon_Supplier,
                                     @Field("Nama_Sales") String Nama_Sales,
                                     @Field("Telepon_Sales") String Telepon_Sales,
                                     @Path("id") Integer id);
}
