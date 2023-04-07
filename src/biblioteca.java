
import java.util.List;
import java.util.Scanner;


public class biblioteca {
    public static void main(String[] args) {
        String url = "files/biblioteca.csv";
        iniciarBiblioteca(url);
    }
    public static void iniciarBiblioteca(String url) {
        Scanner scanner = new Scanner(System.in);
        CadasBiblioteca biblioteca = new CadasBiblioteca();
        boolean start = true;
        if(!CadasBiblioteca.ArquivoExiste(url)) {
            biblioteca.CriarArquivo(url);
        }
        do {
            System.out.println("""
                    Seja bem-vindo a biblioteca, escolha uma das opções:\s
                    1 - Cadastrar Livro\s
                    2 - Remover Livro\s
                    3 - Buscar Livro\s
                    4 - Gerar Relatório\s
                    0 - Sair""");
            int escolha = scanner.nextInt();
            List<String> existentes = biblioteca.LinhaExistentes(url);
            switch (escolha) {
                default -> System.out.println("opção não existente!");
                case 0 -> {
                    System.out.println("Você saiu!");
                    start = false;
                }
                case 1 -> cadastrarLivro(url, scanner, biblioteca, existentes);
                case 2 -> excluirLivro(url, scanner, biblioteca, existentes);
                case 3 -> buscarLivro(scanner, existentes);
                case 4 -> gerarRelatorio(existentes);
            }
        } while(start);
        scanner.close();
    }
    public static void cadastrarLivro(String url, Scanner scanner, CadasBiblioteca biblioteca, List<String> existentes) {
        scanner.nextLine();
        String[] conteudoLivro = new String[3];
        System.out.print("Por favor, digite o nome do livro: ");
        conteudoLivro[0] = scanner.nextLine();
        System.out.print("Por favor, digite o número de páginas: ");
        int numeroPaginas = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Por favor, digite o nome do autor: ");
        conteudoLivro[1] = scanner.nextLine();
        System.out.print("Por favor, digite a área de interesse: ");
        conteudoLivro[2] = scanner.nextLine();
        if(existentes.size() == 1){
            biblioteca.CadastrarBiblioteca(conteudoLivro[0].trim(), String.valueOf(numeroPaginas).trim(), conteudoLivro[1].trim(), conteudoLivro[2].trim(), url);
            System.out.println(conteudoLivro[0] + " - Livro Cadastrado");
        } else {
            boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equalsIgnoreCase(conteudoLivro[0].trim()) && existentes.get(i).split(",")[2].trim().equalsIgnoreCase(conteudoLivro[1].trim()) && existentes.get(i).split(",")[3].trim().equalsIgnoreCase(conteudoLivro[2].trim())) {
                    encontrado = true;
                    break;
                }
            }
            if(encontrado)
                System.out.println("Livro duplicado! \n");
            else {
                biblioteca.CadastrarBiblioteca(conteudoLivro[0].trim(), String.valueOf(numeroPaginas).trim(), conteudoLivro[1].trim(), conteudoLivro[2].trim(), url);
                System.out.println(conteudoLivro[0] + " - Livro Cadastrado");
            }
        }
    }

    public static void buscarLivro(Scanner scanner, List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            scanner.nextLine();
            System.out.print("""
                        Qual será a forma de busca para o livro?\s
                        1- Nome do Livro\s
                        2- Nome do Autor\s
                        3- Área de Interesse\s
                        Escolha:\s""");
            int escolha = scanner.nextInt();
            if (escolha == 1) buscaPorNomeLivro(scanner, existentes);
            else if (escolha == 2) buscaPorNomeAutor(scanner, existentes);
            else if (escolha == 3) buscaPorAreaInteresse(scanner, existentes);
            else System.out.println("Escolha Incorreta!");
        }
    }

    public static void buscaPorNomeLivro(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do livro? ");
        String nomeDoLivro = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[0].trim().equals(nomeDoLivro.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro não encontrado!");
    }
    public static void buscaPorNomeAutor(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual o nome do autor? ");
        String nomeDoAutor = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[2].trim().equals(nomeDoAutor.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro com o nome do autor não encontrado!");
    }
    public static void buscaPorAreaInteresse(Scanner scanner, List<String> existentes) {
        scanner.nextLine();
        System.out.print("Qual à area de interesse? ");
        String areaDeInteresse = scanner.nextLine();
        boolean encontrado = false;
        for (int i = 1; i < existentes.size(); i++) {
            if (existentes.get(i).split(",")[3].trim().equals(areaDeInteresse.trim())) {
                System.out.println(existentes.get(i));
                encontrado = true;
            }
        }
        if(!encontrado) System.out.println("Livro com o área de interesse não encontrado!");
    }


    public static void excluirLivro(String url, Scanner scanner, CadasBiblioteca biblioteca, List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            String[] livro = new String[4];
            String[] estruturaLivro = {
                    "Nome do Livro",
                    "Número de Páginas",
                    "Nome do Autor",
                    "Área de Interesse"
            };
            scanner.nextLine();
            for (int i = 0; i < livro.length; i++) {
                System.out.print("Por favor, digite o(a) " + estruturaLivro[i] + ": ");
                livro[i] = scanner.nextLine();
            }
            boolean encontrado = false;
            for (int i = 1; i < existentes.size(); i++) {
                if (existentes.get(i).split(",")[0].trim().equals(livro[0].trim()) && existentes.get(i).split(",")[1].trim().equals(livro[1].trim()) && existentes.get(i).split(",")[2].trim().equals(livro[2].trim()) && existentes.get(i).split(",")[3].trim().equals(livro[3].trim())) {
                    existentes.remove(existentes.get(i));
                    biblioteca.removeLivroBiblioteca(existentes, url);
                    encontrado = true;
                }
            }
            if (!encontrado) System.out.println("Livro não encontrado! \n");
        }
    }

    public static void gerarRelatorio(List<String> existentes) {
        if (existentes.size() == 1)
            System.out.println("Cadastre pelo menos um livro! \n");
        else {
            for (String livro : existentes) {
                System.out.println(livro);
            }
        }
    }
}