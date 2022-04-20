import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class Server {

    private static final int PORT = 8080;

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {

            // Ожидание подключения
            System.out.println("Ждём подключения...");
            Socket clientSocket = serverSocket.accept();

            System.out.println("К нам подключился клиент: " + clientSocket.toString());

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream())
            );

            PrintWriter out = new PrintWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream()), true
            );

            // Этап знакомства
            out.write("Здравствуйте, Вы успешно подключились к серверу. Пожалуйста, напишите своё имя.\n");
            out.flush();

            String clientName = in.readLine();
            System.out.println("Имя клиента: " + clientName);

            // Этап определения уровня игры
            out.write("Здравствуйте, " + clientName + "! Вы ребёнок? (да/нет)\n");
            out.flush();

            boolean isLoop = true;
            boolean isChildren = false;

            while (isLoop) {
                String clientMessage = in.readLine();
                System.out.println("Сообщение клиента: " + clientMessage);

                switch (clientMessage) {
                    case "да":
                        out.write("Добро пожаловать в детскую зону, " + clientName + "! Давай играть! ");
                        out.flush();
                        isChildren = true;
                        isLoop = false;
                        break;
                    case "нет":
                        out.write("Добро пожаловать во взрослую зону, " + clientName + "! Хорошего отдыха! ");
                        out.flush();
                        isLoop = false;
                        break;
                    default:
                        out.write("Вы ребёнок? (да/нет)\n");
                        out.flush();
                }
            }

            // Этап игры
            int randNumber;

            if (isChildren) {
                randNumber = new Random().nextInt(11);
                out.write("Я загадал число от 1 до 10, попробуй отгадать.\n");
            } else {
                randNumber = new Random().nextInt(1001);
                out.write("Я загадал число от 1 до 1000, попробуйте отгадать.\n");
            }
            out.flush();

            System.out.println("Я загадал " + randNumber);

            int clientNumber;

            while (true) {

                try {
                    clientNumber = Integer.parseInt(in.readLine());
                } catch (Exception e) {
                    out.write("Можно вводить только числа!\n");
                    out.flush();
                    continue;
                }

                System.out.println("Клиент предположил " + clientNumber);

                if (clientNumber == randNumber) {
                    break;
                }

                if (clientNumber > randNumber) {
                    out.write("Загаданное число меньше.\n");
                } else {
                    out.write("Загаданное число больше.\n");
                }
                out.flush();
            }

            if (isChildren) {
                out.write("Ты угадал. Молодец!\n");
            } else {
                out.write("Вы угадали. Поздравляю!\n");
            }
            out.flush();

            // Выход
            out.write("Пока\n");
            out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}