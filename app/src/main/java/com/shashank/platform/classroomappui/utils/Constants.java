package com.shashank.platform.classroomappui.utils;

public class Constants {

    private static final String API_BASE_URL = "http://invoice.uturnhealthcare.com/Default1.aspx/";

    public static String Registration_URL = API_BASE_URL + "InstUptRegistration";
    public static String Login_URL = API_BASE_URL + "GetLogin";
    public static String get_Plan = API_BASE_URL + "GetPlan";
    public static String get_Student = API_BASE_URL + "GetUser";
    public static String plan_Insert_Update = API_BASE_URL + "InstUptPlan";
    public static String Student_Insert_Update = API_BASE_URL + "InstUptRegistrationUser";
    public static String Seat_Insert_Update = API_BASE_URL + "InstUptSeat";
}
