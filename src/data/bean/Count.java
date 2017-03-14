package data.bean;

/**
 * Created by youyouza on 16-5-11.
 */
public class Count {


    private int TotalStep;
    private float calorie;
    private float distance;
    private int Index;

    public Count(){
        distance=0.0f;
        calorie=0.0f;
        TotalStep=0;
        Index=0;

    }

    public int getIndex() {
        return Index;
    }

    public void setIndex(int index) {
        Index = index;
    }



    public float getCalorie() {
        return calorie;
    }

    public void setCalorie(float calorie) {
        this.calorie = calorie;
    }

    public float getDistance() {
        return distance;
    }

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public int getTotalStep() {
        return TotalStep;
    }

    public void setTotalStep(int totalStep) {
        TotalStep = totalStep;
    }






}
