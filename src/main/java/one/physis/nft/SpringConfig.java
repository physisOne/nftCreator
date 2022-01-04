package one.physis.nft;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
@EnableScheduling
public class SpringConfig {

   @Bean
   public RetryTemplate retryTemplate() {
      RetryTemplate retryTemplate = new RetryTemplate();

      FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
      fixedBackOffPolicy.setBackOffPeriod(500l);
      retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

      SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
      retryPolicy.setMaxAttempts(5);
      retryTemplate.setRetryPolicy(retryPolicy);

      return retryTemplate;
   }

   @Bean
   public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder)
   {
      return restTemplateBuilder
              .setConnectTimeout(Duration.ofSeconds(10))
              .setReadTimeout(Duration.ofSeconds(20))
              .build();
   }

}
