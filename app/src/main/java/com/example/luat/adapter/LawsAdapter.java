package com.example.luat.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.widget.TextViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.luat.R;
import com.example.luat.Utils;
import com.example.luat.activities.LawContentActivity;
import com.example.luat.model.Law;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LawsAdapter extends RecyclerView.Adapter<LawsAdapter.ViewHolder> {
    private ArrayList<Law> laws;
    private Context context;

    public LawsAdapter(ArrayList<Law> laws, Context context) {
        this.laws = laws;
        this.context = context;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tvTitle)
        AppCompatTextView tvTitle;

        @BindView(R.id.tvDateIssued)
        AppCompatTextView tvDateIssued;

        @BindView(R.id.tvDateUpdated)
        AppCompatTextView tvDateUpdated;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.law, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Law law = laws.get(position);
        String lawStr = law.getTitle();
        lawStr = lawStr.replaceAll("[\r\n]+", " ");
        ((AppCompatTextView) holder.tvTitle).setText(lawStr);
        ((AppCompatTextView) holder.tvDateIssued).setText(Utils.FormatDate(law.getDate_issued()));
        ((AppCompatTextView) holder.tvDateUpdated).setText(Utils.FormatDate(law.getDate_updated()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newLaw = new Intent(context, LawContentActivity.class);
                newLaw.putExtra("key", law.getKey());
                context.startActivity(newLaw);
            }
        });
    }

    @Override
    public int getItemCount() {
        return laws.size();
    }
}
