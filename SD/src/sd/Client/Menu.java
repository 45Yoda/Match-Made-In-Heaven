
package Client;

import java.util.Scanner;

    public class Menu {
        private Scanner in;
        private int op;

        public Menu() {
            in = new Scanner(System.in);
            in.useDelimiter("[\r\n]");
            op = 0;
        }

        public void showMenu() {
        switch(op){
            case 0: System.out.println("************* MENU ****************\n"+
                                       "* 1 - Iniciar Sessao              *\n"+
                                       "* 2 - Registar                    *\n"+
                                       "* 0 - Sair                        *\n"+
                                       "***********************************\n");
                    break;
            case 1: System.out.println("************* LOGGED **************\n"+
                                       "* 1 - Procurar Jogo               *\n"+
                                       "* 2 - Ver Stats                      *\n"+
                                       "* 0 - Terminar Sessao             *\n"+
                                       "***********************************\n");
                    break;
        }
    }

        public int getOp(){
            return this.op;
        }
        
        public void setOp(int op){
            this.op = op;
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