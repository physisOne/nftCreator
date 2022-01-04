package one.physis.nft.services.dto;

import java.util.List;

public class Addresses extends Response {

   private List<String> addresses;

   public List<String> getAddresses() {
      return addresses;
   }

   public void setAddresses(List<String> addresses) {
      this.addresses = addresses;
   }
}
