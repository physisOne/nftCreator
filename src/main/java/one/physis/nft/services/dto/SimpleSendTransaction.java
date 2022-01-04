package one.physis.nft.services.dto;

public class SimpleSendTransaction {

   private String address;
   private int value;

   public SimpleSendTransaction(String address, int value) {
      this.address = address;
      this.value = value;
   }

   public String getAddress() {
      return address;
   }

   public void setAddress(String address) {
      this.address = address;
   }

   public int getValue() {
      return value;
   }

   public void setValue(int value) {
      this.value = value;
   }
}
