package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {
    private Context context;
    private List<Question> questions;


    public FormAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.history_form_row, parent, false);

        final ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.equipmentTitleTV.setText(questions.get(position).getQuestionTitle());
        if (questions.get(position).getNormalOrDefective() == "normal"){
            holder.normal.setChecked(true);
        } else if(questions.get(position).getNormalOrDefective() == "defective"){
            holder.defective.setChecked(true);
        }
        holder.followUpAction.setText(questions.get(position).getFollowUpAction());
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView equipmentTitleTV;
        RadioButton normal;
        RadioButton defective;
        TextView followUpAction;
        LinearLayout linearLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            equipmentTitleTV = (TextView) itemView.findViewById(R.id.equipmentTitle);
            normal = (RadioButton) itemView.findViewById(R.id.historyNormal);
            defective = (RadioButton) itemView.findViewById(R.id.historyDefective);
            followUpAction = (TextView) itemView.findViewById(R.id.historyFollowUp);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.equipmentRow);
        }
    }
}
