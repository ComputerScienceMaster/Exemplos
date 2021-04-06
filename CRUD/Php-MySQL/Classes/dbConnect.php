<?php

// Conexão com banco de dados

class Connect {

    static $connect;

    public static function getConnection() {
        if (self::$connect == null) {
            $servername = "localhost";
            $username = "root";
            $password = "12345";
            $db_name = "lanchonete";

            self::$connect = new mysqli($servername, $username, $password, $db_name);

            if (mysqli_connect_error()):
                echo "Erro na conexão: " . mysqli_connect_error();
            endif;
        }
        return self::$connect;
    }

}
