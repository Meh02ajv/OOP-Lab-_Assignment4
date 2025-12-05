import java.util.Date;

public class MedicalRecord {
    private String diagnosis;
    private String treatment;
    private Date visitDate;
    private String doctorName;
    private String prescription;
    
    public MedicalRecord(String diagnosis, String treatment, Date visitDate, String doctorName, String prescription) {
        this.diagnosis = diagnosis;
        this.treatment = treatment;
        this.visitDate = visitDate;
        this.doctorName = doctorName;
        this.prescription = prescription;
    }
    
    public String getDiagnosis() {
        return diagnosis;
    }
    
    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }
    
    public String getTreatment() {
        return treatment;
    }
    
    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }
    
    public Date getVisitDate() {
        return visitDate;
    }
    
    public void setVisitDate(Date visitDate) {
        this.visitDate = visitDate;
    }
    
    public String getDoctorName() {
        return doctorName;
    }
    
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    
    public String getPrescription() {
        return prescription;
    }
    
    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }
    
    @Override
    public String toString() {
        return "MedicalRecord{" +
                "diagnosis='" + diagnosis + '\'' +
                ", treatment='" + treatment + '\'' +
                ", visitDate=" + visitDate +
                ", doctorName='" + doctorName + '\'' +
                ", prescription='" + prescription + '\'' +
                '}';
    }
}
