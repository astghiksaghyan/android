package instigatemobile.com.cookmaster.adapters;

import java.util.List;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.RecyclerView;
import de.hdodenhof.circleimageview.CircleImageView;
import instigatemobile.com.cookmaster.R;
import instigatemobile.com.cookmaster.models.TodayMenuItem;

import android.support.annotation.NonNull;


public class AdapterOfTodayMenu extends RecyclerView.Adapter<AdapterOfTodayMenu.MealViewHolder> {
    private List<TodayMenuItem> mMealsList;
    private Context mContext;

    public AdapterOfTodayMenu(List<TodayMenuItem> mealsList, Context context) {
        this.mMealsList = mealsList;
        this.mContext = context;
    }

    @NonNull
    @Override
    public AdapterOfTodayMenu.MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.today_menu_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOfTodayMenu.MealViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMealsList.size();

    }

    public class MealViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView mMealImage;
        private TextView mMealName;
        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mMealImage = itemView.findViewById(R.id.meal_image);
            mMealName = itemView.findViewById(R.id.meal_name);
        }
        public void bind(int position) {
            TodayMenuItem todayMenuItem = mMealsList.get(position);
            mMealName.setText(todayMenuItem.getmMealName());
            Picasso.get().load(todayMenuItem.getmMealImageURL()).into(mMealImage);
        }
    }
}