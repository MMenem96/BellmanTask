
package bellman.com.task.models;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category implements Serializable
{

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("ar_name")
    @Expose
    private Object arName;
    @SerializedName("icon_image_url")
    @Expose
    private String iconImageUrl;
    @SerializedName("cover_image_url")
    @Expose
    private String coverImageUrl;
    private final static long serialVersionUID = -6835391629586205284L;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Category() {
    }

    /**
     * 
     * @param iconImageUrl
     * @param coverImageUrl
     * @param name
     * @param id
     * @param arName
     */
    public Category(int id, String name, Object arName, String iconImageUrl, String coverImageUrl) {
        super();
        this.id = id;
        this.name = name;
        this.arName = arName;
        this.iconImageUrl = iconImageUrl;
        this.coverImageUrl = coverImageUrl;
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

    public Object getArName() {
        return arName;
    }

    public void setArName(Object arName) {
        this.arName = arName;
    }

    public String getIconImageUrl() {
        return iconImageUrl;
    }

    public void setIconImageUrl(String iconImageUrl) {
        this.iconImageUrl = iconImageUrl;
    }

    public String getCoverImageUrl() {
        return coverImageUrl;
    }

    public void setCoverImageUrl(String coverImageUrl) {
        this.coverImageUrl = coverImageUrl;
    }

}
