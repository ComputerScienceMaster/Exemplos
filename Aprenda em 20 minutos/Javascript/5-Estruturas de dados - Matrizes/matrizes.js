var a = new Array(3);

a[0] = new Array("1", "2", "3");
a[1] = new Array("4", "5", "6");
a[2] = new Array("7", "8", "9");

for(i = 0; i <= 2; i++){
    for(j = 0; j <= 2; j++){
        console.log(a[i][j]);
    }
}