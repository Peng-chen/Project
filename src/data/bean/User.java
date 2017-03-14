package data.bean;

/**
 * Created by youyouza on 16-5-10.
 */
public class User {
    private String name;
    private double tall;
    private double weight;
    private String gender;
    private double legLength;

    public User(){}

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public double getLegLength() {
        return legLength;
    }

    public void setLegLength(double legLength) {
        this.legLength = legLength;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTall() {
        return tall;
    }

    public void setTall(double tall) {
        this.tall = tall;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }


    public String toString(){

        StringBuffer sb=new StringBuffer();
        sb.append(name);
        sb.append("  ");
        sb.append(tall);
        sb.append("  ");
        sb.append(weight);
        sb.append("  ");
        sb.append(gender);
        sb.append("  ");
        sb.append(legLength);
        sb.append("  ");

        return new String(sb);

    }
}
