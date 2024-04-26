package com.shashank.platform.classroomappui.ui.students;

public class ViewStudentModel {

    String UserID, Name, MobileNo, EmailID, Gender, MaritalStatus, DOB, PlanID, PlanName, SeatNo, PlanType, SeatID;

    public ViewStudentModel(String userID, String name, String mobileNo, String emailID, String gender, String maritalStatus, String DOB, String planID, String planName, String seatNo, String planType, String seatID) {
        UserID = userID;
        Name = name;
        MobileNo = mobileNo;
        EmailID = emailID;
        Gender = gender;
        MaritalStatus = maritalStatus;
        this.DOB = DOB;
        PlanID = planID;
        PlanName = planName;
        SeatNo = seatNo;
        PlanType = planType;
        SeatID = seatID;
    }

    public String getUserID() {
        return UserID;
    }

    public String getName() {
        return Name;
    }

    public String getMobileNo() {
        return MobileNo;
    }

    public String getEmailID() {
        return EmailID;
    }

    public String getGender() {
        return Gender;
    }

    public String getMaritalStatus() {
        return MaritalStatus;
    }

    public String getDOB() {
        return DOB;
    }

    public String getPlanID() {
        return PlanID;
    }

    public String getPlanName() {
        return PlanName;
    }

    public String getSeatNo() {
        return SeatNo;
    }

    public String getPlanType() {
        return PlanType;
    }

    public String getSeatID() {
        return SeatID;
    }
}
