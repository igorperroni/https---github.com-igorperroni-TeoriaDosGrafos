import java.util.*;

public class ProjetoGrafos {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int[][] adjacencyMatrix;
        int numVertices;
        int numArestas;
        
        //solicita a quantidade de vértices e arestas
        System.out.print("Digite a quantidade de vertices: ");
        numVertices = scan.nextInt();
        System.out.println("################################");
        System.out.print("Digite a quantidade de arestas: ");
        numArestas = scan.nextInt();
        System.out.println("################################");
        
        // Cria a matriz de adjacência
        adjacencyMatrix = new int[numVertices][numVertices];

        // Solicita as arestas e forma o grafo
        for (int i = 0; i < numArestas; i++) {
            System.out.print("Digite a primeira vertice da aresta " + (i + 1) + ": ");
            int v1 = scan.nextInt();
            System.out.print("Digite a segunda vertice da aresta " + (i + 1) + ": ");
            int v2 = scan.nextInt();
            System.out.println("################################");
            
            // Verifica se as vértices estão dentro do intervalo válido
            if (v1 < 1 || v1 > numVertices || v2 < 1 || v2 > numVertices) {
                System.out.println("Vertice invalidas. Tente novamente.");
                i--;
                System.out.println("################################");
                continue;
            }
            
          // Verifica se é uma paralela ou loop
            if (v1 == v2) {
                adjacencyMatrix[v1 - 1][v2 - 1]++;
            } else {
                adjacencyMatrix[v1 - 1][v2 - 1]++;
                adjacencyMatrix[v2 - 1][v1 - 1]++;
            }
        }
        
              // Encontra o vértice com o maior grau
        int maiorGrau = 0;
        int verticeMaiorGrau = 0;
        for (int i = 0; i < numVertices; i++) {
            int grau = 0;
            for (int j = 0; j < numVertices; j++) {
                grau += adjacencyMatrix[i][j];
                if (i == j && adjacencyMatrix[i][j] > 0) { // Verifica se é um loop
                    grau++; // Incrementa o grau em 1 para loops
                }
            }
            if (grau > maiorGrau) {
                maiorGrau = grau;
                verticeMaiorGrau = i + 1;
            }
        }
        System.out.println("Vertice com o maior grau: " + verticeMaiorGrau + " (Grau: " + maiorGrau + ")");

        // Encontra o vértice com o menor grau
        int menorGrau = Integer.MAX_VALUE;
        int verticeMenorGrau = 0;
        for (int i = 0; i < numVertices; i++) {
            int grau = 0;
            for (int j = 0; j < numVertices; j++) {
                grau += adjacencyMatrix[i][j];
                if (i == j && adjacencyMatrix[i][j] > 0) { // Verifica se é um loop
                    grau++; // Incrementa o grau em 1 para loops
                }
            }
            if (grau < menorGrau) {
                menorGrau = grau;
                verticeMenorGrau = i + 1;
            }
        }
        System.out.println("\nVertice com o menor grau: " + verticeMenorGrau + " (Grau: " + menorGrau + ")");
        System.out.println("################################\n");
    
        
        System.out.println("O grafo possui:");
        System.out.println("Numero de vertice: " + numVertices);
        System.out.println("Numero de arestas: " + numArestas);
        System.out.println("################################\n");
        
        // Exibe a matriz de adjacência
        System.out.println("Matriz de Adjacencia:");
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; j++) {
                System.out.print(adjacencyMatrix[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println("################################\n");  
        
        // Verifica se o grafo é conexo
        boolean[] visitados = new boolean[numVertices];
        List<List<Integer>> componentes = new ArrayList<>();

        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                List<Integer> componente = new ArrayList<>();
                dfs(i, adjacencyMatrix, visitados, componente);
                componentes.add(componente);
            }
        }

        if (componentes.size() == 1) {
            System.out.println("O grafo e conexo.");
        } else {
            System.out.println("O grafo nao e conexo. Possui " + componentes.size() + " componentes.");
            System.out.println("Componentes do grafo:");
            for (int i = 0; i < componentes.size(); i++) {
                System.out.println("Componente " + (i + 1) + ": " + componentes.get(i));
            }
        }
         System.out.println("################################\n"); 
        // Busca em Profundidade)
        System.out.println("Busca em Profundidade:");
        Arrays.fill(visitados, false);
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                dfs(i, adjacencyMatrix, visitados);
            }
        }
         System.out.println("\n################################\n"); 
        // Busca em Largura
        System.out.println("\nBusca em Largura:");
        Arrays.fill(visitados, false);
        for (int i = 0; i < numVertices; i++) {
            if (!visitados[i]) {
                bfs(i, adjacencyMatrix, visitados);
            }
        }
         System.out.println("\n################################\n"); 
    }

    private static void dfs(int vertice, int[][] adjacencyMatrix, boolean[] visitados, List<Integer> componente) {
        visitados[vertice] = true;
        componente.add(vertice + 1);

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertice][i] > 0 && !visitados[i]) {
                dfs(i, adjacencyMatrix, visitados, componente);
            }
        }
    }

    private static void dfs(int vertice, int[][] adjacencyMatrix, boolean[] visitados) {
        System.out.print((vertice + 1) + " ");
        visitados[vertice] = true;

        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (adjacencyMatrix[vertice][i] > 0 && !visitados[i]) {
                dfs(i, adjacencyMatrix, visitados);
            }
        }
    }

    private static void bfs(int vertice, int[][] adjacencyMatrix, boolean[] visitados) {
        Queue<Integer> queue = new LinkedList<>();
        queue.add(vertice);
        visitados[vertice] = true;

        while (!queue.isEmpty()) {
            int v = queue.poll();
            System.out.print((v + 1) + " ");

            for (int i = 0; i < adjacencyMatrix.length; i++) {
                if (adjacencyMatrix[v][i] > 0 && !visitados[i]) {
                    queue.add(i);
                    visitados[i] = true;
                }
            }
        }
    }
}