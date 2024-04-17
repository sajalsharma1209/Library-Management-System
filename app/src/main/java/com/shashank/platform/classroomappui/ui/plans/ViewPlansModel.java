package com.shashank.platform.classroomappui.ui.plans;


public class ViewPlansModel {

    String PlanID, PlanName, Amount, RegistrationID, PlanTypeID, PlanType, Duration, PlanDesc, CreateOn;

    public ViewPlansModel(String planID, String planName, String amount, String registrationID, String planTypeID, String planType, String duration, String planDesc, String createOn) {
        PlanID = planID;
        PlanName = planName;
        Amount = amount;
        RegistrationID = registrationID;
        PlanTypeID = planTypeID;
        PlanType = planType;
        Duration = duration;
        PlanDesc = planDesc;
        CreateOn = createOn;
    }

    public String getAmount() {
        return Amount;
    }

    public String getPlanID() {
        return PlanID;
    }

    public String getPlanName() {
        return PlanName;
    }

    public String getRegistrationID() {
        return RegistrationID;
    }

    public String getPlanTypeID() {
        return PlanTypeID;
    }

    public String getPlanType() {
        return PlanType;
    }

    public String getDuration() {
        return Duration;
    }

    public String getPlanDesc() {
        return PlanDesc;
    }

    public String getCreateOn() {
        return CreateOn;
    }
}
