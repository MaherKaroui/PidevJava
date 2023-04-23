/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;


import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.types.FacebookType;
/**
 *
 * @author saada
 */
public class FacebookShare {
public void shareLink(String accessToken, String linkUrl, String message) {
String apiKey = "0e3dd573da61ec3548c655fb51d682bf";
FacebookClient fbClient = new DefaultFacebookClient(apiKey, Version.LATEST);

        // Share the link
        FacebookType result = fbClient.publish("me/feed", FacebookType.class,
                Parameter.with("link", linkUrl),
                Parameter.with("message", message));

        // Log the result
        System.out.println("Posted link: " + result.getId());
    }
    
}
