package bellman.com.task.models;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "hotpots")
public class HotSpot implements Serializable {
    @PrimaryKey
    @SerializedName("id")
    @Expose
    private int id;
    @ColumnInfo(name = "name")
    @SerializedName("name")
    @Expose
    private String name;
    @Ignore
    @SerializedName("photos")
    @Expose
    private List<String> photos = null;
    @Ignore
    @SerializedName("categories")
    @Expose
    private List<Category> categories = null;

    @ColumnInfo(name = "timestamp")
    private int timeStamp;
    @ColumnInfo(name = "photo")
    private String photo;

    @ColumnInfo(name = "categoryName")
    private String categoryName;

    private final static long serialVersionUID = 5271125293373748793L;

    /**
     * No args constructor for use in serialization
     */
    public HotSpot() {
    }


    /**
     * @param photos
     * @param name
     * @param id
     * @param categories
     */
    public HotSpot(int id, String name, List<String> photos, List<Category> categories, int timeStamp, String photo, String categoryName) {
        super();
        this.id = id;
        this.name = name;
        this.photos = photos;
        this.categories = categories;
        this.timeStamp = timeStamp;
        this.photo = photo;
        this.categoryName = categoryName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(int timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}