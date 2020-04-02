/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package adaptivehuffman;
import java.util.Scanner;
import java.util.*; 
import java.util.Vector;
/**
 *
 * @author Elliot
 */
public class AdaptiveHuffman {
    public static Vector<String> dic = new Vector<>();
    public static Vector<String> shortCode = new Vector<>();
    public static Vector<String> finalCodes = new Vector<>();
    public static Vector<String> finalWord = new Vector<>();
    public static Node root = new Node();
    public static Node holdS = new Node();
    public static Node holdC = new Node();
    public static Node holdC2 = new Node();
    public static Node holdP = new Node();
    public static Vector<Node> possibleNodes = new Vector<Node>();
    
    
    public AdaptiveHuffman() {
        //root = null ;
    }
    
    
    public static void search(Node node) 
    { 
        
        if (node == null) 
            return; 
  
        
        search(node.left); 
  
        
        search(node.right); 
  
        
        if(node.nodeSymbol == '*' && node.right == null && node.left == null){
          holdS = node;
          
        }
        return;
        
        
    }
    public static void Check(Node node,char symbol) 
    { 
        
        if (node == null) 
            return; 
  
        if(node.nodeSymbol == symbol){
            holdC = node;
            return;
        }
        Check(node.left,symbol);
        
        Check(node.right,symbol);
     
    }
    
    public static void checkCode(Node node,String code) 
    { 
        
        if (node == null) 
            return; 
  
        if(node.nodeCode.equals(code)){
            holdC2 = node;
            return;
        }
        checkCode(node.left,code);
        
        checkCode(node.right,code);
     
    }
    
    public static void getParent(Node start,Node node) 
    { 
        if (start == null) 
            return; 
  
        
        getParent(start.left,node); 
  
        
        getParent(start.right,node); 
  
        
        if(start.right == node || start.left == node)
          holdP = start;
        
        return;
        
        
    }
    public static Node needSwap(Node node){
      
        getParent(root, node);
        
        Node itr = holdP;
        Node itr2 = new Node();
        boolean found = true;
        while(itr != null){
           
            getParent(root,itr);
            itr2 = holdP;
            
            
            if(itr.nodeCode.endsWith("0") && itr2.nodeCode.endsWith("0")){

                //*****
                found = false;
                if(itr2.right.symbolCount <= node.symbolCount){
                    possibleNodes.add(itr2.right);

                }
                itr = itr2;

            }
            else if(itr.nodeCode.endsWith("0") && itr2.nodeCode.endsWith("1")){
                //******
                found = false;
                if(itr2.right.symbolCount <= node.symbolCount){
                    possibleNodes.add(itr2.right);

                }
                itr = itr2;
            }
            
            else if(itr.nodeCode.endsWith("1") && node.nodeCode.endsWith("1")){


                found = false;
                if(itr2.left.symbolCount <= node.symbolCount){
                    possibleNodes.add(itr2.left);

                }
                getParent(root, itr);
                itr = holdP;
            }
            else if(itr.nodeCode.endsWith("1") && node.nodeCode.endsWith("0")){


                found = false;
                if(itr2.right.symbolCount <= node.symbolCount){
                    possibleNodes.add(itr2.right);

                }
                getParent(root, itr);
                itr = holdP;
            }
            else if(found == true){//in the root
                possibleNodes.clear();
                break;
            }   
            else{
                break;
            }

        }
       
       if(possibleNodes.isEmpty())
           return null;
       else
           return possibleNodes.lastElement();
    }
    
    public static void printNodeCode(Node curr){
        if (curr == null) 
            return; 
  
       System.out.println(curr.nodeCode);
        printNodeCode(curr.left);
        
        printNodeCode(curr.right);
        
  
        
        
    }
    public static void printNodeSymbol(Node curr){
        if (curr == null) 
            return; 
  
       System.out.println(curr.nodeSymbol);
        printNodeCode(curr.left);
        
        printNodeCode(curr.right);
        
  
        
        
    }
    
    
    public static void increase(Node node){
        getParent(root, node);
        while(holdP != root){
            holdP.symbolCount++;
            getParent(root, holdP);
        }
        root.symbolCount++;
        
    }
    public static void updateNodeCode(Node curr){
        if (curr == null) 
            return; 
        
        getParent(curr, root);
        if(holdP == null){
            if(curr.right == null)
                curr.left.nodeCode = curr.nodeCode + "0";
            else if(curr.left == null)
                curr.right.nodeCode = curr.nodeCode + "1";
            else{
                curr.left.nodeCode = curr.nodeCode + "0";
                curr.right.nodeCode = curr.nodeCode + "1";
            }
        }
        else{
            if(curr.right == null && curr.left != null)
                curr.left.nodeCode = curr.nodeCode + "0";
            else if(curr.left == null && curr.right != null)
                curr.right.nodeCode = curr.nodeCode + "1";
            else if(curr.right != null && curr.left != null){
                curr.left.nodeCode = curr.nodeCode + "0";
                curr.right.nodeCode = curr.nodeCode + "1";
            }
                
            else{
                return;
            }
        }
        
      
        updateNodeCode(curr.right);
        updateNodeCode(curr.left);
    }
    
    public static void Compress(char[] letters){
        String shcode="";
        Node needSwap = new Node();
        Node newNode = new Node();
        
        shortCode.add("A");
        shortCode.add("00");
        shortCode.add("B");
        shortCode.add("01");
        shortCode.add("C");
        shortCode.add("10");
        Node curr = root;
        for(int i = 0 ; i < letters.length ; i++){
            if(!dic.contains(letters[i]+"")){
                dic.add(letters[i]+"");
                for(int j = 0 ; j < 6 ; j++){
                    if(shortCode.elementAt(j).equals(letters[i] + "")){
                        shcode = shortCode.elementAt(j+1);
        
                        break;}
                }
                
        
                if(i==0)
                {  
                    finalCodes.add(shcode);
                   
                    newNode.nodeSymbol = letters[i];
                    newNode.nodeNumber = 99;
                    newNode.nodeCode = "1";
                    newNode.symbolCount = 1;
                    curr.right = newNode;
                    curr.nodeNumber = 100;
                    curr.symbolCount = 1;
                    curr.nodeSymbol = '*';
                  
                    Node newNode2 = new Node();
                    newNode2.nodeNumber = 98;
                    newNode2.nodeSymbol = '*';
                    newNode2.nodeCode = "0";
                    curr.left = newNode2;
                    //curr.setLeft(newNode2);
            
                }
                else
                {   
                
                    search(root);
                  
                    newNode = holdS;
                    
                    newNode.right = new Node();
                    newNode.right.nodeSymbol = letters[i];
                    newNode.right.nodeCode = newNode.nodeCode + "1";
                    newNode.right.nodeNumber = newNode.nodeNumber - 1;
                    newNode.right.symbolCount+=1;
                   
                    newNode.left = new Node();
                    newNode.left.nodeSymbol = '*';
                    newNode.left.nodeNumber = newNode.nodeNumber - 2;
                    newNode.left.nodeCode = newNode.nodeCode + "0";
                  
                    increase(newNode.right);
                    finalCodes.add(newNode.nodeCode);
                    finalCodes.add(shcode);
         
                    if(curr.left.symbolCount>curr.right.symbolCount || curr.right.symbolCount>curr.left.symbolCount)
                        {
                           
                            Node temp = curr.right;
                            curr.right = curr.left;
                            curr.left = temp;
                            
                            int tmp2 =curr.right.nodeNumber;
                            curr.right.nodeNumber = curr.left.nodeNumber;
                            curr.left.nodeNumber = tmp2;
                            
                            
                            String tmp3 =curr.right.nodeCode;
                            curr.right.nodeCode = curr.left.nodeCode;
                            curr.left.nodeCode = tmp3;
                            
                            
                            updateNodeCode(curr);
                           
                        }
                    
                    }
            }
            else {  
               
                Check(curr,letters[i]);
               
                newNode = holdC;
                
                
                finalCodes.add(newNode.nodeCode);
                Node iter = newNode;
               
                needSwap = needSwap(holdC);
                
                if(needSwap == null){
                    newNode.symbolCount++;
                }
                
                if((curr.left.symbolCount>=curr.right.symbolCount ) && needSwap==null)
                      {
                            
                            Node temp = curr.right;
                            curr.right = curr.left;
                            curr.left = temp;
                            
                            int temp2 =curr.right.nodeNumber;
                            curr.right.nodeNumber = curr.left.nodeNumber;
                            curr.left.nodeNumber = temp2;
                            
                            
                            String tmp3 =curr.right.nodeCode;
                            curr.right.nodeCode = curr.left.nodeCode;
                            curr.left.nodeCode = tmp3;
                            
                            updateNodeCode(curr);
                            
                            
                            increase(newNode);
                        }
              
                else if(needSwap != null){
                    
                   
                    
                    char tmp = newNode.nodeSymbol;
                    newNode.nodeSymbol = needSwap.nodeSymbol;
                    needSwap.nodeSymbol = tmp;
                    
                    int tmp2 = newNode.symbolCount;
                    newNode.symbolCount = needSwap.symbolCount;
                    needSwap.symbolCount = tmp2;
                    
                    
                    needSwap.symbolCount+=1;
                    increase(needSwap);
                 
                    
                }
                updateNodeCode(curr);
         
                
            }
        }
        for(int i = 0 ; i < finalCodes.size() ; i++){
           
            System.out.print( finalCodes.elementAt(i) + " ");
        }
    }
    public static void Decompress(String codes){
        char[] arr = codes.toCharArray();
        shortCode.add("A");
        shortCode.add("00");
        shortCode.add("B");
        shortCode.add("01");
        shortCode.add("C");
        shortCode.add("10");
        String tmp ="";
        String tmp2 ="";
        int count = 0;
        boolean isFirst = false;
        boolean done = false;
        boolean afterSwap = false ;
        boolean pcount = false;
        Node curr = root;
        Node newNode = new Node();
        Node needSwap = new Node();
        
        for(int i = 0 ; i < arr.length ; i++){
            
            
            if(i == 0){
                String x = "";
                tmp += arr[i] ;
                tmp += arr[i+1];
                for(int j = 0 ; j < shortCode.size() ; j++){
                    if(tmp.equals(shortCode.elementAt(j))){
                       x=shortCode.elementAt(j-1);
                        finalWord.add(shortCode.elementAt(j-1));
                        dic.add(shortCode.elementAt(j-1));
                        
                        break;
                    }
                }
              
                newNode.nodeSymbol = x.charAt(0);
                newNode.nodeNumber = 99;
                newNode.nodeCode = "1";
                newNode.symbolCount = 1;
                
                curr.right = newNode;
                curr.nodeNumber = 100;
                curr.symbolCount = 1;
                curr.nodeSymbol = '*';

                Node newNode2 = new Node();
                newNode2.nodeNumber = 98;
                newNode2.nodeSymbol = '*';
                newNode2.nodeCode = "0";
                curr.left = newNode2;
                
                i+=1;
                count += 2;
                
                tmp = "";
            }
            else{
                while(dic.size() < 3){//adding first time
                    if(isFirst){
                       tmp2 += arr[i];
                       tmp2 += arr[i+1];
                       
                       
                       i+=1;
                       
                       
                       String tmp3="";
                       for(int j = 0 ; j < shortCode.size() ; j++){
                            if(tmp2.equals(shortCode.elementAt(j))){
                                tmp3 += shortCode.elementAt(j-1);
                                finalWord.add(shortCode.elementAt(j-1));
                                dic.add(shortCode.elementAt(j-1));
                                break;
                            }
                        }
                        newNode = holdS;
                        
                        newNode.right = new Node();
                        newNode.right.nodeSymbol = tmp3.charAt(0);
                        newNode.right.nodeCode = newNode.nodeCode + "1";
                        newNode.right.nodeNumber = newNode.nodeNumber - 1;
                        newNode.right.symbolCount+=1;

                        newNode.left = new Node();
                        newNode.left.nodeSymbol = '*';
                        newNode.left.nodeNumber = newNode.nodeNumber - 2;
                        newNode.left.nodeCode = newNode.nodeCode + "0";

                        
                       
                        if(curr.left.symbolCount>=curr.right.symbolCount)
                        {
                            
                            Node temp = curr.right;
                            curr.right = curr.left;
                            curr.left = temp;
                            
                            int nN =curr.right.nodeNumber;
                            curr.right.nodeNumber = curr.left.nodeNumber;
                            curr.left.nodeNumber = nN;
                            
                            
                            String nC =curr.right.nodeCode;
                            curr.right.nodeCode = curr.left.nodeCode;
                            curr.left.nodeCode = nC;
                            
                            
                            updateNodeCode(curr);
                           
                        }
                        
                        increase(newNode.right);
                        tmp2 = "";
                        isFirst = false;
                        

                    }
                    else{
                        tmp2 += arr[i];
                        search(curr);
                    }

                    if(tmp2.equals(holdS.nodeCode) && isFirst == false){
                        tmp2 = "";
                        isFirst = true;
                    }
                    
                    if(dic.size() == 3){
                        done = true;
                        i+=1;
                    }
                    
                    break;
                    
                }
                
                if(done){
                  
                    if(!pcount){count = i;}
                        
                    else i = count;
                    
                    i+=1;
                    
                    
                    tmp+= arr[count];
                    
                    while((newNode.nodeSymbol+"").equals("*") || newNode == curr){
                        if(!afterSwap){
                            
                                tmp += arr[i];
                        }
                       
                        checkCode(curr,tmp);
                        newNode = holdC2;
                        
                        i+=1;
                            
                        afterSwap = false;
                        
                    }
                    
                    
                    count = i;
                    pcount = true;
                   
                    finalWord.add(holdC2.nodeSymbol+"");
                    needSwap = needSwap(holdC2);
                    
                    newNode = holdC2;
                    if(needSwap == null){
                        newNode.symbolCount++;
                    }

                    if((curr.left.symbolCount>=curr.right.symbolCount ) && needSwap==null)
                          {
                             
                                Node temp = curr.right;
                                curr.right = curr.left;
                                curr.left = temp;

                                int temp2 =curr.right.nodeNumber;
                                curr.right.nodeNumber = curr.left.nodeNumber;
                                curr.left.nodeNumber = temp2;


                                String tmp3 =curr.right.nodeCode;
                                curr.right.nodeCode = curr.left.nodeCode;
                                curr.left.nodeCode = tmp3;

                                updateNodeCode(curr);

                                increase(newNode);
                            }

                    else if(needSwap != null){
                        afterSwap = true;
                       
                        char ns = newNode.nodeSymbol;
                        newNode.nodeSymbol = needSwap.nodeSymbol;
                        needSwap.nodeSymbol = ns;

                        int nc = newNode.symbolCount;
                        newNode.symbolCount = needSwap.symbolCount;
                        needSwap.symbolCount = nc;


                        needSwap.symbolCount+=1;
                        increase(needSwap);


                    }
                    updateNodeCode(curr);

                    tmp="";
                    newNode = curr;

                }
                
            }
            //count += 1;
        }
        for(int i = 0 ; i < finalWord.size() ; i++){
            System.out.print(finalWord.elementAt(i)+" ");
        }
        
    }
    public static void main(String[] args) {
        AdaptiveHuffman a = new AdaptiveHuffman();
        char [] arr = {'A','B','C','C','C','A','A','A','A'};
        String word = "000010010";
        String word2 = "000010010101000101110";
        
        //a.Compress(arr);
        a.Decompress(word2);
       
        
    }
    
}