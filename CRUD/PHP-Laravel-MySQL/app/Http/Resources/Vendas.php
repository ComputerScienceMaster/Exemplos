<?php

namespace App\Http\Resources;

use Illuminate\Http\Resources\Json\JsonResource;

class Vendas extends JsonResource
{
    /**
     * Transform the resource into an array.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return array
     */
    public function toArray($request){
        return [
            'id' => $this->id,
            'valor' => $this->valor,
            'datavenda' => $this->datavenda,
            'idvendedor' => $this->idvendedor
        ];
    }
}
