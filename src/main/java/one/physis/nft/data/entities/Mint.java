package one.physis.nft.data.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "mai_pad_mints")
public class Mint {
   @Id
   @GeneratedValue(generator = "uuid2")
   @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
   private String id;

   private int state;

   @ManyToOne
   private Address depositAddress;

   private String userAddress;

   @OneToMany(mappedBy="mint", fetch = FetchType.EAGER)
   private Set<Nft> nfts;

   private boolean dead;

   private Date createdAt;

   private Date updatedAt;

   private String transaction;

   private String sendBackTransaction;

   private Date transactionDate;

   private Integer balance;

   private int count = 1;

   private String email;

   @ManyToOne
   private Project project;

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public int getState() {
      return state;
   }

   public void setState(int state) {
      this.state = state;
   }

   public Address getDepositAddress() {
      return depositAddress;
   }

   public void setDepositAddress(Address depositAddress) {
      this.depositAddress = depositAddress;
   }

   public String getUserAddress() {
      return userAddress;
   }

   public void setUserAddress(String userAddress) {
      this.userAddress = userAddress;
   }

   public Set<Nft> getNfts() {
      return nfts;
   }

   public void setNfts(Set<Nft> nfts) {
      this.nfts = nfts;
   }

   public boolean isDead() {
      return dead;
   }

   public void setDead(boolean dead) {
      this.dead = dead;
   }

   public Date getCreatedAt() {
      return createdAt;
   }

   public void setCreatedAt(Date createdAt) {
      this.createdAt = createdAt;
   }

   public Date getUpdatedAt() {
      return updatedAt;
   }

   public void setUpdatedAt(Date updatedAt) {
      this.updatedAt = updatedAt;
   }

   public String getTransaction() {
      return transaction;
   }

   public void setTransaction(String transaction) {
      this.transaction = transaction;
   }

   public String getSendBackTransaction() {
      return sendBackTransaction;
   }

   public void setSendBackTransaction(String sendBackTransaction) {
      this.sendBackTransaction = sendBackTransaction;
   }

   public Date getTransactionDate() {
      return transactionDate;
   }

   public void setTransactionDate(Date transactionDate) {
      this.transactionDate = transactionDate;
   }

   public Integer getBalance() {
      return balance;
   }

   public void setBalance(Integer balance) {
      this.balance = balance;
   }

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public String getEmail() {
      return email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }
}
