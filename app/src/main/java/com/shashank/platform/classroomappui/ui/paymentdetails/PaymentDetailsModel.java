package com.shashank.platform.classroomappui.ui.paymentdetails;

public class PaymentDetailsModel {
    String Amount, CreditDebitDate, Remarks, CashChequeNEFT, FromDate, ToDate;

    public PaymentDetailsModel(String amount, String creditDebitDate, String remarks, String cashChequeNEFT, String fromDate, String toDate) {
        Amount = amount;
        CreditDebitDate = creditDebitDate;
        Remarks = remarks;
        CashChequeNEFT = cashChequeNEFT;
        FromDate = fromDate;
        ToDate = toDate;
    }

    public String getAmount() {
        return Amount;
    }

    public String getCreditDebitDate() {
        return CreditDebitDate;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getCashChequeNEFT() {
        return CashChequeNEFT;
    }

    public String getFromDate() {
        return FromDate;
    }

    public String getToDate() {
        return ToDate;
    }
}
