/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pravin.services;

import com.pravin.models.Posts;
import java.util.List;

/**
 *
 * @author Pravinkumar
 */
public class HtmlGenerator {
    
    public String genUserInfo(){
        String genTxt="";
       
        return genTxt;
    }
    public String genPosts(List <Posts> list){
        String gen="";
//        "<div class=\"comment\"><header><figure class=\"avatar\"><img class=\"avImg\" src=\";"
//                += "resources/Images/male.png" alt=""></figure>
//                    <address>
//                        By <a href="#"></a> contactHIm 
//                    </address>
//                    <time datetime="2045-04-06T08:15+00:00">Friday, 6<sup>th</sup> April 2045 @08:15:00</time>
//                </header>
//                <div class="comcont">
//                    <p>  </p>
//                </div>
//                <button class="mybutton Myhvr" >Test button</button>
//                <button class="mybuttonLeft Myhvr" >Test button left</button>
//            </div>
        for(Posts ob:list)
        {
            gen = "<div class=\"posts\"><div class=\"comment\">";
            gen+= "<h2>";
            
        }
            

        return gen;
    }
    
}
