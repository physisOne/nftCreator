package one.physis.nft.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "mai_pad_addresses")
public class Address {
   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Integer id;

   @Column(unique = true)
   private String address;

   private boolean taken;

   @ManyToOne
   private Project project;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public boolean isTaken() {
      return taken;
   }

   public void setTaken(boolean taken) {
      this.taken = taken;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }
}
