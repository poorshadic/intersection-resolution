compilar : javac Main.java
executar : java Main
questoes resolvidas:
            .2a- cria a ordem pela qual os pontos devem ser ligados de forma aleatoria
            .2b- cria a ordem pela qual os pontos devem ser ligados com a heuristica do vizinho mais proximo
            .3 - testa a existencia de interseçoes nas ligaçoes criadas (usa como base o 2b)
            .4a - percorre todas as linhas do poligno e testa se as mesmas participam de alguma interseção guardando
              os pontos daquela que apresente uma melhor vairação do perimetro, resolvendo-a depois.
            .4b - percorre todas as linhas do poligno e testa se as mesmas participam de alguma interseçao, 
              resolvendo-a em seguida
            .4c - percorre todas as linhas do poligno e testa se a mesma participa de alguma interseçao guardando
            os pontos da reta que teria o menor numero de interseções diferentes de 0 e resolve todas as 
            interseçoes que tenham como membro essa reta
            .4d - percorre todas as linhas do poligno e testa se a mesma participa de alguma interseçao guardando
            sempre numa lista os 4 pontos que resultam dessa interseçao e entao de forma aleatoria escolher um
            membro da lista e resolver a interseçao criada apartir desse conjunto de pontos
	    .5 - simulated anneling com medida de custo o numero de arestas

bugs conhecidos: Por consequência de não resolver retas colineares com interseções, cria loops na procura por interseções
		