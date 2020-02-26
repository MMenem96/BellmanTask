package bellman.com.task.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bellman.com.task.R;
import bellman.com.task.models.Attraction;
import bellman.com.task.models.HotSpot;


public class CategoryRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //Category type: 1--> HotSpots, 2--> Events, 3--> Attractions

    private int categoryType;
    private List<Attraction> attractionList = new ArrayList<Attraction>();
    private List<HotSpot> hotSpotList = new ArrayList<HotSpot>();

    public CategoryRecyclerAdapter(int categoryType) {
        this.categoryType = categoryType;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(

                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.layout_category_item,
                        parent,
                        false
                )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (categoryType == 1) {
            ((CategoryViewHolder) holder).onBind(hotSpotList.get(position));
        } else if (categoryType == 3) {
            ((CategoryViewHolder) holder).onBind(attractionList.get(position));
        }
    }


    @Override
    public int getItemCount() {
        if (categoryType == 1) {
            return hotSpotList.size();
        } else if (categoryType == 3) {
            return attractionList.size();

        } else {
            return 0;
        }
    }


    public void setAttractionList(List<Attraction> attractionList) {
        this.attractionList = attractionList;
        notifyDataSetChanged();
    }

    public void setHotSpotList(List<HotSpot> hotSpotList) {
        this.hotSpotList = hotSpotList;
        notifyDataSetChanged();
    }
}