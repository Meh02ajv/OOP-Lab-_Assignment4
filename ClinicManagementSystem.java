import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class ClinicManagementSystem implements FileOperations, ReportGenerator {
    private HashMap<Integer, Patient> patients;
    private ArrayList<Doctor> doctors;
    private TreeMap<String, ArrayList<Appointment>> appointmentsByDate;
    
    public ClinicManagementSystem() {
        this.patients = new HashMap<>();
        this.doctors = new ArrayList<>();
        this.appointmentsByDate = new TreeMap<>();
    }
    
    // Patient Management
    public void addPatient(Patient patient) {
        patients.put(patient.getPatientId(), patient);
    }
    
    public Patient getPatient(int patientId) {
        return patients.get(patientId);
    }
    
    public void removePatient(int patientId) {
        patients.remove(patientId);
    }
    
    public HashMap<Integer, Patient> getAllPatients() {
        return patients;
    }
    
    // Doctor Management
    public void addDoctor(Doctor doctor) {
        doctors.add(doctor);
    }
    
    public ArrayList<Doctor> getAllDoctors() {
        return doctors;
    }
    
    public Doctor findDoctorByName(String name) {
        for (Doctor doctor : doctors) {
            if (doctor.getName().equalsIgnoreCase(name)) {
                return doctor;
            }
        }
        return null;
    }
    
    // Appointment Management
    public void scheduleAppointment(String date, Appointment appointment) {
        if (!appointmentsByDate.containsKey(date)) {
            appointmentsByDate.put(date, new ArrayList<>());
        }
        appointmentsByDate.get(date).add(appointment);
    }
    
    public ArrayList<Appointment> getAppointmentsByDate(String date) {
        return appointmentsByDate.getOrDefault(date, new ArrayList<>());
    }
    
    public TreeMap<String, ArrayList<Appointment>> getAllAppointments() {
        return appointmentsByDate;
    }
    
    @Override
    public void saveToFile(String filename) throws java.io.IOException {
        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(filename))) {
            if (filename.toLowerCase().contains("patient")) {
                writer.println("# Patient data format: patientId,name,age,gender,email,phone");
                for (Patient patient : patients.values()) {
                    writer.println(patient.getPatientId() + "," + patient.getName() + "," + 
                                 patient.getAge() + "," + patient.getGender() + "," + 
                                 patient.getEmail() + "," + patient.getPhone());
                }
            } else if (filename.toLowerCase().contains("appointment")) {
                writer.println("# Appointment data format: ID,year,month,day,date,hour,minute,doctorName,goal");
                for (ArrayList<Appointment> appointmentList : appointmentsByDate.values()) {
                    for (Appointment apt : appointmentList) {
                        writer.println(apt.getID() + "," + apt.getYear() + "," + apt.getMonth() + "," +
                                     apt.getDay() + "," + apt.getDate() + "," + apt.getHour() + "," +
                                     apt.getMinute() + "," + apt.getDoctorName() + "," + apt.getGoal());
                    }
                }
            }
        }
    }
    
    @Override
    public void loadFromFile(String filename) throws java.io.IOException {
        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filename))) {
            String line;
            boolean isPatientFile = filename.toLowerCase().contains("patient");
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                if (isPatientFile) {
                    loadPatientData(line);
                } else {
                    loadAppointmentData(line);
                }
            }
        }
    }
    
    private void loadPatientData(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int patientId = Integer.parseInt(parts[0].trim());
                String name = parts[1].trim();
                int age = Integer.parseInt(parts[2].trim());
                String gender = parts[3].trim();
                String email = parts[4].trim();
                String phone = parts[5].trim();
                
                Patient patient = new Patient(name, age, gender, email, phone, patientId);
                addPatient(patient);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing patient data: " + line);
            e.printStackTrace();
        }
    }
    
    private void loadAppointmentData(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 9) {
                int id = Integer.parseInt(parts[0].trim());
                int year = Integer.parseInt(parts[1].trim());
                int month = Integer.parseInt(parts[2].trim());
                int day = Integer.parseInt(parts[3].trim());
                String date = parts[4].trim();
                int hour = Integer.parseInt(parts[5].trim());
                int minute = Integer.parseInt(parts[6].trim());
                String doctorName = parts[7].trim();
                String goal = parts[8].trim();
                
                Appointment appointment = new Appointment(id, year, month, day, date, hour, minute, doctorName, goal);
                scheduleAppointment(date, appointment);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing appointment data: " + line);
            e.printStackTrace();
        }
    }
    
    @Override
    public String generatePatientReport(int patientId) {
        StringBuilder report = new StringBuilder();
        report.append("\n=== Patient Report ===\n");
        
        Patient patient = getPatient(patientId);
        if (patient == null) {
            report.append("Patient not found.\n");
            return report.toString();
        }
        
        report.append("Patient ID: ").append(patient.getPatientId()).append("\n");
        report.append("Name: ").append(patient.getName()).append("\n");
        report.append("Age: ").append(patient.getAge()).append("\n");
        report.append("Gender: ").append(patient.getGender()).append("\n");
        report.append("Email: ").append(patient.getEmail()).append("\n");
        report.append("Phone: ").append(patient.getPhone()).append("\n\n");
        
        report.append("Appointments:\n");
        int count = 0;
        for (ArrayList<Appointment> appointments : appointmentsByDate.values()) {
            for (Appointment apt : appointments) {
                if (apt.getID() == patientId) {
                    report.append("  - ").append(apt.getDate())
                          .append(" at ").append(apt.getTime())
                          .append(" with ").append(apt.getDoctorName())
                          .append(" - ").append(apt.getGoal()).append("\n");
                    count++;
                }
            }
        }
        
        if (count == 0) {
            report.append("  No appointments found.\n");
        }
        
        return report.toString();
    }
    
    @Override
    public String generateDailyAppointments(String date) {
        StringBuilder report = new StringBuilder();
        report.append("\n=== Daily Appointments Report ===\n");
        report.append("Date: ").append(date).append("\n\n");
        
        ArrayList<Appointment> appointments = getAppointmentsByDate(date);
        
        if (appointments.isEmpty()) {
            report.append("No appointments scheduled for this date.\n");
        } else {
            for (Appointment apt : appointments) {
                report.append("ID: ").append(apt.getID())
                      .append(" | Time: ").append(apt.getTime())
                      .append(" | Doctor: ").append(apt.getDoctorName())
                      .append(" | Goal: ").append(apt.getGoal()).append("\n");
            }
            report.append("\nTotal appointments: ").append(appointments.size()).append("\n");
        }
        
        return report.toString();
    }
}
