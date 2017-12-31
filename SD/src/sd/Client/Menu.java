package sd.Client;

import java.util.Scanner;

    public class Menu {
        private Scanner in;

        Menu() {
            in = new Scanner(System.in);
            in.useDelimiter("[\r\n]");
        }

        public int show(String[] entries) {
            int option = 0;

           //apresentar menu


            return option;
        }

        public void printResponse(String response) {
            if (response.length() > 0)
                response += "\n";

            System.out.print("\n" + response);
        }

        public String readString(String msg) {
            System.out.print(msg);
            return in.next();
        }

        public int readInt(String msg) {
            int num;

            try {
                System.out.print(msg);
                num = Integer.parseInt(in.next());
            } catch (NumberFormatException e) {
                System.out.println("\n> O valor introduzido não é válido\n");
                num = readInt(msg);
            }

            return num;
        }
}
