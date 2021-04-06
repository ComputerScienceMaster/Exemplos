#include<iostream>

using namespace std;

class MeioDeTransporte{
    private:
    string tipo;

    public:
        void setTipo(string t){
            tipo = t;
        }

        string getTipo(){
            return tipo;
        }

};

class Carro : public MeioDeTransporte{
    private:
    double valorDoCarro;

    public:
        string nomeDoMotorista;
        int ano;

    void mostrar(){
        cout << nomeDoMotorista << " - " << ano << " - " << valorDoCarro << " - " << getTipo() << endl;
    }

    void setValorDoCarro(double v){
        valorDoCarro = v;
    }

};

main(){
    Carro c;

    c.nomeDoMotorista = "Vanessa";
    c.ano = 2018;
    c.setTipo("Passeio");

    //c.valorDoCarro = 24000.0;
    c.setValorDoCarro(20140.0);
    c.mostrar();

}
