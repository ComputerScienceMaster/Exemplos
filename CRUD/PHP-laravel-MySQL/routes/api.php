<?php

use Illuminate\Http\Request;
Use App\Vendedores;
Use App\Vendas;

/*
|--------------------------------------------------------------------------
| API Routes
|--------------------------------------------------------------------------
|
| Here is where you can register API routes for your application. These
| routes are loaded by the RouteServiceProvider within a group which
| is assigned the "api" middleware group. Enjoy building your API!
|
*/

Route::middleware('auth:api')->get('/user', function (Request $request) {
    return $request->user();
});

Route::get('/vendedor/listarvendedores', 'VendedorController@listarvendedores');
Route::get('/vendedor/{id}', 'VendedorController@visualizarvendedor');
Route::post('/vendedor/criar', 'VendedorController@criarvendedor');

Route::get('/venda/listarvendas', 'VendaController@listarvendas');
Route::get('/venda/{id}', 'VendaController@visualizarvenda');
Route::get('/venda/dovendedor/{id}', 'VendaController@visualizarvendadovendedor');
Route::post('/venda/criar', 'VendaController@criarvenda');