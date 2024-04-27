package com.shashank.platform.classroomappui.ui.expensereport;

public class ExpenseReportModel {
    private String CreditDebit, Amount, CreditDebitDate, Remarks, CashChequeNEFT, FromDate, ToDate;

    public ExpenseReportModel(String creditDebit, String amount, String creditDebitDate, String remarks, String cashChequeNEFT, String fromDate, String toDate) {
        CreditDebit = creditDebit;
        Amount = amount;
        CreditDebitDate = creditDebitDate;
        Remarks = remarks;
        CashChequeNEFT = cashChequeNEFT;
        FromDate = fromDate;
        ToDate = toDate;
    }

    public String getCreditDebit() {
        return CreditDebit;
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
