//package org.example;
//
//import org.apache.http.client.methods.CloseableHttpResponse;
//import org.apache.http.client.methods.HttpPost;
//import org.apache.http.entity.StringEntity;
//import org.apache.http.impl.client.CloseableHttpClient;
//import org.apache.http.impl.client.HttpClients;
//import org.apache.http.util.EntityUtils;
//import org.apache.http.client.protocol.HttpClientContext;
//import org.apache.http.client.CookieStore;
//import org.apache.http.impl.client.BasicCookieStore;
//import org.w3c.dom.Document;
//import org.w3c.dom.NodeList;
//import org.xml.sax.InputSource;
//
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import java.io.IOException;
//import java.io.StringReader;
//import java.nio.charset.StandardCharsets;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//public class Main {
//    public static void main(String[] args) {
//        try {
//            // 로그인
//            Map<String, String> cookies = login();
//            if (cookies.isEmpty()) {
//                System.out.println("Login failed.");
//                return;
//            }
//
//            // 데이터 요청
//            String response = requestData(cookies);
//
//            // 응답 처리
//            System.out.println("Data Response: " + response);
//
//            // 에러 메시지 확인
//            if (response.contains("ErrorMsg:int=1")) {
//                System.out.println("An error occurred while fetching data.");
//            } else {
//                System.out.println("Data fetched successfully.");
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    private static Map<String, String> login() throws Exception {
//        String loginUrl = "https://pp.korea7.co.kr/co/main/retrieveSessionInfoList";
//        String loginPayload = "SSV:utf-8\u001EauthSeq=-999\u001EpvdtTreatYn=N\u001EDataset:dsUserRetvCnd\u001E_RowType_\u001FsysDvCd:STRING(256)\u001FuserId:STRING(256)\u001FuserPw:STRING(256)\u001EN\u001F50\u001Fdkfood\u001Fdkfood0801@\u001E\u001E";
//        return sendLoginRequest(loginUrl, loginPayload);
//    }
//
//    private static String requestData(Map<String, String> cookies) throws Exception {
//        String dataUrl = "https://pp.korea7.co.kr/pp/pp/od/PPOD0010/retrieveMasterList";
//        String dataPayload = "SSV:utf-8\u001EWMONID=" + cookies.get("WMONID") +
//                "\u001EauthSeq=-999\u001EpvdtTreatYn=N\u001EDataset:dsRetvCnd\u001E_RowType_" +
//                "\u001FdayType:STRING(256)\u001FevnStaYmd:STRING(256)\u001FevnEndYmd:STRING(256)" +
//                "\u001FcenterCd:STRING(256)\u001FitemCd:STRING(256)\u001FitemNm:STRING(256)" +
//                "\u001FordDvCd:STRING(256)\u001FcustCd:STRING(256)\u001FshpFnoNo:STRING(256)" +
//                "\u001FcentCd:STRING(256)\u001FcentDvCd:STRING(256)\u001FordShapCd:STRING(256)" +
//                "\u001EN\u001F1\u001F20240513\u001F20240520\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u0003\u001F\u001F\u0003\u001F\u0003\u001E\u001E";
//        String cookiesString = "WMONID=" + cookies.get("WMONID") + "; JSESSIONID=" + cookies.get("JSESSIONID");
//
//        // 디버깅을 위해 페이로드와 쿠키를 출력
//        log("Data Payload: " + dataPayload);
//        log("Cookies: " + cookiesString);
//
//        return sendDataRequest(dataUrl, dataPayload, cookiesString);
//    }
//
//    private static Map<String, String> sendLoginRequest(String requestUrl, String payload) {
//        Map<String, String> cookieMap = new HashMap<>();
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CookieStore cookieStore = new BasicCookieStore();
//        HttpClientContext context = HttpClientContext.create();
//        context.setCookieStore(cookieStore);
//
//        try {
//            HttpPost postRequest = new HttpPost(requestUrl);
//            postRequest.setHeader("User-Agent", "Mozilla/5.0");
//            postRequest.setHeader("Content-Type", "text/plain");
//            postRequest.setHeader("Accept", "application/xml, text/xml, */*");
//            postRequest.setHeader("Origin", "https://pp.korea7.co.kr");
//            postRequest.setHeader("Referer", "https://pp.korea7.co.kr/nxgen/index.html");
//            postRequest.setHeader("X-Requested-With", "XMLHttpRequest");
//
//            postRequest.setEntity(new StringEntity(payload));
//
//            log("Sending login request to URL: " + requestUrl);
//            log("Login Payload: " + payload);
//
//            try (CloseableHttpResponse response = httpClient.execute(postRequest, context)) {
//                int responseCode = response.getStatusLine().getStatusCode();
//                log("Response Code: " + responseCode);
//
//                if (responseCode == 302 || responseCode == 301) {
//                    String newUrl = response.getFirstHeader("Location").getValue();
//                    log("Redirect to: " + newUrl);
//                    return sendLoginRequest(newUrl, payload);
//                }
//
//                String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//                log("Response: " + responseString);
//
//                if (responseString.contains("ErrorCode:int=0")) {
//                    log("Login success, extracting cookies...");
//                    cookieStore.getCookies().forEach(cookie -> {
//                        log("Cookie: " + cookie.getName() + "=" + cookie.getValue());
//                        if (cookie.getName().equals("WMONID") || cookie.getName().equals("JSESSIONID")) {
//                            cookieMap.put(cookie.getName(), cookie.getValue());
//                        }
//                    });
//                } else {
//                    log("Login failed, parsing response for error details...");
//                    log("Response Content: " + responseString);
//                    if (responseString.contains("<Parameters>")) {
//                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//                        DocumentBuilder builder = factory.newDocumentBuilder();
//                        Document document = builder.parse(new InputSource(new StringReader(responseString)));
//                        NodeList errorCodeNodes = document.getElementsByTagName("Parameter");
//                        for (int i = 0; i < errorCodeNodes.getLength(); i++) {
//                            if (errorCodeNodes.item(i).getAttributes().getNamedItem("id").getNodeValue().equals("ErrorCode")) {
//                                String errorCode = errorCodeNodes.item(i).getTextContent();
//                                if (!"0".equals(errorCode)) {
//                                    String errorMsg = "";
//                                    NodeList errorMsgNodes = document.getElementsByTagName("Parameter");
//                                    for (int j = 0; j < errorMsgNodes.getLength(); j++) {
//                                        if (errorMsgNodes.item(j).getAttributes().getNamedItem("id").getNodeValue().equals("ErrorMsg")) {
//                                            errorMsg = errorMsgNodes.item(j).getTextContent();
//                                            break;
//                                        }
//                                    }
//                                    log("Login failed with error code " + errorCode + ": " + errorMsg);
//                                    return Collections.emptyMap();
//                                }
//                            }
//                        }
//                    }
//                }
//
//                return cookieMap;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Collections.emptyMap();
//        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private static String sendDataRequest(String requestUrl, String payload, String cookies) {
//        CloseableHttpClient httpClient = HttpClients.createDefault();
//        CookieStore cookieStore = new BasicCookieStore();
//        HttpClientContext context = HttpClientContext.create();
//        context.setCookieStore(cookieStore);
//
//        StringBuilder responseBuilder = new StringBuilder();
//
//        try {
//            HttpPost postRequest = new HttpPost(requestUrl);
//            postRequest.setHeader("User-Agent", "Mozilla/5.0");
//            postRequest.setHeader("Accept", "application/xml, text/xml, */*");
//            postRequest.setHeader("Content-Type", "text/plain");
//            postRequest.setHeader("Cookie", cookies);
//            postRequest.setHeader("Origin", "https://pp.korea7.co.kr");
//            postRequest.setHeader("Referer", "https://pp.korea7.co.kr/nxgen/index.html");
//            postRequest.setHeader("X-Requested-With", "Fetch");
//
//            postRequest.setEntity(new StringEntity(payload));
//
//            log("Sending data request to URL: " + requestUrl);
//            log("Data Payload: " + payload);
//            log("Cookies: " + cookies);
//
//            try (CloseableHttpResponse response = httpClient.execute(postRequest, context)) {
//                int responseCode = response.getStatusLine().getStatusCode();
//                log("Response Code: " + responseCode);
//
//                if (responseCode == 302 || responseCode == 301) {
//                    String newUrl = response.getFirstHeader("Location").getValue();
//                    log("Redirect to: " + newUrl);
//                    return sendDataRequest(newUrl, payload, cookies);
//                }
//
//                String responseString = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
//                log("Response: " + responseString);
//                return responseString;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                httpClient.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return responseBuilder.toString();
//    }
//
//    private static void log(String message) {
//        System.out.println(message);
//    }
//}
