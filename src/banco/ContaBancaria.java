package banco;

import exceptions.*;
import java.util.ArrayList;
import java.util.Scanner;

@SuppressWarnings("all")
public class ContaBancaria {
    private ArrayList<Conta> contas = new ArrayList<>();
    private Scanner sc = new Scanner(System.in);

    public ContaBancaria() {
        contas.add(new Conta(new Cliente("admin", "1", "admin"), "1"));
    }

    public void criarConta() {
        String enter, nome, cpf, email, password;
        System.out.println("----------------------------");
        do {
            while (true) {
                System.out.print("Nome e sobrenome: ");
                nome = sc.nextLine().toUpperCase();

                if (nome.matches("^([a-zA-Z\\s]{3,30})$")) {
                    break;
                }
                System.out.println("ERROR: O nome deve conter pelo menos 3 caracteres.");
            }

            while (true) {
                System.out.print("CPF: ");
                cpf = sc.nextLine();

                if (cpf.matches("^(\\d{3}\\d{3}\\d{3}\\d{2})$")) {
                    for (Conta verificarCPF : contas) {
                        if (cpf.equals(verificarCPF.getCliente().getCpf())) {
                            System.out.println("ERROR: O CPF digitado acima ja possui uma conta registrada.");
                            return;
                        }
                    }
                    break;
                }
                System.out.println("ERROR: CPF invalido.");
            }

            while (true) {
                System.out.print("Email: ");
                email = sc.nextLine();

                if (email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
                    for (Conta verificarEmail : contas) {
                        if (email.equals(verificarEmail.getCliente().getEmail())) {
                            System.out.println("ERROR: O Email digitado acima ja possui uma conta registrada.");
                            return;
                        }
                    }
                    break;
                }
                System.out.println("ERROR: Email invalido.");
            }

            Cliente criarCliente = new Cliente(nome, cpf, email);
            System.out.println("----------------------------");

            while (true) {
                System.out.print("Crie uma senha de 4 digitos: ");
                password = sc.nextLine();

                if (password.matches("^(\\d{4})$")) {
                    break;
                }
                System.out.println("ERROR: A senha deve conter no minimo 4 caracteres.");
            }

            while (true) {
                System.out.print("Confirme a senha: ");
                String confirm = sc.nextLine();

                if (confirm.equals(password)) {
                    break;
                }
                System.out.println("ERROR: A senha deve ser a mesma criada acima.");
            }

            Conta conta = new Conta(criarCliente, password);
            contas.add(conta);

            System.out.println("----------------------------");
            System.out.println("SUCCESS: Conta criada com sucesso! Basta acessa-la no menu principal.");
            System.out.println(conta);

            System.out.print("Pressione a tecla (ENTER) para voltar ao inicio.");
            enter = sc.nextLine();
        } while (!enter.isBlank());
    }

    public void verificarAcesso() {
        System.out.println("----------------------------");
        System.out.print("CPF ou Email: ");
        String cpf_email = sc.nextLine();

        System.out.print("Senha: ");
        String password = sc.nextLine();

        try {
            acessoConta(cpf_email, password);
        } catch (CredentialAuthenticationException e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    private void acessoConta(String cpf_email, String password) {
        int op1;
        String enter;

        if (!contas.isEmpty()) {
            for (Conta acesso : contas) {
                if ((cpf_email.equals(acesso.getCliente().getCpf()) || cpf_email.equals(acesso.getCliente().getEmail()))
                        && password.equals(acesso.getPassword())) {
                    System.out.println("----------------------------");
                    System.out.println("Seja bem-vindo a sua conta bancaria " + acesso.getCliente().getNome() + "!");
                        do {
                            ViewBanco.menuConta();
                            op1 = sc.nextInt();
                            sc.nextLine();

                            if (op1 == 1) {
                                do {
                                    System.out.println(acesso);
                                    System.out.print("Pressione a tecla (ENTER) para voltar ao inicio.");
                                    enter = sc.nextLine();
                                } while (!enter.isBlank());
                            }

                            if (op1 == 2) {
                                System.out.println("----------------------------");
                                System.out.print("Digite o valor para o saque: ");
                                double saque = sc.nextDouble();
                                sc.nextLine();
                                try {
                                    acesso.sacar(saque);
                                    System.out.printf("SUCCESS: Saque realizado no valor de: R$ %.2f\n", saque);
                                    System.out.printf("Saldo atual: R$ %.2f\n", acesso.getSaldo());
                                } catch (IllegalArgumentException | InsufficientBalanceException e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                            }

                            if (op1 == 3) {
                                System.out.println("----------------------------");
                                System.out.print("Digite o valor para o deposito: ");
                                double deposito = sc.nextDouble();
                                sc.nextLine();
                                try {
                                    acesso.depositar(deposito);
                                    System.out.printf("SUCCESS: Deposito realizado no valor de: R$ %.2f\n", deposito);
                                    System.out.printf("Saldo atual: R$ %.2f\n", acesso.getSaldo());
                                } catch (IllegalArgumentException | InsufficientBalanceException e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                            }

                            if (op1 == 4) {
                                do {
                                    System.out.println("----------------------------");
                                    acesso.extratoConta();
                                    System.out.println("----------------------------");
                                    System.out.print("Pressione a tecla (ENTER) para voltar ao inicio.");
                                    enter = sc.nextLine();
                                } while (!enter.isBlank());
                            }

                            if (op1 == 5) {
                                do {
                                    System.out.println("----------------------------");
                                    System.out.printf("O saldo atual da conta e: R$ %.2f\n", acesso.getSaldo());
                                    System.out.print("Pressione a tecla (ENTER) para voltar ao inicio.");
                                    enter = sc.nextLine();
                                } while (!enter.isBlank());
                            }
                        } while (op1 != 6);
                    return;
                }
            }
            throw new CredentialAuthenticationException("CPF/Email e senha invalidos.");
        } else {
            throw new CredentialAuthenticationException("Nenhuma conta encontrada no sistema.");
        }
    }
}