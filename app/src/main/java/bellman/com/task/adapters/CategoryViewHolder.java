package bellman.com.task.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import bellman.com.task.R;
import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;

class CategoryViewHolder extends RecyclerView.ViewHolder {
    private TextView name, title;
    private ImageView image;

    CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.tv_category_name);
        title = itemView.findViewById(R.id.tv_category_title);
        image = itemView.findViewById(R.id.iv_Category_photo);
    }


    void onBind(Object category) {
        //Set the values for each card of Attraction
        if (category instanceof Attraction) {
            name.setText(((Attraction) category).getName());
            title.setText(((Attraction) category).getCategoryName());
            Glide.with(itemView.getContext()).load(((Attraction) category).getPhoto())
                    .placeholder(R.drawable.ic_image_grey_24dp).into(image);
        } else {
            //Set the values for each card of HotSpot
            name.setText(((HotSpot) category).getName());
            title.setText(((HotSpot) category).getCategoryName());
            Glide.with(itemView.getContext()).load(((HotSpot) category).getPhoto())
                    .placeholder(R.drawable.ic_image_grey_24dp).into(image);
        }
    }


}