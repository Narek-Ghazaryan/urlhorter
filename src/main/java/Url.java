import javax.persistence.*;

@Entity
@Table(name = "urls")
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_url", unique = true, nullable = false)
    private Integer id;
    private String originalUrl;
    private String shortUrl;

    public Url(){ }

    public Integer setId(Integer id) {
       return id;
    }

    public String setOriginalUrl(String url) {
        return originalUrl;
    }

    public String setShortUrl(String url) {
        return shortUrl;
    }

    public int getId() {
        return id;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public Url(String originalUrl, String shortUrl){
        super();
        this.originalUrl = originalUrl;
        this.shortUrl = shortUrl;
    }

    @Override
    public String toString(){
        return "Url [ originalUrl =" + originalUrl + " shortUrl = " + shortUrl + " ]";
    }
}