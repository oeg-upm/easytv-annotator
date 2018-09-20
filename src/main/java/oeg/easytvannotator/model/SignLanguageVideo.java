/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package oeg.easytvannotator.model;

import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Pablo
 */
public class SignLanguageVideo {
    
    private String url;
    private String nls;
    private String sls;
    private String duration;
    private String language;
    private List <segment> segments;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNls() {
        return nls;
    }

    public void setNls(String nls) {
        this.nls = nls;
    }

    public String getSls() {
        return sls;
    }

    public void setSls(String sls) {
        this.sls = sls;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<segment> getSegments() {
        return segments;
    }

    public void setSegments(List<segment> segments) {
        this.segments = segments;
    }

    

    

   
    
    
}


