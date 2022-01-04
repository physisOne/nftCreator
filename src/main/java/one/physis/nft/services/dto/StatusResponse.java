package one.physis.nft.services.dto;

public class StatusResponse extends Response {

   private Integer statusCode;
   private String statusMessage;
   private String serverInfo;

   public Integer getStatusCode() {
      return statusCode;
   }

   public void setStatusCode(Integer statusCode) {
      this.statusCode = statusCode;
   }

   @Override
   public String getStatusMessage() {
      return statusMessage;
   }

   @Override
   public void setStatusMessage(String statusMessage) {
      this.statusMessage = statusMessage;
   }

   public String getServerInfo() {
      return serverInfo;
   }

   public void setServerInfo(String serverInfo) {
      this.serverInfo = serverInfo;
   }
}
