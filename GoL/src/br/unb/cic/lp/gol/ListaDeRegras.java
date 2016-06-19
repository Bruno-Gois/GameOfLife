package br.unb.cic.lp.gol;

import java.util.List;

public class ListaDeRegras {
  
  public List<ListaDeRegras> estrategia;
  
  public ListaDeRegras(List<ListaDeRegras> estrategia){
    this.estrategia = estrategia;
  }

  public List<ListaDeRegras> getEstrategia(){
    return this.estrategia;
  }
  
  public void setEstrategia(List<ListaDeRegras> estrategia){
    this.estrategia = estrategia;
  }
}