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
                        // Antigo link descontinuado da versão 3
                        //$url = 'http://spreadsheets.google.com/feeds/cells/1wwcACuc_stTUGohsk_4ReCtDzzOyDUmAE3WnKwCBO5k/1/public/full?alt=json';
                        // usando csv
                        //$url = 'https://docs.google.com/spreadsheets/d/1wwcACuc_stTUGohsk_4ReCtDzzOyDUmAE3WnKwCBO5k/gviz/tq?tqx=out:csv'
                        // novo V4
                        $url = 'https://sheets.googleapis.com/v4/spreadsheets/1wwcACuc_stTUGohsk_4ReCtDzzOyDUmAE3WnKwCBO5k/values/page1!A1:Z200?majorDimension=ROWS&key=AIzaSyDMb2CKRuyCRJsm_AE3zPH7DXygEzqQ4Zk';
                        $file = file_get_contents($url);
                        $produtosDeInformatica = json_decode($file);
                        $produtos = $produtosDeInformatica->{'values'};
                        print_r($produtos);
                      
                        /*
                        $produtos = $produtosDeInformatica->{'feed'}->{'entry'};

                        $prodAsStrings = Array();
                        foreach ($produtos as $prod) {
                            array_push($prodAsStrings, $prod->{'gs$cell'}->{'$t'});
                        }

                        $produtosObjetos = Array();
                        for ($i = 0; $i < sizeof($prodAsStrings); $i = $i + 5) {
                            $nome = $prodAsStrings[$i];
                            $categoria = $prodAsStrings[$i + 1];
                            $descricao = $prodAsStrings[$i + 2];
                            $imagem = $prodAsStrings[$i + 3];
                            $preco = $prodAsStrings[$i + 4];

                            $item = Array();

                            array_push($item, $nome);
                            array_push($item, $categoria);
                            array_push($item, $descricao);
                            array_push($item, $imagem);
                            array_push($item, $preco);

                            array_push($produtosObjetos, $item);
                        }
                       

                        
                        for ($i = 1; $i < sizeof($produtosObjetos); $i++) {
                            ?>

                                <div class="col-md-6 card-deck mb-3 text-center">
                                    <div class="card mb-4 shadow-sm">
                                        <div style="text-align: center">
                                            <img height="300" width="300" src="<?= $produtosObjetos[$i][3] ?>">
                                        </div>
                                        <div class="card-header">
                                            <h4 class="my-0 font-weight-normal"><?= $produtosObjetos[$i][0] ?></h4>
                                        </div>
                                        <div class="card-body">
                                            <h1 class="card-title pricing-card-title"><?= $produtosObjetos[$i][4] ?></h1>
                                            <div>
                                                <?= $produtosObjetos[$i][2] ?>
                                            </div>
                                            <button type="button" class="btn btn-lg btn-block btn-outline-primary">Comprar agora</button>
                                        </div>
                                    </div>

                                </div>


                            <?php
                        }*/
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
