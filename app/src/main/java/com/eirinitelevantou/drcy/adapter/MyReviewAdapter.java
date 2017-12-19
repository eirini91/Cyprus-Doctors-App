package com.eirinitelevantou.drcy.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.eirinitelevantou.drcy.R;
import com.eirinitelevantou.drcy.model.Doctor;
import com.eirinitelevantou.drcy.model.Review;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;

/**
 * Created by Eirini Televantou on 12/10/2017.
 * televantou91@gmail.com
 * For Castleton Technology PLC
 */

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.ViewHolder> {
    private Context context;
    private List<Review> reviewList;
    private OnReviewClickedListener onReviewClickedListener;

    public MyReviewAdapter(Context context, OnReviewClickedListener onReviewClickedListener, List<Review> reviewList) {
        this.context = context;
        this.reviewList = reviewList;
        this.onReviewClickedListener = onReviewClickedListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row__my_review, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Review review = reviewList.get(position);
        Doctor doctor = Realm.getDefaultInstance().where(Doctor.class).equalTo("Id", review.getDoctorId()).findFirst();
        holder.name.setText(doctor.getName());
            Picasso.with(context)
                    .load(review.getUserImageUrl())
                    .resize(150, 150)
                    .centerCrop()
                    .placeholder(R.drawable.ic_user)
                    .into(holder.userIcon);

        holder.review.setText(review.getReview());
        holder.anonymous.setVisibility(review.getAnonymised()?View.VISIBLE:View.GONE);
        holder.ratingBar.setRating(Float.valueOf(String.valueOf(review.getRating())));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onReviewClickedListener.onReviewClicked(review);
            }
        });

        if (review.getHide()) {
            holder.cancelled.setVisibility(View.VISIBLE);
        } else {
            holder.cancelled.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return reviewList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.user_icon)
        ImageView userIcon;
        @BindView(R.id.view)
        View view;
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.rating_bar)
        RatingBar ratingBar;
        @BindView(R.id.review)
        TextView review;
        @BindView(R.id.layout)
        LinearLayout layout;
        @BindView(R.id.cancelled)
        TextView cancelled;
        @BindView(R.id.switch_anonymous)
        TextView anonymous;



        ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

    public interface OnReviewClickedListener {
        void onReviewClicked(Review review);
    }

}