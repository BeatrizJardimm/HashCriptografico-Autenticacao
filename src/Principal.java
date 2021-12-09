package ItemTres;

import java.io.*;
import java.util.Scanner;

public class Principal {

    public static void main(String[] args) {

        Principal pr = new Principal();
        Scanner ler = new Scanner(System.in);

        System.out.println("*** Opções:");
        System.out.println("1 - Cadastro");
        System.out.println("2 - Autenticação");
        System.out.print("Entre com uma das opções acima: ");
        int procedimento = ler.nextInt();

        if (procedimento == 1) {        //cadstro
            pr.cadastro();
        } else if (procedimento == 2){ //autenticação
            pr.autentica();
        } else{                        //entrada inválida
            System.out.println("Entre com uma opção válida.");
        }
    }

    /** Variáveis **/
    SHA sha = SHA.getInstance();
    MDC md5 = MDC.getInstance();

    Scanner ler = new Scanner(System.in);

    String nome;
    String senha;
    String shaSenha;
    String md5Senha;
    String usuario;

    /** Funções **/

    public void cadastro(){
        System.out.print("Entre com um nome de usuário de, no mínimo, 5 dígitos: ");
        nome = ler.nextLine();

        if (nome.length() >= 5){//tamanho minimo de username
            System.out.print("Entre com uma senha de 8 dígitos: ");
            senha = ler.nextLine();

            if (senha.length() == 8){//tamanho de senha

                shaSenha = sha.toString(senha);    //faz o SHA-512 da senha
                md5Senha = md5.toString(shaSenha); //faz o MD5 do SHA da senha
                usuario = nome + '|' + md5Senha;   //guarda o username e o MD5

                //verifica se o user já existe
                if (GetArquivo(usuario, "C:\\Users\\Usuario\\HashCriptografico\\src\\ItemTres\\Usuarios.txt")){
                    System.out.println("Esse usuário já existe");

                } else{ //insere um novo usuário no arquivo
                    AppendArquivo(usuario + '\n', "C:\\Users\\Usuario\\HashCriptografico\\src\\ItemTres\\Usuarios.txt");
                    System.out.println("Usuário cadastrado com sucesso!");
                }

            } else{                  //tamanho de senha inválido
                System.out.println("A senha deve ter 8 dígitos.");
            }

        } else{                 //tamanho de username inválido
            System.out.println("O nome de usuário é muito pequeno.");
        }
    }

    public void autentica(){
        System.out.print("Digite seu nome de usuário: ");
        nome = ler.nextLine();

        if (nome.length() >= 5){//verifica o tamanho do username
            System.out.print("Digite sua senha: ");
            senha = ler.nextLine();

            if (senha.length() == 8){//verifica se o tamanho da senha está correto

                shaSenha = sha.toString(senha);     //faz o SHA-512 da senha
                md5Senha = md5.toString(shaSenha);  //faz o MD5 do SHA da senha
                usuario = nome + '|' + md5Senha;    //guarda o username e o MD5

                //verifica se o usuário se encontra no arquivo
                if (GetArquivo(usuario, "C:\\Users\\Usuario\\HashCriptografico\\src\\ItemTres\\Usuarios.txt")){
                    System.out.println("Bem vindo "+ nome + "!");

                } else{//usuário não esta no arquivo
                    System.out.println("Usuário não encontrado.");
                }

            } else{                  //tamanho de senha incorreto
                System.out.println("Usuário não encontrado.");
            }

        } else{                 //tamanho de username inválido
            System.out.println("Usuário não encontrado.");
        }
    }

    public static void AppendArquivo(String conteudo, String nomeArquivo){
        try {
            Writer bw;
            bw = new BufferedWriter(new FileWriter(nomeArquivo, true));
            bw.write(conteudo);
            bw.close();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static boolean GetArquivo(String conteudo, String nomeArquivo){
        try {
            BufferedReader br = new BufferedReader(new FileReader(nomeArquivo));
            while (br.ready()) {                //enquanto houver mais linhas
                String linha = br.readLine();   //lê a proxima linha

                if (linha.equals(conteudo)){    //verifica se o conteudo da linha bate com o user digitado
                    return true;
                }
            }
            br.close();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

}