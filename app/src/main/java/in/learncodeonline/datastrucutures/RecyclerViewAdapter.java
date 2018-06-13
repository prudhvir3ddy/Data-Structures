package in.learncodeonline.datastrucutures;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static int currentPosition = -1;
    private ArrayList<QuestionsModel> mList;
    private Context mContext;

    public RecyclerViewAdapter(ArrayList<QuestionsModel> list, Context context) {
        mList = list;
        mContext = context;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_model, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewAdapter.ViewHolder holder, final int position) {
        QuestionsModel questionsModel = mList.get(position);
        holder.textView.setText(questionsModel.getQuestion());
        holder.textView2.setText(questionsModel.getAnswer());
        holder.textView2.setVisibility(View.GONE);
        holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
        if (currentPosition == position) {
            holder.textView2.setVisibility(View.VISIBLE);
            holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPosition == position) {
                    currentPosition = -1;
                    holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_down_float, 0);
                    notifyDataSetChanged();
                } else {
                    currentPosition = position;
                    holder.textView.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.R.drawable.arrow_up_float, 0);
                    notifyDataSetChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView, textView2;
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            textView2 = itemView.findViewById(R.id.textView2);
            cardView = itemView.findViewById(R.id.cardview);
        }
    }
}