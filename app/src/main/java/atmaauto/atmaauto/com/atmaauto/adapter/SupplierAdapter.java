package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;


import atmaauto.atmaauto.com.atmaauto.Api.ApiSupplierSales;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSupplier;
import atmaauto.atmaauto.com.atmaauto.MenuSupplier;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Supplier;
import atmaauto.atmaauto.com.atmaauto.models.Supplier_data;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{

    private Context context;
    private List<Supplier> mList;

    public SupplierAdapter(Context context,List<Supplier> mList){
        this.context=context;
        this.mList=mList;
    }

    @Override
    public SupplierAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.supplier_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SupplierAdapter.MyViewHolder myViewHolder, int i) {
        final Supplier supplier = mList.get(i);
        myViewHolder.mNamaSupplier.setText(supplier.getNamaSupplier());
        myViewHolder.mTeleponSupplier.setText(supplier.getTeleponSupplier());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailSupplier.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idsup", supplier.getIdSupplier().toString());
                intent.putExtra("tempid", supplier.getIdSupplier());
                intent.putExtra("namasup", supplier.getNamaSupplier());
                intent.putExtra("alamatsup", supplier.getAlamatSupplier());
                intent.putExtra("telpsup", supplier.getTeleponSupplier());
                intent.putExtra("namasal", supplier.getNamaSales());
                intent.putExtra("telpsal", supplier.getTeleponSales());
                context.startActivity(intent);
            }
        });

        myViewHolder.mDeleteSupplier.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Bild Gson
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl("http://10.53.12.16:8080").
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiSupplierSales apiSupplierSales = retrofit.create(ApiSupplierSales.class);

                //Calling API
                Call<ResponseBody> responseBodyCall=apiSupplierSales.deleteSupplier(supplier.getIdSupplier());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(context,"berhasil",Toast.LENGTH_SHORT).show();

                        }else
                            Toast.makeText(context,"gagal",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context,"network error",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

   public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mNamaSupplier, mTeleponSupplier;
        public Button mDeleteSupplier;
        public ConstraintLayout kotak;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.listSupplier);
            mNamaSupplier=itemView.findViewById(R.id.nama_supplier_adapter);
            mTeleponSupplier=itemView.findViewById(R.id.telepon_supplier_adapter);
            mDeleteSupplier=itemView.findViewById(R.id.deletebuttonadapter);


            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){}
            });
        }

       @Override
       public void onClick(View v) {

       }
   }

   @Override
    public int getItemCount(){
        return mList.size();
   }
}
