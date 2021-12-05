package nasa;

import com.fasterxml.jackson.annotation.JsonProperty;

public class URLContent {
    String url;

    public URLContent(@JsonProperty("url") String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getNameContent() {
        String[] strings = url.split("/");
        return strings[strings.length - 1];
    }


}
