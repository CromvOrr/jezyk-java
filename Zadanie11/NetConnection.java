public interface NetConnection {

    public final String ODPOWIEDZ_DLA_OSZUSTA = "Figa";

    public void password(String password);

    public void connect(String host, int port);
}