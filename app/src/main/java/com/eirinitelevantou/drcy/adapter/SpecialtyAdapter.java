package com.eirinitelevantou.drcy.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.model.Specialty;

import java.util.List;

/**
 * Created by Eirini Televantou on 12/10/2017.
 * televantou91@gmail.com
 * For Castleton Technology PLC
 */

public class SpecialtyAdapter extends RecyclerView.Adapter<SpecialtyAdapter.MyViewHolder> {
private Context context;
    private List<Specialty> specialityList;
    OnSpecialtyClickedListener onSpecialtyClickedListener;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public ImageView imageView;
        public LinearLayout layout;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.category_name);
            imageView = (ImageView) view.findViewById(R.id.category_icon);
            layout = (LinearLayout) view.findViewById(R.id.layout);

        }
    }


    public SpecialtyAdapter(Context context,OnSpecialtyClickedListener onSpecialtyClickedListener, List<Specialty> specialityList) {
        this.context = context;
        this.onSpecialtyClickedListener =onSpecialtyClickedListener;
        this.specialityList = specialityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_specialty, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final Specialty specialty = specialityList.get(position);
        holder.name.setText(specialty.getName());
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(context,specialty.getDrawable()));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSpecialtyClickedListener.onSpecialtyClicked(specialty);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialityList.size();
    }

    public interface OnSpecialtyClickedListener{
        void onSpecialtyClicked(Specialty specialty);
    }
}