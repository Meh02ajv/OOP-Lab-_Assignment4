public class Doctor extends Person{
    private String doctorID;
    private String doctorType;
    private int yearsOfExperience;
    private boolean isAvailable;
    private boolean isFulltime;

    void prescribeMedicine(){}
    void performsurgery(){}

    public Doctor(String name, int age, String gender, String phone, String email,String doctorID,int yearsOfExperience,boolean isAvailable, boolean isFulltime){
        super(name, age, gender, email, phone);

    }

    public void setDoctorType(String doctorType) {
        this.doctorType = doctorType;
    }

    public String getDoctorType() {
        return doctorType;
    }

    public void setYearsOfExperience(int yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public int getYearsOfExperience() {
        return yearsOfExperience;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public boolean isFulltime() {
        return isFulltime;
    }

    public void setFulltime(boolean fulltime) {
        isFulltime = fulltime;
    }
}
