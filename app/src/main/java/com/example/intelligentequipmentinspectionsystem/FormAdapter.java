package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {
    Context context;
    List<String> questionTitles;
    List<String> questionIds;

    public FormAdapter(Context context, List<String> questionTitles, List<String> questionIds) {
        this.context = context;
        this.questionTitles = questionTitles;
        this.questionIds = questionIds;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_row, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        holder.goodOrBad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.bad){
                    holder.reason.setVisibility(View.VISIBLE);
                } else {
                    holder.reason.setVisibility(View.GONE);
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.questionTitleTV.setText(questionTitles.get(position));
        if(holder.goodOrBad.getCheckedRadioButtonId() == R.id.bad){
            holder.reason.setVisibility(View.VISIBLE);
        } else {
            holder.reason.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return questionTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView questionTitleTV;
        RadioGroup goodOrBad;
        RadioButton good;
        RadioButton bad;
        EditText reason;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            questionTitleTV = (TextView) itemView.findViewById(R.id.questionTitle);
            goodOrBad = (RadioGroup) itemView.findViewById(R.id.goodOrBad);
            good = (RadioButton) itemView.findViewById(R.id.good);
            bad = (RadioButton) itemView.findViewById(R.id.bad);
            reason = (EditText) itemView.findViewById(R.id.reasonForBad);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.questionRow);
        }
    }
}
