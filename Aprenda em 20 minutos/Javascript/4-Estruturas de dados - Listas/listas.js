var a = new Array(4); //Atribuindo dados a variavel 'a'

//Agora vamos popular todas as 4 posições do array criadas na variavel acima
a[0] = "Primeira posição do Array!";
a[1] = "Segunda posição do Array!";
a[2] = "Terceira posição do Array!";
a[3] = "Quarta posição do Array!";

for(i = 0; i <= 3; i++){ //Laço de repetição para exibir todas as linhas do array
    console.log(a[i]);
}

/** Nota-se que utilizamos os for para percorrer o array, esse metodo pode ser utilizado
 * tambem para a inserção de dados no array
 */
