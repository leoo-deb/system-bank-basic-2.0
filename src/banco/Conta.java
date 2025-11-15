package banco;

import exceptions.InsufficientBalanceException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.NoSuchElementException;

@SuppressWarnings("all")
public class Conta {
    private final ArrayList<String> extratoConta;
    private final String password;
    private final Cliente cliente;
    private double saldo;

    private final int ID_CONTA;
    private static int numberID = 1;

    public Conta(Cliente cliente, String password) {
        this.ID_CONTA = numberID++;
        this.cliente = cliente;
        this.password = password;
        this.extratoConta = new ArrayList<>();
    }

    public String getPassword() {
        return password;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getID_CONTA() {
        return ID_CONTA;
    }

    public double getSaldo() {
        return saldo;
    }

    public void depositar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor invalido.");
        }
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy"));
        saldo += valor;
        extratoConta.add(String.format("(+) R$ %.2f (%s)", valor, date));
    }

    public void sacar(double valor) {
        if (valor <= 0) {
            throw new IllegalArgumentException("Valor invalido.");
        }
        if (valor > saldo) {
            throw new InsufficientBalanceException("A conta nao possui saldo suficiente para o saque.");
        }
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss - dd/MM/yyyy"));
        saldo -= valor;
        extratoConta.add(String.format("(-) R$ %.2f (%s)", valor, date));
    }

    public void extratoConta() {
        if (!extratoConta.isEmpty()) {
            System.out.println("Extrato bancario da conta: ");
            for (int i = extratoConta.size(); i >= 1; i--) {
                System.out.println(extratoConta.get(i - 1));
            }
        } else {
            throw new NoSuchElementException("Nao existe nenhuma operacao feita nesta conta.");
        }
    }

    @Override
    public String toString() {
        return String.format("""
                ----------------------------
                Nome: %s
                CPF: %s
                Email: %s
                Conta: 1525-%d
                ----------------------------""", cliente.getNome(), cliente.getCpf(), cliente.getEmail(), ID_CONTA);
    }
}