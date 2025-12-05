import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

public class Patient extends Person{
    private int patientId;
    private ArrayList<MedicalRecord> medicalHistory;
    private TreeMap<Date, Appointment> appointments;
    
    public Patient(String name, int age, String gender, String email, String phone) {
        super(name, age, gender, email, phone);
        this.medicalHistory = new ArrayList<>();
        this.appointments = new TreeMap<>();
    }
    
    public Patient(String name, int age, String gender, String email, String phone, int patientId) {
        super(name, age, gender, email, phone);
        this.patientId = patientId;
        this.medicalHistory = new ArrayList<>();
        this.appointments = new TreeMap<>();
    }
    
    public int getPatientId() {
        return patientId;
    }
    
    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }
    
    public ArrayList<MedicalRecord> getMedicalHistory() {
        return medicalHistory;
    }
    
    public void addMedicalRecord(MedicalRecord record) {
        this.medicalHistory.add(record);
    }
    
    public TreeMap<Date, Appointment> getAppointments() {
        return appointments;
    }
    
    public void addAppointment(Date date, Appointment appointment) {
        this.appointments.put(date, appointment);
    }
    
    @Override
    public String toString() {
        return "Patient{" +
                "patientId=" + patientId +
                ", name='" + getName() + '\'' +
                ", age=" + getAge() +
                ", gender='" + getGender() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", phone='" + getPhone() + '\'' +
                '}';
    }
}
