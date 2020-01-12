package android.example.mobdevjoostmulders.workout;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;



public class Workout implements Parcelable {
    private String name;
    private String description;
    private String category;
    private String muscleGroup;
    private Bitmap imageBitmap;

    public Workout(){

    }

    public Workout(String name, String description, String category, String muscleGroup, Bitmap imageBitmap) {
        this.name = name;
        this.description = description;
        this.category = category;
        this.muscleGroup = muscleGroup;
        this.imageBitmap = imageBitmap;
    }

    protected Workout(Parcel in) {
        name = in.readString();
        description = in.readString();
        category = in.readString();
        muscleGroup = in.readString();
        imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Workout> CREATOR = new Creator<Workout>() {
        @Override
        public Workout createFromParcel(Parcel in) {
            return new Workout(in);
        }

        @Override
        public Workout[] newArray(int size) {
            return new Workout[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    @Override
    public String toString() {
        return "Workout{" +
            "name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", category='" + category + '\'' +
            ", muscleGroup='" + muscleGroup + '\'' +
            '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(description);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeValue(imageBitmap);
    }
}
