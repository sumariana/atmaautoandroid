package atmaauto.atmaauto.com.atmaauto.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiTransaksiPenjualan;
import atmaauto.atmaauto.com.atmaauto.models.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.DetailPengadaan;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PenjualanSparepartAdapter extends RecyclerView.Adapter<PenjualanSparepartAdapter.MyViewHolder>{

    private Context context;
    public int x;
    private List<DetailSparepart> mList;
    private List<DetailSparepart> mListfilter;

    public PenjualanSparepartAdapter(Context context,List<DetailSparepart> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    public PenjualanSparepartAdapter(Context context,List<DetailSparepart> mList, final int x){
        this.x=x;
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public PenjualanSparepartAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.sparepartcart_adapter, viewGroup, false);
        return new PenjualanSparepartAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PenjualanSparepartAdapter.MyViewHolder myViewHolder, final int i) {
        final DetailSparepart detailSparepart = mListfilter.get(i);
        myViewHolder.namasparepart.setText(detailSparepart.getNamaSparepart()+" : "+detailSparepart.getKodeSparepart());
        myViewHolder.jumlahsparepart.setText("Jumlah  : "+detailSparepart.getJumlah());

        if(x==1)
        {
            myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailSparepart.getIdDetailSparepart()==null)
                    {
                        Double harga = detailSparepart.getSubtotalDetailSparepart();
                        Intent intent = new Intent("custom-message");
                        intent.putExtra("harga",harga);
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        mListfilter.remove(i);
                        notifyItemRemoved(i);
                        notifyItemRangeChanged(i,getItemCount());
                    }else{
                        Gson gson = new GsonBuilder()
                                .setLenient()
                                .create();
                        Retrofit.Builder builder=new Retrofit.
                                Builder().baseUrl(ApiTransaksiPenjualan.JSONURL).
                                addConverterFactory(GsonConverterFactory.create(gson));
                        Retrofit retrofit=builder.build();
                        ApiTransaksiPenjualan apiTransaksiPenjualan=retrofit.create(ApiTransaksiPenjualan.class);

                        Call<ResponseBody> responseBodyCall = apiTransaksiPenjualan.deletedetailsparepart(detailSparepart.getIdDetailSparepart());
                        responseBodyCall.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code()==200)
                                {
                                    Double harga = detailSparepart.getSubtotalDetailSparepart();
                                    Intent intent = new Intent("custom-message");
                                    intent.putExtra("harga",harga);
                                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                                    mListfilter.remove(i);
                                    notifyItemRemoved(i);
                                    notifyItemRangeChanged(i,getItemCount());
                                    Log.d( "sucess: ",response.message());
                                }else
                                    Log.d( "gagal: ",response.message());
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }else if(x==0){
            myViewHolder.delete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return mListfilter.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namasparepart,jumlahsparepart;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            //kotak=itemView.findViewById(R.id.listSparepartAdmin);
            namasparepart=itemView.findViewById(R.id.nama_sparepartcart_adapter);
            jumlahsparepart=itemView.findViewById(R.id.jumlah_sparepartcart_adapter);
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
}
