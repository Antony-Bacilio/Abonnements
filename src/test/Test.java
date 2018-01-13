package test;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import dao.factory.Persistance;
import dao.interfaces.ClientDAO;
import dao.interfaces.PeriodiciteDAO;
import dao.interfaces.RevueDAO;
import metier.Client;
import metier.DateAbonnement;
import metier.Periodicite;
import metier.Revue;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class Test {

    private static Persistance persistance;
    private static ClientDAO clientDAO;
    private static PeriodiciteDAO periodiciteDAO;
    private static RevueDAO revueDAO;


    public static void main(String[] args) {

        Client client = null;
        Periodicite periodicite = null;
        Revue revue = null;

        selectPersistance();
        // create client

        client = createClient();
        System.out.println(client.hashCode());
        displayTest(periodicite, client, revue, "createClient");
        updateClient(client);
        displayTest(periodicite, client, revue, "updateClient");
        client = getClientById(client);
        displayTest(periodicite, client, revue, "getClientById");


        // create periodicite

        periodicite = createPeriodicite();
        displayTest(periodicite, client, revue, "createPeriodicite");
        updatePeriodicite(periodicite);
        displayTest(periodicite, client, revue, "updatePeriodicite");
        periodicite = getPeriodiciteId(periodicite);
        displayTest(periodicite, client, revue, "getPeriodiciteId");


        // create revue
        revue = createRevue(periodicite);
        displayTest(periodicite, client, revue, "createRevue");
        updateRevue(revue);
        displayTest(periodicite, client, revue, "updateRevue");
        revue = getRevueId(revue);
        displayTest(periodicite, client, revue, "getRevueId");

        // create abonnement
        client = createDateAbonnement(client, revue);
//        revue = client.getRevueById(revue.getId());
        displayTest(periodicite, client, revue, "createDateAbonnement");
        client = updateAbonnement(client, revue);
//        revue = client.getRevueById(revue.getId());
        displayTest(periodicite, client, revue, "updateAbonnement");
        client = deleteAbonnement(client, revue);
        displayTest(periodicite, client, revue, "deleteAbonnement");


        deleteClient(client);
        client = getClientById(client);

        deleteRevue(revue);
        revue = getRevueId(revue);

        deletePeriodicite(periodicite);
        periodicite = getPeriodiciteId(periodicite);
        displayTest(periodicite, client, revue, "FINI");

        findAll();

    }

    private static void selectPersistance() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Choisir une persistance :\n1: Base de données\n2: Liste");
        if (sc.nextInt() == 1) persistance = Persistance.MYSQL;
        else persistance = Persistance.LISTE;
        clientDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getClientDAO();
        periodiciteDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getPeriodiciteDAO();
        revueDAO = DAOFactory.getDAOFactory(DAOManager.getInstance()).getRevueDAO();
    }

    private static void findAll() {
        System.out.println("-------------------------findAllClient-------------------------");
        System.out.println(clientDAO.getAll());
        System.out.println("-------------------------findAllRevue-------------------------");
        System.out.println(revueDAO.getAll());
        System.out.println("-------------------------findAlPeriodicite-------------------------");
        System.out.println(periodiciteDAO.getAll());
    }

    private static void deleteRevue(Revue revue) {
        revueDAO.delete(revue);
    }

    private static void deletePeriodicite(Periodicite periodicite) {
        periodiciteDAO.delete(periodicite);
    }

    private static void deleteClient(Client client) {
        clientDAO.delete(client);

    }

    private static Client deleteAbonnement(Client client, Revue revue) {
        return clientDAO.deleteAbonnement(client, revue);
    }

    private static Client updateAbonnement(Client client, Revue revue) {
        DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut = LocalDate.parse("30/09/2017", formatage);
        LocalDate dateFin = LocalDate.parse("30/10/2018", formatage);
        DateAbonnement dateAbonnement = new DateAbonnement(dateDebut, dateFin);
        return clientDAO.updateAbonnement(client, revue, dateAbonnement);
    }


    private static Client createDateAbonnement(Client client, Revue revue) {
        DateTimeFormatter formatage = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate dateDebut = LocalDate.parse("30/09/2017", formatage);
        LocalDate dateFin = LocalDate.parse("30/10/2017", formatage);
        DateAbonnement dateAbonnement = new DateAbonnement(dateDebut, dateFin);
        return clientDAO.createAbonnement(client, revue, dateAbonnement);
    }

    private static Revue getRevueId(Revue revue) {
        return revueDAO.getById((Integer) revue.getId());
    }

    private static void updateRevue(Revue revue) {
        revue.setTitre("Science Avenir");
        revueDAO.update(revue);
    }

    private static Revue createRevue(Periodicite periodicite) {
        Revue revue = new Revue("Sciences et vie", "Science", 5, "Poche", (int) periodicite.getId());
        revueDAO.create(revue);
        return revue;
    }

    private static Periodicite getPeriodiciteId(Periodicite periodicite) {
        return periodiciteDAO.getById((Integer) periodicite.getId());
    }

    private static void updatePeriodicite(Periodicite periodicite) {
        periodicite.setLibelle("Trimestriel");
        periodiciteDAO.update(periodicite);
    }

    private static Periodicite createPeriodicite() {
        Periodicite periodicite = new Periodicite("Bi-Mensuel");
        periodiciteDAO.create(periodicite);
        return periodicite;
    }

    private static Client getClientById(Client client) {
        return clientDAO.getById((Integer) client.getId());
    }

    private static void updateClient(Client client) {
        client.setVille("Nancy");
        clientDAO.update(client);
    }


    private static Client createClient() {
        Client client = new Client("Schoirfer", "Maxence", "24", "rue des champs",
                "57970", "Yutz", "France");
        clientDAO.create(client);
        return client;
    }

    private static void displayTest(Periodicite periodicite, Client client, Revue revue, String msg) {
        System.out.println(" ");
        System.out.println("----------------" + msg + "-------------");
        if (periodicite != null) System.out.println(periodicite.toString());
        else System.out.println("periodicite = null");
        if (revue != null) System.out.println(revue.toString());
        else System.out.println("revue = null");
        if (client != null) System.out.println(client.toString());
        else System.out.println("client = null");
    }
}
