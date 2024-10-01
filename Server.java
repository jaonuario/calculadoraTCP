import java.net.*;
import java.io.*;

public class Server {
    
    public static void main(String[] args) {
        
		// declaracao das variaveis
        ServerSocket serverSocket;      // Socket TCP servidor (conexao)
        Socket clientSocket;            // Socket TCP de comunicacao com o cliente
        String message;               	// Mensagem enviada pelo cliente
		BufferedReader in;				// Entrada(recepcao) formatada de dados
		PrintWriter out;				// Saida (envio) formatado de dados

        String token[];
        Float result;

        try {
            // abre socket TCP servidor
            serverSocket = new ServerSocket(1050);
            
            // espera por requisicao de conexao enviada pelo socket cliente
            clientSocket = serverSocket.accept();	// cria socket TCP de comunicacao com o cliente

            // abre fluxos de entrada e saida de dados associados ao socket TCP de comunicacao com o cliente
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
                
		    // recebe mensagem de requisicao, escreve mensagem de requisicao e envia mensagem de resposta		
            System.out.println("Servidor pronto...");
            message = in.readLine();
            if (message != null) {
                System.out.println("Do cliente ->" + message);

                token = message.split(" ");
                result = calculadora(token);

                out.println("Result -> " + result.toString());		// envia mensagem de volta para o cliente
            }

            // fecha fluxos de entrada e saida de dados
            out.close();
            in.close();

            // fecha sockets TCP de comunicacao com o cliente
            clientSocket.close();
            serverSocket.close();    
              
        } catch (IOException e) {
            System.err.println("Erro: " + e);
        }             
    }
    
    static public Float calculadora(String token[]) {    
        String operator = token[1];

        Float value1 = Float.parseFloat(token[0]);
        Float value2 = Float.parseFloat(token[2]);

        switch (operator) {
            case "+":
                return value1 + value2;

            case "-":
                return value1 - value2;
            
            case "*":
                return value1 * value2;
            
            case "/":
                if(value2 == 0f)
                    return 0f;

                return value1 / value2;
            
            default:
                return 0f;
            }
    }
}
