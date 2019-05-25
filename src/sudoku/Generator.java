/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sudoku;

import java.util.*;

/**
 *
 * @author Jack
 */
public class Generator {
    private int[][] puzzle = new int[9][9];
    private Integer[] nums = {1,2,3,4,5,6,7,8,9};
    
    public boolean solve(int row,int col){
        if (col==9){
            col=0;
            row++;
            if(row==9){
                return true;
            }
        }
        
        if (puzzle[row][col] != 0) {
            return solve(row,col+1);
        }
        
        Collections.shuffle(Arrays.asList(nums));
        for (int i = 0; i < 9; i++){
            if(isValid(row,col,nums[i])){
                puzzle[row][col]=nums[i];
                if(solve(row,col+1)){
                    return true;
                }
            }
        }
        puzzle[row][col]=0;
        return false;
    }
    
    public boolean solve(int row,int col, int r, int c, int v){
        if (col==9){
            col=0;
            row++;
            if(row==9){
                return true;
            }
        }
        
        if (puzzle[row][col] != 0) {
            return solve(row,col+1,r,c,v);
        }
        
        if (row == r && col == c){
            for (int i = 1; i <= 9; i++){
                if(i==v){
                    continue;
                }
                if(isValid(row,col,i)){
                    puzzle[row][col]=i;
                    if(solve(row,col+1,r,c,v)){
                        return true;
                    }
                }
            }
        }
        else{
            for (int i = 1; i <= 9; i++){
                if(isValid(row,col,i)){
                    puzzle[row][col]=i;
                    if(solve(row,col+1,r,c,v)){
                        return true;
                    }
                }
            }
        }
        puzzle[row][col]=0;
        return false;
    }
    
    public boolean isValid(int row, int col,int val){
        
        for(int i=0;i<9;i++){
            if (val==puzzle[row][i]){
                return false;
            }
        }
        for(int i=0;i<9;i++){
            if (val==puzzle[i][col]){
                return false;
            }
        }
        
        int row3x3 =(row/3)*3;
        int col3x3 =(col/3)*3;
        
        for(int i=0;i<3;i++){
            for(int j=0;j<3;j++){
                if(val==puzzle[row3x3+i][col3x3+j]){
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String getCode(int[][] grid){
        String out = "";
        for(int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                out+=grid[i][j];
            }
            
        }
        System.out.println(out);
        return out;
    }
    
    public static int uniqueRandom(int min, int max){
        int counter=0;
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=min; i<=max; i++){
            list.add(i);
        }
        if(counter == 0){
            Collections.shuffle(list);
        }
        int num = list.get(counter);
        counter++;
        if(counter==(list.size()+1)){
            counter=0;
            Collections.shuffle(list);
        }
        return num;
    }
    //55 
    public void generate(int count){
        
        if(count == 1){
            System.out.println(Arrays.deepToString(puzzle));
            return;
        }
        
        int ran = uniqueRandom(0,80);
        int row = ran/9;
        int col = ran-(ran/9)*9;
        int val = puzzle[row][col];
        puzzle[row][col]=0;
        
        if(solve(0,0,row,col,val) == false){
            generate(count-1);
        }
        
        else{
            puzzle[row][col]=val;
            generate(count);
        }
        
    }
    
    public void generator(){        
        for (int i = 0; i < 80; i++){
            int ran = uniqueRandom(0,80);
            int row = ran/9;
            int col = ran-(ran/9)*9;
            int val = puzzle[row][col];
            puzzle[row][col]=0;
            
            if(solve(0,0,row,col,val)){
                puzzle[row][col]=val;
                i--;
            }

        }
//        int temp = puzzle[8][8];
//        puzzle[8][8]=0;
//        solve(0,0,8,8,temp);//false        
    }
    
    public static void main(String[] args){
//        Generator gen = new Generator();
//        gen.solve(0,0);
//        String s = getCode(gen.puzzle);
//        
//        int temp = gen.puzzle[8][8];
//        gen.puzzle[8][8]=0;
//        System.out.println(temp);
//        System.out.println(gen.puzzle[8][8]);
//        System.out.println(gen.solve(0,0,8,8,temp));
//        
//        System.out.println(Arrays.deepToString(gen.puzzle));
//        
//        int tem = gen.puzzle[8][7];
//        gen.puzzle[8][7]=0;
//        System.out.println(tem);
//        System.out.println(gen.puzzle[8][7]);
//        System.out.println(gen.solve(0,0,8,7,tem));
//        
//        System.out.println(Arrays.deepToString(gen.puzzle));
//    
        Generator gen = new Generator();
        gen.solve(0,0);
        gen.generate(55);
//        gen.generator();
        String s = getCode(gen.puzzle);
        //System.out.println(Arrays.deepToString(gen.puzzle));
        
    }
}
