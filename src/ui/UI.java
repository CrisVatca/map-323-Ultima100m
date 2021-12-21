package ui;

import domain.Message;
import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import service.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class UI {
    private final Service service;

    public UI(Service service) {
        this.service = service;
    }

    private void saveUI() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Dati prenumele si numele utilizatorului de adaugat: ");
        String firstName = sc.nextLine();
        String lastName = sc.nextLine();
        this.service.addUser(firstName, lastName);
        System.out.println("Utilizatorul a fost adaugat!");
    }

    private void addFriendUI() {
        printAllUI();
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Dati id-ul primului utilizator: ");
            Long id1 = sc.nextLong();
            System.out.println("Dati id-ul celui de al doilea utilizator: ");
            Long id2 = sc.nextLong();
            LocalDateTime datenow = LocalDateTime.now();
            this.service.addFriend(id1, id2, datenow);
            System.out.println("Prietenia a fost creata!");
        } catch (IllegalArgumentException | NullPointerException | ValidationException e) {
            System.out.println(e.getMessage());
        }

    }

    private void deleteFriendUI() {
        printAllUI();
        Scanner sc = new Scanner(System.in);
        try {
            System.out.println("Dati id-ul primului utilizator: ");
            Long id1 = sc.nextLong();
            System.out.println("Dati id-ul celui de al doilea utilizator: ");
            Long id2 = sc.nextLong();
            this.service.deleteFriend(id1, id2);
            System.out.println("Prietenia a fost stearsa!");
        } catch (IllegalArgumentException | NullPointerException e) {
            System.out.println(e.getMessage());
        }
    }

    private void deleteUI() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Dati id-ul utilizatorului de sters: ");
            Long id = sc.nextLong();
            this.service.deleteUser(id);
            System.out.println("Utilizatorul a fost sters!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void getNrOfConnectedComponentsUI() {
        int nr = this.service.getNrOfConnectedComponents();
        System.out.println("Numarul de comunitati este: " + nr);
    }

    private void getLargestConnectedComponentUI() {
        System.out.println("Cea mai sociabila comunitate este: \n");
        List<Utilizator> largestConnectedComponent = this.service.getLargestConnectedComponent();
        for (Utilizator utilizator : largestConnectedComponent) {
            System.out.println(utilizator);
        }
    }

    public void prieteniiUnuiUtilizatorUI(){
        try{
        Scanner sc = new Scanner(System.in);
        System.out.println("Dati id-ul utilizatorului pentru care doriti prietenii:");
        Long id = sc.nextLong();
        Map<Utilizator,LocalDateTime> prieteniiUnuiUtilizator = this.service.prieteniiUnuiUtilizator(id);
        for(Utilizator u: prieteniiUnuiUtilizator.keySet())
            System.out.println(u.getLastName()+" | "+u.getFirstName()+" | "+prieteniiUnuiUtilizator.get(u).toLocalDate());
        } catch (IllegalArgumentException e){
            System.out.println(e);
        }
    }
    private void printAllUI() {
        Iterable<Utilizator> users = this.service.getUsers();
        users.forEach(System.out::println);
    }

    private void addMessageUI(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Id-ul utilizatorului from: ");
        Long idFrom = sc.nextLong();
        System.out.println("Id-ul utilizatorului to: ");
        Long idTo = sc.nextLong();
        System.out.println("Mesajul:");
        String mesaj = sc.nextLine();
        LocalDateTime dateTime = LocalDateTime.now();
        System.out.println("Id-ul mesajului reply: ");
        Long idReply = sc.nextLong();
        this.service.addMessage(idFrom,idTo,mesaj,dateTime,idReply);
        System.out.println("Mesajul a fost adaugat!");
    }

    private void deleteMessageUI(){
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Dati id-ul mesajului de sters: ");
            Long id = sc.nextLong();
            this.service.deleteMessage(id);
            System.out.println("Mesajul a fost sters!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void allMessagesUI(){
        service.getAllMessages().forEach(System.out::println);
    }

    public  void conversatiiUI(){
        Scanner S = new Scanner(System.in);
        try {
            System.out.println("id1=: ");
            Long id1;
            id1 = S.nextLong();
            System.out.println("id2=: ");
            Long id2;
            id2 = S.nextLong();
            Utilizator u1 = service.getById(id1);
            Utilizator u2 = service.getById(id2);

            List<Message> lista = service.conversatii(u1,u2);

            for(Message message:lista){
                System.out.println("Utilizator from: " + message.getFrom() + " : " + message.getMessage());
            }

        } catch (ValidationException e) {
            System.out.println(e.toString());
        }
    }

    private void menuPrint() {
        System.out.println("Selectati optiunea: ");
        System.out.println("1. Adaugare utilizator");
        System.out.println("2. Stergere utilizator");
        System.out.println("3. Afisare utilizatori");
        System.out.println("4. Adaugare prieten");
        System.out.println("5. Stergere prieten");
        System.out.println("6. Adaugare mesaj");
        System.out.println("7. Stergere mesaj");
        System.out.println("8. Afisare mesaje");
        System.out.println("9. Afisarea prieteniilor unui utilizator dat");
        System.out.println("10. Determinarea numarului de comunitati");
        System.out.println("11. Determinarea celei mai sociabile comunitati");
        System.out.println("12. Mesajele dintre doi utilizatori");
        System.out.println("0. Iesire");
        System.out.println("-----------------------");
    }

    public void menu() {
        Scanner sc = new Scanner(System.in);
        boolean loop = true;
        while (loop) {
            menuPrint();
            int option = sc.nextInt();
            if (option == 1) {
                saveUI();
            } else if (option == 2) {
                deleteUI();
            } else if (option == 3) {
                printAllUI();
            } else if (option == 4) {
                addFriendUI();
            } else if (option == 5) {
                deleteFriendUI();
            } else if (option == 6) {
                addMessageUI();
            } else if (option == 7) {
                deleteMessageUI();
            } else if (option == 8){
                allMessagesUI();
            } else if (option == 9) {
                prieteniiUnuiUtilizatorUI();
            } else if (option == 10) {
                getNrOfConnectedComponentsUI();
            } else if (option == 11) {
                getLargestConnectedComponentUI();
            } else if (option == 12) {
                conversatiiUI();
            } else if (option == 0) {
                loop = false;
            }
            else {
                System.out.println("Optiune inexistenta! Reincercati!");
            }
        }
    }

}
