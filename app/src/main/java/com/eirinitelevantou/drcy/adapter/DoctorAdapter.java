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
import com.eirinitelevantou.drcy.model.Doctor;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.eirinitelevantou.drcy.util.ProjectUtils.capitalize;

/**
 * Created by Eirini Televantou on 12/10/2017.
 * televantou91@gmail.com
 * For Castleton Technology PLC
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context context;
    private List<Doctor> doctorList;
    private OnDoctorClickedListener onDoctorClickedListener;


    public DoctorAdapter(Context context,OnDoctorClickedListener onDoctorClickedListener, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
        this.onDoctorClickedListener = onDoctorClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_doctor, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Doctor doctor = doctorList.get(position);

        holder.name.setText(capitalize( doctor.getName()));

        holder.sex_icon.setImageDrawable(doctor.getSex()==0? ContextCompat.getDrawable(context,R.drawable.male):ContextCompat.getDrawable(context,R.drawable.female));



            holder.category_name.setText(capitalize( doctor.getCommaSeparatedSpecialties()));

        switch (doctor.getCity()){
            case 0:{
                holder.location_icon.setImageDrawable( ContextCompat.getDrawable(context,R.drawable.nicosia_hi_trimmed));
                break;
            }
            case 1:{
                holder.location_icon.setImageDrawable( ContextCompat.getDrawable(context,R.drawable.limasol_hi_trimmed));

                break;
            }
            case 2:{
                holder.location_icon.setImageDrawable( ContextCompat.getDrawable(context,R.drawable.larnaca_hi_trimmed));

                break;
            }
            case 3:{
                holder.location_icon.setImageDrawable( ContextCompat.getDrawable(context,R.drawable.paphos_hi_trimmed));

                break;
            }  case 4:{
                holder.location_icon.setImageDrawable( ContextCompat.getDrawable(context,R.drawable.famagusta_hi_trimmed));

                break;
            }


        }
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDoctorClickedListener.onDoctorClicked(doctor.getId());
            }
        });

    }


    @Override
    public int getItemCount() {
        return doctorList.size();
    }

    static class ViewHolder  extends RecyclerView.ViewHolder {
        @BindView(R.id.sex_icon)
        ImageView sex_icon;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.category_name)
        TextView category_name;
        @BindView(R.id.location_icon)
        ImageView location_icon;

        @BindView(R.id.layout)
        LinearLayout layout;

        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }



    }
    public interface OnDoctorClickedListener{
        void onDoctorClicked(int doctorId);
    }
}