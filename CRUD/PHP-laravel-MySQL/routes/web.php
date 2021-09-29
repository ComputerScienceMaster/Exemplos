<?php

/*
|--------------------------------------------------------------------------
| Web Routes
|--------------------------------------------------------------------------
|
| Here is where you can register web routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| contains the "web" middleware group. Now create something great!
|
*/

use App\Http\Controllers\VendedorController as VendedorController;
use App\Http\Controllers\VendaController as VendaController;

Route::get('/', function () {
    return view('welcome');
});


Route::get('/criarvendedores', function () {
    return view('criarvendedores');
});

Route::get('/criarvenda', function () {
	$cvend = new VendedorController();
	$vendedores = $cvend->listarvendedores();
    return view('criarvenda')->with('vendedores', $vendedores);
});


Route::get('/listarvendedores', function () {
	$cvend = new VendedorController();
	$vendedores = $cvend->listarvendedores();
    return view('listarvendedores')->with('vendedores', $vendedores);
});

Route::get('/listarvendas', function () {
	$cvend = new VendaController();
	$vendas = $cvend->listarvendas();
    return view('listarvendas')->with('vendas', $vendas);
});

Route::get('/dovendedor/{id}', function ($id) {
	$cvend = new VendaController();
	$vendas = $cvend->visualizarvendadovendedor($id);
    return view('listarvendasdovendedor')->with('vendas', $vendas);
});





