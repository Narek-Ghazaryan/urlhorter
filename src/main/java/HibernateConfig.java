import io.javalin.Javalin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public final class HibernateConfig {
    public static void main(final String[] args) throws Exception {
        Javalin app = Javalin.create().start(9000);

        Stream<String> lines = Files.lines(Paths.get(String.valueOf(new File("index.html"))));
        String content = lines.collect(Collectors.joining(System.lineSeparator()));

        app.get("/", context -> context
                .contentType("text/html")
                .html(content));


        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Url.class);
        StandardServiceRegistryBuilder builder =
                new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory factory = configuration.buildSessionFactory(builder.build());
        Session session = factory.openSession();
        Transaction transaction = session.beginTransaction();

        app.post("/", context -> {
            String shortUrl = "http://localhost:9000/"+generate();
            String url = context.formParam("url");

            Url url1 = new Url(url,shortUrl);
            session.save(url1);
            context
                    .contentType("text/html")
                    .result((content).replaceAll("Shorten your link", shortUrl));

            app.get("/:shortUrl", ctx -> {
                ctx.redirect(url);
            });

            transaction.commit();
            session.close();


        });


    }



    public static String generate() {
        String lowercaseAlphabet = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String completeAlphabet = lowercaseAlphabet + lowercaseAlphabet.toUpperCase() + numbers;

        Random r = new Random();
        String res = "";
        for (int i = 0; i < 10; i++) {
            res += completeAlphabet.charAt(r.nextInt(completeAlphabet.length()));
        }

        return res;
    }

}