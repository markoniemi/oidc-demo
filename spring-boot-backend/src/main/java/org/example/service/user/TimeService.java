package org.example.service.user;

import java.util.Date;
import org.springframework.stereotype.Component;
import jakarta.jws.WebParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

@Component(value = "timeService")
@Path("/time")
public class TimeService {
  @POST
  public String getTime(@WebParam(name = "name") String name) {
    return String.format("%s", new Date());
  }
}
