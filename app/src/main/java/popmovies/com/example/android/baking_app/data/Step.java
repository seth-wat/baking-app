package popmovies.com.example.android.baking_app.data;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

/**
 * * POJO for JSON data
 */
@Parcel
public class Step {

    int id;
    String shortDescription;
    String longDescription;
    String videoUrl;
    String thumbnailUrl;

    @ParcelConstructor
    public Step(int id, String shortDescription, String longDescription, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {
        return id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getLongDescription() {
        return longDescription;
    }

    //The JSON parsed contains missing video info at times, use this
    //method to get the video url
    public String getDisplayVideoUrlString() {
        if (!videoUrl.isEmpty()) {
            return videoUrl;
        }
        if (!thumbnailUrl.isEmpty()) {
            return thumbnailUrl;
        }
        return "No video found.";
    }


}
