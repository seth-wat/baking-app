package popmovies.com.example.android.baking_app.data;

import org.parceler.Parcel;

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

    public Step(int id, String shorDescription, String longDescription, String videoUrl, String thumbnailUrl) {
        this.id = id;
        this.shortDescription = shorDescription;
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
    public String getDisplayVideoUrl() {
        if (!videoUrl.isEmpty()) {
            return videoUrl;
        }
        if (!thumbnailUrl.isEmpty()) {
            return thumbnailUrl;
        }
        return "No video found.";
    }


}
