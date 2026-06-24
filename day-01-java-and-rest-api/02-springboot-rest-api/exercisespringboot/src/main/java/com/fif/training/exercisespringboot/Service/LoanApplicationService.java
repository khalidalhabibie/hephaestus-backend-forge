package com.fif.training.exercisespringboot.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fif.training.exercisespringboot.Model.LoanApplication;

public class LoanApplicationService {

    private final Map<Long, LoanApplication> db = new HashMap<>();
    private Long idCounter = 1L;

    public LoanApplication create(LoanApplication loan) {
        LoanApplication newLoan = new LoanApplication();
        newLoan.setId(loan.getId());
        newLoan.setCustomerId(loan.getCustomerId());
        newLoan.setLoanAmount(loan.getLoanAmount());
        newLoan.setTenorMonth(loan.getTenorMonth());
        newLoan.setPurpose(loan.getPurpose());
        newLoan.setStatus("PENDING");
        db.put(idCounter++, newLoan);

        return loan;
    }

    public List<LoanApplication> getAll() {
        return new ArrayList<>(db.values());
    }

    public LoanApplication getById(Long id) {
        return db.get(id);
    }

    public LoanApplication approve(Long id) {
        LoanApplication loan = db.get(id);
        if (loan != null)
            loan.setStatus("APPROVED");
        return loan;
    }

    public LoanApplication reject(Long id) {
        LoanApplication loan = db.get(id);
        if (loan != null)
            loan.setStatus("REJECTED");
        return loan;
    }
}
