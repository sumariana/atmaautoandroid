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
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import atmaauto.atmaauto.com.atmaauto.Api.ApiKonsumen;
import atmaauto.atmaauto.com.atmaauto.Api.ApiSparepart;
import atmaauto.atmaauto.com.atmaauto.DetilList.DetailMotorKonsumen;
import atmaauto.atmaauto.com.atmaauto.R;
import atmaauto.atmaauto.com.atmaauto.models.MotorKonsumen;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MotorKonsumenAdapter extends RecyclerView.Adapter<MotorKonsumenAdapter.MyViewHolder>{
    private Context context;
    private List<MotorKonsumen> mList;
    private List<MotorKonsumen> mListfilter;

    public MotorKonsumenAdapter(Context context, List<MotorKonsumen> mList){
        this.context=context;
        this.mList=mList;
        this.mListfilter=mList;
    }
    @Override
    public MotorKonsumenAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.motorkonsumen_adapter, viewGroup, false);
        return new MyViewHolder(itemView);
    }

    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mListfilter = mList;
                } else {
                    List<MotorKonsumen> filteredList = new ArrayList<>();
                    for (MotorKonsumen obj : mList) {

                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or phone number match
                        if (obj.getTipe().toLowerCase().contains(charString.toLowerCase())) {
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
                mListfilter = (ArrayList<MotorKonsumen>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
    @Override
    public void onBindViewHolder(@NonNull MotorKonsumenAdapter.MyViewHolder myViewHolder, final int i) {
        final MotorKonsumen motorKonsumen = mListfilter.get(i);
        myViewHolder.namamotor.setText(motorKonsumen.getTipe());
        myViewHolder.platmotor.setText(motorKonsumen.getPlatKendaraan());
        myViewHolder.kotak.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Toast.makeText(v.getContext(), "Item clicked", Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, DetailMotorKonsumen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("idmkn", motorKonsumen.getIdMotorKonsumen().toString());
                intent.putExtra("idkn", motorKonsumen.getIdKonsumen().toString());
                intent.putExtra("idm", motorKonsumen.getIdMotor().toString());
                intent.putExtra("plat", motorKonsumen.getPlatKendaraan());
                context.startActivity(intent);
            }
        });

        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit.Builder builder=new Retrofit.
                        Builder().baseUrl(ApiSparepart.JSONURL).
                        addConverterFactory(GsonConverterFactory.create(gson));
                Retrofit retrofit=builder.build();
                ApiKonsumen apiKonsumen = retrofit.create(ApiKonsumen.class);

                Call<ResponseBody> responseBodyCall=apiKonsumen.deletemotorkonsumen(motorKonsumen.getIdMotorKonsumen());
                responseBodyCall.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.code()==200)
                        {
                            mListfilter.remove(i);
                            notifyItemRemoved(i);
                            notifyItemRangeChanged(i,getItemCount());
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
        public TextView namamotor, platmotor;
        public ConstraintLayout kotak;
        public Button delete;

        public MyViewHolder(@NonNull View itemView){
            super(itemView);

            kotak=itemView.findViewById(R.id.kotak);
            namamotor=itemView.findViewById(R.id.namamotorkonsumenadapter);
            platmotor=itemView.findViewById(R.id.platmotorkonsumenadapter);
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
}
