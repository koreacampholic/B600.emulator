package b600.emulator.mailapp;

public interface IMailSessionFactory {

	IMailSession openSession(String host, String username, String password);

}
