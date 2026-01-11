package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import jakarta.xml.ws.BindingProvider;
import jakarta.xml.ws.Service;
import jakarta.xml.ws.WebServiceException;
import jakarta.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.QName;
import org.apache.cxf.transport.http.HTTPException;
import org.example.model.user.User;
import org.example.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserServiceOAuthWsIT extends AbstractIntegrationTestBase {
  private UserService userService;
  private final OAuthTokenHelper oAuthTokenHelper;

  @Autowired
  public UserServiceOAuthWsIT(UserService userService, OAuthTokenHelper oAuthTokenHelper) {
    this.userService = userService;
    this.oAuthTokenHelper = oAuthTokenHelper;
  }

  @BeforeEach
  public void setUp() throws MalformedURLException {
    userService = getUserService();
    setAuthorizationHeader(userService, "Bearer " + oAuthTokenHelper.getAccessToken());
  }

  @Test
  public void findAll()  {
    List<User> users = userService.findAll();
    assertNotNull(users);
    assertEquals(6, users.size());
  }

  @Test
  public void getUsersWithoutAuthorizationFails()  {
    setAuthorizationHeader(userService, null);
    try {
      userService.findAll();
      fail();
    } catch (WebServiceException e) {
      assertInstanceOf(HTTPException.class, e.getCause());
    }
  }

  public UserService getUserService() throws MalformedURLException {
    URL wsdlURL = new URL("http://localhost:8080/api/soap/users?wsdl");
    QName qname = new QName("http://user.service.example.org/", "UserService");
    Service service = Service.create(wsdlURL, qname);
    return service.getPort(UserService.class);
  }

  public static void setAuthorizationHeader(Object service, String jwtHeader) {
    Map<String, List<String>> requestHeaders = new HashMap<>();
    if (jwtHeader != null) {
      requestHeaders.put("Authorization", Arrays.asList(jwtHeader));
    }
    ((BindingProvider) service)
        .getRequestContext()
        .put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
  }
}
