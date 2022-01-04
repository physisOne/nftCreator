package one.physis.nft.services.dto;

import java.util.ArrayList;
import java.util.List;

public class SendTransaction {
   private List<Output> outputs = new ArrayList<>();

   public List<Output> getOutputs() {
      return outputs;
   }

   public void setOutputs(List<Output> outputs) {
      this.outputs = outputs;
   }
}
