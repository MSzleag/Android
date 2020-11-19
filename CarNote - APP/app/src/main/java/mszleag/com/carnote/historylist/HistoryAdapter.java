package mszleag.com.carnote.historylist;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.Normalizer;
import java.util.List;

import mszleag.com.carnote.R;
import mszleag.com.carnote.model.TankUpRecord;

/**
 * Adapter budujacy elementy listy historii na podstawie historii auta
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private Context context;
    private List<TankUpRecord> tankList;
    private TankUpRecord tankUpRecord;
    private Drawable drawable;

    public HistoryAdapter(Context context, List<TankUpRecord> tankList) {
        this.tankList = tankList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_history_item, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        tankUpRecord = tankList.get(position);
        drawable = context.getDrawable(R.drawable.ic_new_tankup);
        holder.activityImageView.setImageDrawable(drawable);
        DateFormat dateFormat = DateFormat.getDateInstance();
        holder.leftLabelTopTextView.setText(dateFormat.format(tankUpRecord.getTankUpDate()));
        holder.rightLabelTopTextView.setText(tankUpRecord.getMileage().toString() + "km");
        holder.leftLabelBottomTextView.setText(tankUpRecord.getLiters() + "L");
        holder.rightLabelBottomTextView.setText(tankUpRecord.getCostInPLN() + "PLN");

        holder.trashImageView.setOnClickListener(v -> {
            tankList.remove(position);
            HistoryAdapter.this.notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {

        if(tankList == null)
        {
            return 0;
        }else{
            return tankList.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        protected ImageView activityImageView;
        protected ImageView trashImageView;

        protected TextView leftLabelTopTextView;
        protected TextView leftLabelBottomTextView;
        protected TextView rightLabelTopTextView;
        protected TextView rightLabelBottomTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.activityImageView = itemView.findViewById(R.id.activity_image_view);
            this.trashImageView = itemView.findViewById(R.id.trash_image_view);

            this.leftLabelTopTextView = itemView.findViewById(R.id.left_label_top);
            this.leftLabelBottomTextView = itemView.findViewById(R.id.left_label_bottom);
            this.rightLabelTopTextView = itemView.findViewById(R.id.right_label_top);
            this.rightLabelBottomTextView = itemView.findViewById(R.id.right_label_bottom);
        }
    }
}
