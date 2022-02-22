import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
class Point {
    int x;
    int y;

    Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    protected Point clone(){
        return new Point(x,y);
    }
}
class Perimetro {
    Point p1;
    Point p2;
    Point p3;
    Point p4;
    double variacao;
    double per1,per2;

    Perimetro(Point p1,Point p2,Point p3,Point p4) {
        this.p1 = p1.clone();
        this.p2 = p2.clone();
        this.p3 = p3.clone();
        this.p4 = p4.clone();
        per1 = (Math.pow((p1.x - p2.x), 2)) + (Math.pow(p1.y - p2.y, 2)) + (Math.pow((p3.x - p4.x), 2)) + (Math.pow(p3.y - p4.y, 2));
        per2 = (Math.pow((p1.x - p3.x), 2)) + (Math.pow(p1.y - p3.y, 2)) + (Math.pow((p2.x - p4.x), 2)) + (Math.pow(p2.y - p4.y, 2));
        variacao = per1-per2;
    }
}
public class Main {
    private static double temperature = 1000000000;
    private static double coolingFactor = 0.995;
    public static double probability(int f2, double temp) {
        if (f2 > 0) {
            return 1;
        } else{
            return Math.exp(f2 / temp);
        }
    }
    static int min(Point[] polig, int rand, int m, boolean[] visited) {
        double temp = Math.pow(m, 2) * 2; // distancia maxima entre pontos
        int fim = 0;
        for (int i = 0; i < polig.length - 1; i++) {
            if (rand != i && !visited[i]) {
                double dist = Math.sqrt((Math.pow((polig[rand].x - polig[i].x), 2)) + (Math.pow(polig[rand].y - polig[i].y, 2)));
                if (temp > dist) {
                    temp = dist;
                    fim = i;
                }
            }

        }
        visited[fim] = true;
        return fim;
    }
    static Point[] aleatorios(Scanner in,int n,int m){
        boolean flag = true;
        Point[] polig = new Point[n + 1];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            int b = rand.nextInt((m - (-m)) + 1) - m;
            int a = rand.nextInt((m - (-m)) + 1) - m;
            Point p1 = new Point(a, b);
            System.out.println(p1.x + "," + p1.y);
            for(int j=0;j<i;j++){
                if(p1.x==polig[j].x && p1.y==polig[j].y){
                    i--;
                    flag= false;
                }
            }
            if(flag) polig[i] = p1;
            flag = true;
        }
        System.out.println("Pontos Gerados: ");
        for(int i =0;i<n;i++){
            System.out.print("( " + polig[i].x + ", " + polig[i].y + ")" + " ");
            if (i < n-1) {
                System.out.print("-> ");
            }
        }
        System.out.println();
        return polig;
    }
    static Point[] rdfromterm(Scanner in,int n,int m){
        Point[] polig = new Point[n + 1];
        for (int i = 0; i < n; i++) {
            int a = in.nextInt();
            int b = in.nextInt();
            Point p1 = new Point(a, b);
            polig[i] = p1;
        }
        System.out.println("Pontos Inseridos: ");
        for(int i =0;i<n;i++){
            System.out.print("( " + polig[i].x + ", " + polig[i].y + ")" + " ");
            if (i < n-1) {
                System.out.print("-> ");
            }
        }
        System.out.println();
        return polig;
    }
    private static Point[] shuffle(Point[] array,int n)
    {
        int index;
        Point temp;
        Random random = new Random();
        boolean[] visited = new boolean[n];
        for (int i = n - 1; i > 0; i--) {
            index = random.nextInt(i+1);
            if (!visited[index]){
                temp = new Point(array[index].x, array[index].y);
                array[index] = array[i];
                array[i] = temp;
                visited[index] = true;
            }
        }
        array[n] = new Point(array[0].x, array[0].y);
        for(int i =0;i<n+1;i++){
            System.out.print("( " + array[i].x + ", " + array[i].y + ")" + " ");
            if (i < n) {
                System.out.print("-> ");
            }
        }
        System.out.println();
        return array;
    }
    static Point[] nerst_neibour(Point[] polig,int n,int m){
        Point[] order= new Point[n+1];
        boolean[] visited = new boolean[n]; // vetor que guarda os pontos já visitados
        int r = 0;
        int t = r;
        visited[r] = true;
        order[0] = new Point(polig[r].x, polig[r].y);
        for (int i = 1; i < n; i++) {
            r = min(polig, r, m, visited);
            order[i] = new Point(polig[r].x, polig[r].y);
        }
        order[n] = new Point(order[0].x, order[0].y);
        for(int i =0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if (i < n ) {
                System.out.print("-> ");
            }
        }
        System.out.println();
        return order;
    }
    static int direction(Point pi, Point pj, Point pk) {
        return (((pk.x - pi.x) * (pj.y - pi.y)) - ((pj.x - pi.x) * (pk.y - pi.y)));
    }

    static boolean on_seg(Point pi, Point pj, Point pk) {
        if ((Math.min(pi.x, pj.x) <= pk.x && pk.x <= Math.max(pi.x, pj.x)) && (Math.min(pi.y, pj.y) <= pk.y && pk.y <= Math.max(pi.y, pj.y))) {
            return true;
        } else {
            return false;
        }
    }

    static boolean check_intersection(Point p1, Point p2, Point p3, Point p4) {
        int d1 = direction(p3, p4, p1);
        int d2 = direction(p3, p4, p2);
        int d3 = direction(p1, p2, p3);
        int d4 = direction(p1, p2, p4);
        if (((d1 > 0 && d2 < 0) || (d1 < 0 && d2 > 0)) && ((d3 > 0 && d4 < 0) || (d3 < 0 && d4 > 0))) {
            return true;
        } else if ((d1 == 0) && on_seg(p3, p4, p1)) {
            return true;
        } else if ((d2 == 0) && on_seg(p3, p4, p2)) {
            return true;
        } else if ((d3 == 0) && on_seg(p1, p2, p3)) {
            return true;
        } else if ((d4 == 0) && on_seg(p1, p2, p4)) {
            return true;
        } else {
            return false;
        }
    }
    static void espelhar(int p1,int p2,Point[] order){
        int i;
        i = p1;
        Point k = new Point(0, 0);
        for (int j = p2; j >= i; j = j-1){
            k.x = order[i].x;
            k.y = order[i].y;
            order[i].x = order[j].x;
            order[i].y = order[j].y;
            order[j].x = k.x;
            order[j].y = k.y;
            i++;
        }
    }
    static int colisions(Point[] temp,int n){
        int count=0;
        for(int i=0;i<n-1;i++){
            for(int j=i+2;j<n;j++){
                if(i==0 && j==n-1) break;
                boolean d = check_intersection(temp[i], temp[i + 1], temp[j], temp[j + 1]);
                if (d) {
                    count++;
                }
            }
        }
        return count;
    }
    static int r_colisions(Point[] order,Point[] temp,int n){
        int col= 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 2; j < n; j++) {
                if(i==0 && j==n-1) break;
                if(check_intersection(order[i], order[i + 1], order[j], order[j + 1])){
                    col++;
                }
                if(check_intersection(temp[i], temp[i + 1], temp[j], temp[j + 1])){
                    col--;
                }
            }
        }
        return col;
    }
    static void mtd1(Point[] order,int n){
        int count = 0;
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 2; j < n; j++) {
                if(i==0 && j==n-1) break;
                boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                if (d) {
                    System.out.println("Intreseção encontrada: ");
                    System.out.print("p1 = ( "+ order[i].x  + ", " + order[i].y + ")" + " ");
                    System.out.print("p2 = ( "+ order[i+1].x  + ", " + order[i+1].y + ")" + " ");
                    System.out.print("p3 = ( "+ order[j].x  + ", " + order[j].y + ")" + " ");
                    System.out.print("p4 = ( "+ order[j+1].x  + ", " + order[j+1].y + ")" + " ");
                    System.out.println();
                    count++;
                }
            }
        }
        System.out.println("Foram encontradas : " + count + " interseções");
    }
    static void mtd2(Point[] order,int n){
        Point k = new Point(0, 0);
        int flag = 1;
        Perimetro v = new Perimetro(k,k,k,k);
        int pi = 0;
        int pj=0;
        while(flag==1){
            v.variacao=0;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if(i==0 && j==n-1) break;
                    boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                    if (d) {
                        Perimetro p = new Perimetro(order[i],order[i+1],order[j],order[j+1]);
                        if(p.variacao>v.variacao){
                            v.p1 = p.p1.clone();
                            v.p2 = p.p2.clone();
                            v.p3 = p.p3.clone();
                            v.p4 = p.p4.clone();
                            v.variacao = p.variacao;
                            pi = i;
                            pj = j;
                        }
                        flag++;
                    }
                }
            }
            if(flag > 1){
                System.out.println("Intreseção escolhida: ");
                System.out.print(":p1 = ( "+ order[pi].x  + ", " + order[pi].y + ")" + " ");
                System.out.print("p2 = ( "+ order[pi+1].x  + ", " + order[pi+1].y + ")" + " ");
                System.out.print("p3 = ( "+ order[pj].x  + ", " + order[pj].y + ")" + " ");
                System.out.print("p4 = ( "+ order[pj+1].x  + ", " + order[pj+1].y + ")" + " ");
                System.out.println();
                espelhar(pi+1,pj,order);
                flag = 1;
            }
            else{
                flag = 0;
            }
        }
        for(int i = 0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if(i<n) System.out.print("-> ");
        }
        System.out.println();
    }
    static void mtd3(Point[] order,int n){
        int flag = 1;
        while(flag==1){
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if(i==0 && j==n-1) break;
                    boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                    if (d) {
                        espelhar(i+1,j,order);
                        flag++;
                    }

                }
            }
            if(flag > 1){
                flag = 1;
            }
            else{
                flag = 0;
            }
        }
        for(int i = 0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if(i<n) System.out.print("-> ");
        }
        System.out.println();
    }
    static void mtd4(Point[] order,int n){
        int flag = 1;
        int pi = 0;
        int pj=0;
        while(flag==1){
            int maxcol=Integer.MAX_VALUE;
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if(i==0 && j==n-1) break;
                    boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                    if (d) {
                        espelhar(i+1,j,order); // função que resolve a intreseção p(i) -> p(i+1) com p(j) -> p(j+1)
                        int y= colisions(order,n); // numero de colisões depois de quebrada a intreseção
                        espelhar(i+1,j,order); // retoma ao estado inicial
                        if(y<maxcol){ // obter o menor numero de colisões e guardar sobre que retas aconteceu
                            maxcol = y;
                            pi = i;
                            pj = j;
                        }
                        flag++;
                    }
                }
            }
            if(flag > 1){
                System.out.println("Intreseção escolhida: ");
                System.out.print("p1 = ( "+ order[pi].x  + ", " + order[pi].y + ")" + " ");
                System.out.print("p2 = ( "+ order[pi+1].x  + ", " + order[pi+1].y + ")" + " ");
                System.out.print("p3 = ( "+ order[pj].x  + ", " + order[pj].y + ")" + " ");
                System.out.print("p4 = ( "+ order[pj+1].x  + ", " + order[pj+1].y + ")" + " ");
                System.out.println();
                espelhar(pi+1,pj,order);
                flag = 1;
            }
            else{
                flag = 0;
            }
        }
        for(int i = 0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if(i<n) System.out.print("-> ");
        }
        System.out.println();
    }
    static void mtd5(Point[] order,int n){
        int flag = 1;
        LinkedList<Point> l = new LinkedList<>();
        Random rand = new Random();
        while(flag==1){
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if(i==0 && j==n-1) break;
                    boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                    if (d) {
                        System.out.println("Intreseção encontrada: ");
                        System.out.print("p1 = ( "+ order[i].x  + ", " + order[i].y + ")" + " ");
                        System.out.print("p2 = ( "+ order[i+1].x  + ", " + order[i+1].y + ")" + " ");
                        System.out.print("p3 = ( "+ order[j].x  + ", " + order[j].y + ")" + " ");
                        System.out.print("p4 = ( "+ order[j+1].x  + ", " + order[j+1].y + ")" + " ");
                        System.out.println();
                        l.add(new Point(i,j));
                        flag++;
                    }
                }
            }
            if(l.size()!=0){
                int q = rand.nextInt(l.size());
                System.out.println(q);
                Point p = l.get(q);
                l.clear();
                espelhar((p.x)+1,p.y,order);
            }
            if(flag > 1){
                flag = 1;
            }
            else{
                flag = 0;
            }
        }
        for(int i = 0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if(i<n) System.out.print("-> ");
        }
        System.out.println();
    }
    static void mtd6(Point[] order,int n){
        Point[] temp = new Point[n + 1];
        for (double t = temperature; t > 1; t *= coolingFactor) {
            for (int i = 0; i < n - 1; i++) {
                for (int j = i + 2; j < n; j++) {
                    if(i==0 && j==n-1) break;
                    boolean d = check_intersection(order[i], order[i + 1], order[j], order[j + 1]);
                    if (d) {
                        for(int u=0;u<n+1;u++){
                            temp[u] = new Point(order[u].x,order[u].y);
                        }
                        espelhar(i+1, j, temp);
                        if(Math.random() < probability(r_colisions(order, temp,n),t)) {
                            order = Arrays.copyOf(temp,temp.length);
                        }
                    }
                }
            }
        }
        for(int i = 0;i<=n;i++){
            System.out.print("( " + order[i].x + ", " + order[i].y + ")" + " ");
            if(i<n) System.out.print("-> ");
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Insira o numero de pontos: ");
        int n = in.nextInt(); //numero de pontos
        System.out.println("Insira o intrevalo de geração de pontos: ");
        int m = in.nextInt();
        Point[] polig= new Point[n];
        Point[] order= new Point[n+1];
        int flag=0;
        do {
            flag =0;
            System.out.println("Forma de inserir pontos: ");
            System.out.println("1 - gerar aleatóriamente ");
            System.out.println("2 - Inserir pelo terminal no formato x y ");
            int g = in.nextInt();
            switch(g){
                case 1: polig = aleatorios(in,n,m);
                    break;
                case 2: polig = rdfromterm(in,n,m);
                    break;
                default:
                    System.out.println("Opção Inválida! ");
                    flag++;
            }
        }while(flag==1);
        do {
            flag =0;
        System.out.println("Heuristica: ");
        System.out.println("1 - Permutação aleatoria ");
        System.out.println("2 - Nearest-neibour first ");
        int h=in.nextInt();
            switch(h){
                case 1: order = shuffle(polig,n);
                    break;
                case 2: order = nerst_neibour(polig,n,m);
                    break;
                default:
                    System.out.println("Opção Inválida! ");
                    flag++;
            }
        }while(flag==1);
        do {
            flag =0;
        System.out.println("Métodos: ");
        System.out.println("1 - Detetar intreseçoes ");
        System.out.println("2 - Optar pelo candidato que reduz mais o perímetro ");
        System.out.println("3 - Optar pelo primeiro candidato que encontrar ");
        System.out.println("4 - Optar pelo candidato que tiver menos conflitos ");
        System.out.println("5 - Optar por qualquer candidato na vizinhança ");
        System.out.println("6 - Simulated anneling ");
        int met=in.nextInt();
            switch(met){
                case 1:
                    mtd1(order,n);
                    break;
                case 2:
                    mtd2(order,n);
                    break;
                case 3:
                    mtd3(order,n);
                    break;
                case 4:
                    mtd4(order,n);
                    break;
                case 5:
                    mtd5(order,n);
                    break;
                case 6:
                    mtd6(order,n);
                    break;
                default:
                    System.out.println("Opção Inválida! ");
                    flag++;
            }
        }while(flag==1);
    }
}
