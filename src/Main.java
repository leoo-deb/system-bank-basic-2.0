import banco.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ContaBancaria contaBancaria = new ContaBancaria();
        int op;

        do {
            ViewBanco.menuBanco();
            op = sc.nextInt();
            sc.nextLine();

            if (op == 1) {
                contaBancaria.verificarAcesso();
            }

            if (op == 2) {
                contaBancaria.criarConta();
            }
        } while (op != 3);
    }
}