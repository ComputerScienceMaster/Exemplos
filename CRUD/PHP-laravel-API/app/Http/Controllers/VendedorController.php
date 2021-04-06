<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;

use App\Vendedores;
use App\Http\Resources\Vendedores as VendedorResource;

class VendedorController extends Controller
{
    public function visualizarvendedor ($id)
    {
        return new VendedorResource(Vendedores::findOrFail($id));
    }

    public function listarvendedores ()
    {
        return Vendedores::all();
    }

    public function criarvendedor(Request $request)
    {	
        $tosave = new Vendedores;
        $tosave->nome = $request->input('nome');
        $tosave->email = $request->input('email');
        $tosave->comissao = 0;
        $tosave->save();
        return redirect('api/vendedor/listarvendedores');
    }
    
}