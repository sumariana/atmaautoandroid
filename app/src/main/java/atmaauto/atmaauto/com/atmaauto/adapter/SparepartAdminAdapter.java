package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SparepartAdminAdapter extends RecyclerView.Adapter<SparepartAdminAdapter.MyViewHolder> {

    private Context context;
    private List<Sparepart> mList;

    public SparepartAdminAdapter(Context context,List<Sparepart> mList){
        this.context=context;
        this.mList=mList;
    }

    @Override
    public SparepartAdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sparepartadmin_adapter, viewGroup, false);
        return new SparepartAdminAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SparepartAdminAdapter.MyViewHolder myViewHolder, int i) {
        final Sparepart sparepart = mList.get(i);
        myViewHolder.namasparepart.setText(sparepart.getNamaSparepart());
        myViewHolder.jumlahsparepart.setText("Sisa Stok : "+sparepart.getJumlahSparepart());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailSparepart.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("kodesp", sparepart.getKodeSparepart());
                intent.putExtra("namasp", sparepart.getNamaSparepart());
                intent.putExtra("tipesp", sparepart.getTipeBarang());
                intent.putExtra("merksp", sparepart.getMerkSparepart());
                intent.putExtra("raksp", sparepart.getRakSparepart());
                intent.putExtra("jmlsp", sparepart.getJumlahSparepart());
                intent.putExtra("jmlminsp", sparepart.getStokMinimumSparepart());
                intent.putExtra("hargasp", sparepart.getHargaJual());
                intent.putExtra("hargabelisp", sparepart.getHargaBeli());
                intent.putExtra("gambarsp", sparepart.getGambar());
                context.startActivity(intent);
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bild Gson
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiSparepart.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiSparepart apiSparepart = retrofit.create(ApiSparepart.class);

                //Calling API
                Call<ResponseBody> responseBodyCall=apiSparepart.deleteSparepart(sparepart.getKodeSparepart());
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
        public TextView namasparepart,jumlahsparepart;
        public ConstraintLayout kotak;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namasparepart=itemView.findViewById(R.id.nama_sparepartadmin_adapter);
            jumlahsparepart=itemView.findViewById(R.id.jumlah_sparepartadmin_adapter);
            delete=itemView.findViewById(R.id.deletebuttonadapter);

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
