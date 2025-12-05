import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;

public class Main implements FileOperations, ReportGenerator {
    private HashMap<Integer, Patient> patients;
    private TreeMap<String, ArrayList<Appointment>> appointmentsByDate;
    private HashMap<Integer, ArrayList<MedicalRecord>> medicalRecordsByPatient;
    
    public Main() {
        this.patients = new HashMap<>();
        this.appointmentsByDate = new TreeMap<>();
        this.medicalRecordsByPatient = new HashMap<>();
    }
    
    @Override
    public void saveToFile(String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
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
            } else if (filename.toLowerCase().contains("medical") || filename.toLowerCase().contains("record")) {
                writer.println("# Medical Record data format: patientId,diagnosis,treatment,visitDate,doctorName,prescription");
                for (Integer patientId : medicalRecordsByPatient.keySet()) {
                    for (MedicalRecord record : medicalRecordsByPatient.get(patientId)) {
                        writer.println(patientId + "," + record.getDiagnosis() + "," + 
                                     record.getTreatment() + "," + record.getVisitDate().getTime() + "," + 
                                     record.getDoctorName() + "," + record.getPrescription());
                    }
                }
            }
        }
    }
    
    @Override
    public void loadFromFile(String filename) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isPatientFile = filename.toLowerCase().contains("patient");
            boolean isMedicalRecordFile = filename.toLowerCase().contains("medical") || filename.toLowerCase().contains("record");
            
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.trim().startsWith("#")) {
                    continue;
                }
                
                if (isPatientFile) {
                    loadPatientData(line);
                } else if (isMedicalRecordFile) {
                    loadMedicalRecordData(line);
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
                patients.put(patientId, patient);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing patient data: " + line);
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
                
                if (!appointmentsByDate.containsKey(date)) {
                    appointmentsByDate.put(date, new ArrayList<>());
                }
                appointmentsByDate.get(date).add(appointment);
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing appointment data: " + line);
        }
    }
    
    private void loadMedicalRecordData(String line) {
        try {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                int patientId = Integer.parseInt(parts[0].trim());
                String diagnosis = parts[1].trim();
                String treatment = parts[2].trim();
                long visitDateTime = Long.parseLong(parts[3].trim());
                String doctorName = parts[4].trim();
                String prescription = parts[5].trim();
                
                java.util.Date visitDate = new java.util.Date(visitDateTime);
                MedicalRecord record = new MedicalRecord(diagnosis, treatment, visitDate, doctorName, prescription);
                
                if (!medicalRecordsByPatient.containsKey(patientId)) {
                    medicalRecordsByPatient.put(patientId, new ArrayList<>());
                }
                medicalRecordsByPatient.get(patientId).add(record);
                
                // Also add to patient object if patient exists
                Patient patient = patients.get(patientId);
                if (patient != null) {
                    patient.addMedicalRecord(record);
                }
            }
        } catch (NumberFormatException e) {
            System.err.println("Error parsing medical record data: " + line);
        }
    }
    
    @Override
    public String generatePatientReport(int patientId) {
        StringBuilder report = new StringBuilder();
        report.append("\n=== Patient Report ===\n");
        
        Patient patient = patients.get(patientId);
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
        
        ArrayList<Appointment> appointments = appointmentsByDate.get(date);
        
        if (appointments == null || appointments.isEmpty()) {
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
    
    public static void main(String[] args) {
        Main system = new Main();
        
        String patientsFile = "C:\\Users\\ajavo\\OneDrive\\Desktop\\OOP Assignment\\Java_Lab_project\\src\\patients.txt";
        String appointmentsFile = "C:\\Users\\ajavo\\OneDrive\\Desktop\\OOP Assignment\\Java_Lab_project\\src\\appointment.txt";
        String medicalRecordsFile = "C:\\Users\\ajavo\\OneDrive\\Desktop\\OOP Assignment\\Java_Lab_project\\src\\medicalRecords.txt";
        
        try {
            System.out.println("Loading data from files...");
            system.loadFromFile(patientsFile);
            system.loadFromFile(appointmentsFile);
            
            // Try to load medical records if file exists
            try {
                system.loadFromFile(medicalRecordsFile);
            } catch (IOException e) {
                System.out.println("Note: Medical records file not found. Skipping...");
            }
            
            System.out.println("\n=== Clinic Management System ===");
            System.out.println("Total Patients: " + system.patients.size());
            
            // Display all patients
            System.out.println("\n=== Patients List ===");
            for (Patient patient : system.patients.values()) {
                System.out.println(patient);
            }
            
            // Display all appointments
            System.out.println("\n=== All Appointments ===");
            for (String date : system.appointmentsByDate.keySet()) {
                System.out.println("\nDate: " + date);
                for (Appointment apt : system.appointmentsByDate.get(date)) {
                    System.out.println("  ID: " + apt.getID() + 
                                     " | Time: " + apt.getTime() + 
                                     " | Doctor: " + apt.getDoctorName() + 
                                     " | Goal: " + apt.getGoal());
                }
            }
            // Display medical records
            if (!system.medicalRecordsByPatient.isEmpty()) {
                System.out.println("\n=== Medical Records ===");
                for (Integer patientId : system.medicalRecordsByPatient.keySet()) {
                    System.out.println("\nPatient ID: " + patientId);
                    for (MedicalRecord record : system.medicalRecordsByPatient.get(patientId)) {
                        System.out.println("  Diagnosis: " + record.getDiagnosis() + 
                                         " | Treatment: " + record.getTreatment() + 
                                         " | Doctor: " + record.getDoctorName() + 
                                         " | Date: " + record.getVisitDate());
                    }
                }
            }
            
            // Generate sample reports
            if (!system.patients.isEmpty()) {
                int firstPatientId = system.patients.keySet().iterator().next();
                System.out.println(system.generatePatientReport(firstPatientId));
            }
            
            if (!system.appointmentsByDate.isEmpty()) {
                String firstDate = system.appointmentsByDate.firstKey();
                System.out.println(system.generateDailyAppointments(firstDate));
            }
            
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("\nPlease ensure the following files exist:");
            System.err.println("1. " + patientsFile);
            System.err.println("2. " + appointmentsFile);
            System.err.println("\nSample file formats:");
            System.err.println("\nPatients file format:");
            System.err.println("# Patient data format: patientId,name,age,gender,email,phone");
            System.err.println("1,John Doe,35,Male,john.doe@email.com,555-0101");
            System.err.println("\nAppointments file format:");
            System.err.println("# Appointment data format: ID,year,month,day,date,hour,minute,doctorName,goal");
            System.err.println("1,2025,12,10,2025-12-10,9,30,Dr. Smith,General Checkup");
        }
    }
}
