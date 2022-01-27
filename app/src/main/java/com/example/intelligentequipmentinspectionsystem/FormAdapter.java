package com.example.intelligentequipmentinspectionsystem;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.ViewHolder> {
    private Context context;
    private List<Question> questions;
//    private List<String> questionTitles;
//    private List<String> questionIds;
//    private List<String> toDoList;
//    HashMap<String, String> toDoList = new HashMap<>();
//    private boolean warningMode = false;


    public FormAdapter(Context context, List<Question> questions) {
        this.context = context;
        this.questions = questions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.question_row, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        holder.goodOrBad.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.bad) {
                    // selected bad, show reason editText
                    questions.get(holder.getAdapterPosition()).setGoodOrBad("bad");
                    // show reason editText
                    holder.reason.setVisibility(View.VISIBLE);
                } else {
                    // selected good, remove reason for bad
                    holder.reason.setVisibility(View.GONE);
                    questions.get(holder.getAdapterPosition()).setGoodOrBad("good");
                    questions.get(holder.getAdapterPosition()).setReason("");
                }
            }
        });

        // record the reason
        holder.reason.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                questions.get(holder.getAdapterPosition()).setReason(editable.toString());
                System.out.println("Reason: " + questions.get(holder.getAdapterPosition()).getReason());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.questionTitleTV.setText(questions.get(position).getQuestionTitle());
//        if(warningMode){
//            System.out.println("it ran");
//            if(questionIds.get(holder.getAdapterPosition()).contains(toDoList.toString())){
//                holder.questionTitleTV.setTextColor(Color.RED);
//            }
//        }
//        if(holder.goodOrBad.getCheckedRadioButtonId() == R.id.bad){
//            holder.reason.setVisibility(View.VISIBLE);
//        } else {
//            holder.reason.setVisibility(View.GONE);
//        }
    }

    @Override
    public int getItemCount() {
        return questions.size();
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public String validation() {
        for (int i=0; i<questions.size() ; i++){
            if(questions.get(i).getGoodOrBad()=="null"){
                System.out.println("Validation: Missing");
                return "missing";
            }
        }
        for (int i=0; i<questions.size() ; i++){
            if(questions.get(i).getGoodOrBad()=="bad"){
                System.out.println("Validation: bad");
                return "pic";
            }
        }
        System.out.println("Validation: pass");
        return "pass";

    }
}
