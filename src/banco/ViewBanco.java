package banco;

@SuppressWarnings("all")
public class ViewBanco {
    public static void menuBanco() {
        System.out.println("""
                ----------------------------
                [1] Acessar conta
                [2] Criar conta
                [3] Sair
                ----------------------------""");
        System.out.print("Escolha uma opcao: ");
    }

    public static void menuConta() {
        System.out.println("""
                ----------------------------
                [1] Verificar informacoes da conta
                [2] Fazer um novo saque
                [3] Fazer um novo deposito
                [4] Consultar extrato bancario
                [5] Consultar saldo da conta
                [6] Sair da conta
                ----------------------------""");
        System.out.print("Escolha uma opcao: ");
    }
}
