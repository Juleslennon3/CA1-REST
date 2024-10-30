package creditUnion.models;

import javax.persistence.*;

@Entity
@Table(name = "deposits")
public class Deposit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String date;  
    private Double amount; 

    // Many-to-one relationship with Loan (many deposits can be made by loan)
    @ManyToOne
    @JoinColumn(name = "loan_id")
    private Loan loan;

   
    public Deposit() {}

    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public Loan getLoan() { return loan; }
    public void setLoan(Loan loan) { this.loan = loan; }
}
