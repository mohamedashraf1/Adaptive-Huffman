/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adaptivehuffman;

/**
 *
 * @author Elliot
 */
public class Node {
    public char nodeSymbol;
    public String nodeCode;
    public int nodeNumber;
    public int symbolCount;
    
    public Node right;
    public Node left;

    public Node(){
        right = null;
        left = null;
        symbolCount = 0;
        nodeSymbol = '*';
        nodeCode = "";
        nodeNumber = -1;
    }
    
     /*
    public void setNodesymbol(char nodeSymbol) {
        this.nodeSymbol = nodeSymbol;
    }    
    public char getnodeSymbol() {
        return nodeSymbol;
    }

   
    
    public void setnodeCode(String nodeCode) {
        this.nodeCode = nodeCode;
    }
    public String getnodeCode() {
        return nodeCode;
    }

    
    
    public void setnodeNumber(int nodeNumber) {
        this.nodeNumber = nodeNumber;
    }
    public int getnodeNumber() {
        return nodeNumber;
    }

    
    
    public void setsymbolCount(int symbolCount) {
        this.symbolCount = symbolCount;
    }
    public int getsymbolCount() {
        return symbolCount;
    }

    
    
    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getLeft() {
        return left;
    }
    
    
    
    public void setRight(Node right) {
        this.right = right;
    }

    public Node getRight() {
        return right;
    }

    */
   
}
