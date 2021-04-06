void main(){
    int a = 0;
    printf("Digite um numero:");
    scanf("%d", &a);

    int i = 0;
    while(i < a){
        printf("while: %d\n", i);
        i++;
    }

    for(i = 0; i < a; i++){
        printf("for: %d\n", i);
    }

}
