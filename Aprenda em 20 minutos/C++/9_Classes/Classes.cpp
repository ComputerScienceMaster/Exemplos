#include<iostream>

using namespace std;

class Pessoa{
    private:
    string nome;
    int idade;
    char sexo;
    int quantidadeDeFilhos;

    public:
        Pessoa(){
            nome = "";
            idade = 0;
            sexo = ' ';
            quantidadeDeFilhos = 0;
        }

        Pessoa(string n, int i, int s, int qtd){
            nome = n;
            idade = i;
            sexo = s;
            quantidadeDeFilhos = qtd;
        }

        ~Pessoa(){
            cout << "destruido!!" << endl;
        }

    void mostrarPessoa(){
        cout << nome << " - " << idade << " - " << sexo << " - " << quantidadeDeFilhos << endl;
    }

    void setNome(string n){
        nome = n;
    }

    void setIdade(int i){
        idade = i;
    }

    void setSexo(int s){
        sexo = s;
    }

    void setQuantidadeDeFilhos(int q){
        quantidadeDeFilhos = q;
    }
};


main(){
    Pessoa p;
    Pessoa p2("geovana", 20, 'f', 1);

    p.setNome("vinicius");
    p.setIdade(18);
    p.setSexo('M');
    p.setQuantidadeDeFilhos(0);

    p.mostrarPessoa();
    p2.mostrarPessoa();
    return 0;
}
