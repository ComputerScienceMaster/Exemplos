void main(){
    printf("o numero digitado eh: %d", lerNumero());
}

int lerNumero(){
    int a = 0;
    printf("Digite um numero:");
    scanf("%d", &a);
    return a;
}
