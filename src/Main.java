import domain.Cerere;
import domain.Message;
import domain.Prietenie;
import domain.Utilizator;
import domain.validators.MessageValidator;
import domain.validators.PrietenieValidator;
import domain.validators.UtilizatorValidator;
import repository.Repository;
import repository.db.MessageDbRepository;
import repository.db.PrietenieDbRepository;
import repository.db.UtilizatorDbRepository;
import repository.file.PrietenieFileRepository;
import repository.file.UtilizatorFileRepository;
import repository.db.CerereDbRepository;
import service.Service;
import ui.UI;

public class Main {
    public static void main(String[] args) {
        UtilizatorValidator validator = new UtilizatorValidator();
        PrietenieValidator validator1 = new PrietenieValidator();
        MessageValidator validator2 = new MessageValidator();

        Repository<Long, Cerere> repoCerere = new CerereDbRepository("jdbc:postgresql://localhost:5432/db","postgres","compunere");
        Repository<Long, Utilizator> repoUtilizatori = new UtilizatorDbRepository("jdbc:postgresql://localhost:5432/db","postgres","compunere",validator);
        Repository<Long, Prietenie> repoPrietenie = new PrietenieDbRepository("jdbc:postgresql://localhost:5432/db","postgres","compunere", validator1);
        Repository<Long, Message> repoMessage = new MessageDbRepository("jdbc:postgresql://localhost:5432/db","postgres","compunere", validator2);
        //Repository<Long, Utilizator> repoUtilizatori = new UtilizatorFileRepository("data/users.csv", new UtilizatorValidator());
        //Repository<Long, Prietenie> repoPrietenie = new PrietenieFileRepository("data/friendships.csv", new PrietenieValidator());
        Service service = new Service(repoUtilizatori, repoPrietenie, repoMessage, repoCerere);
        UI ui = new UI(service);
        ui.menu();
    }
}
