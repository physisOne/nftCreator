package one.physis.nft.services.dto;

public class SendResponse extends Response {
   private String hash;
   private long timestamp;
   private long nonce;

   public String getHash() {
      return hash;
   }

   public void setHash(String hash) {
      this.hash = hash;
   }

   public long getTimestamp() {
      return timestamp;
   }

   public void setTimestamp(long timestamp) {
      this.timestamp = timestamp;
   }

   public long getNonce() {
      return nonce;
   }

   public void setNonce(long nonce) {
      this.nonce = nonce;
   }
}
