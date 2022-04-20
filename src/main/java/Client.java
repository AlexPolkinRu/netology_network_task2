import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    private static final String HOST = "netology.homework";
    private static final int PORT = 8080;
    private static BufferedReader in;
    private static PrintWriter out;
    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        try (Socket clientSocket = new Socket(HOST, PORT)) {

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(clientSocket.getOutputStream()), true);

            while (!readMessage().equals("Пока")) {
                writeMessage();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String readMessage() throws IOException {
        String serverMessage = in.readLine();
        System.out.println("[Сервер]: " + serverMessage);

        return serverMessage;
    }

    public static void writeMessage() {
        String message;
        System.out.print("[Вы]: ");
        message = scanner.nextLine();
        out.write(message + "\n");
        out.flush();
    }
}

