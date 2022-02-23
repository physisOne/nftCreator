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
import java.util.*;

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
   @Value("${project.rarity}")
   private boolean rarity;

   private final WalletService walletService;
   private final NftRepository nftRepository;
   private final ProjectRepository projectRepository;
   private final Gson gson;
   private Project project;
   private Map rarityMap = null;

   private

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
   public void init() throws Exception {
      if(this.rarity) {
         InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/count.json");
         String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
         this.rarityMap = gson.fromJson(json, Map.class);
      }
      this.project = this.projectRepository.findById(this.projectId).get();


      //this.walletService.initAddresses();
      //generateDatabaseNfts();
      generateNfts();
   }

   private String createNft(int i, Nft nft) throws Exception {
      String ipfsJsonUrl = "ipfs://ipfs/" + this.jsonIpfs + "/" + i + ".json";
      //String ipfsJsonUrl = "ipfs://ipfs/" + this.jsonIpfs; //TODO + "/1.json";

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

            Integer htrBalance = walletService.checkHtrBalance();
            if (htrBalance != null) {
               logger.info("HTR Balance is " + htrBalance);
               if(htrBalance == 0) {
                  logger.info("Balance is 0, exiting");
                  break;
               }
            }

            boolean sleep = true;
            Optional<Nft> nft = nftRepository.findByNumberAndProject(i, this.project);
            if (nft.isPresent()) {
               Nft n = nft.get();
               if(n.getToken().contains("hash")) {
                  String hash = createNft(i, n);
                  n.setToken(hash);
                  nftRepository.save(n);
               } else {
                  sleep = false;
                  logger.info("NFT " + i + " has already been generated");
               }
            }
            if(sleep) {
               logger.info("Successfully created NFT " + i);
               Thread.sleep(1500);
            }
         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
            failed.add(i);
            return;
         }
      }

      if(failed.size() > 0) {
         logger.error("FAILED NFTS");
         for(Integer i : failed) {
            logger.info(i.toString());
         }
      }
   }

   public void generateDatabaseNfts() {
      for(int i = 1; i <= this.nftCount; i++) {
         try {
            Optional<Nft> n = nftRepository.findByNumberAndProject(i, this.project);
            if(n.isPresent()) {
               logger.info("NFT " + i + " already exists");
               continue;
            }

            logger.info("Creating NFT " + i);

            InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/" + i + ".json");
            //InputStream inputStream = getClass().getClassLoader().getResourceAsStream("generator/metadata/1.json");
            String json = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

            if(rarity) {
               Map jsonMap = gson.fromJson(json, Map.class);

               for (Map attribute : (List<Map>) jsonMap.get("attributes")) {
                  String traitType = (String) attribute.get("trait_type");
                  String value = (String) attribute.get("value");
                  boolean found = false;
                  for(String key : (Set<String>)rarityMap.keySet()) {
                     if(key.equalsIgnoreCase(traitType)) {
                        for(String key2 : (Set<String>)((Map)rarityMap.get(key)).keySet()){
                           if(key2.equalsIgnoreCase(value)) {
                              found = true;
                              String rarity = String.valueOf(((Double)((Map)rarityMap.get(key)).get(key2)).intValue());
                              attribute.put("rarity", rarity);
                           }
                        }
                     }
                  }
                  if(!found) {
                     logger.error("Could not find rarity for " + traitType + " : " + value);
                     return;
                  }
               }
               json = gson.toJson(jsonMap);
            }

            String ipfsPublicUrl = "https://ipfs.io/ipfs/" + this.imageIpfs + "/" + i + ".png";
            //String ipfsPublicUrl = "https://ipfs.io/ipfs/" + this.imageIpfs; //TODO + "/1.jpg";

            String hash = null;

            hash = "hash" + i;

            Nft nft = new Nft();
            nft.setNumber(i);
            nft.setToken(hash);
            nft.setTaken(false);
            nft.setIpfs(ipfsPublicUrl);
            nft.setProject(this.project);
            nft.setAttributes(json);
            nft.setFilename(i + ".png");

            nftRepository.save(nft);

            logger.info("Successfully created NFT " + i);

         } catch (Exception ex) {
            logger.error("Could not create NFT " + i, ex);
         }
      }
   }
}
