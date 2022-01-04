package one.physis.nft.data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "mai_pad_projects")
public class Project {

   @Id
   private Integer id;

   private String name;

   private int price;

   private String logo;

   private String title;

   private String subtitle;

   private String socials;

   private boolean active;

   private Date createdAt;

   private Date updatedAt;

   private String customCss;

   public Integer getId() {
      return id;
   }

   public void setId(Integer id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public int getPrice() {
      return price;
   }

   public void setPrice(int price) {
      this.price = price;
   }

   public String getLogo() {
      return logo;
   }

   public void setLogo(String logo) {
      this.logo = logo;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public String getSubtitle() {
      return subtitle;
   }

   public void setSubtitle(String subtitle) {
      this.subtitle = subtitle;
   }

   public String getSocials() {
      return socials;
   }

   public void setSocials(String socials) {
      this.socials = socials;
   }

   public boolean isActive() {
      return active;
   }

   public void setActive(boolean active) {
      this.active = active;
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

   public String getCustomCss() {
      return customCss;
   }

   public void setCustomCss(String customCss) {
      this.customCss = customCss;
   }
}
