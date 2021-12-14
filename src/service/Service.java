package service;

import domain.Prietenie;
import domain.Utilizator;
import domain.validators.ValidationException;
import jdk.jshell.execution.Util;
import repository.Repository;
import utils.Graph;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Service{
    private final Repository<Long, Utilizator> repoUtilizatori;
    private final Repository<Long, Prietenie> repoPrietenie;

    public Service(Repository<Long, Utilizator> repoUtilizatori, Repository<Long, Prietenie> repoPrietenie) {
        this.repoUtilizatori = repoUtilizatori;
        this.repoPrietenie = repoPrietenie;
    }

    public void addUser(String firstName, String lastName) {
        Utilizator utilizator = new Utilizator(firstName, lastName);
        this.repoUtilizatori.save(utilizator);
    }

    public void deleteUser(Long id) {
        this.repoUtilizatori.delete(id);
        List<Long> l = new ArrayList<>();
        for (Prietenie p : this.repoPrietenie.findAll()) {
            if (id.equals(p.getIdU())) {
                l.add(p.getId());
            }

            if (id.equals(p.getIdP())) {
                l.add(p.getId());
            }
        }
        for (long i : l)
            this.repoPrietenie.delete(i);
    }

    public List<Utilizator> getUsers() {

        Iterable<Utilizator> utilizatori = this.repoUtilizatori.findAll();
        Iterable<Prietenie> prietenii = this.repoPrietenie.findAll();

        for (Prietenie p : prietenii) {
            for (Utilizator u : utilizatori) {
                if (u.getId().equals(p.getIdU()))
                    for (Utilizator u2 : utilizatori) {
                        if (u2.getId().equals(p.getIdP())) {
                            Iterable<Utilizator> uFriends = u.getFriends();
                            boolean ok = true;
                            for(Utilizator prieten:uFriends)
                                if(prieten.getId().equals(u2.getId())){
                                    ok = false;
                                    break;
                                }
                            if(!ok)
                                continue;
                            u.makeFriend(u2);
                            u2.makeFriend(u);
                        }
                    }
            }
        }
        List<Utilizator> utilizatorList = new ArrayList<>();
        for(Utilizator u: utilizatori)
            utilizatorList.add(u);

        return utilizatorList;

    }


    public void addFriend(Long id1, Long id2) {
        try {
            if (!Objects.equals(id1, id2)) {
                Utilizator utilizator1 = this.repoUtilizatori.findOne(id1);
                Utilizator utilizator2 = this.repoUtilizatori.findOne(id2);
                if(utilizator1 == null || utilizator2 == null)
                    throw new NullPointerException("Utilizatorul trebuie sa existe pentru a se crea o prietenie!");
                Prietenie p = new Prietenie(id1,id2);
                this.repoPrietenie.save(p);
            }
        } catch (NullPointerException | ValidationException e) {
            throw e;
        }
    }

    public void deleteFriend(Long id1, Long id2) {
        Utilizator u1 = this.repoUtilizatori.findOne(id1);
        Utilizator u2 = this.repoUtilizatori.findOne(id2);
        u1.deleteOneFriend(u2);
        u2.deleteOneFriend(u1);
        Iterable<Prietenie> prietenie = this.repoPrietenie.findAll();
        Long id = 0L;
        for(Prietenie p: prietenie)
            if(id1.equals(p.getIdU())) {
                if (id2.equals(p.getIdP())) {
                    id = p.getId();
                    break;
                }
            }
        if( id != 0L)
            this.repoPrietenie.delete(id);
    }

    public int getNrOfConnectedComponents() {
        Graph graph = new Graph(getUsers());
        return graph.getNrOfConnectedComponents();
    }

    public List<Utilizator> getLargestConnectedComponent() {
        Graph graph = new Graph(getUsers());
        return graph.getLargestConnectedComponent();
    }

    public Utilizator getById(Long x) {
        return this.repoUtilizatori.setFriends(this.repoUtilizatori.findOne(x));
    }
}