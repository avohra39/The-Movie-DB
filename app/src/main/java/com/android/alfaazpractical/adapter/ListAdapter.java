package com.android.alfaazpractical.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.alfaazpractical.R;
import com.android.alfaazpractical.core.ClickListener;
import com.android.alfaazpractical.core.Constant;
import com.android.alfaazpractical.databinding.LayoutListBinding;
import com.android.alfaazpractical.model.Movie;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Movie> movieList;
    private ClickListener clickListener;

    public ListAdapter(Context context, ArrayList<Movie> movieList, ClickListener clickListener) {
        this.context = context;
        this.movieList = movieList;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (movieList != null) {
            if (movieList.get(position).getPosterPath() != null && !movieList.get(position).getPosterPath().isEmpty()) {
                loadImage(context, holder.binding.ivImage, Constant.BACKDROP_PATH + movieList.get(position).getPosterPath());
            }
            holder.binding.tvTitle.setText(movieList.get(position).getTitle());
            holder.binding.tvOverview.setText(movieList.get(position).getOverview());
        }
    }

    public static void loadImage(Context context, ImageView imageView, String imageUrl) {
        Glide.with(context).
                load(imageUrl)
                .apply(new RequestOptions()
                        .placeholder(R.drawable.placeholder_image)
                        .fitCenter()
                        .centerCrop()
                        .skipMemoryCache(true))
                .into(imageView);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        LayoutListBinding binding;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            binding = LayoutListBinding.bind(itemView);
            binding.cvClick.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.cvClick){
                clickListener.onItemClick(v, getAdapterPosition());
            }
        }
    }

}
