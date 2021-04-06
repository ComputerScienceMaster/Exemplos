#include<iostream>

using namespace std;

class Carro{
    private:
    double valorDoCarro;

    public:
        string nomeDoMotorista;
        int ano;

    void mostrar(){
        cout << nomeDoMotorista << " - " << ano << " - " << valorDoCarro << endl;
    }

    void setValorDoCarro(double v){
        valorDoCarro = v;
    }

};

main(){
    Carro c;

    c.nomeDoMotorista = "Vanessa";
    c.ano = 2018;

    //c.valorDoCarro = 24000.0;
    c.setValorDoCarro(20140.0);
    c.mostrar();

}
