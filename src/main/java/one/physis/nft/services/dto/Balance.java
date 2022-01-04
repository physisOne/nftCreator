package one.physis.nft.services.dto;

public class Balance {

   private int total_amount_available;
   private int total_utxos_available;
   private int total_amount_locked;
   private int total_utxos_locked;
   private int available;

   public int getTotal_amount_available() {
      return total_amount_available;
   }

   public void setTotal_amount_available(int total_amount_available) {
      this.total_amount_available = total_amount_available;
   }

   public int getTotal_utxos_available() {
      return total_utxos_available;
   }

   public void setTotal_utxos_available(int total_utxos_available) {
      this.total_utxos_available = total_utxos_available;
   }

   public int getTotal_amount_locked() {
      return total_amount_locked;
   }

   public void setTotal_amount_locked(int total_amount_locked) {
      this.total_amount_locked = total_amount_locked;
   }

   public int getTotal_utxos_locked() {
      return total_utxos_locked;
   }

   public void setTotal_utxos_locked(int total_utxos_locked) {
      this.total_utxos_locked = total_utxos_locked;
   }

   public int getAvailable() {
      return available;
   }

   public void setAvailable(int available) {
      this.available = available;
   }
}
