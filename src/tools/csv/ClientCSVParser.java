package tools.csv;

import dao.factory.DAOFactory;
import dao.factory.DAOManager;
import metier.Client;

import java.util.List;

public class ClientCSVParser {

    public static void importerClients(String fileName) {
        Client client;
        CSVReader csvReader = new CSVReader();
        try {
            csvReader.openFile(fileName);
            while (csvReader.hasNext()) {
                client = ClientCSVParser.parser(csvReader.nextLine());
                if (client != null) {
                    DAOFactory.getDAOFactory(DAOManager.getInstance()).getClientDAO().create(client);
                }
            }
            csvReader.closeFile();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Client parser(List<String> lines) {
        Client client = new Client();
        client.setNom(lines.get(0));
        client.setPrenom(lines.get(1));
        client.setNoVoie(lines.get(2));
        client.setVoie(lines.get(3));
        client.setVille(lines.get(5));
        client.setPays(lines.get(6));
        client.setCodePostal(lines.get(4));
        return client;
    }
}
