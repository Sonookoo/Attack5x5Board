package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class Reversi {
    private String[][] table;
    private Stack<String[][]> stack;

    public void initTable() {
        try {
            this.loadCsv(new File("./tabledata/InitTable.csv"));
            this.setStack(new Stack<>());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setTable(String[][] table) {
        this.table = table;
    }

    public void setStack(Stack<String[][]> stack) {
        this.stack = stack;
    }

    public String[][] getTable() {
        return this.table;
    }

    public Stack<String[][]> getStack() {
        return this.stack;
    }

    public void setPanel(int i, int j, String color) {
        this.stack.push(copyTable(this.table));
        this.table[j][i] = color;
    }
    
    public void searchAllDirection(int i, int j, String color) {
        int dirX;
        int dirY;

        this.setPanel(i, j, color);
        if (color.equals("Yellow")) {
            return;
        }
        for (dirY=-1; dirY<=1; dirY++) {
            for (dirX=-1; dirX<=1; dirX++) {
                if (dirX != 0 || dirY != 0) {
                    searchOneDirection(i, j, color, dirX, dirY);
                }
            }
        }
    }
    public void searchOneDirection(int i, int j, String color, int directionX, int directionY) {
        int x = i + directionX;
        int y = j + directionY;
        boolean flag = false;
        
        while (0<=x && x<=4 && 0<=y && y<= 4) {
            if ("None".equals(this.table[y][x])) {
                return;
            }
            if (Objects.equals(this.table[y][x], color)) {
                flag = true;
                break;
            }
            x += directionX;
            y += directionY;
        }

        while (flag && (y!=j || x!=i)) {
            this.getTable()[y][x] = color;
            x -= directionX;
            y -= directionY;
        }
    }

    public void showTable() {
        int i;
        int j;
        for (j=0; j<5; j++) {
            for (i=0; i<5; i++) {
                System.out.print(this.getTable()[j][i].substring(0, 1)+" ");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public boolean[][] diffTable() {
        boolean[][] dt = new boolean[5][5];
        if (this.getStack().isEmpty()) {
            return dt;
        }  
        int i;
        int j;
        for (j=0; j<5; j++) {
            for (i=0; i<5; i++) {
                if (Objects.equals(this.getTable()[j][i], this.getStack().peek()[j][i])) {
                    dt[j][i] = false;
                } else {
                    dt[j][i] = true;
                }
            }
        }
        return dt;
    }

    public void backTable() {
        if (!this.getStack().isEmpty()) {
            this.setTable(copyTable(this.getStack().pop()));
        }
    }

    public static String[][] copyTable(String[][] argt) {
        String[][] t = new String[5][5];
        int j;
        for (j=0; j<5; j++) {
            t[j] = argt[j].clone();
        }
        return t;
    }

    public void loadCsv(File openCsv) throws IOException{
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(openCsv));
        String br;
        String[] data;
        String[][] t = new String[5][5];
        int j;
        for (j=0; j<5; j++) {
            br = reader.readLine();
            data = br.split(",");
            t[j] = data.clone();
        }
        reader.close();
        this.setTable(copyTable(t));
    }

    public void saveCsv(File file) throws IOException{
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        int j;
        int i;
        for (j=0; j<5; j++) {
            for (i=0; i<5; i++) {
                writer.write(this.getTable()[j][i]);
                if (i < 4) {
                    writer.write(",");
                }
            }
            writer.write(System.lineSeparator());
        }
        writer.close();
    }
}
