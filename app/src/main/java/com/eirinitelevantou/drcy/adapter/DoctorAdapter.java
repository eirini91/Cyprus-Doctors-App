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

import com.eirinitelevantou.drcy.DrApp;
import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.Specialty;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Eirini Televantou on 12/10/2017.
 * televantou91@gmail.com
 * For Castleton Technology PLC
 */

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private Context context;
    private List<Doctor> doctorList;


    public DoctorAdapter(Context context, List<Doctor> doctorList) {
        this.context = context;
        this.doctorList = doctorList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_doctor, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Doctor doctor = doctorList.get(position);

        holder.name.setText(capitalize( doctor.getName()));

        holder.sex_icon.setImageDrawable(doctor.getSex()==0? ContextCompat.getDrawable(context,R.drawable.male):ContextCompat.getDrawable(context,R.drawable.female));

        Specialty specialty = null;
        List<Integer> list = new ArrayList<Integer>();
        String specialties =doctor.getSpeciality().replaceAll("\\s+","");
        specialties=specialties+",";
        for (int i = 0, j, n = specialties.length(); i < n; i = j + 1) {
            j = specialties.indexOf(",", i);
            list.add(Integer.parseInt(specialties.substring(i, j).trim()));
        }
        for(Specialty specialty1: DrApp.getInstance().getSpecialtyArrayList()){
            if(list.contains(specialty1.getId())){
                specialty = specialty1;
                break;
            }
        }
        if(specialty!=null) {

            holder.category_name.setText(capitalize( specialty.getName()));
        }
    //todo location

    }
    private String capitalize(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capitalizeGr(capMatcher.appendTail(capBuffer).toString());
    }

    private String capitalizeGr(String capString){
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([α-ω])([α-ω]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()){
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
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


        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}