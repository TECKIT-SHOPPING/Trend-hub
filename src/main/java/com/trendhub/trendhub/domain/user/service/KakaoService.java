package com.trendhub.trendhub.domain.user.service;

import com.trendhub.trendhub.domain.user.dto.KakaoUserInfo;
import com.trendhub.trendhub.domain.user.entity.SocialProvider;
import com.trendhub.trendhub.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class KakaoService {

    private final UserService userService;

    public String getToken(String code) throws IOException {
        // 인가코드로 토큰받기
        String host = "https://kauth.kakao.com/oauth/token";
        URL url = new URL(host);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        String token = "";
        try {
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoOutput(true); // 데이터 기록 알려주기

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(urlConnection.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=bcda04f3dbb1052307427b9508ca0914");
            sb.append("&redirect_uri=http://localhost:8080/kakao/callback");
            sb.append("&code=" + code);

            bw.write(sb.toString());
            bw.flush();

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String result = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("result = " + result);

            // json parsing
            JSONParser parser = new JSONParser();
            JSONObject elem = (JSONObject) parser.parse(result);

            String access_token = elem.get("access_token").toString();
            String refresh_token = elem.get("refresh_token").toString();
            System.out.println("refresh_token = " + refresh_token);
            System.out.println("access_token = " + access_token);

            token = access_token;

            br.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        return token;
    }


    public KakaoUserInfo getUserInfo(String access_token) {
        String host = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(host);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);
            urlConnection.setRequestMethod("GET");

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);


            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            String res = "";
            while ((line = br.readLine()) != null) {
                res += line;
            }

            System.out.println("res = " + res);


            JSONParser parser = new JSONParser();
            JSONObject obj = (JSONObject) parser.parse(res);
            JSONObject properties = (JSONObject) obj.get("properties");
            System.out.println(obj);

            String id = obj.get("id").toString();
            String nickname = properties.get("nickname").toString();
            String profileImg = properties.get("profile_image").toString();

            return KakaoUserInfo.builder()
                    .id(Long.valueOf(id))
                    .nickname(nickname)
                    .profileImg(profileImg)
                    .build();

        } catch (IOException | ParseException e) {
            e.printStackTrace();
            throw new RuntimeException("카카오 정보를 가져오는데 실패했습니다!");
        }
    }

    public String getAgreementInfo(String access_token) {
        String result = "";
        String host = "https://kapi.kakao.com/v2/user/scopes";
        try {
            URL url = new URL(host);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Authorization", "Bearer " + access_token);

            BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line = "";
            while ((line = br.readLine()) != null) {
                result += line;
            }

            int responseCode = urlConnection.getResponseCode();
            System.out.println("responseCode = " + responseCode);

            // result is json format
            br.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User login(KakaoUserInfo userInfo) {
        System.out.println(userInfo);

        Optional<User> opUser = userService.findByProviderAndProviderId("KAKAO", userInfo.getId().toString());

        if (opUser.isPresent()) {
            return opUser.get();
        }

        // 강제 회원가입
        User user = User.builder()
                .provider(SocialProvider.KAKAO)
                .providerId(userInfo.getId().toString())
                .nickname(userInfo.getNickname())
                .profile(userInfo.getProfileImg())
                .build();

        return userService.saveUser(user);
        // Yes - 기존의 유저인지
        // No - 로그인 후 DB에 저장
        /*1. provider -> enum
        2. 기존의 유저라면? -> 세션 업데이트
        3. 카카오 정보를 받아온 후에 DB저장 후 세션 업데이트
        (회원정보는 로직 선택 구현)*/
    }
}