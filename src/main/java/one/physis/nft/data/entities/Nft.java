package one.physis.nft.data.entities;

import javax.persistence.*;

@Entity
@Table(name = "mai_pad_nfts")
public class Nft {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO)
   private Integer id;

   private Integer number;

   private String ipfs;

   @Column(unique = true)
   private String token;

   private boolean taken;

   @Lob
   private String attributes;

   @ManyToOne
   private Mint mint;

   @ManyToOne
   private Project project;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public Integer getNumber() {
      return number;
   }

   public void setNumber(Integer number) {
      this.number = number;
   }

   public boolean isTaken() {
      return taken;
   }

   public void setTaken(boolean taken) {
      this.taken = taken;
   }

   public String getToken() {
      return token;
   }

   public void setToken(String token) {
      this.token = token;
   }

   public Mint getMint() {
      return mint;
   }

   public void setMint(Mint mint) {
      this.mint = mint;
   }

   public String getIpfs() {
      return ipfs;
   }

   public void setIpfs(String ipfs) {
      this.ipfs = ipfs;
   }

   public Project getProject() {
      return project;
   }

   public void setProject(Project project) {
      this.project = project;
   }

   public String getAttributes() {
      return attributes;
   }

   public void setAttributes(String attributes) {
      this.attributes = attributes;
   }
}
