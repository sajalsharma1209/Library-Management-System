package com.shashank.platform.classroomappui.ui.students;

public class ViewStudentModel {

    String UserID, Name, MobileNo, EmailID, Gender, MaritalStatus, DOB, Createdon;

    public ViewStudentModel(String userID, String name, String mobileNo, String emailID, String gender, String maritalStatus, String DOB, String createdon) {
        UserID = userID;
        Name = name;
        MobileNo = mobileNo;
        EmailID = emailID;
        Gender = gender;
        MaritalStatus = maritalStatus;
        this.DOB = DOB;
        Createdon = createdon;
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

    public String getCreatedon() {
        return Createdon;
    }
}
