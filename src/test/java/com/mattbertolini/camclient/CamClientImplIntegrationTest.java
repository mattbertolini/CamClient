package com.mattbertolini.camclient;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.equalTo;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;

/**
 * @author Matt Bertolini
 */
public class CamClientImplIntegrationTest {
    private static final String API_URL = "/admin/cisco_api.jsp";

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private String userName;
    private String password;
    private CamClient camClient;

    private String loadFile(final String filePath) throws IOException {
        InputStream is = this.getClass().getResourceAsStream(filePath);
        return CharStreams.toString(new InputStreamReader(is, Charsets.UTF_8));
    }

    @Before
    public void setUp() throws URISyntaxException {
        this.userName = "user";
        this.password = "password";
        URI uri = new URI("http://127.0.0.1:8089" + API_URL);
        this.camClient = CamClientFactory.newCamClient(uri, this.userName, this.password);
    }

    @After
    public void tearDown() {
        this.userName = null;
        this.password = null;
        this.camClient = null;
    }

    @Test
    public void testAddMacAddressSuccessResponse() throws IOException {
        String s = "op=addmac&mac=000112233445&admin=" + this.userName + "&passwd=" + this.password;
        String responseBody = this.loadFile("/com/mattbertolini/camclient/success-response.txt");
        stubFor(post(urlMatching(API_URL))
                .withHeader("User-Agent", matching(".*"))
                .withRequestBody(equalTo(s))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "text/html").withBody(responseBody)
                )
        );
        MacAddress macAddress = MacAddress.valueOf("00:01:12:23:34:45");
        this.camClient.addMacAddress(macAddress);
    }

    @Test
    public void testAddMacAddressErrorResponse() throws IOException {
        this.thrown.expect(CamClientException.class);
        this.thrown.expectMessage("CAM Error - This is an error response");

        String s = "op=addmac&mac=000112233445&admin=" + this.userName + "&passwd=" + this.password;
        String responseBody = this.loadFile("/com/mattbertolini/camclient/error-response.txt");
        stubFor(post(urlMatching(API_URL))
                .withHeader("User-Agent", matching(".*"))
                .withRequestBody(equalTo(s))
                .willReturn(aResponse().withStatus(200)
                        .withHeader("Content-Type", "text/html").withBody(responseBody)
                )
        );
        MacAddress macAddress = MacAddress.valueOf("00:01:12:23:34:45");
        this.camClient.addMacAddress(macAddress);
    }
}
