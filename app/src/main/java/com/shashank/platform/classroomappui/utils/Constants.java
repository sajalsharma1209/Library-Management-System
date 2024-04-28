package com.shashank.platform.classroomappui.utils;

public class Constants {

    private static final String API_BASE_URL = "http://invoice.uturnhealthcare.com/Default1.aspx/";

    public static String Registration_URL = API_BASE_URL + "InstUptRegistration";
    public static String Login_URL = API_BASE_URL + "GetLogin";
    public static String get_Plan = API_BASE_URL + "GetPlan";
    public static String get_Student = API_BASE_URL + "GetUser";
    public static String get_Account = API_BASE_URL + "GetAccount";
    public static String get_Expense = API_BASE_URL + "GetExpense";
    public static String get_Address = API_BASE_URL + "GetAddress";
    public static String get_Attendance = API_BASE_URL + "GetUserAttendance";
    public static String plan_Insert_Update = API_BASE_URL + "InstUptPlan";
    public static String Student_Insert_Update = API_BASE_URL + "InstUptRegistrationUser";
    public static String Seat_Insert_Update = API_BASE_URL + "InstUptSeat";
    public static String Payment_Expense_Insert_Update = API_BASE_URL + "InstUptAccount";
    public static String Address_Insert_Update = API_BASE_URL + "InstUptAddress";
    public static String Attendance_Insert_Update = API_BASE_URL + "InstUptUserAttendance";
}
