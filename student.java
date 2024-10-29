package creditUnion.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "students")
public class student {

	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	 
	 private String name;
	    private String studentNumber;
	    private String phoneNumber;
	    private String address;
	    private String programCode;

	    // One-to-many relationship with Loan (one student can have one loan, vice versa)
	    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
	    private Loan loan;

	   
	    public student() {}

	    public Long getId() { return id; }
	    public void setId(Long id) { this.id = id; }

	    public String getName() { return name; }
	    public void setName(String name) { this.name = name; }

	    public String getStudentNumber() { return studentNumber; }
	    public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

	    public String getPhoneNumber() { return phoneNumber; }
	    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }

	    public String getAddress() { return address; }
	    public void setAddress(String address) { this.address = address; }

	    public String getProgramCode() { return programCode; }
	    public void setProgramCode(String programCode) { this.programCode = programCode; }

	    public Loan getLoan() { return loan; }
	    public void setLoan(Loan loan) { this.loan = loan; }
}
