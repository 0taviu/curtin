package in.curt;

import java.io.Serializable;
import java.net.URL;

public class ShortenURLRequest implements Serializable {
    private URL url;

    public URL getUrl() {
        return url;
    }

    public void setUrl(URL url) {
        this.url = url;
    }
}
