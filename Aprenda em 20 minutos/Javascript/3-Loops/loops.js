var a = "Olá mundo!" //Atribui string a variavel 'a'

for(i = 0; i < 10; i++){ //Metodo de loop pré fixado
    //Conteúdo a ser impresso em cada loop
    console.log(a);
}
/*Nota-se que na declaração do metodo 'for' foi criada uma variavel
temporária chamada de 'i' e atribuimos o valor dela, logo depois foi
defeinido o limite do loop(enquanto 'i' for menor que 10), e por fim
declaramos o metodo de iteração de 'i' (i = i + 1) ou seja, a cada loop
'i' irá acrescentar 1 até cheagar em 10*/

console.log("---------------------"); //Separando os exemplos

var cont = 0; //Atribuindo valor a variavel 'cont'
while(cont < 10){ //Metodo de loop pós fixado
    cont++; //Iterando +1 a variavel 'cont'
    console.log(a); //imprimindo a variavel 'a'
}

/*Nota-se que há um diferença entre for e while, tanto no for quanto no while o final do loop
é declarado, no entando apenas no for o metodo de iteração é declarado, no while cabe ao bloco
de comando internos fazer esta iteração*/