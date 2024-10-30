package creditUnion.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private Double amount;

    // One-to-one relationship with student
    @OneToOne
    @JoinColumn(name = "student_id")
    private student student;

    // One-to-many relationship with Deposit (one loan can have many deposits)
    @OneToMany(mappedBy = "loan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deposit> deposits = new ArrayList<>();

 
    public Loan() {}

  
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public student getStudent() { return student; } 
    public void setStudent(student student) { this.student = student; }

    public List<Deposit> getDeposits() { return deposits; }
    public void setDeposits(List<Deposit> deposits) { this.deposits = deposits; }
}
