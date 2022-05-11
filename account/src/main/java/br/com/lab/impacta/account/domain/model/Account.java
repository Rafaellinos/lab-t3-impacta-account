package br.com.lab.impacta.account.domain.model;

import lombok.Data;

import javax.persistence.*;

// dominio rico com comportamento
@Data
@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long number;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "person_id")
    private Person customer;

    private Double balance;

    // regra de negocio fica no centro
    public boolean debit(Double valueOfDebit) {
        if (this.getBalance() < valueOfDebit) {
            return false;
        }
        Double debitedAmount = this.getBalance() - valueOfDebit;
        this.setBalance(debitedAmount);
        return true;
    }
}
