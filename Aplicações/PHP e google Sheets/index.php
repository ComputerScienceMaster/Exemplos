<?php

$sheetID = "<Id da sua planilha>";
$key = "<chave de acesso de API>";
$sheetName = "<nome da sua planilha>";
$range = "A1:Z200";

?>

<html>
    <head>
        <title>CSM Store</title>
        <?php require_once 'elements/head.php'; ?>

    </head>
    <body style="background-color: #fefefe">
       

        <?php require_once 'elements/navbar.php'; ?>
        <div class="container">
            <div style="background-color: #6600a5">
                <h1 class="jumbotron" style="background-color: #6600a5; color: #fff; font-family: 'Shadows into light'; text-align: center; font-size: 100px">CSM Store</h1>    
                <div  style="background-color: #cc9eca; padding: 40px">;
                    <h2>Informática</h2>
                    <div class="row">
                        <?php
                      // Novo link adaptado para versão V4 da API do google
                        $url = "https://sheets.googleapis.com/v4/spreadsheets/$sheetID/values/$sheetName!$range?majorDimension=ROWS&key=$key";
                        $file = file_get_contents($url);
                        $produtosDeInformatica = json_decode($file);
                        $produtos = $produtosDeInformatica->{'values'};
                      
                        
                        for ($i = 1; $i < sizeof($produtos); $i++) {
                            ?>

                                <div class="col-md-6 card-deck mb-3 text-center">
                                    <div class="card mb-4 shadow-sm">
                                        <div style="text-align: center">
                                            <img height="300" width="300" src="<?= $produtos[$i][3] ?>">
                                        </div>
                                        <div class="card-header">
                                            <h4 class="my-0 font-weight-normal"><?= $produtos[$i][0] ?></h4>
                                        </div>
                                        <div class="card-body">
                                            <h1 class="card-title pricing-card-title"><?= $produtos[$i][4] ?></h1>
                                            <div>
                                                <?= $produtos[$i][2] ?>
                                            </div>
                                            <button type="button" class="btn btn-lg btn-block btn-outline-primary">Comprar agora</button>
                                        </div>
                                    </div>

                                </div>


                            <?php
                        }
                        ?>
                    </div>
                </div>
            </div>


            <div id='footer' style="background-color: #6600a5; color:#fff; font-family: 'helvetica'; font-size: 20px; text-align: center; padding: 20px">
                <div>Esse site foi desenvolvido por ComputerScienceMaster e está sob a licença do MIT</div>
            </div>
        </div>


    </body>



    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>
</html>
