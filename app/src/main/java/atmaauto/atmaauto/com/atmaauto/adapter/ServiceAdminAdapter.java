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
import android.widget.Filter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiJasaService;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailSparepart;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.TambahJasaService;
import atmaauto.atmaauto.com.atmaauto.models.Jasa;
import atmaauto.atmaauto.com.atmaauto.models.Sparepart;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceAdminAdapter extends RecyclerView.Adapter<ServiceAdminAdapter.MyViewHolder>{
    private Context context;
    private List<Jasa> mList;
    private List<Jasa> mListfilter;

    public ServiceAdminAdapter(Context context,List<Jasa> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }

    @Override
    public ServiceAdminAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.servis_adapter, viewGroup, false);
        return new ServiceAdminAdapter.MyViewHolder(itemView);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<Jasa> filteredList = new ArrayList<>();
                    for (Jasa obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getNamaJasa().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(obj);
                        }
                    }

                    mListfilter = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mListfilter;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mListfilter = (ArrayList<Jasa>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView namaservice,hargaservice;
        public LinearLayout kotak;
        Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.kotak);
            namaservice=itemView.findViewById(R.id.namaservis);
            hargaservice=itemView.findViewById(R.id.hargaservis);
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
        return mListfilter.size();
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdminAdapter.MyViewHolder myViewHolder, final int i) {
        final Jasa jasa = mListfilter.get(i);
        myViewHolder.namaservice.setText(jasa.getNamaJasa());
        myViewHolder.hargaservice.setText("Harga Jasa: "+jasa.getHargaJasa());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, TambahJasaService.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idjasa", jasa.getIdJasa());
                intent.putExtra("namajasa", jasa.getNamaJasa());
                intent.putExtra("hargajasa", jasa.getHargaJasa());
                intent.putExtra("editable", 1);
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
                ApiJasaService apiJasaService = retrofit.create(ApiJasaService.class);

                //Calling API
                Call<ResponseBody> responseBodyCall=apiJasaService.deleteservice(jasa.getIdJasa());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                        {
                            Toast.makeText(context,"berhasil",Toast.LENGTH_SHORT).show();
                            mListfilter.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,getItemCount());
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
}
