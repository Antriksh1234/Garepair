package com.atandroidlabs.garepair;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Objects;

public class FaqActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    ArrayList<FAQ_Question> questions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq);
        Objects.requireNonNull(getSupportActionBar()).hide();
        questions = new ArrayList<>();
        fillQuestions();
        recyclerView =  findViewById(R.id.faq_rcv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new FAQAdapter(questions));
    }

    private void fillQuestions() {
        questions.add(new FAQ_Question(getString(R.string.lorem_small), getString(R.string.lorem_big), false));
        questions.add(new FAQ_Question(getString(R.string.lorem_small), getString(R.string.lorem_big), false));
        questions.add(new FAQ_Question(getString(R.string.lorem_small), getString(R.string.lorem_big), false));
        questions.add(new FAQ_Question(getString(R.string.lorem_small), getString(R.string.lorem_big), false));
        questions.add(new FAQ_Question(getString(R.string.lorem_small), getString(R.string.lorem_big), false));
    }

    class FAQ_Question {
        String ques, ans;
        boolean isOpen;
        FAQ_Question(String ques, String ans, boolean isOpen) {
            this.ans =  ans;
            this.isOpen = isOpen;
            this.ques = ques;
        }
    }

    class FAQAdapter extends RecyclerView.Adapter<FAQViewHolder> {

        private ArrayList<FAQ_Question> questions = new ArrayList<>();
        FAQAdapter(ArrayList<FAQ_Question> questions) {
            this.questions = questions;
        }

        @NonNull
        @Override
        public FAQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.faq_item, parent, false);
            return new FAQViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull FAQViewHolder holder, int position) {
            holder.question.setText(questions.get(position).ques);
            holder.answer.setText(questions.get(position).ans);

            holder.arrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //toggle
                    questions.get(position).isOpen = !questions.get(position).isOpen;
                    if (questions.get(position).isOpen) {
                        holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_up_24);
                        holder.answer.setVisibility(View.VISIBLE);
                    } else {
                        holder.arrow.setImageResource(R.drawable.ic_baseline_keyboard_arrow_down_24);
                        holder.answer.setVisibility(View.GONE);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return questions.size();
        }
    }

    class FAQViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private TextView question, answer;
        ImageView arrow;
        FAQViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            question = itemView.findViewById(R.id.faq_ques);
            answer = itemView.findViewById(R.id.faq_ans);
            arrow = itemView.findViewById(R.id.down_img);
        }
    }
}