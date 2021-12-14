package ui;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import service.Service;

import java.util.List;
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
            this.service.addFriend(id1, id2);
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

    private void printAllUI() {
        Iterable<Utilizator> users = this.service.getUsers();
        users.forEach(System.out::println);
    }

    private void menuPrint() {
        System.out.println("Selectati optiunea: ");
        System.out.println("1. Adaugare utilizator");
        System.out.println("2. Stergere utilizator");
        System.out.println("3. Afisare utilizatori");
        System.out.println("4. Adaugare prieten");
        System.out.println("5. Stergere prieten");
        System.out.println("6. Determinarea numarului de comunitati");
        System.out.println("7. Determinarea celei mai sociabile comunitati");
        System.out.println("8. Iesire");
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
                getNrOfConnectedComponentsUI();
            } else if (option == 7) {
                getLargestConnectedComponentUI();
            } else if (option == 8) {
                loop = false;
            } else {
                System.out.println("Optiune inexistenta! Reincercati!");
            }
        }
    }

}
