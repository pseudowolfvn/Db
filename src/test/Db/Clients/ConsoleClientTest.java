package Db.Clients;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class ConsoleClientTest {
    public static void main(String[] args) {
        ConsoleClient client = new ConsoleClient();
        try {
            client.run("/home/pseudowolf/dev/Projects/KNU/IT/Db/out/db");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }
}