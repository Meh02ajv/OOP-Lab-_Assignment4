public class Appointment {
    private int ID;
    private int year;
    private int month;
    private int day;
    private String date;
    private int hour;
    private int minute;
    private String time;
    private String doctorName;
    private String goal;

    public Appointment(int ID, int year, int month, int day, String date, int hour, int minute, String doctorName, String goal){
        this.ID = ID;
        this.year = year;
        this.month = month;
        this.day = day;
        this.date = date;
        this.hour = hour;
        this.minute = minute;
        this.time = String.format("%02d:%02d", hour, minute);
        this.doctorName = doctorName;
        this.goal = goal;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(int year,int month, int day) {
      this.year =year;
      this.month=month;
      this.day=day;
      System.out.println("Date: "+ getYear() + ":"+ getMonth()+":"+ getDay());

    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }


    public String getTime() {
        return time;
    }

    public void setTime(int hour, int minute) {
        System.out.println("the time is :" + getHour() + ":" + getMinute());
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }
}
