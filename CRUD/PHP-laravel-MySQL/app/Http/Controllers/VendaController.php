<?php

namespace App\Http\Controllers;

use Illuminate\Http\Request;
use App\Vendas;
use App\Vendedores;
use App\Http\Resources\Vendas as VendasResource;

class VendaController extends Controller{
    
    public function visualizarvenda ($id){
        return new VendasResource(Vendas::findOrFail($id));
    }

    public function visualizarvendadovendedor ($id){

    	return Vendas::where('idvendedor',$id)->get();
    }

    public function listarvendas (){
        return Vendas::all();
    }

    public function criarvenda(Request $request){	
        $vendedor = Vendedores::findOrFail($request->input('idvendedor'));
        $vendedor->comissao = $vendedor->comissao + 0.08 * $request->input('valor');
        $vendedor->update();

        $tosave = new Vendas;
        $tosave->valor = $request->input('valor');
        $tosave->datavenda = $request->input('datavenda');
        $tosave->idvendedor = $request->input('idvendedor');
        $tosave->save();
        return redirect('listarvendas');
    }
}
