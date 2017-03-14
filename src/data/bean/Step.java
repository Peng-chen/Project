package data.bean;

public class Step {

//
//	public static final String CREATE_STEP = "create table  "+STEP_TABLE_NAME+"("
//			+ "id integer primary key autoincrement,"
//			+ "date integer ,"
//			+ "total_step integer,"
//			+ "step_in_hand integer,"
//			+ "step_in_pocket integer,"
//			+ "step_in_run integer)";

	private int id;
	private int total_step;
	private int step_in_hand;
	private int step_pocket ;
	private int step_in_run;
	private String date;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getStep_in_hand() {
		return step_in_hand;
	}

	public void setStep_in_hand(int step_in_hand) {
		this.step_in_hand = step_in_hand;
	}

	public int getStep_in_run() {
		return step_in_run;
	}

	public void setStep_in_run(int step_in_run) {
		this.step_in_run = step_in_run;
	}

	public int getStep_pocket() {
		return step_pocket;
	}

	public void setStep_pocket(int step_pocket) {
		this.step_pocket = step_pocket;
	}

	public int getTotal_step() {
		return total_step;
	}

	public void setTotal_step(int total_step) {
		this.total_step = total_step;
	}


}
