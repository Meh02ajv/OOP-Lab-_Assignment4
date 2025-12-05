public interface ReportGenerator {
    String generatePatientReport(int patientId);
    String generateDailyAppointments(String date);
}
