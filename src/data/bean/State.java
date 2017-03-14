package data.bean;

import java.util.Calendar;

/**
 * Created by youyouza on 16-5-3.
 */

public class State {

    public Calendar state;
    public Step step;

    public Step getStep() {
        return step;
    }

    public void setStep(Step step) {
        this.step = step;
    }

    public Calendar getState() {
        return state;
    }

    public void setState(Calendar state) {
        this.state = state;
    }


    public State(Calendar state,Step step){

        this.state=state;
        this.step=step;

    }


}
