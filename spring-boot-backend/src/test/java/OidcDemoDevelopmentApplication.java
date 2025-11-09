import org.example.OidcDemoApplication;
import org.example.config.ApplicationConfig;
import org.example.config.TestcontainersConfig;
import org.springframework.boot.SpringApplication;

public class OidcDemoDevelopmentApplication {
  public static void main(String[] args) {
    SpringApplication.from(OidcDemoApplication::main)
        .with(ApplicationConfig.class, TestcontainersConfig.class)
        .run(args);
  }
}
