package revisaoa1;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Gerenciamento {
	
	private List<Produto> estoque = new ArrayList<>();

public static void main(String[] args) throws ParseException {
    Gerenciamento gerenciador = new Gerenciamento();
    Scanner scanner = new Scanner(System.in);

    int escolha = 0; 
    do{
        System.out.println("Menu Principal:");
        System.out.println("1. Adicionar Produto");
        System.out.println("2. Atualizar Produto");
        System.out.println("3. Remover Produto");
        System.out.println("4. Procurar Produto");
        System.out.println("5. Calcular Valor Total do Estoque");
        System.out.println("6. Gerar Relatório de Produtos Próximos ao Vencimento");
        System.out.println("7. Sair");
        System.out.print("Escolha uma opção: ");

      
        if (scanner.hasNextInt()) {
            escolha = scanner.nextInt();
            scanner.nextLine(); 
        } else {
            System.out.println("Opção inválida. Digite um número válido.");
            scanner.nextLine(); 
            continue;
        }

        switch (escolha) {
            case 1:
                gerenciador.adicionarProduto();
                break;
            case 2:
                gerenciador.atualizarProduto();
                break;
            case 3:
                gerenciador.removerProduto();
                break;
            case 4:
                gerenciador.procurarProduto();
                break;
            case 5:
                gerenciador.calcularValorTotal();
                break;
            case 6:
                gerenciador.gerarRelatorioVencimento();
                break;
            case 7:
                System.out.println("Saindo do sistema.");
                break;
            default:
                System.out.println("Opção inválida. Tente novamente.");
        }
    } while (escolha != 7);

    scanner.close();
}



public void adicionarProduto() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Nome do produto: ");
    String nome = scanner.nextLine();

    String codigoDeBarras = null;
    boolean codigoUnico = false;

    while (!codigoUnico) {
        System.out.print("Código de barras: ");
        codigoDeBarras = scanner.nextLine();

        boolean codigoRepetido = false;
        for (Produto produto : estoque) {
            if (produto.getCodigoDeBarras().equals(codigoDeBarras)) {
                codigoRepetido = true;
                break;
            }
        }

        if (!codigoRepetido) {
            codigoUnico = true;
        } else {
            System.out.println("Código de barras já existe. Tente novamente.");
        }
    }

    int quantidade = 0; 
    boolean quantidadeValida = false;

    while (!quantidadeValida) {
        System.out.print("Quantidade (apenas números): ");
        String quantidadeStr = scanner.nextLine();

        
        try {
            quantidade = Integer.parseInt(quantidadeStr);
            quantidadeValida = true; 
        } catch (NumberFormatException e) {
            System.out.println("A quantidade deve ser um número válido. Tente novamente.");
        }
    }

    double precoUnitario = 0.0; 
    boolean precoValido = false;

    while (!precoValido) {
        System.out.print("Preço unitário: ");
        String precoStr = scanner.nextLine();

        
        try {
            precoUnitario = Double.parseDouble(precoStr);
            precoValido = true; 
        } catch (NumberFormatException e) {
            System.out.println("O preço unitário deve ser um número válido. Tente novamente.");
        }
    }

    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    Date dataDeValidade = null;
    boolean dataValida = false;

    while (!dataValida) {
        System.out.print("Data de validade (ddMMaaaa, sem barras): ");
        String dataValidadeStr = scanner.nextLine();

        try {
            dataDeValidade = dateFormat.parse(dataValidadeStr);
            dataValida = true;
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use o formato ddMMaaaa.");
        }
    }

    Produto novoProduto = new Produto(nome, codigoDeBarras, quantidade, precoUnitario, dataDeValidade);
    estoque.add(novoProduto);

    System.out.println("Produto adicionado com sucesso!");
}

public void atualizarProduto() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Código de barras do produto a ser atualizado: ");
    String codigoDeBarras = scanner.nextLine();

    Produto produtoParaAtualizar = null;
    for (Produto produto : estoque) {
        if (codigoDeBarras != null && codigoDeBarras.equals(produto.getCodigoDeBarras())) {
            produtoParaAtualizar = produto;
            break;
        }
    }

    if (produtoParaAtualizar != null) {
        System.out.println("Produto encontrado: " + produtoParaAtualizar.getNome());
        System.out.println("Informe as informações atualizadas:");

        System.out.print("Novo nome: ");
        String novoNome = scanner.nextLine();
        produtoParaAtualizar.setNome(novoNome);

        System.out.print("Nova quantidade: ");
        int novaQuantidade = scanner.nextInt();
        produtoParaAtualizar.setQuantidade(novaQuantidade);

        System.out.print("Novo preço unitário: ");
        double novoPrecoUnitario = scanner.nextDouble();
        produtoParaAtualizar.setPrecoUnitario(novoPrecoUnitario);

        scanner.nextLine(); 

        System.out.print("Nova data de validade (ddMMaaaa)por favor digite sem as barras: ");
        String novaDataValidadeStr = scanner.nextLine();
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
        Date novaDataValidade = null;
        try {
            novaDataValidade = dateFormat.parse(novaDataValidadeStr);
        } catch (ParseException e) {
            System.out.println("Formato de data inválido. Use o formato ddMMaaaa.");
            return;
        }
        produtoParaAtualizar.setDataDeValidade(novaDataValidade);

        System.out.print("Novo código de barras: ");
        String novoCodigoDeBarras = scanner.nextLine();
        produtoParaAtualizar.setCodigoDeBarras(novoCodigoDeBarras);

        System.out.println("Produto atualizado com sucesso!");
    } else {
        System.out.println("Produto não encontrado.");
    }
}


public void removerProduto() {
    Scanner scanner = new Scanner(System.in);

    System.out.print("Código de barras do produto a ser removido: ");
    String codigoDeBarras = scanner.nextLine();

    Produto produtoARemover = null;
    for (Produto produto : estoque) {
        if (produto.getCodigoDeBarras().equals(codigoDeBarras)) {
            produtoARemover = produto;
            break;
        }
    }

    if (produtoARemover != null) {
        System.out.println("Produto encontrado: " + produtoARemover.getNome());
        System.out.print("Tem certeza de que deseja remover este produto? (S/N): ");
        String confirmacao = scanner.nextLine();
        if (confirmacao.equalsIgnoreCase("S")) {
            estoque.remove(produtoARemover);
            System.out.println("Produto removido com sucesso!");
        } else {
            System.out.println("Remoção cancelada.");
        }
    } else {
        System.out.println("Produto não encontrado.");
    }
}
public void procurarProduto() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Opções de pesquisa:\n1. Por nome\n2. Por código de barras\n3. Por data de validade\nEscolha a opção: ");
    int opcao = scanner.nextInt();
    scanner.nextLine(); 

    switch (opcao) {
        case 1:
            System.out.print("Digite o nome do produto: ");
            String nomePesquisa = scanner.nextLine();
            for (Produto produto : estoque) {
                if (produto.getNome().equalsIgnoreCase(nomePesquisa)) {
                    System.out.println("Produto encontrado: " + produto.getNome());
                }
            }
            break;

        case 2:
            System.out.print("Digite o código de barras do produto: ");
            String codigoBarrasPesquisa = scanner.nextLine();
            for (Produto produto : estoque) {
                if (produto.getCodigoDeBarras().equals(codigoBarrasPesquisa)) {
                    System.out.println("Produto encontrado: " + produto.getNome());
                }
            }
            break;

        case 3:
            System.out.print("Digite a data de validade (ddMMaaaa): ");
            String dataPesquisaStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
            try {
                Date dataPesquisa = dateFormat.parse(dataPesquisaStr);
                for (Produto produto : estoque) {
                    if (produto.getDataDeValidade().equals(dataPesquisa)) {
                        System.out.println("Produto encontrado: " + produto.getNome());
                    }
                }
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Use o formato ddMMaaaa.");
            }
            break;

        default:
            System.out.println("Opção inválida.");
            break;
    }
    
}
public void calcularValorTotal() {
    double valorTotal = 0.0;
    for (Produto produto : estoque) {
        valorTotal += produto.getPrecoUnitario() * produto.getQuantidade();
    }
    System.out.println("Valor total do estoque: R$" + valorTotal);
}
public void gerarRelatorioVencimento() {
    SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    Date dataAtual = new Date(); 

    Calendar calendar = Calendar.getInstance();
    calendar.setTime(dataAtual);
    calendar.add(Calendar.DAY_OF_MONTH, 30); 

    Date dataFutura = calendar.getTime();

    System.out.println("Produtos próximos ao vencimento:");

    boolean encontrado = false; 

    for (Produto produto : estoque) {
        if (produto.getDataDeValidade().before(dataFutura)) {
            System.out.println("Nome: " + produto.getNome() + ", Data de Validade: " + dateFormat.format(produto.getDataDeValidade()));
            encontrado = true; 
        }
    }

    if (!encontrado) {
        System.out.println("Nenhum produto próximo ao vencimento encontrado.");
    }
}

}
