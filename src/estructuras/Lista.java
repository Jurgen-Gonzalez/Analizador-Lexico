package estructuras;

class Nodo {
    Nodo siguiente;
    int valor;
}

public class Lista {
    private Nodo top;
    
    public void add(int valor){
        Nodo nodo = new Nodo();
        nodo.siguiente = null;
        nodo.valor = valor;
        
        if(top == null){
            top = nodo;
            return;
        }
        
        Nodo temp = top;
        while(temp.siguiente != null)
            temp = temp.siguiente;
        
        temp.siguiente = nodo;
    }
    
    public int get(int index){
        Nodo nodo = top;
        for (int i = 0; i < index; i++)
            nodo = nodo.siguiente;
        
        return nodo.valor;
    }
    
    public int size(){
        Nodo nodo = top;
        int contador = 0;
        while(nodo != null){
            contador++;
            nodo = nodo.siguiente;
        }
        
        return contador;
    }
    
    
}
