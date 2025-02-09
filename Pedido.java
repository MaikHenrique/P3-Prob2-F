package main;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author jecunha
 */
public final class Pedido {

    private int numero;
    private String nomeCliente;
    private Date data;
    private String endereco;
    private TipoEntrega tipo;
    private ArrayList<ItemPedido> itens;

    public Pedido(int numero, String nomeCliente, String endereco) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.endereco = endereco;
        this.setData(data);
        this.itens = new ArrayList<>(Arrays.asList());
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNomeCliente() {
        return nomeCliente;
    }

    public void setNomeCliente(String nomeCliente) {
        this.nomeCliente = nomeCliente;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public void setTipo(TipoEntrega tipo) {
        this.tipo = tipo;
    }

    public void incluirItem(ItemPedido item) {
        itens.add(item);
    }

    public double getValorPedido() {
        double valor = 0;

        for (ItemPedido item : itens) {
            valor = valor + item.getValorItem();
        }

        return valor;
    }

    public double getValorEntrega() throws TipoEntregaInvalido {
        double valor = 0;
        int peso = 0;

        for (ItemPedido item : itens) {
            peso = peso + item.getProduto().getPeso() * item.getQuantidade();
        }

        switch (tipo) {
            case SEDEX:
                valor = calculaSedex(peso);
                break;

            case PAC:
                valor = calculaPac(peso);
                break;

            case LOCAL:
                return 0;

            default:
                break;
        }

        return valor;
    }

    public double getValorTotal() throws TipoEntregaInvalido {
        return getValorPedido() + getValorEntrega();
    }

    private double calculaSedex(double peso) throws TipoEntregaInvalido {
        if (peso <= 500) {
            return 12.50;
        } else if (peso <= 750) {
            return 20;
        } else if (peso <= 1200) {
            return 30;
        } else if (peso <= 2000) {
            return 45;
        } else {
            double valor = 45;
            return 45 + (((int) Math.ceil((peso - 2000) / 100.0)) * 1.50);
        }
    }

    private double calculaPac(double peso) throws TipoEntregaInvalido {
        if (peso <= 1000) {
            return 15;
        } else if (peso <= 3000) {
            return 20;
        } else if (peso <= 5000) {
            return 30;
        } else {
            throw new TipoEntregaInvalido("Tipo de entrega não suportado");
        }
    }

}
