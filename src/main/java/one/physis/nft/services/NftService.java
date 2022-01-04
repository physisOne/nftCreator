package one.physis.nft.services;

import com.google.gson.Gson;
import one.physis.nft.data.entities.Nft;
import one.physis.nft.data.entities.Project;
import one.physis.nft.data.repositories.NftRepository;
import one.physis.nft.data.repositories.ProjectRepository;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class NftService {

   @Value("${project.jsonIpfs}")
   private String jsonIpfs;
   @Value("${project.imageIpfs}")
   private String imageIpfs;
   @Value("${project.nftCount}")
   private int nftCount;
   @Value("${project.tokenSymbol}")
   private String tokenSymbol;
   @Value("${project.tokenName}")
   private String tokenName;
   @Value("${project.projectId}")
   private int projectId;

   private final WalletService walletService;
   private final NftRepository nftRepository;
   private final ProjectRepository projectRepository;
   private final Gson gson;
   private Project project;

   Logger logger = LoggerFactory.getLogger(NftService.class);

   public NftService(WalletService walletService,
                     NftRepository nftRepository,
                     ProjectRepository projectRepository) {
      this.walletService = walletService;
      this.nftRepository = nftRepository;
      this.projectRepository = projectRepository;
      this.gson = new Gson();
   }

   @PostConstruct
   public void init() {
      this.project = this.projectRepository.findById(this.projectId).get();
   }

   private String createNft(int i, Nft nft) throws Exception {
      String ipfsJsonUrl = "ipfs://ipfs/" + this.jsonIpfs + "/" + i + ".json";

      String hash = null;
      String tokenSymbol = this.tokenSymbol + i;

      while(hash == null) {
         hash = walletService.createNft(this.tokenName + " " + i, tokenSymbol, ipfsJsonUrl);
         if(hash == null) {
            try {
               walletService.checkWallets();
               Thread.sleep(10000);
            } catch (Exception ignored) {

            }
         }
      }
      return hash;
   }

   public void generateNfts() {
      List<Integer> failed = new ArrayList<>();
      for (int i = 1; i <= this.nftCount; i++) {
         try {
            logger.info("Creating NFT " + i);
            try {
               walletService.checkWallets();
            } catch (Exception ex) {
               logger.error("Unable to check wallets, exiting!", ex);
               break;
            }

            Integer htrBalance = walletService.checkHtrBalance(true);
            if (htrBalance != null) {
               logger.info("HTR Balance is " + htrBalance);
               if(htrBalance == 0) {
                  logger.info("Balance is 0, exiting");
                  break;
               }
            }

            Optional<Nft> nft = nftRepository.findByIdAndProject(i, this.project);
            if (nft.isPresent()) {
               Nft n = nft.get();
               String hash = createNft(i, n);
               n.setToken(hash);
               nftRepository.save(n);
            }

            logger.info("Successfully created NFT " + i);
            Thread.sleep(3000);
         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
            failed.add(i);
         }
      }

      if(failed.size() > 0) {
         logger.error("FAILED TREES");
         for(Integer i : failed) {
            logger.info(i.toString());
         }
      }
   }

   public void generateDatabaseNfts() {
      for(int i = 1; i <= this.nftCount; i++) {
         try {
            Optional<Nft> n = nftRepository.findByIdAndProject(i, this.project);
            if(n.isPresent()) {
               logger.info("NFT " + i + " already exists");
               continue;
            }

            logger.info("Creating NFT " + i);

            //InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/" + i + ".json");
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/1.json");
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

            //String ipfsPublicUrl = "https://ipfs.io/ipfs/" + this.imageIpfs + "/" + i + ".png";
            String ipfsPublicUrl = "https://ipfs.io/ipfs/" + this.imageIpfs + "/1.jpg";

            String hash = null;

            hash = "hash" + i;

            Nft nft = new Nft();
            nft.setNumber(i);
            nft.setToken(hash);
            nft.setTaken(false);
            nft.setIpfs(ipfsPublicUrl);
            nft.setProject(this.project);
            nft.setAttributes(json);

            nftRepository.save(nft);

            logger.info("Successfully created NFT " + i);

         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
         }
      }
   }
}
